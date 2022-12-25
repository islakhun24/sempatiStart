package sempati.star.app.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import sempati.star.app.R;
import sempati.star.app.adapter.KeberangkatanAdapter;
import sempati.star.app.constants.URLs;
import sempati.star.app.models.Agen;
import sempati.star.app.models.RefKelaArmada;
import sempati.star.app.models.RefLambung;
import sempati.star.app.models.TblKeberangkatan;
import sempati.star.app.models.TblTrayek;
import sempati.star.app.models.Trayek;
import sempati.star.app.services.SharedPrefManager;
import sempati.star.app.services.VolleySingleton;

public class TiketResultAct extends AppCompatActivity {
    LinearLayout btnBeli;
    ImageView back;
    int tujuanAgenId, asalAgenId, jumlahKursi;
    String tanggal, tanggalString, dari, ke, jumlahKursiString;
    SharedPrefManager sharedPrefManager;
    ArrayList<Trayek> trayekArrayList = new ArrayList<>();
    private static RecyclerView.Adapter adapter;
    ShimmerFrameLayout container;
    RecyclerView rvResult;
    private RecyclerView.LayoutManager layoutManager;
    TextView tvDari, tvKe, tvTanggal, tvKursi;
    LinearLayout empty;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tiket_result);

        rvResult = (RecyclerView) findViewById(R.id.rvResult);
        rvResult.setHasFixedSize(true);
        tvDari = findViewById(R.id.tvDari);
        tvKe = findViewById(R.id.tvKe);
        tvTanggal = findViewById(R.id.tvTanggal);
        tvKursi = findViewById(R.id.tvKursi);
        empty = findViewById(R.id.empty);
        container = (ShimmerFrameLayout) findViewById(R.id.shimmer_view_container);
        container.startShimmer();

        layoutManager = new LinearLayoutManager(this);
        rvResult.setLayoutManager(layoutManager);
        rvResult.setItemAnimator(new DefaultItemAnimator());

        btnBeli = findViewById(R.id.btnBeli);
        back = findViewById(R.id.back);
        sharedPrefManager = new SharedPrefManager(TiketResultAct.this);
        tujuanAgenId = getIntent().getIntExtra("tujuanAgenId", 0);
        asalAgenId = getIntent().getIntExtra("asalAgenId", 0);
        jumlahKursi = getIntent().getIntExtra("jumlahKursi", 0);
        jumlahKursiString = String.valueOf(jumlahKursi);
        tanggal = getIntent().getStringExtra("tanggal");
        tanggalString = getIntent().getStringExtra("tanggalString");
        dari = getIntent().getStringExtra("dari");
        ke = getIntent().getStringExtra("ke");

        tvDari.setText(dari);
        tvKe.setText(ke);
        tvTanggal.setText(tanggalString);
        tvKursi.setText(jumlahKursi + " Penumpang");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        fetchDataKeberangkatan();
    }

    void fetchDataKeberangkatan(){
        container.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.SELECT_KEBERANGKATAN_TIKET,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("Tiket Result: ", response);
                        try {
                            //converting response to json object
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i <jsonArray.length(); i++){
                                JSONObject object = jsonArray.getJSONObject(i);
                                JSONObject jsonObjectKeberangkatan = jsonArray.getJSONObject(i).getJSONObject("tbl_keberangkatan");
                                JSONObject jsonObjectRefLambung = jsonArray.getJSONObject(i).getJSONObject("tbl_keberangkatan").getJSONObject("ref_lambung");
                                JSONObject jsonObjectRefKelasArmada = jsonArray.getJSONObject(i).getJSONObject("tbl_keberangkatan").getJSONObject("ref_lambung").getJSONObject("ref_kelas_armada");
                                JSONObject jsonObjectTrayek = jsonArray.getJSONObject(i).getJSONObject("tbl_trayek");
                                JSONObject jsonAsal = jsonArray.getJSONObject(i).getJSONObject("tbl_trayek").getJSONObject("asal");
                                JSONObject jsonTujuan = jsonArray.getJSONObject(i).getJSONObject("tbl_trayek").getJSONObject("tujuan");

                                RefKelaArmada kelaArmada = new RefKelaArmada();
                                kelaArmada.setNamaKelas( jsonObjectRefKelasArmada.getString("nama_kelas"));
                                kelaArmada.setJumlahSeat(jsonObjectRefKelasArmada.getString("jumlah_seat"));

                                RefLambung refLambung = new RefLambung();
                                refLambung.setNama(jsonObjectRefLambung.getString("nama"));
                                refLambung.setRefKelaArmada(kelaArmada);

                                TblKeberangkatan keberangkatan = new TblKeberangkatan();
                                keberangkatan.setId(jsonObjectKeberangkatan.getInt("id"));
                                keberangkatan.setWaktu(jsonObjectKeberangkatan.getString("waktu"));
                                keberangkatan.setJam(jsonObjectKeberangkatan.getString("jam"));
                                keberangkatan.setRefLambung(refLambung);

                                Agen agenAsal = new Agen();
                                agenAsal.setNamaAgen(jsonAsal.getString("nama_agen"));
                                agenAsal.setKodeAgn(jsonAsal.getString("kode_agen"));
                                agenAsal.setId(jsonAsal.getInt("id"));

                                Agen agenTujuan = new Agen();
                                agenTujuan.setNamaAgen(jsonTujuan.getString("nama_agen"));
                                agenTujuan.setKodeAgn(jsonTujuan.getString("kode_agen"));
                                agenTujuan.setId(jsonTujuan.getInt("id"));

                                TblTrayek tblTrayek = new TblTrayek();
                                tblTrayek.setJamBerangkat(jsonObjectTrayek.getString("jam_berangkat"));
                                tblTrayek.setNamaTrayek(jsonObjectTrayek.getString("nama_trayek"));
                                tblTrayek.setAsal(agenAsal);
                                tblTrayek.setTujuan(agenTujuan);

                                Trayek trayek = new Trayek();
                                trayek.setTujuanAgenId(object.getInt("tujuan_agen_id"));
                                trayek.setHargaTrayek(object.getInt("harga_trayek"));
                                trayek.setPerubahanHarga(object.getInt("perubahan_harga"));
                                trayek.setJadwalHarga(object.getInt("jadwal_harga"));
                                trayek.setHargaAwal(object.getInt("harga_awal"));
                                trayek.setStatus(object.getString("status"));
                                trayek.setTblKeberangkatan(keberangkatan);
                                trayek.setTblTrayek(tblTrayek);
                                trayek.setTblKeberangkatan(keberangkatan);

                                trayekArrayList.add(trayek);

                            }
                            adapter = new KeberangkatanAdapter(trayekArrayList, TiketResultAct.this, jumlahKursi, jumlahKursiString);
                            rvResult.setAdapter(adapter);
                        } catch (JSONException e) {
                            Log.e("TAG", e.toString());
                            e.printStackTrace();

                        }
                        if(trayekArrayList.size()!=0){
                            rvResult.setVisibility(View.VISIBLE);
                            empty.setVisibility(View.GONE);
                        }else {
                            rvResult.setVisibility(View.GONE);
                            empty.setVisibility(View.VISIBLE);
                        }
                        container.setVisibility(View.GONE);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        rvResult.setVisibility(View.GONE);
                        empty.setVisibility(View.VISIBLE);
                        container.setVisibility(View.GONE);
//                        NetworkResponse response = error.networkResponse;
//                        try {
//                            String responseData = new String(response.data, "UTF-8");
////                            JSONObject object = new JSONObject(responseData);
////                            dialogWidget.errorDialog(LoginAct.this, "Proses Gagal",  object.getString("message"));
//                            Log.e("TAG VolleyError", responseData);
//                        } catch (UnsupportedEncodingException e) {
//                            e.printStackTrace();
//                        }
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
                params.put("tujuanAgenId", String.valueOf(tujuanAgenId));
                params.put("tanggal", tanggal);
                params.put("asalAgenId", String.valueOf(asalAgenId));
                return params;
            }

        };

        VolleySingleton.getInstance(TiketResultAct.this).addToRequestQueue(stringRequest);
    }
}