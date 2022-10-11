package sempati.star.app.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import dev.shreyaspatil.MaterialDialog.MaterialDialog;
import dev.shreyaspatil.MaterialDialog.interfaces.DialogInterface;
import sempati.star.app.R;
import sempati.star.app.adapter.KeberangkatanAdapter;
import sempati.star.app.adapter.SeatAdapter;
import sempati.star.app.constants.URLs;
import sempati.star.app.models.Agen;
import sempati.star.app.models.IsColumn;
import sempati.star.app.models.Kursi;
import sempati.star.app.models.RefKelaArmada;
import sempati.star.app.models.RefLambung;
import sempati.star.app.models.TblKeberangkatan;
import sempati.star.app.models.TblTrayek;
import sempati.star.app.models.Trayek;
import sempati.star.app.models.User;
import sempati.star.app.services.AndroidSharedPref;
import sempati.star.app.services.SharedPrefManager;
import sempati.star.app.services.VolleySingleton;
import sempati.star.app.utils.Device;

public class SeatAct extends AppCompatActivity implements SeatAdapter.OnShareClickedListener{
    Button btnBayar;
    int keberankatanId, asalAgenId, tujuanAgenId, jumlahPenumpang,hargaTiket, perubahanHarga, jadwalHarga, hargaAwal;
    TextView namaKelas, tvDari, tvDari2, tvKe, tvKe2, tvSisaKursi, tvHarga, tvHargaTotal;
    ImageView back;
    SharedPrefManager sharedPrefManager;
    ArrayList<Kursi.Seat> seatArrayList = new ArrayList<>();
    RecyclerView rvSeat;
    SeatAdapter seatAdapter;
    AndroidSharedPref androidSharedPref;
    private RecyclerView.LayoutManager layoutManager;
    ProgressBar progressBar;
    String deviceId ;
    Button btnBatal;
    ShimmerFrameLayout container;
    @Override
    public void onBackPressed() {
        new MaterialDialog.Builder(this)
                .setAnimation(R.raw.dissapointed)
                .setTitle("Batalkan Booking?") // You can also send title like R.string.from_resources
                .setMessage("Apakah anda yakin ingin membatalkan semua proses booking?")
                .setCancelable(false)
                .setPositiveButton("Ya", new MaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        deleteBooking();
                        dialogInterface.dismiss();
                        finish();
                    }
                })
                .setNegativeButton("Tidak", new MaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        dialogInterface.dismiss();
                    }
                })
                .build().show();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat);
        androidSharedPref = new AndroidSharedPref(this);
        sharedPrefManager = new SharedPrefManager(this);
        keberankatanId = getIntent().getIntExtra("keberankatanId", 0);
        asalAgenId = getIntent().getIntExtra("asalAgenId", 0);
        tujuanAgenId = getIntent().getIntExtra("tujuanAgenId", 0);
        jumlahPenumpang = getIntent().getIntExtra("jumlahPenumpang", 0);
        hargaTiket = getIntent().getIntExtra("hargaTiket", 0);
        perubahanHarga = getIntent().getIntExtra("perubahanHarga", 0);
        jadwalHarga = getIntent().getIntExtra("jadwalHarga", 0);
        hargaAwal = getIntent().getIntExtra("hargaAwal", 0);

        container = (ShimmerFrameLayout) findViewById(R.id.shimmer_view_container);

        deviceId = android.provider.Settings.Secure.getString(
                getApplication().getApplicationContext().getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
        progressBar = findViewById(R.id.progersBar);
        rvSeat = findViewById(R.id.rvSeat);
        btnBatal = findViewById(R.id.btnBatal);
        tvDari = findViewById(R.id.tvDari);
        tvDari2 = findViewById(R.id.tvDari2);
        tvKe = findViewById(R.id.tvKe);
        tvKe2 = findViewById(R.id.tvKe2);
        tvHarga = findViewById(R.id.tvHarga);
        tvSisaKursi = findViewById(R.id.tvSisaKursi);
        tvHargaTotal = findViewById(R.id.tvHargaTotal);
        tvDari.setText(getIntent().getStringExtra("dari"));
        tvDari2.setText(getIntent().getStringExtra("dari2"));
        tvKe.setText(getIntent().getStringExtra("ke"));
        tvKe2.setText(getIntent().getStringExtra("ke2"));
        tvHarga.setText("Harga : "+getIntent().getStringExtra("rupiah"));
        int hargatotal = Integer.valueOf(getIntent().getStringExtra("rupiahNoFormat")) * Integer.valueOf(getIntent().getStringExtra("jumlahKursiString"));
        tvHargaTotal.setText("Total Bayar : "+formatRupiah(Double.parseDouble(String.valueOf(hargatotal))));
        tvSisaKursi.setText(getIntent().getStringExtra("jumlahKursiString")+" kursi");

        layoutManager = new LinearLayoutManager(this);
        rvSeat.setLayoutManager(layoutManager);
        rvSeat.setItemAnimator(new DefaultItemAnimator());
        btnBatal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialDialog.Builder(SeatAct.this)
//                .setAnimation(R.raw.dissapointed)
                .setTitle("Batalkan Booking?") // You can also send title like R.string.from_resources
                .setMessage("Apakah anda yakin ingin membatalkan semua proses booking?")
                .setCancelable(false)
                .setPositiveButton("Ya", new MaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        deleteBooking();
                        dialogInterface.dismiss();
                    }
                })
                .setNegativeButton("Tidak", new MaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        dialogInterface.dismiss();
                    }
                })
                .build().show();

            }
        });
        back = findViewById(R.id.back);
        btnBayar = findViewById(R.id.btnBayar);
        namaKelas = findViewById(R.id.namaKelas);
        btnBayar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent i = new Intent( SeatAct.this, PembayaranAct.class);
               i.putExtra("keberangkatanId", keberankatanId);
               i.putExtra("android_id", deviceId);
               i.putExtra("from", "not fame");
               startActivity(i);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialDialog.Builder(SeatAct.this)
