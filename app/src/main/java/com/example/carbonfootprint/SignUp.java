package com.example.carbonfootprint;

import android.content.Intent;
import android.os.Bundle;
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

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SignUp extends AppCompatActivity {

    TextInputEditText textInputLayoutUsername, textInputLayoutPassword, textInputLayoutEmail;
    Button buttonSignUp;
    TextView textViewLogin;
    ProgressBar progressBar;

    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        textInputLayoutUsername = findViewById(R.id.username);
        textInputLayoutPassword = findViewById(R.id.password);
        textInputLayoutEmail = findViewById(R.id.email);
        buttonSignUp = findViewById(R.id.buttonSignUp);
        textViewLogin = findViewById(R.id.loginText);
        progressBar = findViewById(R.id.progress);

        textViewLogin.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
        });

        buttonSignUp.setOnClickListener(v -> {
            String username, password, email;
            username = String.valueOf(textInputLayoutUsername.getText());
            password = String.valueOf(textInputLayoutPassword.getText());
            email = String.valueOf(textInputLayoutEmail.getText());

            if (username.isEmpty() || password.isEmpty() || email.isEmpty()) {
                Snackbar.make(findViewById(R.id.main), "All fields are required!", Snackbar.LENGTH_LONG).show();
            } else {
                boolean valid = true;
                if (!isValidUsername(username)) {
                    textInputLayoutUsername.setError("Invalid username. Use 6-64 alphanumeric characters, underscores, hyphens, and periods.");
                    valid = false;
                } else {
                    textInputLayoutUsername.setError(null);
                }

                if (!isValidPassword(password)) {
                    textInputLayoutPassword.setError("Password too weak. Must include upper, lower, number, special character, and be at least 8 characters long.");
                    valid = false;
                } else {
                    textInputLayoutPassword.setError(null);
                }

                if (!isValidEmail(email)) {
                    textInputLayoutEmail.setError("Invalid email format.");
                    valid = false;
                } else {
                    textInputLayoutEmail.setError(null);
                }

                if (valid) {
                    progressBar.setVisibility(View.VISIBLE);
                    executor.execute(() -> {
                        String[] field = {"username", "password", "email"};
                        String[] data = {username, password, email};
                        PutData putData = new PutData(ApiConfig.SIGNUP_URL, "POST", field, data);
                        boolean sent = putData.startPut() && putData.onComplete();
                        String result = sent ? putData.getResult() : "";
                        runOnUiThread(() -> {
                            progressBar.setVisibility(View.GONE);
                            if (result.equals("Sign Up Success")) {
                                Snackbar.make(findViewById(R.id.main), "Account created successfully!", Snackbar.LENGTH_LONG).show();
                                Intent intent = new Intent(getApplicationContext(), Login.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Snackbar.make(findViewById(R.id.main), "Please try again!", Snackbar.LENGTH_LONG).show();
                            }
                        });
                    });
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executor.shutdownNow();
    }

    public boolean isValidUsername(String username) {
        return username.matches("^[A-Za-z0-9]+(?:[ _-][A-Za-z0-9]+)*$");
    }

    public boolean isValidPassword(String password) {
        return password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$");
    }

    public boolean isValidEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}