package com.example.carbonfootprint;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.vishnusivadas.advanced_httpurlconnection.FetchData;
import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import smile.clustering.KMeans;

public class action extends Fragment {

    private static final String TAG = action.class.getSimpleName();
    private TextView textView;

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
                        // Ensure the list is long enough to handle the current questionId index
                        while (answers.size() <= questionId) {
                            answers.add(0);  // Initialize with a default value of 0
                        }
                        answers.set(questionId, answer);  // Set the answer at the appropriate index
                    }

                    List<Integer> keys = new ArrayList<>(userData.keySet());
                    double[][] dataMatrix = new double[userData.size()][];
                    for (int i = 0; i < keys.size(); i++) {
                        List<Integer> answers = userData.get(keys.get(i));
                        assert answers != null;
                        dataMatrix[i] = answers.stream().mapToDouble(Integer::doubleValue).toArray();
                    }

                    PearsonsCorrelation correlation = new PearsonsCorrelation();
                    double[][] correlationMatrix = correlation.computeCorrelationMatrix(dataMatrix).getData();

                    KMeans kmeans = KMeans.fit(dataMatrix, 3);
                    int[] labels = kmeans.y;

                    Map<Integer, Integer> userClusterMap = new HashMap<>();
                    for (int i = 0; i < keys.size(); i++) {
                        userClusterMap.put(keys.get(i), labels[i]);
                    }

                    for (Map.Entry<Integer, Integer> entry : userClusterMap.entrySet()) {
                        Log.d(TAG, "User ID: " + entry.getKey() + " belongs to Cluster: " + entry.getValue());
                    }


                    displayClusterCharacteristics(kmeans.centroids);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    String[] questionDescriptions = {
            "Number of people in household",
            "Primary source of energy",
            "Monthly energy consumption (approx.)",
            "Daily bathing method",
            "Laundry frequency",
            "Weekly waste production (kg)",
            "Grocery shopping location",
            "Weekly grocery spending",
            "Weekly restaurant visits",
            "Leftover food packing habit in restaurants",
            "Handling of unfinished prepared food",
            "Tendency to finish food on plate",
            "Frequency of bringing own bag for shopping",
            "Ownership of Hybrid/Electric vehicle",
            "Weekly fuel consumption",
            "Travel mode to school",
            "Most used means of transport"
    };

    public void displayClusterCharacteristics(double[][] centroids) {
        StringBuilder info = new StringBuilder();
        for (int i = 0; i < centroids.length; i++) {
            info.append("Cluster ").append(i + 1).append(" characteristics:\n");
            for (int j = 0; j < centroids[i].length && j < questionDescriptions.length; j++) {
                info.append(questionDescriptions[j])
                        .append(": ")
                        .append(String.format("%.2f", centroids[i][j]))
                        .append("\n");
            }
            info.append("\n");
        }
        textView.setText(info);
    }

}
