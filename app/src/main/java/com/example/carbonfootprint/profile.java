package com.example.carbonfootprint;

import android.app.MediaRouteButton;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.vishnusivadas.advanced_httpurlconnection.FetchData;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class profile extends Fragment {

    // Parameters
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = profile.class.getSimpleName();

    private String mParam1;
    private String mParam2;
    int userId = 1;
    ProgressBar progressBar;


    public profile() {
        // Required empty public constructor
    }

    public static profile newInstance(String param1, String param2) {
        profile fragment = new profile();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(() -> {
            String[] field = new String[1];
            field[0] = "userId";
            String[] data = new String[1];
            data[0] = "123";
            FetchData fetchData = new FetchData("http://10.100.18.9/CarbonFootprintFYP/carbCalc.php?userId=1");
            if (fetchData.startFetch()) {
                if (fetchData.onComplete()) {
                    String result = fetchData.getResult();
                    Log.d(TAG, "Response: '" + result + "'");
                    Log.d(TAG, "Trimmed length: " + result.trim().length());
                    //End ProgressBar (Set visibility to GONE)
                    Log.i("FetchData", result);
                }
            }

        });
//        PieChart pieChart = view.findViewById(R.id.pieChart); // Use view.findViewById here
//        ArrayList<PieEntry> visitors = new ArrayList<>();
//        // Add visitors data
//        visitors.add(new PieEntry(508, "2016"));
//        visitors.add(new PieEntry(508,2016));
//        visitors.add(new PieEntry(600,2017));
//        visitors.add(new PieEntry(203,2018));
//        visitors.add(new PieEntry(807,2016));
//        visitors.add(new PieEntry(690,2020));
//        visitors.add(new PieEntry(453,2016));
//        visitors.add(new PieEntry(576,2018));
//        visitors.add(new PieEntry(768,2019));
//        visitors.add(new PieEntry(567,2019));
//
//        PieDataSet pieDataSet = new PieDataSet(visitors, "Visitors");
//        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
//        pieDataSet.setValueTextColor(Color.BLACK);
//        pieDataSet.setValueTextSize(16f);
//
//        PieData pieData = new PieData(pieDataSet);
//        pieChart.setData(pieData);
//        pieChart.getDescription().setEnabled(false);
//        pieChart.setCenterText("Visitors");
//        pieChart.animate();

    }
}
