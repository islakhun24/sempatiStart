package sempati.star.app.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import sempati.star.app.adapter.KeberangkatanAdapter;
import sempati.star.app.adapter.TiketAdapter;
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
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TicketDoneAct extends AppCompatActivity {
    Button print;
    String data;
    SharedPrefManager sharedPrefManager;
    QRCodeUtil qrCodeUtil;

    private static RecyclerView.Adapter adapter;
    ShimmerFrameLayout container;
    ImageView back;
    RecyclerView rvResult;
    private RecyclerView.LayoutManager layoutManager;
    ArrayList<PembayranDetail> arrayList =  new ArrayList<>();

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(TicketDoneAct.this, HomeAct.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_done);
        print = findViewById(R.id.btnPrint);
        qrCodeUtil = new QRCodeUtil();
        sharedPrefManager = new SharedPrefManager(this);
        rvResult = (RecyclerView) findViewById(R.id.rvResult);
        rvResult.setHasFixedSize(true);
        container = (ShimmerFrameLayout) findViewById(R.id.shimmer_view_container);
        container.startShimmer();
        layoutManager = new LinearLayoutManager(this);
        rvResult.setLayoutManager(layoutManager);
        rvResult.setItemAnimator(new DefaultItemAnimator());
        back = findViewById(R.id.back);
        Intent intent = getIntent();
        data = intent.getStringExtra("data");
        Log.d( "onCreate: asdasd ", data);
        getSavedPrinter();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TicketDoneAct.this, HomeAct.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();
            }
        });
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

        if(getIntent().getStringExtra("from").equalsIgnoreCase("fame")){
            parseDataFame(data);
        }else {
            parseData();
        }


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

    void parseData(){
        container.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.BUY_TICKET_DETAIL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("TAG SS", response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean status = jsonObject.getBoolean("success");
                            if(status) {
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                JSONObject jsonDetail = jsonObject.getJSONObject("detail");
                                for (int i =0; i <jsonArray.length(); i++) {
                                    JSONObject jsonData = jsonArray.getJSONObject(i);
                                    JSONObject jsonKeberangkatan = jsonData.getJSONObject("keberangkatan");
                                    Keberangkatan keberangkatan = new Keberangkatan();
                                    keberangkatan.setId(jsonKeberangkatan.getInt("id"));
                                    keberangkatan.setTanggal(jsonKeberangkatan.getString("tanggal"));
                                    keberangkatan.setJam(jsonKeberangkatan.getString("jam"));
                                    keberangkatan.setWaktu(jsonKeberangkatan.getString("waktu"));

                                    JSONObject jsonLambung = jsonKeberangkatan.getJSONObject("lambung");
                                    Lambung lambung = new Lambung();
                                    lambung.setId(jsonLambung.getInt("id"));
                                    lambung.setNama(jsonLambung.getString("nama"));

                                    JSONObject jsonKelasArmada = jsonLambung.getJSONObject("kelas_armada");
                                    Kelas_armada kelas_armada = new Kelas_armada();
                                    kelas_armada.setId(jsonKelasArmada.getInt("id"));
                                    kelas_armada.setNama_kelas(jsonKelasArmada.getString("nama_kelas"));
                                    kelas_armada.setJenis_seat(jsonKelasArmada.getInt("jenis_seat"));
                                    kelas_armada.setJenis_seat(0);

                                    lambung.setKelas_armada(kelas_armada);

                                    JSONObject jsonTrayek = jsonKeberangkatan.getJSONObject("trayek");
                                    TbTrayek trayek = new TbTrayek();
                                    trayek.setId(jsonTrayek.getInt("id"));
                                    trayek.setNama_trayek(jsonTrayek.getString("nama_trayek"));
                                    trayek.setNama_laporan(jsonTrayek.getString("nama_laporan"));

                                    keberangkatan.setTrayek(trayek);
                                    keberangkatan.setLambung(lambung);

                                    JSONObject jsonAsal = jsonData.getJSONObject("asal");
                                    Asal asal = new Asal();
                                    asal.setId(jsonAsal.getInt("id"));
                                    asal.setNama_agen(jsonAsal.getString("nama_agen"));
                                    asal.setKode_agen(jsonAsal.getString("kode_agen"));

                                    JSONObject jsonTujuna = jsonData.getJSONObject("tujuan");
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
//                                    pembayranDetail.setLambung_id(jsonData.getString("lambung_id"));
                                    pembayranDetail.setNo_kursi(jsonData.getString("no_kursi"));
                                    pembayranDetail.setStatus(jsonData.getString("status"));
                                    pembayranDetail.setStatusBayar(jsonData.getString("status_bayar"));
                                    pembayranDetail.setStatus_migrasi(jsonData.getString("status_migrasi"));
                                    pembayranDetail.setQrcode(jsonData.getString("qrcode"));
                                    pembayranDetail.setKet(jsonData.getString("ket"));
                                    pembayranDetail.setLimit_boking(jsonData.getString("limit_boking"));
//                                    pembayranDetail.setTickes(jsonData.getString("tickes"));
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
                                adapter = new TiketAdapter(arrayList, TicketDoneAct.this);
                                rvResult.setAdapter(adapter);

                                container.setVisibility(View.GONE);

                                rvResult.setVisibility(View.VISIBLE);
                            }
                            if(arrayList.size()!=0){
                                rvResult.setVisibility(View.VISIBLE);
                            }else {
                                rvResult.setVisibility(View.GONE);
                            }
                            container.setVisibility(View.GONE);
                        }catch (JSONException e){
                            e.printStackTrace();
                            rvResult.setVisibility(View.GONE);
                            container.setVisibility(View.GONE);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("TAG ERR", error.toString());
                        rvResult.setVisibility(View.GONE);
                        container.setVisibility(View.GONE);
                    }
                })
        {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("x-access-token" , sharedPrefManager.getUser().getAccessToken().toString());
                return headers;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Log.e("TAG", "log android id "+ android.provider.Settings.Secure.getString(
                        getApplication().getApplicationContext().getContentResolver(), android.provider.Settings.Secure.ANDROID_ID) );
                Map<String, String> params = new HashMap<>();
                Log.e("TAG", "keberangkatan_id"+ getIntent().getStringExtra("keberangkatan_id"));
                params.put("keberangkatan_id", getIntent().getStringExtra("keberangkatan_id"));
                params.put("android_id", android.provider.Settings.Secure.getString(
                        getApplication().getApplicationContext().getContentResolver(), android.provider.Settings.Secure.ANDROID_ID));
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
//        try {
//            JSONArray array = new JSONArray(data);
//            for (int i = 0; i<array.length(); i++){
//                JSONObject jsonData = array.getJSONObject(i);
//                JSONObject jsonKeberangkatan = jsonData.getJSONObject("KeberangkatanObject");
//                Keberangkatan keberangkatan = new Keberangkatan();
//                keberangkatan.setId(jsonKeberangkatan.getInt("id"));
//                keberangkatan.setTanggal(jsonKeberangkatan.getString("tanggal"));
//                keberangkatan.setJam(jsonKeberangkatan.getString("jam"));
//                keberangkatan.setWaktu(jsonKeberangkatan.getString("waktu"));
//
//                JSONObject jsonLambung = jsonKeberangkatan.getJSONObject("LambungObject");
//                Lambung lambung = new Lambung();
//                lambung.setId(jsonLambung.getInt("id"));
//                lambung.setNama(jsonLambung.getString("nama"));
//
//                JSONObject jsonKelasArmada = jsonLambung.getJSONObject("Kelas_armadaObject");
//                Kelas_armada kelas_armada = new Kelas_armada();
//                kelas_armada.setId(jsonKelasArmada.getInt("id"));
//                kelas_armada.setNama_kelas(jsonKelasArmada.getString("nama_kelas"));
//                kelas_armada.setJenis_seat(jsonKelasArmada.getInt("jenis_seat"));
//                kelas_armada.setJenis_seat(0);
//
//                lambung.setKelas_armada(kelas_armada);
//
//                JSONObject jsonTrayek = jsonKeberangkatan.getJSONObject("TrayekObject");
//                TbTrayek trayek = new TbTrayek();
//                trayek.setId(jsonTrayek.getInt("id"));
//                trayek.setNama_trayek(jsonTrayek.getString("nama_trayek"));
//                trayek.setNama_laporan(jsonTrayek.getString("nama_laporan"));
//
//                keberangkatan.setTrayek(trayek);
//                keberangkatan.setLambung(lambung);
//
//                JSONObject jsonAsal = jsonData.getJSONObject("AsalObject");
//                Asal asal = new Asal();
//                asal.setId(jsonAsal.getInt("id"));
//                asal.setNama_agen(jsonAsal.getString("nama_agen"));
//                asal.setKode_agen(jsonAsal.getString("kode_agen"));
//
//                JSONObject jsonTujuna = jsonData.getJSONObject("TujuanObject");
//                Tujuan tujuan = new Tujuan();
//                tujuan.setId(jsonTujuna.getInt("id"));
//                tujuan.setNama_agen(jsonTujuna.getString("nama_agen"));
//                tujuan.setKode_agen(jsonTujuna.getString("kode_agen"));
//
//                PembayranDetail pembayranDetail = new PembayranDetail();
//                pembayranDetail.setId(jsonData.getInt("id"));
//                pembayranDetail.setNo_kursi(jsonData.getString("no_kursi"));
//                pembayranDetail.setPenumpang_hp(jsonData.getString("penumpang_hp"));
//                pembayranDetail.setPenumpang_umum(jsonData.getString("penumpang_umum"));
//                pembayranDetail.setHarga_potongan(jsonData.getString("harga_potongan"));
//                pembayranDetail.setKeberangkatan_id(jsonData.getString("keberangkatan_id"));
//                pembayranDetail.setAgen_id(jsonData.getString("agen_id"));
//                pembayranDetail.setPenumpang_id(jsonData.getString("penumpang_id"));
//                pembayranDetail.setKomisi_agen_id(jsonData.getString("komisi_agen_id"));
//                pembayranDetail.setPenumpang_umum(jsonData.getString("penumpang_umum"));
//                pembayranDetail.setTiba_id(jsonData.getString("tiba_id"));
//                pembayranDetail.setHarga_tiket(jsonData.getString("harga_tiket"));
//                pembayranDetail.setHarga_potongan(jsonData.getString("harga_potongan"));
////                pembayranDetail.setLambung_id(jsonData.getString("lambung_id"));
//                pembayranDetail.setNo_kursi(jsonData.getString("no_kursi"));
//                pembayranDetail.setStatus(jsonData.getString("status"));
//                pembayranDetail.setStatusBayar(jsonData.getString("status_bayar"));
//                pembayranDetail.setStatus_migrasi(jsonData.getString("status_migrasi"));
//                pembayranDetail.setQrcode(jsonData.getString("qrcode"));
//                pembayranDetail.setKet(jsonData.getString("ket"));
//                pembayranDetail.setLimit_boking(jsonData.getString("limit_boking"));
////                pembayranDetail.setTickes(jsonData.getString("tickes"));
//                pembayranDetail.setCr_id(jsonData.getString("cr_id"));
//                pembayranDetail.setCr_id_pusat(jsonData.getString("cr_id_pusat"));
//                pembayranDetail.setCr_datetime(jsonData.getString("cr_datetime"));
//                pembayranDetail.setUp_id(jsonData.getString("up_id"));
//                pembayranDetail.setUp_datetime(jsonData.getString("up_datetime"));
//
//                pembayranDetail.setAsal(asal);
//                pembayranDetail.setKeberangkatan(keberangkatan);
//                pembayranDetail.setTujuan(tujuan);
//
//                arrayList.add(pembayranDetail);
//            }
//            adapter = new TiketAdapter(arrayList, TicketDoneAct.this);
//            rvResult.setAdapter(adapter);
//
//            container.setVisibility(View.GONE);
//
//            rvResult.setVisibility(View.VISIBLE);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

    }

    void parseDataFame(String data){
        container.setVisibility(View.VISIBLE);
        Log.e("TAG", "parseDataFame: "+data );
        try {
            JSONObject jsonData = new JSONObject(data);
            Keberangkatan keberangkatan = new Keberangkatan();
            keberangkatan.setId(Integer.parseInt(jsonData.getString("keberangkatan_id")));
            keberangkatan.setTanggal(jsonData.getString("keberangkatan_tanggal"));
            keberangkatan.setJam(jsonData.getString("keberangkatan_jam"));
            keberangkatan.setWaktu(jsonData.getString("keberangkatan_waktu"));

            Lambung lambung = new Lambung();
            lambung.setId(Integer.parseInt(jsonData.getString("lambung_id")));
            lambung.setNama(jsonData.getString("lambung_nama"));

            Kelas_armada kelas_armada = new Kelas_armada();
            kelas_armada.setId(Integer.parseInt(jsonData.getString("kelas_armada_id")));
            kelas_armada.setNama_kelas(jsonData.getString("kelas_armada_nama"));
            kelas_armada.setJenis_seat(jsonData.getInt("kelas_armada_jenis_seat"));
            kelas_armada.setJenis_seat(0);

            lambung.setKelas_armada(kelas_armada);

            TbTrayek trayek = new TbTrayek();
            trayek.setId(Integer.parseInt(jsonData.getString("trayek_id")));
            trayek.setNama_trayek(jsonData.getString("trayek_nama"));
            trayek.setNama_laporan(jsonData.getString("trayek_nama_laporan"));

            keberangkatan.setTrayek(trayek);
            keberangkatan.setLambung(lambung);

            Asal asal = new Asal();
            asal.setId(Integer.parseInt(jsonData.getString("asal_id")));
            asal.setNama_agen(jsonData.getString("asal_nama_agen"));
            asal.setKode_agen(jsonData.getString("asal_kode_agen"));

            Tujuan tujuan = new Tujuan();
            tujuan.setId(Integer.parseInt(jsonData.getString("tujuan_id")));
            tujuan.setNama_agen(jsonData.getString("tujuan_nama_agen"));
            tujuan.setKode_agen(jsonData.getString("tujuan_kode_agen"));

            PembayranDetail pembayranDetail = new PembayranDetail();
            pembayranDetail.setId(Integer.parseInt(jsonData.getString("id")));
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
            pembayranDetail.setStatusBayar(jsonData.getString("status_bayar"));
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
            adapter = new TiketAdapter(arrayList, TicketDoneAct.this);
            rvResult.setAdapter(adapter);

            container.setVisibility(View.GONE);

            rvResult.setVisibility(View.VISIBLE);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}