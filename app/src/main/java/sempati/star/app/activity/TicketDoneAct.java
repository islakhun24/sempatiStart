package sempati.star.app.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;

import dev.shreyaspatil.MaterialDialog.MaterialDialog;
import dev.shreyaspatil.MaterialDialog.interfaces.DialogInterface;
import sempati.star.app.R;
import sempati.star.app.constants.URLs;
import sempati.star.app.models.Asal;
import sempati.star.app.models.Keberangkatan;
import sempati.star.app.models.Kelas_armada;
import sempati.star.app.models.Lambung;
import sempati.star.app.models.PembayranDetail;
import sempati.star.app.models.TbTrayek;
import sempati.star.app.models.Tujuan;
import sempati.star.app.services.SharedPrefManager;
import sempati.star.app.services.VolleySingleton;
import sempati.star.app.utils.QRCodeUtil;

import com.anggastudio.printama.Printama;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TicketDoneAct extends AppCompatActivity {
    ImageView imageView;
    LinearLayout printView;
    Button print;
    String data;
    SharedPrefManager sharedPrefManager;
    QRCodeUtil qrCodeUtil;

    ArrayList<PembayranDetail> arrayList =  new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_done);
        imageView = findViewById(R.id.qr);
        printView = findViewById(R.id.printView);
        print = findViewById(R.id.btnPrint);
        qrCodeUtil = new QRCodeUtil();
        sharedPrefManager = new SharedPrefManager(this);

        Intent intent = getIntent();
        data = intent.getStringExtra("data");
        Glide.with(this).load("https://chart.googleapis.com/chart?chs=250x250&cht=qr&chl=22000065").into(imageView);
        getSavedPrinter();
        print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.logo);

                Printama.with(TicketDoneAct.this).connect(printama -> {
                    for (int i = 0; i < arrayList.size(); i++){
                        PembayranDetail data = arrayList.get(i);
                        printama.printTextWide(data.getAsalObject().getNama_agen() + " - " + data.getTujuanObject().getNama_agen(), Printama.CENTER);
                        printama.printText(Printama.CENTER, data.getKeberangkatanObject().getTanggal() + data.getKeberangkatan().getWaktu());
                        printama.addNewLine();
                        printama.printTextJustify("Kelas bus", "Nomor kursi");
                        printama.printTextJustifyBold(data.getKeberangkatanObject().getLambung().getKelas_armada().getNama_kelas().toString(),data.getNo_kursi().toString());
                        printama.printTextJustify("Nama Penumpang", "Tanggal transaksi");
                        printama.printTextJustifyBold(data.getPenumpang_umum(),data.getNo_kursi().toString());
                        printama.addNewLine();
                        printama.printText("-----------------------------");
                        printama.addNewLine();
                        printama.printTextJustifyBold(String.valueOf(data.getId()), data.getNo_kursi());
                        printama.printTextJustify("Nama loket", "Keterangan pembayaran");
                        printama.printTextJustifyBold(data.getKeberangkatanObject().getLambung().getKelas_armada().getNama_kelas().toString(),data.getNo_kursi().toString());
                        printama.addNewLine();
                        printama.printText("Harga");
                        printama.printTextBold(data.getHarga_potongan());
                        printama.addNewLine();
                        printama.printText("-----------------------------");
                        printama.addNewLine();
                        printama.printTextBold("Perhatian: ", Printama.LEFT);
                        printama.printText("1. Lapor tiket maksimal 15 menit sebelum keberangkatan ", Printama.LEFT);
                        printama.printText("2. Dilarang membawa sajam dan segala jenis narkotika ", Printama.LEFT);
                        printama.printText("3. Barang-barang bawaan menjadi tanggung jawab penumpang ", Printama.LEFT);
                        printama.printText("4. Barang bawaan yang melebihi kapasitas barang dikenakan tarif bagasi ", Printama.LEFT);
                        printama.printText("5. Tukarkan tiket untuk makan ", Printama.LEFT);
                        printama.addNewLine();
                        printama.printText("X----------------------------");
                        printama.addNewLine();
                        printama.printImage(Printama.CENTER, bitmap, 200);
                        printama.addNewLine();
                        printama.printImage(Printama.CENTER, QRCodeUtil.encodeAsBitmap(data.getNo_kursi(), 250, 250), 200);
                        printama.addNewLine();
                        printama.printTextBold(Printama.CENTER ,data.getHarga_potongan());
                        printama.printTextBold(Printama.CENTER ,"Kupon untuk gratis makan");
                        printama.feedPaper();
                    }
                    printama.close();
// /
                });
            }
        });

        parseData(data);

        Log.d("onCreate TAG, : ", arrayList.toString());
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

    void parseData(String data){
        try {
            JSONArray array = new JSONArray(data);
            for (int i = 0; i<array.length(); i++){
                JSONObject jsonData = array.getJSONObject(i);
                JSONObject jsonKeberangkatan = jsonData.getJSONObject("KeberangkatanObject");
                Keberangkatan keberangkatan = new Keberangkatan();
                keberangkatan.setId(jsonKeberangkatan.getInt("id"));
                keberangkatan.setTanggal(jsonKeberangkatan.getString("tanggal"));
                keberangkatan.setJam(jsonKeberangkatan.getString("jam"));
                keberangkatan.setWaktu(jsonKeberangkatan.getString("waktu"));

                JSONObject jsonLambung = jsonKeberangkatan.getJSONObject("LambungObject");
                Lambung lambung = new Lambung();
                lambung.setId(jsonLambung.getInt("id"));
                lambung.setNama(jsonLambung.getString("nama"));

                JSONObject jsonKelasArmada = jsonLambung.getJSONObject("Kelas_armadaObject");
                Kelas_armada kelas_armada = new Kelas_armada();
                kelas_armada.setId(jsonKelasArmada.getInt("id"));
                kelas_armada.setNama_kelas(jsonKelasArmada.getString("nama_kelas"));
                kelas_armada.setJenis_seat(jsonKelasArmada.getInt("jenis_seat"));
                kelas_armada.setJenis_seat(0);

                lambung.setKelas_armada(kelas_armada);

                JSONObject jsonTrayek = jsonKeberangkatan.getJSONObject("TrayekObject");
                TbTrayek trayek = new TbTrayek();
                trayek.setId(jsonTrayek.getInt("id"));
                trayek.setNama_trayek(jsonTrayek.getString("nama_trayek"));
                trayek.setNama_laporan(jsonTrayek.getString("nama_laporan"));

                keberangkatan.setTrayek(trayek);
                keberangkatan.setLambung(lambung);

                JSONObject jsonAsal = jsonData.getJSONObject("AsalObject");
                Asal asal = new Asal();
                asal.setId(jsonAsal.getInt("id"));
                asal.setNama_agen(jsonAsal.getString("nama_agen"));
                asal.setKode_agen(jsonAsal.getString("kode_agen"));

                JSONObject jsonTujuna = jsonData.getJSONObject("TujuanObject");
                Tujuan tujuan = new Tujuan();
                tujuan.setId(jsonTujuna.getInt("id"));
                tujuan.setNama_agen(jsonTujuna.getString("nama_agen"));
                tujuan.setKode_agen(jsonTujuna.getString("kode_agen"));

                PembayranDetail pembayranDetail = new PembayranDetail();
                pembayranDetail.setId(jsonData.getInt("id"));
                pembayranDetail.setNo_kursi(jsonData.getString("no_kursi"));
                pembayranDetail.setPenumpang_hp(jsonData.getString("penumpang_hp"));
                pembayranDetail.setPenumpang_umum(jsonData.getString("penumpang_umum"));
                pembayranDetail.setHarga_potongan(jsonData.getString("harga_potongan"));
                pembayranDetail.setKeberangkatan_id(jsonData.getString("keberangkatan_id"));
                pembayranDetail.setAgen_id(jsonData.getString("agen_id"));
                pembayranDetail.setPenumpang_id(jsonData.getString("penumpang_id"));
                pembayranDetail.setKomisi_agen_id(jsonData.getString("komisi_agen_id"));
                pembayranDetail.setPenumpang_umum(jsonData.getString("penumpang_umum"));
                pembayranDetail.setTiba_id(jsonData.getString("tiba_id"));
                pembayranDetail.setHarga_tiket(jsonData.getString("harga_tiket"));
                pembayranDetail.setHarga_potongan(jsonData.getString("harga_potongan"));
//                pembayranDetail.setLambung_id(jsonData.getString("lambung_id"));
                pembayranDetail.setNo_kursi(jsonData.getString("no_kursi"));
                pembayranDetail.setStatus(jsonData.getString("status"));
                pembayranDetail.setStatus_migrasi(jsonData.getString("status_migrasi"));
                pembayranDetail.setQrcode(jsonData.getString("qrcode"));
                pembayranDetail.setKet(jsonData.getString("ket"));
                pembayranDetail.setLimit_boking(jsonData.getString("limit_boking"));
//                pembayranDetail.setTickes(jsonData.getString("tickes"));
                pembayranDetail.setCr_id(jsonData.getString("cr_id"));
                pembayranDetail.setCr_id_pusat(jsonData.getString("cr_id_pusat"));
                pembayranDetail.setCr_datetime(jsonData.getString("cr_datetime"));
                pembayranDetail.setUp_id(jsonData.getString("up_id"));
                pembayranDetail.setUp_datetime(jsonData.getString("up_datetime"));

                pembayranDetail.setAsal(asal);
                pembayranDetail.setKeberangkatan(keberangkatan);
                pembayranDetail.setTujuan(tujuan);

                arrayList.add(pembayranDetail);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}