package sempati.star.app.utils;

import android.content.Context;
import android.provider.Settings;

public class Device {
    public static String getDeviceId(Context context) {

        String id = Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        return id;
    }
}
