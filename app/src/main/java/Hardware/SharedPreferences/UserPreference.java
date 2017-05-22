package Hardware.SharedPreferences;

import Infrastructure.AccountSessions.CurrentUser;
import Infrastructure.AccountSessions.User;
import android.content.SharedPreferences;
import com.google.gson.Gson;

/**
 * Created by MARKAN on 13.05.2017.
 */
//Клас для роботи з локальним сховищем користувача
public class UserPreference {
    private static SharedPreferences sharedPreferences;
    private static String isLoged = "IsLoged";
    private static String userAccount = "UserAccount";
    public static void UserPreferenceInit(SharedPreferences sp) {
        sharedPreferences = sp;
    }

    //Зберігання облікового запису користувача у пам'яті
    public static void storeUserAccount() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.putBoolean(isLoged, true);
        editor.putString(userAccount, (new Gson()).toJson(CurrentUser.getInstance()));
        editor.apply();
    }

    //Знищення сховища
    public static void destroyPreference() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

    //Перевірка, чи залогінений користувач
    public static boolean checkSavedAccount() {
        return sharedPreferences.getBoolean(isLoged, false);
    }

    //Зчитання даних користувача
    public static User readUserAccount() {
        return (new Gson()).fromJson(sharedPreferences.getString(userAccount, null), User.class);
    }
}
