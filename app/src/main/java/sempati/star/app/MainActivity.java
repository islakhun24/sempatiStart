package sempati.star.app;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.wang.avi.AVLoadingIndicatorView;

import sempati.star.app.activity.HomeAct;
import sempati.star.app.activity.LoginAct;
import sempati.star.app.widget.DialogWidget;

public class MainActivity extends AppCompatActivity {
    private LinearLayout lv_loading;
    private AVLoadingIndicatorView avi;
//    private FloatingActionButton fab;
    public static String[] PERMISSIONS_STORAGE = {
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS,
        Manifest.permission.BLUETOOTH_SCAN,
        Manifest.permission.BLUETOOTH_CONNECT,
        Manifest.permission.BLUETOOTH_PRIVILEGED
};
    public static String[] PERMISSIONS_LOCATION = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS,
            Manifest.permission.BLUETOOTH_SCAN,
            Manifest.permission.BLUETOOTH_CONNECT,
            Manifest.permission.BLUETOOTH_PRIVILEGED
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv_loading = (LinearLayout) findViewById(R.id.lv_loading);
        avi= (AVLoadingIndicatorView) findViewById(R.id.avi);
        avi.setIndicator("BallClipRotateMultipleIndicator");
        //membuat sebuah proses yang ter delay

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //mendefenisikan Intent activity
                Intent i = new Intent(MainActivity.this, LoginAct.class);
                startActivity(i);
                //finish berguna untuk mengakhiri activity
                //disini saya menggunakan finish,supaya ketika menekan tombol back
                //tidak kembali pada activity SplashScreen nya
                finish();
            }
            //disini maksud 3000 nya itu adalah lama screen ini terdelay 3 detik,dalam satuan mili second
        }, 3000);
    }
}