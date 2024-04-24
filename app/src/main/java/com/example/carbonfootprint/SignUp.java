package com.example.carbonfootprint;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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

public class SignUp extends AppCompatActivity {

    TextInputEditText textInputLayoutUsername, textInputLayoutPassword, textInputLayoutEmail;
    Button buttonSignUp;
    TextView textViewLogin;
    ProgressBar progressBar;

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

            if (!username.isEmpty() && !password.isEmpty() && !email.isEmpty()) {
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
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(() -> {
                        String[] field = new String[3];
                        field[0] = "username";
                        field[1] = "password";
                        field[2] = "email";
                        String[] data = new String[3];
                        data[0] = username;
                        data[1] = password;
                        data[2] = email;
                        PutData putData = new PutData("http://192.168.100.4/CarbonFootprintFYP/signup.php", "POST", field, data);
                        if (putData.startPut()) {
                            if (putData.onComplete()) {
                                progressBar.setVisibility(View.GONE);
                                String result = putData.getResult();
                                if (result.equals("Sign Up Success")) {
                                    Snackbar.make(findViewById(R.id.main), "Account created successfully!", Snackbar.LENGTH_LONG).show();
                                    Intent intent = new Intent(getApplicationContext(), Login.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Snackbar.make(findViewById(R.id.main), "Please try again!", Snackbar.LENGTH_LONG).show();
                                }
                            }
                        }
                    });
                } else {
                    Snackbar.make(findViewById(R.id.main), "All fields are required!", Snackbar.LENGTH_LONG).show();
                }
            }
        });
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