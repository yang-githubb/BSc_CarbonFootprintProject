package com.example.carbonfootprint;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.vishnusivadas.advanced_httpurlconnection.FetchData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class profile extends Fragment {

    private static final String TAG = "ProfileFragment";

    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public profile() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView usernametextview = view.findViewById(R.id.textUsername);
        usernametextview.setText(getString(R.string.profile_greeting, Session.getUsername()));

        executor.execute(() -> {
            FetchData fetchData = new FetchData(ApiConfig.CARB_CALC_URL + "?token=" + Session.getToken());
            if (fetchData.startFetch() && fetchData.onComplete()) {
                String result = fetchData.getResult();
                try {
                    JSONArray jsonArray = new JSONArray(result);
                    CarbonCalculator.Breakdown breakdown = new CarbonCalculator.Breakdown();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        breakdown.add(jsonObject.getInt("question_id"), jsonObject.getInt("option_index"));
                    }
                    Session.setLastFootprintTonnes(breakdown.getTotalTonnes());
                    if (isAdded()) {
                        requireActivity().runOnUiThread(() -> showBreakdown(view, breakdown));
                    }
                } catch (JSONException e) {
                    Log.e(TAG, "Failed to parse footprint data", e);
                }
            }
        });
    }

    private void showBreakdown(View view, CarbonCalculator.Breakdown breakdown) {
        if (!isAdded()) {
            return;
        }
        PieChart pieChart = view.findViewById(R.id.pieChart);
        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(round2(breakdown.getElectricityTonnes()), "Electricity"));
        entries.add(new PieEntry(round2(breakdown.getFuelTonnes()), "Fuel"));
        entries.add(new PieEntry(round2(breakdown.getWasteTonnes()), "Waste"));

        PieDataSet pieDataSet = getPieDataSet(entries, pieChart);
        pieChart.setData(new PieData(pieDataSet));
        pieChart.getDescription().setEnabled(false);
        pieChart.setCenterText("Total Carbon Footprint\n(tCO2e)");
        pieChart.invalidate();
        pieChart.animateY(1000, Easing.EaseInOutQuad);

        TextView totalcarbon = view.findViewById(R.id.total_text);
        totalcarbon.setText(String.format(Locale.getDefault(),
                "Your carbon footprint: %.2f tCO2e (tons of CO2 equivalent).",
                breakdown.getTotalTonnes()));
    }

    private static float round2(double value) {
        return (float) (Math.round(value * 100.0) / 100.0);
    }

    @NonNull
    private static PieDataSet getPieDataSet(ArrayList<PieEntry> entries, PieChart pieChart) {
        PieDataSet pieDataSet = new PieDataSet(entries, "");
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieDataSet.setValueTextColor(Color.BLACK);
        pieChart.setEntryLabelColor(Color.BLACK);
        pieDataSet.setValueTextSize(16f);
        pieDataSet.setSliceSpace(3f);

        pieDataSet.setDrawValues(true);
        pieDataSet.setValueLineColor(Color.GRAY);
        pieDataSet.setValueLinePart1OffsetPercentage(30.f);
        pieDataSet.setValueLinePart1Length(0.2f);
        pieDataSet.setValueLinePart2Length(0.2f);
        pieDataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        pieDataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        return pieDataSet;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        executor.shutdownNow();
    }
}
