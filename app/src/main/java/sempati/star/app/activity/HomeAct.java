package sempati.star.app.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import sempati.star.app.R;
import sempati.star.app.fragment.BookingFrag;
import sempati.star.app.fragment.ManifestFrag;
import sempati.star.app.fragment.ReportFrag;
import sempati.star.app.fragment.TiketFrag;
import sempati.star.app.fragment.TransaksiFrag;

public class HomeAct extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    BottomNavigationView bottomNavigationView;
    FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
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


}