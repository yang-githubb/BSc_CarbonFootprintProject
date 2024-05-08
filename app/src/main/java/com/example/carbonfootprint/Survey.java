package com.example.carbonfootprint;

import android.content.Intent;

import com.google.android.material.snackbar.Snackbar;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.ScrollView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Arrays;
import static com.example.carbonfootprint.Login.username;

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
    private final int[] selectedAnswers = new int[radioGroupIDs.length];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_survey);
        boolean receivedValue = getIntent().getBooleanExtra("LOGGEDIN", false);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        setupListeners();

        Button submitButton = findViewById(R.id.submit_button);
        submitButton.setOnClickListener(v -> {
            if (areAllQuestionsAnswered()) {
                insertAnswersIntoDatabase(username, selectedAnswers,receivedValue);
            } else {
                int firstUnansweredId = findFirstUnansweredQuestion();
                scrollToUnansweredQuestion(firstUnansweredId);
                Snackbar.make(findViewById(R.id.main), "Please answer all questions before submitting.", Snackbar.LENGTH_LONG).show();
            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        toolbar.setNavigationOnClickListener(v -> {
            if (receivedValue) {
                Intent intent = new Intent(getApplicationContext(), MainPage.class);
                startActivity(intent);
                finish();
            } else {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }
        });

        Snackbar.make(findViewById(R.id.main), "Welcome to the Survey! Please answer all questions.", Snackbar.LENGTH_LONG).show();
    }

    private void setupListeners() {
        for (int i = 0; i < radioGroupIDs.length; i++) {
            RadioGroup radioGroup = findViewById(radioGroupIDs[i]);
            final int index = i;
            radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
                int radioButtonIndex = group.indexOfChild(findViewById(checkedId));
                if (radioButtonIndex != -1) {
                    selectedAnswers[index] = radioButtonIndex + 1;
                }
            });
        }
    }

    void insertAnswersIntoDatabase(String userId, int[] selectedOptionIds,boolean logged) {
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
                if (logged) {
                    PutData putData = new PutData("http://192.168.100.4/CarbonFootprintFYP/update_answer.php", "POST", field, data);
                    if (putData.startPut()) {
                        if (putData.onComplete()) {
                            String result = putData.getResult();
                            if (result.equals("Update Success")) {
                                Snackbar.make(findViewById(R.id.main), result, Snackbar.LENGTH_LONG).show();
                                Intent intent = new Intent(getApplicationContext(), MainPage.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Snackbar.make(findViewById(R.id.main), "Error: Please try again!", Snackbar.LENGTH_LONG).show();
                            }
                        }
                    }
                } else {
                    PutData putData = new PutData("http://192.168.100.4/CarbonFootprintFYP/user_answer.php", "POST", field, data);
                    if (putData.startPut()) {
                        if (putData.onComplete()) {
                            String result = putData.getResult();
                            if (result.equals("Insert Success")) {
                                Snackbar.make(findViewById(R.id.main), result, Snackbar.LENGTH_LONG).show();
                                Intent intent = new Intent(getApplicationContext(), MainPage.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Snackbar.make(findViewById(R.id.main), "Error: Please try again!", Snackbar.LENGTH_LONG).show();
                            }
                        }
                    }
                }
            });
        }
    }

    private boolean areAllQuestionsAnswered() {
        for (int i = 0; i < radioGroupIDs.length; i++) {
            RadioGroup radioGroup = findViewById(radioGroupIDs[i]);
            if (radioGroup.getCheckedRadioButtonId() == -1) {
                return false;
            }
        }
        return true;
    }

    private int findFirstUnansweredQuestion() {
        for (int radioGroupId : radioGroupIDs) {
            RadioGroup radioGroup = findViewById(radioGroupId);
            if (radioGroup != null) {
                int checkedId = radioGroup.getCheckedRadioButtonId();
                if (checkedId == -1) {
                    View parentView = (View) radioGroup.getParent();
                    if (parentView instanceof LinearLayout) {
                        View cardViewParent = (View) parentView.getParent();
                        if (cardViewParent != null) {
                            return cardViewParent.getId();
                        }
                    }
                }
            }
        }
        return -1;
    }

    private void scrollToUnansweredQuestion(int cardViewId) {
        final ScrollView scrollView = findViewById(R.id.scrollView);
        final View targetView = findViewById(cardViewId);
        scrollView.post(() -> {
            int y = targetView.getTop() - 20;
            scrollView.smoothScrollTo(0, y);
            targetView.requestFocus();
        });
    }
}