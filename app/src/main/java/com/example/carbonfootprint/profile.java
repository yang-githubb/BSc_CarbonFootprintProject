package com.example.carbonfootprint;

import static java.lang.Math.round;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.anastr.speedviewlib.ImageLinearGauge;
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

public class profile extends Fragment {

    double carbonfootprint_amount = 0;
    double electricity_amount = 0;
    double fuel_amount = 0;
    double waste_amount = 0;


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
        ImageLinearGauge imageLinearGauge = view.findViewById(R.id.speedView);
        imageLinearGauge.speedTo(50, 4000);
        imageLinearGauge.setWithTremble(false);

        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(() -> {
            FetchData fetchData = new FetchData("http://192.168.100.4/CarbonFootprintFYP/carbCalc.php?userId=1");
            if (fetchData.startFetch()) {
                if (fetchData.onComplete()) {
                    String result = fetchData.getResult();
                    try {
                        JSONArray jsonArray = new JSONArray(result);

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            String question_id = jsonObject.getString("question_id");
                            String optionIndex = jsonObject.getString("option_index");

                            double electricity_emmisionfactor = 0.758;
                            double fuel_emmisionfactor = 2.34502;
                            double waste_emmisionfactor = 0.497;

                            switch (question_id) {
                                case "3":
                                    switch (optionIndex) {
                                        case "1":
                                            carbonfootprint_amount += 600 * electricity_emmisionfactor;
                                            electricity_amount += 600 * electricity_emmisionfactor;
                                            break;
                                        case "2":
                                            carbonfootprint_amount += 1800 * electricity_emmisionfactor;
                                            electricity_amount += 1800 * electricity_emmisionfactor;
                                            break;
                                        case "3":
                                            carbonfootprint_amount += 3000 * electricity_emmisionfactor;
                                            electricity_amount += 3000 * electricity_emmisionfactor;
                                            break;
                                        case "4":
                                            carbonfootprint_amount += 5400 * electricity_emmisionfactor;
                                            electricity_amount += 5400 * electricity_emmisionfactor;
                                            break;
                                        case "5":
                                            carbonfootprint_amount += 12000 * electricity_emmisionfactor;
                                            electricity_amount += 12000 * electricity_emmisionfactor;
                                            break;
                                    }
                                    break;
                                case "6":
                                    switch (optionIndex) {
                                        case "1":
                                            carbonfootprint_amount += 6000 * waste_emmisionfactor;
                                            waste_amount += 6000 * waste_emmisionfactor;
                                            break;
                                        case "2":
                                            carbonfootprint_amount += 30000 * waste_emmisionfactor;
                                            waste_amount += 30000 * waste_emmisionfactor;
                                            break;
                                        case "3":
                                            carbonfootprint_amount += 90000 * waste_emmisionfactor;
                                            waste_amount += 90000 * waste_emmisionfactor;
                                            break;
                                    }
                                    break;
                                case "15":
                                    switch (optionIndex) {
                                        case "1":
                                            carbonfootprint_amount += 171 * fuel_emmisionfactor;
                                            fuel_amount += 171 * fuel_emmisionfactor;
                                            break;
                                        case "2":
                                            carbonfootprint_amount += 514 * fuel_emmisionfactor;
                                            fuel_amount += 514 * fuel_emmisionfactor;
                                            break;
                                        case "3":
                                            carbonfootprint_amount += 857 * fuel_emmisionfactor;
                                            fuel_amount += 857 * fuel_emmisionfactor;
                                            break;
                                        case "4":
                                            carbonfootprint_amount += 1200 * fuel_emmisionfactor;
                                            fuel_amount += 1200 * fuel_emmisionfactor;
                                            break;
                                        case "5":
                                            carbonfootprint_amount += 1714 * fuel_emmisionfactor;
                                            fuel_amount += 1714 * fuel_emmisionfactor;
                                            break;
                                    }
                                    break;
                            }
                        }
                        PieChart pieChart = view.findViewById(R.id.pieChart);
                        ArrayList<PieEntry> visitors = new ArrayList<>();
                        visitors.add(new PieEntry(round(electricity_amount), "Electricity Amount"));
                        visitors.add(new PieEntry(round(fuel_amount), "Fuel Amount"));
                        visitors.add(new PieEntry(round(waste_amount), "Waste Amount"));
                        PieDataSet pieDataSet = new PieDataSet(visitors, "Total Emission Amount");
                        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                        pieDataSet.setValueTextColor(Color.BLACK);
                        pieDataSet.setValueTextSize(16f);
                        PieData pieData = new PieData(pieDataSet);
                        pieChart.setData(pieData);
                        pieChart.getDescription().setEnabled(false);
                        pieChart.invalidate();
                        pieChart.setCenterText("Carbon Footprint");
                        pieChart.animate();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
