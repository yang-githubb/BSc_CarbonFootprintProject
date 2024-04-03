package com.example.carbonfootprint;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class Survey extends AppCompatActivity {

    private static final String TAG = Survey.class.getSimpleName();

    private final int[] radioGroupIDs = new int[]{
            R.id.question1RadioGroup,
            R.id.question2RadioGroup,
            R.id.question3RadioGroup,
            R.id.question4RadioGroup,
            R.id.question5RadioGroup,
            R.id.question6RadioGroup,
            R.id.question7RadioGroup,
            R.id.question8RadioGroup,
            R.id.question9RadioGroup,
            R.id.question10RadioGroup,
            R.id.question11RadioGroup,
            R.id.question12RadioGroup,
            R.id.question13RadioGroup,
            R.id.question14RadioGroup,
            R.id.question15RadioGroup,
            R.id.question16RadioGroup,
            R.id.question17RadioGroup
    };
    private final int[] selectedAnswers = new int[radioGroupIDs.length]; // Assuming one answer per RadioGroup


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_survey);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setupListeners();

        Button submitButton = findViewById(R.id.submit_button);
        submitButton.setOnClickListener(v -> insertAnswersIntoDatabase(1,selectedAnswers));
    }

    private void setupListeners() {
        for (int i = 0; i < radioGroupIDs.length; i++) {
            RadioGroup radioGroup = findViewById(radioGroupIDs[i]);
            final int index = i;
            radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
                int radioButtonIndex = group.indexOfChild(findViewById(checkedId));
                selectedAnswers[index] = radioButtonIndex;
            });
        }
    }

    void insertAnswersIntoDatabase(int userId, int[] selectedOptionIds) {
        for (int i = 0; i < selectedOptionIds.length; i++) {
            int questionId = i + 1;
            int optionId = selectedOptionIds[i];

            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(() -> {
                String[] field = new String[3];
                field[0] = "userId";
                field[1] = "questionId";
                field[2] = "optionId";
                String[] data = new String[3];
                data[0] = String.valueOf(userId);
                data[1] = String.valueOf(questionId);
                data[2] = String.valueOf(optionId);
                PutData putData = new PutData("http://192.168.100.4/CarbonFootprintFYP/user_answer.php", "POST", field, data);
                if (putData.startPut()) {
                    if (putData.onComplete()) {
                        String result = putData.getResult();
                        Log.d(TAG,result);
                        if (result.equals("Insert Success")) {
                            Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
//                            Intent intent = new Intent(getApplicationContext(), MainPage.class);
//                            startActivity(intent);
//                            finish();
                        }
                    }
                }
            });
        }
    }
}