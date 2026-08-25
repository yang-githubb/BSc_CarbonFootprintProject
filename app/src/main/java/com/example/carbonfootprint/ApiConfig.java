package com.example.carbonfootprint;

/**
 * Single place to configure the backend location. Change BASE_URL to match
 * the machine running the PHP backend (XAMPP) on your network.
 */
public final class ApiConfig {

    public static final String BASE_URL = "http://192.168.100.4/CarbonFootprintFYP/";

    public static final String LOGIN_URL = BASE_URL + "login.php";
    public static final String SIGNUP_URL = BASE_URL + "signup.php";
    public static final String SUBMIT_ANSWERS_URL = BASE_URL + "submit_answers.php";
    public static final String CARB_CALC_URL = BASE_URL + "carbCalc.php";
    public static final String CLUSTER_CALC_URL = BASE_URL + "clusterCalc.php";
    public static final String GET_ACTION_URL = BASE_URL + "getAction.php";

    private ApiConfig() {
    }
}
