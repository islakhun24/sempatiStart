package sempati.star.app.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import sempati.star.app.MainActivity;
import sempati.star.app.R;
import sempati.star.app.fragment.BookingFrag;
import sempati.star.app.fragment.ManifestFrag;
import sempati.star.app.fragment.ReportFrag;
import sempati.star.app.fragment.TiketFrag;
import sempati.star.app.fragment.TransaksiFrag;
import sempati.star.app.services.AndroidSharedPref;
import sempati.star.app.services.SharedPrefManager;
import sempati.star.app.utils.Device;
import android.os.Build;

public class HomeAct extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    BottomNavigationView bottomNavigationView;
    FloatingActionButton fab;
    Device device = new Device();
    public static final int PERMISSION_BLUETOOTH = 1;
    public String androidId;

    AndroidSharedPref androidSharedPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.BLUETOOTH}, HomeAct.PERMISSION_BLUETOOTH);
        }


       androidSharedPref = new AndroidSharedPref(this);
       if(isNullOrEmpty(androidSharedPref.getAndroidID())){
           if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
               Log.e("TAG", "onCreate: "+Settings.Secure.getString(
                       this.getContentResolver(),
                       Settings.Secure.ANDROID_ID));
               androidSharedPref.saveAndroidID(Settings.Secure.getString(
                       this.getContentResolver(),
                       Settings.Secure.ANDROID_ID));
           }else {
               TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
               Log.e("TAG", "onCreate: "+ telephonyManager.getDeviceId());
               androidSharedPref.saveAndroidID(telephonyManager.getDeviceId());
           }
       }

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setBackground(null);
        bottomNavigationView.getMenu().getItem(2).setEnabled(false);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new TiketFrag());
            }
        });
        loadFragment(new TiketFrag());
    }

    private boolean loadFragment(Fragment fragment){
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fl_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment = null;
        switch (menuItem.getItemId()){
            case R.id.transaksi:
                fragment = new TransaksiFrag();
                break;
            case R.id.booking:
                fragment = new BookingFrag();
                break;
            case R.id.manifest:
                fragment = new ManifestFrag();
                break;
            case R.id.laporan:
                fragment = new ReportFrag();
                break;
        }
        return loadFragment(fragment);
    }

    public boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }


}