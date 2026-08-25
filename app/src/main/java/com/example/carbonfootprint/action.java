package com.example.carbonfootprint;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vishnusivadas.advanced_httpurlconnection.FetchData;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import smile.clustering.KMeans;

public class action extends Fragment {

    private static final String TAG = "ActionFragment";
    private static final int QUESTION_COUNT = 17;
    private static final int MAX_CLUSTERS = 3;

    private TextView textView;
    private RecyclerView recyclerView;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public action() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_action, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.rvHorizontalCards);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        textView = view.findViewById(R.id.textViewClusterInfo);

        executor.execute(this::loadRecommendations);
    }

    /**
     * Fetches every user's answers, clusters them, finds the questions the
     * logged-in user's cluster performs worst on, and asks the backend for
     * recommended actions for those questions.
     */
    private void loadRecommendations() {
        FetchData fetchData = new FetchData(ApiConfig.CLUSTER_CALC_URL + "?token=" + Session.getToken());
        if (!fetchData.startFetch() || !fetchData.onComplete()) {
            return;
        }
        try {
            Map<Integer, double[]> userAnswers = parseAnswers(fetchData.getResult());
            if (userAnswers.isEmpty() || !userAnswers.containsKey(Session.getUserId())) {
                Log.w(TAG, "No survey answers found for the current user");
                return;
            }

            List<Integer> userIds = new ArrayList<>(userAnswers.keySet());
            double[][] dataMatrix = new double[userIds.size()][];
            for (int i = 0; i < userIds.size(); i++) {
                dataMatrix[i] = userAnswers.get(userIds.get(i));
            }

            double[][] centroids;
            int userClusterId;
            if (userIds.size() < 2) {
                // Not enough users to cluster: treat the lone user as their own cluster.
                centroids = dataMatrix;
                userClusterId = 0;
            } else {
                int k = Math.min(MAX_CLUSTERS, userIds.size());
                KMeans kmeans = KMeans.fit(dataMatrix, k);
                centroids = kmeans.centroids;
                userClusterId = kmeans.y[userIds.indexOf(Session.getUserId())];
            }

            ClusterAnalysis analysis = new ClusterAnalysis(centroids, userClusterId);
            Map<String, String> userCategoryAction = analysis.compareWithMaxValues();

            String feedback = buildFeedback(userCategoryAction);
            List<DataItem> recommendations = fetchRecommendedActions(userCategoryAction);

            if (isAdded()) {
                requireActivity().runOnUiThread(() -> {
                    if (!isAdded()) {
                        return;
                    }
                    textView.setText(feedback);
                    recyclerView.setAdapter(new CustomAdapter(recommendations));
                });
            }
        } catch (Exception e) {
            Log.e(TAG, "Failed to load recommendations", e);
        }
    }

    /** Parses the answers table into one row of 17 option indexes per user. */
    private Map<Integer, double[]> parseAnswers(String json) throws Exception {
        JSONArray jsonArray = new JSONArray(json);
        Map<Integer, double[]> userAnswers = new HashMap<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            int userId = obj.getInt("user_id");
            int questionId = obj.getInt("question_id");
            int answer = obj.getInt("option_index");
            if (questionId < 1 || questionId > QUESTION_COUNT) {
                continue;
            }
            double[] answers = userAnswers.get(userId);
            if (answers == null) {
                answers = new double[QUESTION_COUNT];
                userAnswers.put(userId, answers);
            }
            answers[questionId - 1] = answer;
        }
        return userAnswers;
    }

    /** Asks the backend recommender for actions matching the flagged questions. */
    private List<DataItem> fetchRecommendedActions(Map<String, String> userCategoryAction) {
        List<DataItem> recommendActions = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject();
            for (Map.Entry<String, String> entry : userCategoryAction.entrySet()) {
                jsonObject.put(entry.getKey(), entry.getValue());
            }
            String url = ApiConfig.GET_ACTION_URL
                    + "?token=" + Session.getToken()
                    + "&userQues=" + URLEncoder.encode(jsonObject.toString(), "UTF-8");
            FetchData fd = new FetchData(url);
            if (fd.startFetch() && fd.onComplete()) {
                JSONArray recommendations = new JSONArray(fd.getResult());
                for (int i = 0; i < recommendations.length(); i++) {
                    JSONObject recommendation = recommendations.getJSONObject(i);
                    recommendActions.add(new DataItem(
                            recommendation.getString("name"),
                            recommendation.getString("description")));
                }
            }
        } catch (UnsupportedEncodingException e) {
            throw new AssertionError(e);
        } catch (Exception e) {
            Log.e(TAG, "Failed to fetch recommended actions", e);
        }
        return recommendActions;
    }

    @NonNull
    private static String buildFeedback(Map<String, String> userCategoryAction) {
        Map<String, String> feedbackMap = new HashMap<>();

        feedbackMap.put("How many people live in your household?", "Try to minimize your home's electricity usage.");
        feedbackMap.put("What is the source of energy?", "Explore renewable energy options for your home.");
        feedbackMap.put("How much energy do you approximately consume monthly?", "Your monthly energy usage is on the high side - look for ways to cut back.");
        feedbackMap.put("How do you take a bath on daily basis?", "Consider shorter showers to save water and energy.");
        feedbackMap.put("How often do you do laundry?", "Wait for a full load before doing laundry to save water and energy.");
        feedbackMap.put("How much waste you throw per week? (In kg)", "Try recycling and composting to reduce your waste.");
        feedbackMap.put("Where do you usually purchase groceries?", "Buying local goods helps reduce your carbon footprint.");
        feedbackMap.put("How much do you usually spend on groceries weekly?", "Plan your meal portions to minimize waste and save money.");
        feedbackMap.put("How frequently do you eat at a restaurant on a weekly basis?", "Cooking at home reduces both your carbon footprint and your expenses.");
        feedbackMap.put("Do you pack the leftover food in a restaurant when you can't finish?", "Order less or pack the leftovers when you cannot finish your food.");
        feedbackMap.put("If the food that you have prepared is not finished, what will you do?", "Leftovers can be kept for later or turned into compost.");
        feedbackMap.put("Will you try your best to finish the food on your plate?", "Try to finish the food on your plate to reduce waste.");
        feedbackMap.put("How frequently do you bring your bag whenever you go shopping?", "Bring a reusable bag whenever you shop to reduce plastic waste.");
        feedbackMap.put("Do you or your family member own a Hybrid or electric car?", "A hybrid or electric vehicle would greatly cut your transport emissions.");
        feedbackMap.put("How many fuel consumption on weekly basis?", "Try public transport - you might make new friends!");
        feedbackMap.put("How do you go to school?", "The school bus or a carpool is a greener way to get to school.");
        feedbackMap.put("What means of transport do you use the most?", "Consider eco-friendlier modes of transportation to reduce your carbon footprint.");

        StringBuilder output = new StringBuilder();
        userCategoryAction.forEach((question, category) -> {
            String feedback = feedbackMap.get(question);
            if (feedback != null) {
                output.append("• ").append(feedback).append("\n");
            }
        });
        if (output.length() == 0) {
            output.append("Great job! Your answers already compare well with other users.");
        }
        return output.toString();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        executor.shutdownNow();
    }
}

class DataItem {
    private final String name;
    private final String description;

    public DataItem(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getTitle() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
