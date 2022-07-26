package sempati.star.app.services;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.ArrayList;

import sempati.star.app.models.TempSeat;

public class AndroidSharedPref {

    private static SharedPrefManager mInstance;
    private static Context ctx;

    private static final String SHARED_PREF_NAME = "androidAppPref";
    private static final String ANDROID_ID = "androidID";

    public AndroidSharedPref(Context context) {
        ctx = context;
    }
    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    public String getAndroidID(){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return  sharedPreferences.getString(ANDROID_ID, null);
    }
    public void saveAndroidID(String androidId) {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(ANDROID_ID, androidId);
        editor.apply();
    }
}
