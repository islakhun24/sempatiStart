package sempati.star.app.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import dev.shreyaspatil.MaterialDialog.MaterialDialog;
import dev.shreyaspatil.MaterialDialog.interfaces.DialogInterface;
import sempati.star.app.R;
import sempati.star.app.adapter.KeberangkatanAdapter;
import sempati.star.app.adapter.PenumpangAdapter;
import sempati.star.app.constants.URLs;
import sempati.star.app.models.Asal;
import sempati.star.app.models.Keberangkatan;
import sempati.star.app.models.Kelas_armada;
import sempati.star.app.models.Lambung;
import sempati.star.app.models.PembayranDetail;
import sempati.star.app.models.TbTrayek;
import sempati.star.app.models.Trayek;
import sempati.star.app.models.Tujuan;
import sempati.star.app.services.SharedPrefManager;
import sempati.star.app.services.VolleySingleton;

public class PembayaranAct extends AppCompatActivity {
    SharedPrefManager sharedPrefManager;
    Gson gson;
    Button btnBayar, btnBooking;
    int status;
    String android_id;
    int keberankatanId;
    ArrayList <PembayranDetail> arrayList = new ArrayList<>();
    TextView tvDari, tvDari2, tvKe, tvKe2, tvJam, tvSisaKursi, tvClass;
    ShimmerFrameLayout container;
    RecyclerView rvResult;
    private RecyclerView.LayoutManager layoutManager;
    LinearLayout empty;
    private static RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pembayaran);
        gson = new Gson();
        keberankatanId = getIntent().getIntExtra("keberangkatanId", 0);
        android_id = getIntent().getStringExtra("android_id");
        sharedPrefManager = new SharedPrefManager(this);
        status = sharedPrefManager.getUser().getStatusUser();
        btnBayar = findViewById(R.id.btnBayar);
        btnBooking = findViewById(R.id.btnBooking);
        rvResult = (RecyclerView) findViewById(R.id.rvResult);
        rvResult.setHasFixedSize(true);
        empty = findViewById(R.id.empty);
        container = (ShimmerFrameLayout) findViewById(R.id.shimmer_view_container);
        container.startShimmer();

        layoutManager = new LinearLayoutManager(this);
        rvResult.setLayoutManager(layoutManager);
        rvResult.setItemAnimator(new DefaultItemAnimator());
        tvClass = findViewById(R.id.tvClass);
        tvDari = findViewById(R.id.tvDari);
        tvDari2 = findViewById(R.id.tvDari2);
        tvKe = findViewById(R.id.tvKe);
        tvKe2 = findViewById(R.id.tvKe2);
        tvJam = findViewById(R.id.tvJam);
        tvSisaKursi = findViewById(R.id.tvSisaKursi);
        btnBayar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(PembayaranAct.this, TicketDoneAct.class));
                postDataPenumpang();
            }
        });
        btnBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(PembayaranAct.this, TicketDoneAct.class));
                bookingDataPenumpang();
            }
        });
        fechData();

    }

    void fechData(){
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Mengambil Data ...");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.BUY_TICKET_DETAIL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("TAG SS", response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean status = jsonObject.getBoolean("success");
                            if(status){
                                progressDialog.dismiss();
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                JSONObject jsonDetail = jsonObject.getJSONObject("detail");

                                for (int i =0; i <jsonArray.length(); i++){
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
                                    kelas_armada.setJenis_seat(jsonKelasArmada.getInt("jumlah_seat"));

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

                                adapter = new PenumpangAdapter(arrayList, PembayaranAct.this, jsonDetail.getString("statusUser"));
                                rvResult.setAdapter(adapter);
                                adapter.notifyDataSetChanged();

                                tvDari.setText(jsonDetail.getJSONObject("asal").getString("nama_agen"));
                                tvDari2.setText(jsonDetail.getJSONObject("asal").getString("kode_agen"));
                                tvKe.setText(jsonDetail.getJSONObject("tujuan").getString("nama_agen"));
                                tvKe2.setText(jsonDetail.getJSONObject("tujuan").getString("kode_agen"));

                                tvJam.setText(jsonDetail.getJSONObject("keberangkatan").getString("jam")+ " "+ jsonDetail.getJSONObject("keberangkatan").getString("waktu"));
                                tvSisaKursi.setText("Jumlah "+jsonDetail.getJSONObject("keberangkatan").getJSONObject("lambung").getJSONObject("kelas_armada").getString("jumlah_seat")+" kursi");
                                tvClass.setText(jsonDetail.getJSONObject("keberangkatan").getJSONObject("lambung").getString("nama"));
                            }
                            if(arrayList.size()!=0){
                                rvResult.setVisibility(View.VISIBLE);
                                empty.setVisibility(View.GONE);
                                progressDialog.dismiss();
                            }else {
                                rvResult.setVisibility(View.GONE);
                                empty.setVisibility(View.VISIBLE);
                                progressDialog.dismiss();
                            }
                            container.setVisibility(View.GONE);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            rvResult.setVisibility(View.GONE);
                            empty.setVisibility(View.VISIBLE);
                            container.setVisibility(View.GONE);
                            progressDialog.dismiss();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("TAG ERR", error.toString());
                        rvResult.setVisibility(View.GONE);
                        empty.setVisibility(View.VISIBLE);
                        container.setVisibility(View.GONE);
                        progressDialog.dismiss();

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
                Map<String, String> params = new HashMap<>();
                if(getIntent().getStringExtra("from").equalsIgnoreCase("fame")){

                    params.put("id", getIntent().getStringExtra("id"));
                }else {
                    params.put("keberangkatan_id", String.valueOf(keberankatanId));
                    params.put("android_id", String.valueOf(android_id));
                }

                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }

    void postDataPenumpang(){
        for (int i=0;i<arrayList.size();i++){
            if(arrayList.get(i).getPenumpang_umum().equalsIgnoreCase("-")){
                Toast.makeText(PembayaranAct.this,"Nama penumpang harus diisi!", Toast.LENGTH_LONG).show();
                return;
            }
            if(arrayList.get(i).getPenumpang_hp().equalsIgnoreCase("0")){
                Toast.makeText(PembayaranAct.this,"Nomor HP penumpang harus diisi!", Toast.LENGTH_LONG).show();
                return;
            }
        }
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.POST_DATA_PENUMPANG,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("TAG SS", response);

                        try {
                            Gson gson = new Gson();
                            String json = gson.toJson(arrayList);
                            JSONObject object = new JSONObject(response);
                            if (object.getBoolean("success")==true){
                                Intent i = new Intent(PembayaranAct.this, TicketDoneAct.class);
                                i.putExtra("data", json);
                                i.putExtra("from", "not fame");
                                startActivity(i);
                            }else {
                                new MaterialDialog.Builder(PembayaranAct.this)
                                        .setAnimation(R.raw.dissapointed)
                                        .setTitle("Booking Gagal?") // You can also send title like R.string.from_resources
                                        .setMessage("Maaf, proses booking anda gagal?")
                                        .setCancelable(false)
                                        .setPositiveButton("Ok", new MaterialDialog.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int which) {
                                                dialogInterface.dismiss();
                                            }
                                        })
                                        .setNegativeButton("Cancel", new MaterialDialog.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int which) {
                                                dialogInterface.dismiss();
                                            }
                                        })
                                        .build().show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            new MaterialDialog.Builder(PembayaranAct.this)
                                    .setAnimation(R.raw.dissapointed)
                                    .setTitle("Booking Gagal?") // You can also send title like R.string.from_resources
                                    .setMessage("Maaf, proses booking anda gagal?")
                                    .setCancelable(false)
                                    .setPositiveButton("Ok", new MaterialDialog.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int which) {
                                            dialogInterface.dismiss();
                                        }
                                    })
                                    .setNegativeButton("Cancel", new MaterialDialog.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int which) {
                                            dialogInterface.dismiss();
                                        }
                                    })
                                    .build().show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("TAG ERR", error.toString());
                        new MaterialDialog.Builder(PembayaranAct.this)
                                .setAnimation(R.raw.dissapointed)
                                .setTitle("Booking Gagal?") // You can also send title like R.string.from_resources
                                .setMessage("Maaf, proses booking anda gagal?")
                                .setCancelable(false)
                                .setPositiveButton("Ok", new MaterialDialog.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int which) {
                                        dialogInterface.dismiss();
                                    }
                                })
                                .setNegativeButton("Cancel", new MaterialDialog.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int which) {
                                        dialogInterface.dismiss();
                                    }
                                })
                                .build().show();

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
                Map<String, String> params = new HashMap<>();
                String json = gson.toJson(arrayList);
                params.put("penumpangArray", json);
                params.put("android_id", String.valueOf(android_id));
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    void bookingDataPenumpang(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.BOOKING_DATA_PENUMPANG,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("TAG SS", response);

                        try {
                            Gson gson = new Gson();

                            String json = gson.toJson(arrayList);
                            JSONObject object = new JSONObject(response);
                            if (object.getBoolean("success")==true){
                                Intent i = new Intent(PembayaranAct.this, HomeAct.class);
//                                i.putExtra("data", json);
                                startActivity(i);
                            }else {
                                new MaterialDialog.Builder(PembayaranAct.this)
                                        .setAnimation(R.raw.dissapointed)
                                        .setTitle("Booking Gagal?") // You can also send title like R.string.from_resources
                                        .setMessage("Maaf, proses booking anda gagal?")
                                        .setCancelable(false)
                                        .setPositiveButton("Ok", new MaterialDialog.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int which) {
                                                dialogInterface.dismiss();
                                            }
                                        })
                                        .setNegativeButton("Cancel", new MaterialDialog.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int which) {
                                                dialogInterface.dismiss();
                                            }
                                        })
                                        .build().show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            new MaterialDialog.Builder(PembayaranAct.this)
                                    .setAnimation(R.raw.dissapointed)
                                    .setTitle("Booking Gagal?") // You can also send title like R.string.from_resources
                                    .setMessage("Maaf, proses booking anda gagal?")
                                    .setCancelable(false)
                                    .setPositiveButton("Ok", new MaterialDialog.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int which) {
                                            dialogInterface.dismiss();
                                        }
                                    })
                                    .setNegativeButton("Cancel", new MaterialDialog.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int which) {
                                            dialogInterface.dismiss();
                                        }
                                    })
                                    .build().show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("TAG ERR", error.toString());
                        new MaterialDialog.Builder(PembayaranAct.this)
                                .setAnimation(R.raw.dissapointed)
                                .setTitle("Booking Gagal?") // You can also send title like R.string.from_resources
                                .setMessage("Maaf, proses booking anda gagal?")
                                .setCancelable(false)
                                .setPositiveButton("Ok", new MaterialDialog.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int which) {
                                        dialogInterface.dismiss();
                                    }
                                })
                                .setNegativeButton("Cancel", new MaterialDialog.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int which) {
                                        dialogInterface.dismiss();
                                    }
                                })
                                .build().show();

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
                Map<String, String> params = new HashMap<>();
                Gson gson = new Gson();
                String json = gson.toJson(arrayList);
                params.put("penumpangArray", json);
                params.put("android_id", String.valueOf(android_id));
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }
}