package sempati.star.app.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sempati.star.app.R;
import sempati.star.app.adapter.BookingAdapter;
import sempati.star.app.adapter.TransaksiAdapter;
import sempati.star.app.constants.URLs;
import sempati.star.app.databinding.FragmentBookingBinding;
import sempati.star.app.databinding.FragmentTransaksiBinding;
import sempati.star.app.models.TransaksiModel;
import sempati.star.app.services.SharedPrefManager;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BookingFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BookingFrag extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BookingFrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BookingFrag.
     */
    // TODO: Rename and change types and number of parameters
    public static BookingFrag newInstance(String param1, String param2) {
        BookingFrag fragment = new BookingFrag();
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

    FragmentBookingBinding v;

    List<TransaksiModel> listBooking;

    private LinearLayoutManager llm;
    private DividerItemDecoration did;
    private RecyclerView.Adapter adapter;

    SharedPrefManager sharedPrefManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = FragmentBookingBinding.inflate(inflater, container, false);

        listBooking = new ArrayList<>();
        sharedPrefManager = new SharedPrefManager(getContext());

        getBooking();

        return v.getRoot();
    }

    private void getBooking(){
        v.shimmerViewContainer.setVisibility(View.VISIBLE);

        adapter = new BookingAdapter(getContext(), listBooking);
        llm = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        did = new DividerItemDecoration(v.rvResult.getContext(), llm.getOrientation());
        v.rvResult.setHasFixedSize(true);
        v.rvResult.setLayoutManager(llm);
//        v.recyclerViewTransaksiTerakhir.addItemDecoration(did);
        v.rvResult.setAdapter(adapter);

        StringRequest jsonObjectRequest = new StringRequest(Request.Method.GET, URLs.LIST_BOOKING, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("response get booking : ", response);
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
                                transaksiModel.setAsal_nama_agen(objectAsal.getString("nama_agen"));
                                transaksiModel.setTujuan_nama_agen(objectTujuan.getString("nama_agen"));
                                transaksiModel.setAsal_kode_agen(objectAsal.getString("kode_agen"));
                                transaksiModel.setTujuan_kode_agen(objectTujuan.getString("kode_agen"));
                                transaksiModel.setTrayek_nama(objecttrayek.getString("nama_trayek"));
                                transaksiModel.setKeberangkatan_jam(objectkeberangkatan.getString("jam"));
                                transaksiModel.setKelas_armada_nama(objectKelasArmada.getString("nama_kelas"));
                                transaksiModel.setKelas_armada_jumlah_seat(objectKelasArmada.getString("jumlah_seat"));
                                transaksiModel.setStatus_bayar(object.getString("status_bayar"));

                                listBooking.add(transaksiModel);
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
}