package proyectointegrador.bidup;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class FirebaseIDService extends FirebaseInstanceIdService {
    private static final String TAG = "FirebaseIDService";
    public static final String PREFS_NAME = "MyPrefsFile";

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        sendRegistrationToServer(refreshedToken);
    }

    /**
     * Persist token to third-party servers.
     *
     * Modify this method to associate the user's FCM InstanceID token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
    private void sendRegistrationToServer(String token) {
        //TODO esta bien esto? o cuando hace el login trato de forzar que se llame a este metodo
        SharedPreferences sp = getSharedPreferences(PREFS_NAME,0);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("firebaseToken",token);
        editor.commit();
    }
}
