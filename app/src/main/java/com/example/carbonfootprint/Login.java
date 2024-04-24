package com.example.carbonfootprint;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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
import com.vishnusivadas.advanced_httpurlconnection.FetchData;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity {
    TextInputEditText textInputLayoutUsername, textInputLayoutPassword;
    Button buttonLogin;
    TextView textViewSignUp;
    ProgressBar progressBar;
    private static final String TAG = Survey.class.getSimpleName();

    public static String username;
    public static String password;
    public static String user_Id;

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
            username = String.valueOf(textInputLayoutUsername.getText());
            password = String.valueOf(textInputLayoutPassword.getText());

            if (!username.isEmpty() && !password.isEmpty()) {
                progressBar.setVisibility(View.VISIBLE);
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(() -> {
                    String[] field = new String[2];
                    field[0] = "username";
                    field[1] = "password";
                    String[] data = new String[2];
                    data[0] = username;
                    data[1] = password;
                    PutData putData = new PutData("http://192.168.100.4/CarbonFootprintFYP/login.php", "POST", field, data);
                    if (putData.startPut()) {
                        if (putData.onComplete()) {
                            progressBar.setVisibility(View.GONE);
                            String result = putData.getResult();
                            if (result.equals("Login Success")) {
                                fetchUserId(username, MainPage.class);
                            } else if (result.equals("Survey")) {
                                fetchUserId(username, Survey.class);
                            } else if (result.equals("Username or Password wrong")) {
                                Snackbar.make(findViewById(R.id.buttonLogin), "Username or Password wrong", Snackbar.LENGTH_LONG).show();
                            }
                        }
                    }
                });
            } else {
                Snackbar.make(findViewById(R.id.buttonLogin), "All fields are required!", Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private void fetchUserId(String username, Class<?> activityClass) {
        FetchData fetchData = new FetchData("http://192.168.100.4/CarbonFootprintFYP/get_userid.php?username=" + username);
        if (fetchData.startFetch()) {
            if (fetchData.onComplete()) {
                String response = fetchData.getResult();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String userId = jsonObject.getString("userId");
                    Intent intent = new Intent(getApplicationContext(), activityClass);
                    intent.putExtra("USER_ID", userId);
                    user_Id=userId;
                    startActivity(intent);
                    finish();
                } catch (JSONException e) {
                    Log.e(TAG, "Error parsing user data", e);
                    progressBar.setVisibility(View.GONE);
                    Snackbar.make(findViewById(R.id.buttonLogin), "Failed to handle user data", Snackbar.LENGTH_LONG).show();
                }
            }
        }
    }
}