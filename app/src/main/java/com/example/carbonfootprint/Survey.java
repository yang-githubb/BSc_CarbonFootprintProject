package com.example.carbonfootprint;

import android.content.Intent;

import com.google.android.material.snackbar.Snackbar;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import android.os.Bundle;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Survey extends AppCompatActivity {
    private static final String TAG = "Survey";

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

    private final ExecutorService executor = Executors.newSingleThreadExecutor();

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
                submitButton.setEnabled(false);
                submitAnswers(submitButton);
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

    /** Sends all answers in one request; the server saves them in a single transaction. */
    private void submitAnswers(Button submitButton) {
        JSONArray answers = new JSONArray();
        try {
            for (int i = 0; i < selectedAnswers.length; i++) {
                JSONObject answer = new JSONObject();
                answer.put("questionId", i + 1);
                answer.put("optionIndex", selectedAnswers[i]);
                answers.put(answer);
            }
        } catch (JSONException e) {
            Log.e(TAG, "Failed to build answers payload", e);
            submitButton.setEnabled(true);
            return;
        }

        executor.execute(() -> {
            String[] field = {"token", "answers"};
            String[] data = {Session.getToken(), answers.toString()};
            PutData putData = new PutData(ApiConfig.SUBMIT_ANSWERS_URL, "POST", field, data);
            boolean sent = putData.startPut() && putData.onComplete();
            String result = sent ? putData.getResult() : "";
            runOnUiThread(() -> {
                if (result.equals("Save Success")) {
                    Intent intent = new Intent(getApplicationContext(), MainPage.class);
                    startActivity(intent);
                    finish();
                } else {
                    submitButton.setEnabled(true);
                    Snackbar.make(findViewById(R.id.main), "Error: Please try again!", Snackbar.LENGTH_LONG).show();
                }
            });
        });
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executor.shutdownNow();
    }
}
