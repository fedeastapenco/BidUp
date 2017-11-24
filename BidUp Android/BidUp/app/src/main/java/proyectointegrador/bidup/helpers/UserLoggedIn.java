package proyectointegrador.bidup.helpers;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.Objects;

import proyectointegrador.bidup.activities.LoginActivity;

/**
 * Created by user on 23/11/2017.
 */

public class UserLoggedIn {
    public static final String PREFS_NAME = "MyPrefsFile";

    public static void IsUserLoggedIn(Activity activity){
        SharedPreferences sp = activity.getSharedPreferences(PREFS_NAME,0);
        if(Objects.equals(sp.getString("currentAuthenticationToken", "empty"), "empty")){
            Intent intentLogin = new Intent(activity, LoginActivity.class);
            activity.startActivity(intentLogin);
        }
    }
}
