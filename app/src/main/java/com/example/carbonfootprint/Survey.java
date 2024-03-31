package com.example.carbonfootprint;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Survey extends AppCompatActivity {

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
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setupListeners();

        Button submitButton = findViewById(R.id.submit_button);
        //submitButton.setOnClickListener(v -> insertAnswersIntoDatabase());
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Survey.this, MainPage.class);
                startActivity(intent);
            }
        });
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

            String sql = "INSERT INTO answers (user_id, question_id, option_id) VALUES ("
                    + userId + ", " + questionId + ", " + optionId + ");";

        }
    }
}