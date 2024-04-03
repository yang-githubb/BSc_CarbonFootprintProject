package com.example.carbonfootprint;

import android.graphics.Color;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class profile extends Fragment {

    // Parameters
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

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

        PieChart pieChart = view.findViewById(R.id.pieChart); // Use view.findViewById here
        ArrayList<PieEntry> visitors = new ArrayList<>();
        // Add visitors data
        visitors.add(new PieEntry(508, "2016"));
        visitors.add(new PieEntry(508,2016));
        visitors.add(new PieEntry(600,2017));
        visitors.add(new PieEntry(203,2018));
        visitors.add(new PieEntry(807,2016));
        visitors.add(new PieEntry(690,2020));
        visitors.add(new PieEntry(453,2016));
        visitors.add(new PieEntry(576,2018));
        visitors.add(new PieEntry(768,2019));
        visitors.add(new PieEntry(567,2019));

        PieDataSet pieDataSet = new PieDataSet(visitors, "Visitors");
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieDataSet.setValueTextColor(Color.BLACK);
        pieDataSet.setValueTextSize(16f);

        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.getDescription().setEnabled(false);
        pieChart.setCenterText("Visitors");
        pieChart.animate();
    }
}
