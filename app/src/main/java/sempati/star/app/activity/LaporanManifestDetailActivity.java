package sempati.star.app.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import sempati.star.app.R;
import sempati.star.app.adapter.TransaksiLaporanDetailAdapter;
import sempati.star.app.adapter.TransaksiManifestDetailAdapter;
import sempati.star.app.constants.URLs;
import sempati.star.app.databinding.ActivityLaporanManifestDetailBinding;
import sempati.star.app.databinding.ActivityLaporanTransaksiDetailBinding;
import sempati.star.app.models.TransaksiModel;
import sempati.star.app.services.SharedPrefManager;

public class LaporanManifestDetailActivity extends AppCompatActivity {

    ActivityLaporanManifestDetailBinding v;

    List<TransaksiModel> listTransaksi;

    private LinearLayoutManager llm;
    private DividerItemDecoration did;
    private RecyclerView.Adapter adapter;

    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        v = ActivityLaporanManifestDetailBinding.inflate(getLayoutInflater());
        setContentView(v.getRoot());

        listTransaksi = new ArrayList<>();
        sharedPrefManager = new SharedPrefManager(this);

        getTransaksi();
    }

    private void getTransaksi(){
        listTransaksi.clear();
//        Log.e(TAG, "getTransaksi: "+getTanggalAwal()+"-"+getTanggalAkhir() );
        v.shimmerViewContainer.setVisibility(View.VISIBLE);

        adapter = new TransaksiManifestDetailAdapter(this, listTransaksi);
        llm = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        did = new DividerItemDecoration(v.rvResult.getContext(), llm.getOrientation());
        v.rvResult.setHasFixedSize(true);
        v.rvResult.setLayoutManager(llm);
//        v.recyclerViewTransaksiTerakhir.addItemDecoration(did);
        v.rvResult.setAdapter(adapter);
        StringRequest jsonObjectRequest = new StringRequest(Request.Method.GET, URLs.LIST_LAPORAN_TRANSAKSI+"?id_keberangkatan="+getIntent().getStringExtra("id_keberangkatan"), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("response get transaksi : ", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    String success = jsonObject.getString("success");
                    Log.e("TAG", "onResponse: " + success );
                    if (success.equalsIgnoreCase("true")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        v.tvTotal.setText("Total : "+formatRupiah(Double.valueOf(jsonObject.getString("total"))));
                        for (int i = 0; i < jsonArray.length(); i++) {
                            try {
                                JSONObject object = jsonArray.getJSONObject(i);
                                JSONObject objectAsal = object.getJSONObject("asal");
                                JSONObject objectTujuan = object.getJSONObject("tujuan");
                                JSONObject objectkeberangkatan = object.getJSONObject("keberangkatan");
                                JSONObject objecttrayek = objectkeberangkatan.getJSONObject("trayek");
                                JSONObject objectLambung = objectkeberangkatan.getJSONObject("lambung");
                                JSONObject objectKelasArmada = objectLambung.getJSONObject("kelas_armada");
                                TransaksiModel transaksiModel = new TransaksiModel();
                                transaksiModel.setId(object.getString("id"));
                                transaksiModel.setHarga_tiket(object.getString("harga_tiket"));
                                transaksiModel.setNo_kursi(object.getString("no_kursi"));
                                transaksiModel.setPenumpang_hp(object.getString("penumpang_hp"));
                                transaksiModel.setPenumpang_umum(object.getString("penumpang_umum"));
                                transaksiModel.setHarga_potongan(object.getString("harga_potongan"));
                                transaksiModel.setAgen_id(object.getString("agen_id"));
                                transaksiModel.setPenumpang_id(object.getString("penumpang_id"));
                                transaksiModel.setKomisi_agen_id(object.getString("komisi_agen_id"));
                                transaksiModel.setPenumpang_umum(object.getString("penumpang_umum"));
                                transaksiModel.setTiba_id(object.getString("tiba_id"));
                                transaksiModel.setStatus(object.getString("status"));
                                transaksiModel.setStatus_migrasi(object.getString("status_migrasi"));
                                transaksiModel.setQrcode(object.getString("qrcode"));
                                transaksiModel.setKet(object.getString("ket"));
                                transaksiModel.setLimit_boking(object.getString("limit_boking"));
                                transaksiModel.setCr_id(object.getString("cr_id"));
                                transaksiModel.setCr_id_pusat(object.getString("cr_id_pusat"));
                                transaksiModel.setCr_datetime(object.getString("cr_datetime"));
                                transaksiModel.setUp_id(object.getString("up_id"));
                                transaksiModel.setUp_datetime(object.getString("up_datetime"));
                                //asal
                                transaksiModel.setAsal_nama_agen(objectAsal.getString("nama_agen"));
                                transaksiModel.setAsal_kode_agen(objectAsal.getString("kode_agen"));
                                transaksiModel.setAsal_id(objectAsal.getString("id"));
                                //tujuan
                                transaksiModel.setTujuan_nama_agen(objectTujuan.getString("nama_agen"));
                                transaksiModel.setTujuan_kode_agen(objectTujuan.getString("kode_agen"));
                                transaksiModel.setTujuan_id(objectTujuan.getString("id"));
                                //trayek
                                transaksiModel.setTrayek_nama(objecttrayek.getString("nama_trayek"));
                                transaksiModel.setTrayek_id(objecttrayek.getString("id"));
                                transaksiModel.setTrayek_nama_laporan(objecttrayek.getString("nama_laporan"));
                                //keberangkatan
                                transaksiModel.setKeberangkatan_jam(objectkeberangkatan.getString("jam"));
                                transaksiModel.setKeberangkatan_id(objectkeberangkatan.getString("id"));
                                transaksiModel.setKeberangkatan_tanggal(objectkeberangkatan.getString("tanggal"));
                                transaksiModel.setKeberangkatan_waktu(objectkeberangkatan.getString("waktu"));
                                //kelas armada
                                transaksiModel.setKelas_armada_nama(objectKelasArmada.getString("nama_kelas"));
                                transaksiModel.setKelas_armada_jumlah_seat(objectKelasArmada.getString("jumlah_seat"));
                                transaksiModel.setKelas_armada_jenis_seat(objectKelasArmada.getString("jenis_seat"));
                                transaksiModel.setKelas_armada_id(objectKelasArmada.getString("id"));
                                //lambung
                                transaksiModel.setLambung_id(objectLambung.getString("id"));
                                transaksiModel.setLambung_nama(objectLambung.getString("nama"));

                                listTransaksi.add(transaksiModel);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    } else {
                        v.shimmerViewContainer.setVisibility(View.GONE);
                        v.empty.setVisibility(View.VISIBLE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("TAG", "onResponse: fame" );
                    v.shimmerViewContainer.setVisibility(View.GONE);
                    v.empty.setVisibility(View.VISIBLE);
                }
                v.rvResult.setVisibility(View.VISIBLE);
                v.rlTotal.setVisibility(View.VISIBLE);
                adapter.notifyDataSetChanged();
                v.shimmerViewContainer.setVisibility(View.GONE);
                v.empty.setVisibility(View.GONE);
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("VolleyError", error.toString());
                v.shimmerViewContainer.setVisibility(View.GONE);
                v.empty.setVisibility(View.VISIBLE);
                if (error instanceof TimeoutError) {
                    v.textOutput.setText("Server sedang sibuk, siahkan coba kembali 😊");
                }else if(error instanceof NoConnectionError){
                    v.textOutput.setText("koneksi tidak tersedia, pastikan koneksi internet tersedia dan coba kembali 😊");
                } else if (error instanceof AuthFailureError
                        || error instanceof ServerError
                        || error instanceof NetworkError){
                    v.textOutput.setText("Koneksi Error. silakan coba lagi 😊");
                } else {
                    v.textOutput.setText("VolleyError : " + error.toString());
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("x-access-token" , sharedPrefManager.getUser().getAccessToken().toString());
                return headers;
            }
        };
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                60000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);

    }

    private String formatRupiah(Double number) {
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        return formatRupiah.format(number);
    }
}