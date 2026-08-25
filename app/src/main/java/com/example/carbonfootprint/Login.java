package com.example.carbonfootprint;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Login extends AppCompatActivity {
    private static final String TAG = "Login";

    TextInputEditText textInputLayoutUsername, textInputLayoutPassword;
    Button buttonLogin;
    TextView textViewSignUp;
    ProgressBar progressBar;

    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        textInputLayoutUsername = findViewById(R.id.username);
        textInputLayoutPassword = findViewById(R.id.password);
        buttonLogin = findViewById(R.id.buttonLogin);
        textViewSignUp = findViewById(R.id.signUpText);
        progressBar = findViewById(R.id.progress);

        textViewSignUp.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), SignUp.class);
            startActivity(intent);
        });

        buttonLogin.setOnClickListener(v -> {
            String username = String.valueOf(textInputLayoutUsername.getText());
            String password = String.valueOf(textInputLayoutPassword.getText());

            if (username.isEmpty() || password.isEmpty()) {
                Snackbar.make(findViewById(R.id.buttonLogin), "All fields are required!", Snackbar.LENGTH_LONG).show();
                return;
            }

            progressBar.setVisibility(View.VISIBLE);
            executor.execute(() -> logIn(username, password));
        });
    }

    private void logIn(String username, String password) {
        String[] field = {"username", "password"};
        String[] data = {username, password};
        PutData putData = new PutData(ApiConfig.LOGIN_URL, "POST", field, data);
        if (putData.startPut() && putData.onComplete()) {
            String result = putData.getResult();
            runOnUiThread(() -> handleLoginResult(username, result));
        } else {
            runOnUiThread(() -> {
                progressBar.setVisibility(View.GONE);
                showError("Could not reach the server. Please try again.");
            });
        }
    }

    private void handleLoginResult(String username, String result) {
        progressBar.setVisibility(View.GONE);
        try {
            JSONObject response = new JSONObject(result);
            if ("success".equals(response.getString("status"))) {
                Session.start(username, response.getInt("userId"), response.getString("token"));
                Class<?> next = response.getBoolean("needsSurvey") ? Survey.class : MainPage.class;
                startActivity(new Intent(getApplicationContext(), next));
                finish();
            } else {
                showError(response.optString("message", "Login failed. Please try again."));
            }
        } catch (JSONException e) {
            Log.e(TAG, "Unexpected login response", e);
            showError("Login failed. Please try again.");
        }
    }

    private void showError(String message) {
        Snackbar.make(findViewById(R.id.buttonLogin), message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executor.shutdownNow();
    }
}
