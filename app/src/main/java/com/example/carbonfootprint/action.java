package com.example.carbonfootprint;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.vishnusivadas.advanced_httpurlconnection.FetchData;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import smile.clustering.KMeans;

public class action extends Fragment {
    private TextView textView;

    int userClusterId;

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
                        if (entry.getKey() == 1) {
                            userClusterId = entry.getValue();
                        }
                    }

                    ClusterAnalysis analysis = new ClusterAnalysis(kmeans.centroids,userClusterId);
                    Map<String,String> userCategoryAction = analysis.compareWithMaxValues();

                    StringBuilder output = new StringBuilder();
                    userCategoryAction.forEach((question, category) -> {
                        output.append(question).append(": High\n");
                    });


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
