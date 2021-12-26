package sempati.star.app.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import sempati.star.app.R;

public class SeatAct extends AppCompatActivity {
    Button btnBayar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat);
        btnBayar = findViewById(R.id.btnBayar);
        btnBayar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SeatAct.this, PembayaranAct.class));
            }
        });
    }
}