package proyectointegrador.bidup;

import android.app.Application;

import com.cloudinary.android.MediaManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by user on 8/11/2017.
 */

public class CloudinaryConfigApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Map config = new HashMap();
        config.put("cloud_name", "dnrotybo4");
        MediaManager.init(this, config);

    }
}
