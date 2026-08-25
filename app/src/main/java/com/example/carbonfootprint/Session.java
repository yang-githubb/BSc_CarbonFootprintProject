package com.example.carbonfootprint;

/**
 * In-memory session state for the logged-in user. The password is never
 * retained; the API token returned by login.php authenticates requests.
 */
public final class Session {

    private static String username;
    private static int userId = -1;
    private static String token;
    private static double lastFootprintTonnes;

    private Session() {
    }

    public static void start(String name, int id, String apiToken) {
        username = name;
        userId = id;
        token = apiToken;
    }

    public static void clear() {
        username = null;
        userId = -1;
        token = null;
        lastFootprintTonnes = 0;
    }

    public static void setLastFootprintTonnes(double tonnes) {
        lastFootprintTonnes = tonnes;
    }

    public static double getLastFootprintTonnes() {
        return lastFootprintTonnes;
    }

    public static boolean isActive() {
        return token != null;
    }

    public static String getUsername() {
        return username;
    }

    public static int getUserId() {
        return userId;
    }

    public static String getToken() {
        return token;
    }
}