//                        .setAnimation(R.raw.dissapointed)
                        .setTitle("Batalkan Booking?") // You can also send title like R.string.from_resources
                        .setMessage("Apakah anda yakin ingin membatalkan semua proses booking?")
                        .setCancelable(false)
                        .setPositiveButton("Ya", new MaterialDialog.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                deleteBooking();
                                finish();
                            }
                        })
                        .setNegativeButton("Tidak", new MaterialDialog.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                dialogInterface.dismiss();
                            }
                        })
                        .build().show();
            }
        });

        fetchDataKursi();
    }

    void fetchDataKursi(){
        container.startShimmer();
        container.setVisibility(View.VISIBLE);
        seatArrayList.clear();
        Log.e("TAG", "fetchDataKursi: fame " +keberankatanId + " "+androidSharedPref.getAndroidID());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.SELECT_VIEW_KURSI_TIKET+"/"+keberankatanId+"/"+androidSharedPref.getAndroidID(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("onResponse: ", response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject jsonObjectDetail = jsonObject.getJSONObject("detail");
                            JSONArray jsonArrayData = jsonObject.getJSONArray("data");
                            Kursi kursi = new Kursi();
                            // DETAIL
                            Kursi.Detail detail = new Kursi.Detail();
                            detail.setId(jsonObjectDetail.getInt("id"));
                            detail.setJam(jsonObjectDetail.getString("jam"));
                            detail.setTanggal(jsonObjectDetail.getString("tanggal"));

                            //DETAIL -> REF LAMBUNG
                            JSONObject jsonObjectRefLambung = jsonObjectDetail.getJSONObject("ref_lambung");
                            RefLambung refLambung = new RefLambung();
                            refLambung.setNama(jsonObjectRefLambung.getString("nama"));
                            refLambung.setId(jsonObjectRefLambung.getInt("id"));
                            detail.setRefLambung(refLambung);

                            //DETAIL -> REF LAMBUNG -> KELAS ARMADA
                            JSONObject jsonObjectKelasArmade = jsonObjectRefLambung.getJSONObject("ref_kelas_armada");
                            RefKelaArmada kelaArmada = new RefKelaArmada();
                            kelaArmada.setJumlahSeat(jsonObjectKelasArmade.getString("jumlah_seat"));
                            kelaArmada.setNamaKelas(jsonObjectKelasArmade.getString("nama_kelas"));
                            kelaArmada.setId(jsonObjectKelasArmade.getInt("id"));

                            //DETAIL -> TBL TRAYEK
                            JSONObject jsonObjectTrayek = jsonObjectDetail.getJSONObject("tbl_trayek");
                            TblTrayek tblTrayek = new TblTrayek();
                            tblTrayek.setId(jsonObjectTrayek.getInt("id"));
                            tblTrayek.setNamaTrayek(jsonObjectTrayek.getString("nama_trayek"));
                            tblTrayek.setNamaLaporan(jsonObjectTrayek.getString("nama_laporan"));

                            //SET DATA DETAIL
                            refLambung.setRefKelaArmada(kelaArmada);
                            detail.setRefLambung(refLambung);
                            detail.setTblTrayek(tblTrayek);

                            // DATA
                            for (int i=0; i <jsonArrayData.length(); i ++){

                                JSONObject object = jsonArrayData.getJSONObject(i);
                                Kursi.Seat seat = new Kursi.Seat();
                                seat.setId(object.getInt("id"));
                                seat.setKelasArmadaId(object.getInt("kelas_armada_id"));
                                seat.setNamaDenah(object.getString("nama_denah"));
                                seat.setBaris(object.getInt("baris"));
                                seat.setKolom1(object.getString("kolom1"));
                                seat.setNamaKursi1(object.getString("nama_kursi1"));
                                seat.setKolom2(object.getString("kolom2"));
                                seat.setNamaKursi2(object.getString("nama_kursi2"));
                                seat.setKolom3(object.getString("kolom3"));
                                seat.setNamaKursi3(object.getString("nama_kursi3"));
                                seat.setKolom4(object.getString("kolom4"));
                                seat.setNamaKursi4(object.getString("nama_kursi4"));
                                seat.setKolom5(object.getString("kolom5"));
                                seat.setNamaKursi5(object.getString("nama_kursi5"));

                                JSONObject objectKolumn1 = object.getJSONObject("isKolumn1");
                                IsColumn isColumn1 = new IsColumn();
                                isColumn1.setMe(objectKolumn1.getBoolean("isMe"));
                                isColumn1.setStatus(objectKolumn1.getString("status"));

                                JSONObject objectKolumn2 = object.getJSONObject("isKolumn2");
                                IsColumn isColumn2 = new IsColumn();
                                isColumn2.setMe(objectKolumn2.getBoolean("isMe"));
                                isColumn2.setStatus(objectKolumn2.getString("status"));

                                JSONObject objectKolumn3 = object.getJSONObject("isKolumn3");
                                IsColumn isColumn3 = new IsColumn();
                                isColumn3.setMe(objectKolumn3.getBoolean("isMe"));
                                isColumn3.setStatus(objectKolumn3.getString("status"));


                                JSONObject objectKolumn4 = object.getJSONObject("isKolumn4");
                                IsColumn isColumn4 = new IsColumn();
                                isColumn4.setMe(objectKolumn4.getBoolean("isMe"));
                                isColumn4.setStatus(objectKolumn4.getString("status"));


                                JSONObject objectKolumn5 = object.getJSONObject("isKolumn5");
                                IsColumn isColumn5 = new IsColumn();
                                isColumn5.setMe(objectKolumn5.getBoolean("isMe"));
                                isColumn5.setStatus(objectKolumn5.getString("status"));

                                seat.setIsKolumn1(isColumn1);
                                seat.setIsKolumn2(isColumn2);
                                seat.setIsKolumn3(isColumn3);
                                seat.setIsKolumn4(isColumn4);
                                seat.setIsKolumn5(isColumn5);
                                seatArrayList.add(seat);
                            }
                            kursi.setDetail(detail);
                            kursi.setSeat(seatArrayList);
                            Comparator<Kursi.Seat> comparator = new Comparator<Kursi.Seat>() {
                                @Override
                                public int compare(Kursi.Seat movie, Kursi.Seat t1) {
                                    return String.valueOf(movie.getId()).compareTo(String.valueOf(t1.getId()));
                                }
                            };

// ordered by genre
                            Collections.sort(seatArrayList, comparator);
                            namaKelas.setText(kursi.getDetail().getRefLambung().getNama());
                            seatAdapter = new SeatAdapter(seatArrayList, SeatAct.this, String.valueOf(keberankatanId), SeatAct.this);
                            rvSeat.setAdapter(seatAdapter);
                            container.setVisibility(View.GONE);
                            rvSeat.setVisibility(View.VISIBLE);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
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

        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                60000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    @Override
    public void ShareClicked(String noKursi, String namaKursi) {
        new MaterialDialog.Builder(this)
//                .setAnimation(R.raw.booking)
                .setTitle("Booking Kursi?") // You can also send title like R.string.from_resources
                .setMessage("Apakah anda yakin ingin memboking kursi nomor "+noKursi + "?")
                .setCancelable(false)
                .setPositiveButton("Booking", new MaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {

                        dialogInterface.dismiss();
                        postBooking(noKursi, namaKursi);
                    }
                })
                .setNegativeButton("Batal", new MaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        dialogInterface.dismiss();
                    }
                })
                .build().show();


    }

    void postBooking(String noKursi, String nama_kursi){
        rvSeat.setVisibility(View.GONE);
        container.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.BOOKING_SEAT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("TAG SS", response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if(!success){
                                new MaterialDialog.Builder(SeatAct.this)
//                                        .setAnimation(R.raw.dissapointed)
                                        .setTitle("Gagal Booking")
                                        .setMessage(jsonObject.getString("message").toString())
                                        .setCancelable(false)
                                        .setPositiveButton("OK", new MaterialDialog.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int which) {
                                                dialogInterface.dismiss();
                                            }
                                        })
                                        .setNegativeButton("Batal", new MaterialDialog.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int which) {
                                                dialogInterface.dismiss();
                                            }
                                        })
                                        .build().show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        container.setVisibility(View.GONE);
                        fetchDataKursi();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("TAG ERR", error.toString());
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
                Map<String, String> params = new HashMap<>();
                params.put("keberangkatan_id", String.valueOf(keberankatanId));
                params.put("agen_id", String.valueOf(asalAgenId));
                params.put("tujuan_id", String.valueOf(tujuanAgenId));
                params.put("no_kursi", String.valueOf(noKursi));
                params.put("nama_kursi", String.valueOf(nama_kursi));
                params.put("android_id", deviceId);
                params.put("jumlah", String.valueOf(jumlahPenumpang));
                params.put("harga_tiket", String.valueOf(hargaTiket));
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }
    void deleteBooking(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.DELETE_BATAL_PENJUALAN_TIKET,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("deleteBooking", response);
                        progressBar.setVisibility(View.GONE);
                        fetchDataKursi();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("TAG ERR", error.toString());
                        progressBar.setVisibility(View.GONE);

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
                params.put("android_id", String.valueOf(deviceId));
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    private String formatRupiah(Double number){
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        return formatRupiah.format(number);
    }
}