package Hardware.SharedPreferences;

import Infrastructure.AccountSessions.User;
import android.content.SharedPreferences;
import com.google.gson.Gson;

/**
 * Created by MARKAN on 13.05.2017.
 */
public class UserPreference {
    public static SharedPreferences sharedPreferences;
    private static String isLoged = "IsLoged";
    private static String userAccount = "UserAccount";

    public static void UserPreferenceInit(SharedPreferences sp) {
        sharedPreferences = sp;
    }

    public static void storeUserAccount(Infrastructure.AccountSessions.User user) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.putBoolean(isLoged, true);
        editor.putString(userAccount, (new Gson()).toJson(user));
        editor.apply();
    }

    public static boolean checkSavedAccount() {
        return sharedPreferences.getBoolean(isLoged, false);
    }

    public static User readUserAccount() {
        return (new Gson()).fromJson(sharedPreferences.getString(userAccount, null), User.class);
    }
}
