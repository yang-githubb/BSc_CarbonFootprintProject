package com.example.carbonfootprint;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vishnusivadas.advanced_httpurlconnection.FetchData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.rvHorizontalCards);
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        List<DataItem> texts = getActions();

        CustomAdapter adapter = new CustomAdapter(texts);
        recyclerView.setAdapter(adapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), layoutManager.getOrientation());
        Drawable dividerDrawable = ContextCompat.getDrawable(getContext(), R.drawable.divider);
        dividerItemDecoration.setDrawable(dividerDrawable);
        recyclerView.addItemDecoration(dividerItemDecoration);
        textView = view.findViewById(R.id.textViewClusterInfo);

        fetchDataAndCluster();
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

                    for (Map.Entry<Integer, Integer> entry : userClusterMap.entrySet()) {
                        int userId = Integer.parseInt(Login.user_Id);
                        if (entry.getKey() == userId) {
                            Log.d("eikugfwei", String.valueOf(entry.getValue()));
                            userClusterId = entry.getValue();
                        }
                    }

                    ClusterAnalysis analysis = new ClusterAnalysis(kmeans.centroids, userClusterId);
                    Map<String, String> userCategoryAction = analysis.compareWithMaxValues();


                    StringBuilder output = new StringBuilder();
                    userCategoryAction.forEach((question, category) -> {
                        output.append(question).append("\n");
                    });

                    textView.setText(output);

                    StringBuilder output1 = new StringBuilder("User cluster group: ").append(userClusterId).append("\nCluster Characteristics:\n");
                    for (int i = 0; i < kmeans.centroids.length; i++) {
                        output1.append("Cluster ").append(i + 1).append(":\n");
                        for (int j = 0; j < kmeans.centroids[i].length; j++) {
                            output1.append("Q").append(j + 1).append(": ").append(String.format("%.2f", kmeans.centroids[i][j])).append("\n");
                        }
                        output1.append("\n");
                    }
                    Log.d("uigiuy", String.valueOf(output1));

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private List<DataItem> getActions() {
        FetchData fetchData = new FetchData("http://192.168.100.4/CarbonFootprintFYP/getAction.php");
        List<DataItem> recommendAction = new ArrayList<>();
        if (fetchData.startFetch()) {
            if (fetchData.onComplete()) {
                String result = fetchData.getResult();
                try {
                    JSONArray jsonArray = new JSONArray(result);

                    for (int i = 0; i < 5; i++) {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        String action_name = obj.getString("action_name");
                        String action_description = obj.getString("action_description");

                        recommendAction.add(new DataItem(action_name, action_description));
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return recommendAction;
    }
}
