package sempati.star.app.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import sempati.star.app.R;

import com.anggastudio.printama.Printama;
public class TicketDoneAct extends AppCompatActivity {
    ImageView imageView;
    LinearLayout printView;
    Button print;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_done);
        imageView = findViewById(R.id.qr);
        printView = findViewById(R.id.printView);
        print = findViewById(R.id.btnPrint);

        Glide.with(this).load("https://chart.googleapis.com/chart?chs=250x250&cht=qr&chl=22000065").into(imageView);
        getSavedPrinter();
        print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Printama.with(TicketDoneAct.this).connect(printama -> {
                    printama.printFromView(printView);
                    printama.feedPaper();
                    printama.close();
//                    printama.printTextlnBold(Printama.CENTER, "Jakarta Bandung");
//                    printama.setSmallText();
//                    printama.printTextln(Printama.CENTER, "Rabu, 28 oktober 2021 16:00 WIB");
//                    printama.addNewLine();
//                    printama.setSmallText();
//                    printama.printTextJustify("Kelas bus","", "No. Kursi");
//                    printama.addNewLine();
//                    printama.printTextJustifyBold("Executive", "31");
//                    printama.addNewLine();
//                    printama.setSmallText();
//                    printama.printTextJustify("Nama penumpang", "Tanggal Transaksi");
//                    printama.addNewLine();
//                    printama.printTextJustifyBold("Kiki Amalia", "22 Oktober 2021");
//                    printama.addNewLine(2);
//                    printama.printTextJustifyBold("2001223","MX.321");
//                    printama.close();
                });
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String printerName = Printama.getPrinterResult(resultCode, requestCode, data);
        showResult(printerName);
    }

    private void showResult(String printerName) {
        showToast(printerName);
//        TextView connectedTo = findViewById(R.id.tv_printer_info);
        String text = "Connected to : " + printerName;
//        connectedTo.setText(text);
//        if (!printerName.contains("failed")) {
//            findViewById(R.id.btn_printer_test).setVisibility(View.VISIBLE);
//            findViewById(R.id.btn_printer_test).setOnClickListener(v -> testPrinter());
//        }
    }
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
    private void getSavedPrinter() {
        BluetoothDevice connectedPrinter = Printama.with(this).getConnectedPrinter();
        Printama.showPrinterList(this, R.color.colorBlue, printerName -> {
            if (connectedPrinter != null) {

            }
        });
    }
}