package sempati.star.app.fragment;

import android.app.DatePickerDialog;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sempati.star.app.R;
import sempati.star.app.adapter.TransaksiLaporanAdapter;
import sempati.star.app.adapter.TransaksiManifestAdapter;
import sempati.star.app.constants.URLs;
import sempati.star.app.databinding.FragmentLaporanManifestBinding;
import sempati.star.app.databinding.FragmentLaporanTransaksiBinding;
import sempati.star.app.models.TransaksiModel;
import sempati.star.app.services.SharedPrefManager;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LaporanManifestFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LaporanManifestFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LaporanManifestFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LaporanManifestFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LaporanManifestFragment newInstance(String param1, String param2) {
        LaporanManifestFragment fragment = new LaporanManifestFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    FragmentLaporanManifestBinding v;

    List<TransaksiModel> listTransaksi;

    private LinearLayoutManager llm;
    private DividerItemDecoration did;
    private RecyclerView.Adapter adapter;

    SharedPrefManager sharedPrefManager;

    String tanggalAwal, tanggalAwalString;
    String tanggalAkhir, tanggalAkhirString;

    public String getTanggalAwal() {
        return tanggalAwal;
    }

    public void setTanggalAwal(String tanggalAwal) {
        this.tanggalAwal = tanggalAwal;
    }

    public String getTanggalAwalString() {
        return tanggalAwalString;
    }

    public void setTanggalAwalString(String tanggalAwalString) {
        this.tanggalAwalString = tanggalAwalString;
    }

    public String getTanggalAkhir() {
        return tanggalAkhir;
    }

    public void setTanggalAkhir(String tanggalAkhir) {
        this.tanggalAkhir = tanggalAkhir;
    }

    public String getTanggalAkhirString() {
        return tanggalAkhirString;
    }

    public void setTanggalAkhirString(String tanggalAkhirString) {
        this.tanggalAkhirString = tanggalAkhirString;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = FragmentLaporanManifestBinding.inflate(inflater, container, false);

        listTransaksi = new ArrayList<>();
        sharedPrefManager = new SharedPrefManager(getContext());

        v.lnTanggalAwal.setOnClickListener(x -> getDate("awal"));
        v.lnTanggalAkhir.setOnClickListener(x -> getDate("akhir"));
        v.btnCari.setOnClickListener(x -> getTransaksi("tidak ada"));

        getTransaksi("ada");

        return v.getRoot();
    }

    private void getTransaksi(String tanggal){
        listTransaksi.clear();
//        Log.e(TAG, "getTransaksi: "+getTanggalAwal()+"-"+getTanggalAkhir() );
        v.shimmerViewContainer.setVisibility(View.VISIBLE);

        adapter = new TransaksiManifestAdapter(getContext(), listTransaksi);
        llm = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        did = new DividerItemDecoration(v.rvResult.getContext(), llm.getOrientation());
        v.rvResult.setHasFixedSize(true);
        v.rvResult.setLayoutManager(llm);
//        v.recyclerViewTransaksiTerakhir.addItemDecoration(did);
        v.rvResult.setAdapter(adapter);

        String urlfix;
        if(tanggal.equalsIgnoreCase("ada")){
            urlfix = URLs.LIST_LAPORAN_TRANSAKSI;
        }else {
            urlfix = URLs.LIST_LAPORAN_TRANSAKSI+"?tanggal_awal="+getTanggalAwal()+"&tanggal_akhir="+getTanggalAkhir();
        }

        StringRequest jsonObjectRequest = new StringRequest(Request.Method.GET,urlfix , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("response get transaksi : ", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    String success = jsonObject.getString("success");
                    Log.e("TAG", "onResponse: " + success );
                    if (success.equalsIgnoreCase("true")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
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
                                transaksiModel.setStatus_bayar(object.getString("status_bayar"));
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

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(jsonObjectRequest);

    }

    private void getDate(String point) {
        final Calendar calendar = Calendar.getInstance();
        int yy = calendar.get(Calendar.YEAR);
        int mm = calendar.get(Calendar.MONTH);
        int dd = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                SimpleDateFormat simpleDateFormat = null;
                Date date = null;
                String dayString ="";
                Log.d( "onDateSet: ",String.valueOf(dayString));
                String datePatams = "";
                String dateString = "";
                if(point.equalsIgnoreCase("awal")){
                    v.tvTanggal.setText(datePatams);
                }else {
                    v.tvTanggalAkhir.setText(datePatams);
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    simpleDateFormat = new SimpleDateFormat("EEEE");
                }
                date = new Date(year, monthOfYear, dayOfMonth - 1);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    dayString = simpleDateFormat.format(date) + ", ";
                }
                datePatams = formadDateParams(year,monthOfYear+1, dayOfMonth);
                dateString = formateDateString(year,monthOfYear+1, dayOfMonth,dayString);
                if(point.equalsIgnoreCase("awal")){
//                    v.tvTanggal.setText(datePatams);
                    v.tvTanggal.setText(dateString);
                    setTanggalAwal(datePatams);
                    setTanggalAwalString(dateString);
                }else {
//                    v.tvTanggalAkhir.setText(datePatams);
                    v.tvTanggalAkhir.setText(dateString);
                    setTanggalAkhir(datePatams);
                    setTanggalAkhirString(dateString);
                }
            }
        }, yy, mm, dd);
        datePicker.show();
    }

    String formadDateParams (int year, int month, int day){
        String days = String.valueOf(day);
        String months = String.valueOf(month);
        String years = String.valueOf(year);
        if(day<10){
            days = '0'+days;
        }
        if(month<10){
            months = '0'+months;
        }
        return  years + "-" + months + "-" + days;
    }

    String formateDateString(int year, int month, int day, String dayName){
        String months = "", days = String.valueOf(day), years =String.valueOf(year);
        if(month == 1){
            months = "Januari";
        }else if(month == 2){
            months = "Februari";
        }else if(month == 3){
            months = "Maret";
        }else if(month == 4){
            months = "April";
        }else if(month == 5){
            months = "Mei";
        }else if(month == 6){
            months = "Juni";
        }else if(month == 7){
            months = "Juli";
        }else if(month == 8){
            months = "Agustus";
        }else if(month == 9){
            months = "September";
        }else if(month == 10){
            months = "Oktober";
        }else if(month == 11){
            months = "November";
        }else if(month == 12){
            months = "Desember";
        }
        if(day<10){
            days = '0'+days;
        }
        return dayName+ day +" "+ months + " " + years;
    }
}