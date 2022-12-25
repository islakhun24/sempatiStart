package sempati.star.app.services;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import sempati.star.app.activity.LoginAct;
import sempati.star.app.models.TempSeat;

public class TempSeatPrefManager {
    private static final String SHARED_PREF_NAME = "tempSeatPrefManager";
    private static final String KEY_KEBERANGKATAN_ID = "keberangkatanId";
    private static final String KEY_NO_KURSI = "noKursi";
    private static final String KEY_BARIS = "baris";
    private static SharedPrefManager mInstance;
    private static Context ctx;

    public TempSeatPrefManager(Context context) {
        ctx = context;
    }
    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    //this method will store the user data in shared preferences
    public void saveSeat(ArrayList<TempSeat> seatList) {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(seatList);
        editor.putString("list", json);
        editor.apply();
    }

    //this method will checker whether user is already logged in or not

    //this method will give the logged in user
    public ArrayList<TempSeat> getSeat() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("list", null);
        Type type = new TypeToken<ArrayList<TempSeat>>() {}.getType();
        return gson.fromJson(json, type);
    }

    //this method will logout the user
    public void clearSeat() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}