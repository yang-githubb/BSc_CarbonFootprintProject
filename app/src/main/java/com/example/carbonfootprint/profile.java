package com.example.carbonfootprint;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.vishnusivadas.advanced_httpurlconnection.FetchData;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class profile extends Fragment {

    // Parameters
    private static final String TAG = profile.class.getSimpleName();

    double carbonfootprint_amount=0;


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
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(() -> {
            String[] field = new String[1];
            field[0] = "userId";
            String[] data = new String[1];
            data[0] = "123";
            FetchData fetchData = new FetchData("http://192.168.100.4/CarbonFootprintFYP/carbCalc.php?userId=1");
            if (fetchData.startFetch()) {
                if (fetchData.onComplete()) {
                    String result = fetchData.getResult();
                    try {
                        // Parse the JSON string to a JSONArray
                        JSONArray jsonArray = new JSONArray(result);

                        // Iterate over each JSONObject in the JSONArray
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            // Extract and log values from the current JSONObject
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
                                            break;
                                        case "2":
                                            carbonfootprint_amount += 1800 * electricity_emmisionfactor;
                                            break;
                                        case "3":
                                            carbonfootprint_amount += 3000 * electricity_emmisionfactor;
                                            break;
                                        case "4":
                                            carbonfootprint_amount += 5400 * electricity_emmisionfactor;
                                            break;
                                        case "5":
                                            carbonfootprint_amount += 12000 * electricity_emmisionfactor;
                                            break;
                                    }
                                    break;
                                case "6":
                                    switch (optionIndex) {
                                        case "1":
                                            carbonfootprint_amount += 6000 * waste_emmisionfactor;
                                            break;
                                        case "2":
                                            carbonfootprint_amount += 30000 * waste_emmisionfactor;
                                            break;
                                        case "3":
                                            carbonfootprint_amount += 90000 * waste_emmisionfactor;
                                            break;
                                    }
                                    break;
                                case "15":
                                    switch (optionIndex) {
                                        case "1":
                                            carbonfootprint_amount += 171 * fuel_emmisionfactor;
                                            break;
                                        case "2":
                                            carbonfootprint_amount += 514 * fuel_emmisionfactor;
                                            break;
                                        case "3":
                                            carbonfootprint_amount += 857 * fuel_emmisionfactor;
                                            break;
                                        case "4":
                                            carbonfootprint_amount += 1200 * fuel_emmisionfactor;
                                            break;
                                        case "5":
                                            carbonfootprint_amount += 1714 * fuel_emmisionfactor;
                                            break;
                                    }
                                    break;
                            }
                        }
                        Log.d(TAG, "Carbon Footprint=" + carbonfootprint_amount);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
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
