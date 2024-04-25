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

import org.apache.commons.math3.ml.clustering.*;
import org.apache.commons.math3.ml.clustering.CentroidCluster;

import com.vishnusivadas.advanced_httpurlconnection.FetchData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import smile.clustering.KMeans;

public class action extends Fragment {
    TextView textView;
    int userClusterId;
    private LinearLayoutManager layoutManager;

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
        try {
            super.onViewCreated(view, savedInstanceState);
            RecyclerView recyclerView = view.findViewById(R.id.rvHorizontalCards);
            layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
            recyclerView.setLayoutManager(layoutManager);
            List<DataItem> texts = null;
            texts = getActions();

            Log.d("fegerg", texts.toString());
            CustomAdapter adapter = new CustomAdapter(texts);
            recyclerView.setAdapter(adapter);
            textView = view.findViewById(R.id.textViewClusterInfo);

            fetchDataAndCluster();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    private void fetchDataAndCluster() {
        FetchData fetchData = new FetchData("http://192.168.100.4/CarbonFootprintFYP/clusterCalc.php");
        if (fetchData.startFetch()) {
            if (fetchData.onComplete()) {
                String result = fetchData.getResult();
                try {
                    JSONArray jsonArray = new JSONArray(result);
                    Map<Integer, List<Integer>> userData = new HashMap<>();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        int userId = obj.getInt("user_id");
                        int questionId = obj.getInt("question_id");
                        int answer = obj.getInt("option_index");

                        List<Integer> answers = userData.computeIfAbsent(userId, k -> new ArrayList<>());
                        while (answers.size() <= questionId) {
                            answers.add(0);
                        }
                        answers.set(questionId, answer);
                    }

                    List<Integer> keys = new ArrayList<>(userData.keySet());
                    double[][] dataMatrix = new double[userData.size()][];
                    for (int i = 0; i < keys.size(); i++) {
                        List<Integer> answers = userData.get(keys.get(i));
                        assert answers != null;
                        dataMatrix[i] = answers.stream().mapToDouble(Integer::doubleValue).toArray();
                    }

                    KMeans kmeans = KMeans.fit(dataMatrix, 3);
                    int[] labels = kmeans.y;

                    Map<Integer, Integer> userClusterMap = new HashMap<>();
                    for (int i = 0; i < keys.size(); i++) {
                        userClusterMap.put(keys.get(i), labels[i]);
                    }

                    StringBuilder output = getStringBuilder(kmeans);
                    textView.setText(output);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @NonNull
    private StringBuilder getStringBuilder(KMeans kmeans) {
        ClusterAnalysis analysis = new ClusterAnalysis(kmeans.centroids, userClusterId);
        Map<String, String> userCategoryAction;
        userCategoryAction = analysis.compareWithMaxValues();

        Map<String, String> feedbackMap = new HashMap<>();

        feedbackMap.put("How many people live in your household?", "You should minimize energy consumption and adopt sustainable lifestyles.");
        feedbackMap.put("What is the source of energy?", "You should explore renewable energy sources to reduce environmental impact.");
        feedbackMap.put("How much energy do you approximately consume monthly?", "You should aim to reduce their monthly energy consumption.");
        feedbackMap.put("How do you take a bath on daily basis?", "You should consider shorter showers to save water and energy.");
        feedbackMap.put("How often do you do laundry?", "You should use full loads and cold water to save energy.");
        feedbackMap.put("How much waste you throw per week? (In kg)", "You should aim to reduce waste through recycling and composting.");
        feedbackMap.put("Where do you usually purchase groceries?", "You should buy local to reduce their carbon footprint.");
        feedbackMap.put("How much do you usually spend on groceries weekly?", "You should plan meals to minimize waste and save money.");
        feedbackMap.put("How frequently do you eat at a restaurant on a weekly basis?", "You should reduce dining out to save money and reduce food waste.");
        feedbackMap.put("Do you pack the leftover food in a restaurant when you have not finished eating?", "You should always take leftovers home to avoid waste.");
        feedbackMap.put("If the food that you have prepared is not finished, what do you do with it?", "You should properly store leftovers for future consumption.");
        feedbackMap.put("Will you try your best to finish the food on your plate?", "You should try to finish the food on their plate to reduce waste.");
        feedbackMap.put("How frequently do you bring your bag whenever you go shopping?", "You should use reusable bags to reduce plastic waste.");
        feedbackMap.put("Do you or your family member own a Hybrid or electric vehicle?", "You should consider the benefits of hybrid or electric vehicles.");
        feedbackMap.put("How many fuel consumption on weekly basis?", "You should reduce their weekly fuel consumption to save costs and decrease environmental impact.");
        feedbackMap.put("How do you go to school?", "You should use public transport or carpool to reduce pollution.");
        feedbackMap.put("What means of transport do you use the most?", "You should opt for eco-friendlier modes of transportation to reduce their carbon footprint.");

        StringBuilder output = new StringBuilder();
        userCategoryAction.forEach((question, category) -> {
            String feedback = feedbackMap.get(question);
            if (feedback != null) {
                output.append("\u2022 ").append(feedback).append("\n");
            }
        });
        return output;
    }

    private List<DataItem> getActions() throws JSONException {
        List<DataItem> recommendActions = new ArrayList<>();
        FetchData fetchData = new FetchData("http://192.168.100.4/CarbonFootprintFYP/clusterCalc.php");
        if (fetchData.startFetch()) {
            if (fetchData.onComplete()) {
                String result = fetchData.getResult();
                try {
                    JSONArray jsonArray = new JSONArray(result);
                    Map<Integer, List<Integer>> userData = new HashMap<>();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        int userId = obj.getInt("user_id");
                        int questionId = obj.getInt("question_id");
                        int answer = obj.getInt("option_index");

                        List<Integer> answers = userData.computeIfAbsent(userId, k -> new ArrayList<>());
                        while (answers.size() <= questionId) {
                            answers.add(0);
                        }
                        answers.set(questionId, answer);
                    }

                    List<Integer> keys = new ArrayList<>(userData.keySet());
                    double[][] dataMatrix = new double[userData.size()][];
                    for (int i = 0; i < keys.size(); i++) {
                        List<Integer> answers = userData.get(keys.get(i));
                        assert answers != null;
                        dataMatrix[i] = answers.stream().mapToDouble(Integer::doubleValue).toArray();
                    }

                    KMeans kmeans = KMeans.fit(dataMatrix, 3);
                    ClusterAnalysis analysis = new ClusterAnalysis(kmeans.centroids, userClusterId);
                    Map<String, String> userCategoryAction = analysis.compareWithMaxValues();
                    JSONObject jsonObject = new JSONObject();
                    for (Map.Entry<String, String> entry : userCategoryAction.entrySet()) {
                        jsonObject.put(entry.getKey(), entry.getValue());
                    }
                    FetchData fd = new FetchData("http://192.168.100.4/CarbonFootprintFYP/getAction.php?userQues=" + jsonObject);
                    Log.d("1", String.valueOf(jsonObject));
                    if (fd.startFetch()) {
                        if (fd.onComplete()) {
                            String result1 = fd.getResult();
                            Log.d("2", result1);
                            String[] recommendations = result1.split("\\), \\(");

                            for (String recommendation : recommendations) {
                                recommendation = recommendation.replaceAll("[()']", "");
                                recommendation = recommendation.replaceAll("\\[", "").replaceAll("\\]","");

                                // Split the string at the first comma only
                                String[] parts = recommendation.split(", ", 2);
                                if (parts.length == 2) {
                                    String actionName = parts[0].trim(); // Trim any leading or trailing spaces
                                    String actionDescription = parts[1].trim(); // Ensure description is clean
                                    recommendActions.add(new DataItem(actionName, actionDescription));
                                    Log.d("SplitDebug", "Action: " + actionName + ", Description: " + actionDescription);
                                }
                            }
                        }
                    }
                    return recommendActions;
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return recommendActions;
    }
}

class DataItem {
    private String name;
    private String description;

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