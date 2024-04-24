package com.example.carbonfootprint;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
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

    public double carbonfootprint_amount = 0;
    double electricity_amount = 0;
    double fuel_amount = 0;
    double waste_amount = 0;

    public static double total_amount;


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

        String userid = Login.user_Id;
        String username = Login.username;
        TextView usernametextview = view.findViewById(R.id.textUsername);
        String greets = "Hi! " + username;
        usernametextview.setText(greets);
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(() -> {
            FetchData fetchData = new FetchData("http://192.168.100.4/CarbonFootprintFYP/carbCalc.php?userId=" + userid);
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
                        total_amount = carbonfootprint_amount / 1000;
                        double carbonfootprint_val = carbonfootprint_amount / 1000;
                        float carbon_footprint_rounded = (float) (Math.round(carbonfootprint_val * 100.0) / 100.0);

                        double elec_val = electricity_amount / 1000;
                        float electricity_amount_rounded = (float) (Math.round(elec_val * 100.0) / 100.0);
                        double fuel_val = fuel_amount / 1000;
                        float fuel_amount_rounded = (float) (Math.round(fuel_val * 100.0) / 100.0);

                        double waste_value = waste_amount / 1000;
                        float waste_rounded = (float) (Math.round(waste_value * 100.0) / 100.0);

                        PieChart pieChart = view.findViewById(R.id.pieChart);
                        ArrayList<PieEntry> visitors = new ArrayList<>();
                        visitors.add(new PieEntry(electricity_amount_rounded, "Electricity Amount"));
                        visitors.add(new PieEntry(fuel_amount_rounded, "Fuel Amount"));
                        visitors.add(new PieEntry(waste_rounded, "Waste Amount"));
                        PieDataSet pieDataSet = getPieDataSet(visitors, pieChart);
                        PieData pieData = new PieData(pieDataSet);
                        pieChart.setData(pieData);
                        pieChart.getDescription().setEnabled(false);
                        pieChart.invalidate();
                        pieChart.setCenterText("Total Carbon Footprint\n(tCO2e)");
                        Description description = new Description();
                        description.setText("*tCO2e is Tons of Carbon Dioxide equivalent");
                        description.setTextColor(Color.BLACK);
                        description.setTextSize(10f);
                        description.setPosition(545f, 560f);
                        pieChart.setDescription(description);
                        pieChart.getDescription().setEnabled(true);
                        pieChart.animateY(1000, Easing.EaseInOutQuad);

                        TextView totalcarbon = view.findViewById(R.id.total_text);
                        String text = "Your carbon footprint: " + carbon_footprint_rounded + "tCO2e.";
                        totalcarbon.setText(text);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @NonNull
    private static PieDataSet getPieDataSet(ArrayList<PieEntry> visitors, PieChart pieChart) {
        PieDataSet pieDataSet = new PieDataSet(visitors, "");
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
        Description description = new Description();
        description.setText("*tCO2e is Tons of Carbon Dioxide in equivalence");
        pieChart.setDescription(description);
        return pieDataSet;
    }
}
