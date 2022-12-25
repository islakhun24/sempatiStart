package sempati.star.app.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
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

import com.dantsu.escposprinter.EscPosPrinter;
import com.dantsu.escposprinter.connection.bluetooth.BluetoothConnection;
import com.dantsu.escposprinter.connection.bluetooth.BluetoothPrintersConnections;
import com.dantsu.escposprinter.exceptions.EscPosConnectionException;
import com.dantsu.escposprinter.textparser.PrinterTextParserImg;
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
    BluetoothConnection connection;
    EscPosPrinter printer;

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
        connection = BluetoothPrintersConnections.selectFirstPaired();
        try {
            printer = new EscPosPrinter(connection, 203, 48f, 32);
        } catch (EscPosConnectionException e) {
            e.printStackTrace();
        }

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
                doPrint();
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
    }
    private Bitmap createBitmapFromLayout(View tv) {
        int spec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        tv.measure(spec, spec);
        tv.layout(0, 0, tv.getMeasuredWidth(), tv.getMeasuredHeight());
        Bitmap b = Bitmap.createBitmap(tv.getMeasuredWidth(), tv.getMeasuredHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        c.translate((-tv.getScrollX()), (-tv.getScrollY()));
        tv.draw(c);
        return b;
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
        if (ContextCompat.checkSelfPermission(TicketDoneAct.this, Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_DENIED)
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
            {
                ActivityCompat.requestPermissions(TicketDoneAct.this, new String[]{Manifest.permission.BLUETOOTH_CONNECT}, 2);
                return;
            }
        }

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
    public static final int PERMISSION_BLUETOOTH = 1;

    public void doPrint() {
        try {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.BLUETOOTH}, TicketDoneAct.PERMISSION_BLUETOOTH);
            } else if (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.BLUETOOTH}, 1);
            } else if (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_ADMIN) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.BLUETOOTH_ADMIN}, 1);
            } else if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S && ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.BLUETOOTH_CONNECT}, 1);
            } else if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S && ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.BLUETOOTH_SCAN}, 1);
            } else {
                BluetoothConnection connection = BluetoothPrintersConnections.selectFirstPaired();
                if (connection != null) {
                    EscPosPrinter printer = new EscPosPrinter(connection, 203, 48f, 32);
                    StringBuilder sb = new StringBuilder();

                    for (int i = 0; i < arrayList.size(); i++){
                        PembayranDetail data = arrayList.get(i);
                        sb.append("[L]\n");
                        sb.append("[L]\n");
                        sb.append("[C]<font size='big'>"+data.getAsalObject().getNama_agen() + " - " + data.getTujuanObject().getNama_agen()+"</font>\n");
                        sb.append("[C]"+data.getKeberangkatanObject().getTanggal() + data.getKeberangkatan().getWaktu()+"\n");
                        sb.append("[L]\n");
                        sb.append("[L]Kelas bus[R]Nomor kursi\n");
                        sb.append("[L]<b>"+data.getKeberangkatanObject().getLambung().getKelas_armada().getNama_kelas().toString()+"</b>[R]<b>"+data.getNo_kursi().toString()+"</b>\n");
                        sb.append("[L]Nama Penumpang[R]Tanggal transaksi\n");
                        sb.append("[L]<b>"+data.getPenumpang_umum()+"</b>[R]<b>"+data.getNo_kursi().toString()+"</b>\n");
                        sb.append("[L]\n");
                        sb.append( "[C]================================\n"  );
                        sb.append("[L]\n");
                        sb.append("[L]<b>"+String.valueOf(data.getId())+"</b>[R]<b>"+data.getNo_kursi()+"</b>\n");
                        sb.append("[L]Nama loket[R]Keterangan pembayaran\n");
                        sb.append("[L]<b>"+data.getKeberangkatanObject().getLambung().getKelas_armada().getNama_kelas().toString()+"</b>[R]<b>"+data.getNo_kursi() != null ? data.getNo_kursi().toString() : "-"+"</b>\n");
                        sb.append("[L]\n");
                        sb.append("[C]Harga\n");
                        sb.append("[C]<b>"+data.getHarga_potongan()+"</b>\n");
                        sb.append("[L]\n");
                        sb.append( "[C]================================\n" );
                        sb.append("[L]\n");
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            sb.append("[C]<img>" + PrinterTextParserImg.bitmapToHexadecimalString(printer,
                                this.getApplicationContext().getResources().getDrawableForDensity(R.drawable.logo,
                                        DisplayMetrics.DENSITY_LOW, getTheme())) + "</img>\n");
                        }
                        sb.append("[L]\n");
                        sb.append("[L]<b>Perhatina: </b> \n");
                        sb.append("[L]1. Lapor tiket maksimal 15 menit sebelum keberangkatan\n");
                        sb.append("[L]2. Dilarang membawa sajam dan segala jenis narkotika \n");
                        sb.append("[L]3. Barang-barang bawaan menjadi tanggung jawab penumpang \n");
                        sb.append("[L]4. Barang bawaan yang melebihi kapasitas barang dikenakan tarif bagasi\n");
                        sb.append("[L]5. Tukarkan tiket untuk makan \n");
                        sb.append("[L]\n");
                        sb.append("[C]<qrcode>"+data.getNo_kursi()+"</qrcode>\n");
                        sb.append("[L]\n");
                        sb.append("[C]<b>Kupon untuk gratis makan</b>\n");
                        sb.append("[L]\n");
                        sb.append("[L]\n");
                        sb.append("[C]--------------------------------\n" );
                    }
                    printer.printFormattedText(String.valueOf(sb));
                } else {
                    Toast.makeText(this, "No printer was connected!", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception e) {
            Log.e("APP", "Can't print", e);
        }
    }
}