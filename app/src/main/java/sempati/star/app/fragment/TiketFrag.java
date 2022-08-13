package sempati.star.app.fragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import sempati.star.app.R;
import sempati.star.app.activity.HomeAct;
import sempati.star.app.activity.LoginAct;
import sempati.star.app.activity.TiketResultAct;
import sempati.star.app.adapter.AgenAdapter;
import sempati.star.app.adapter.ListFameAdapter;
import sempati.star.app.constants.URLs;
import sempati.star.app.models.Agen;
import sempati.star.app.models.User;
import sempati.star.app.services.SharedPrefManager;
import sempati.star.app.services.VolleySingleton;
import sempati.star.app.utils.RecyclerItemClickListener;

public class TiketFrag extends Fragment {
    Button btnCari;
    SharedPrefManager sharedPrefManager;
    EditText etKeberankatan, etTujuan, etTanggal, etJumlahKursi;
    ArrayList<Agen> agenArrayList = new ArrayList<>();
    ArrayList<Agen> agenKeberankatanArrayList = new ArrayList<>();
    ArrayList<String> agens = new ArrayList<>();
    Dialog dialog;
    int tujuanAgenId, keberangkatanAgenId;
    String tanggal, tanggalString;

    public String getTanggalString() {
        return tanggalString;
    }

    public void setTanggalString(String tanggalString) {
        this.tanggalString = tanggalString;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public int getTujuanAgenId() {
        return tujuanAgenId;
    }

    public void setTujuanAgenId(int tujuanAgenId) {
        this.tujuanAgenId = tujuanAgenId;
    }

    public int getKeberangkatanAgenId() {
        return keberangkatanAgenId;
    }

    public void setKeberangkatanAgenId(int keberangkatanAgenId) {
        this.keberangkatanAgenId = keberangkatanAgenId;
    }

    //======================================================

    private RecyclerView recyclerView;
    private LinearLayoutManager llm;
    private DividerItemDecoration did;
    private RecyclerView.Adapter adapter;

    ArrayList<Agen> lokasiKeberangkatanList = new ArrayList<>();
    ArrayList<Agen> tujuanKeberangkatanList = new ArrayList<>();
    String query_tujuan;

    public String getQuery_tujuan() {
        return query_tujuan;
    }

    public void setQuery_tujuan(String query_tujuan) {
        this.query_tujuan = query_tujuan;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_tiket, container, false);


        sharedPrefManager = new SharedPrefManager(getContext());
        etKeberankatan = v.findViewById(R.id.etKeberangkatan);
        etKeberankatan.setInputType(InputType.TYPE_NULL);
        etTujuan = v.findViewById(R.id.etTujuan);
        etTujuan.setInputType(InputType.TYPE_NULL);
        etJumlahKursi = v.findViewById(R.id.etJumlahKursi);
        etTanggal = v.findViewById(R.id.etTanggal);
        etTanggal.setInputType(InputType.TYPE_NULL);
        etTujuan.setEnabled(false);
        etTanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                        etTanggal.setText(datePatams);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            simpleDateFormat = new SimpleDateFormat("EEEE");
                        }
                        date = new Date(year, monthOfYear, dayOfMonth - 1);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            dayString = simpleDateFormat.format(date) + ", ";
                        }
                        datePatams = formadDateParams(year,monthOfYear+1, dayOfMonth);
                        dateString = formateDateString(year,monthOfYear+1, dayOfMonth,dayString);
                        etTanggal.setText(dateString);
                        setTanggal(datePatams);
                        setTanggalString(dateString);
                    }
                }, yy, mm, dd);
                datePicker.show();
            }
        });
        btnCari = v.findViewById(R.id.btnCari);
        btnCari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tujuanId = getTujuanAgenId();
                int keberangkatanId = getKeberangkatanAgenId();
                String jumlah =  TextUtils.isEmpty(etJumlahKursi.getText().toString()) ? "0" : etJumlahKursi.getText().toString();
                int jumlahKursi = Integer.parseInt(jumlah);
                String tanggal = getTanggal();

                if (TextUtils.isEmpty(etTanggal.getText().toString().trim())) {
                    etTanggal.setError("Tanggal tidak boleh kosong");
                    return;
                }
                if (TextUtils.isEmpty(etTujuan.getText().toString().trim())) {
                    etTujuan.setError("Tujuan tidak boleh kosong");
                    return;
                }
                if (TextUtils.isEmpty(etJumlahKursi.getText().toString().trim())) {
                    etJumlahKursi.setError("Jumlah Kursi tidak boleh kosong");
                    return;
                }
                if (TextUtils.isEmpty(etKeberankatan.getText().toString().trim())) {
                    etKeberankatan.setError("Keberangkatan tidak boleh kosong");
                    return;
                }

                Intent i = new Intent(getActivity(), TiketResultAct.class);
                i.putExtra("tujuanAgenId", tujuanId);
                i.putExtra("tanggalString", tanggalString);
                i.putExtra("dari", etKeberankatan.getText().toString().trim());
                i.putExtra("ke", etTujuan.getText().toString().trim());
                i.putExtra("tanggal", tanggal);
                i.putExtra("asalAgenId", keberangkatanId);
                i.putExtra("jumlahKursi", jumlahKursi);
                startActivity(i);

//                Log.e(String.valueOf(tujuanId), String.valueOf(keberangkatanId));
            }
        });

        etKeberankatan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogKeberangkatan();
            }
        });

        etTujuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogTujuan(keberangkatanAgenId);
            }
        });
        return v;
    }

    void fetchKeberangkatan(ListView listView, TextView textView, ShimmerFrameLayout shimmerFrameLayout){
        agenArrayList.clear();
        shimmerFrameLayout.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.SELECT_AGEN_BY_ID_TIKET,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting response to json object
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i <jsonArray.length(); i++){
                                JSONObject object = jsonArray.getJSONObject(i);
                                Agen agen = new Agen();
                                agen.setId(object.getInt("id"));
                                agen.setNamaAgen(object.getString("nama_agen"));
                                agens.add(object.getString("nama_agen"));
                                agenArrayList.add(agen);

                            }


                        } catch (JSONException e) {
                            Log.e("TAG", e.toString());
                            e.printStackTrace();

                        }
                        if(agenArrayList.size()!=0){
                            listView.setVisibility(View.VISIBLE);
                            textView.setVisibility(View.GONE);
                        }else {
                            listView.setVisibility(View.GONE);
                            textView.setVisibility(View.VISIBLE);
                        }

                        shimmerFrameLayout.setVisibility(View.GONE);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        shimmerFrameLayout.setVisibility(View.GONE);
                        listView.setVisibility(View.GONE);
                        textView.setVisibility(View.VISIBLE);
                        Log.e( "onErrorResponse: ", error.toString());
                        Toast.makeText(getActivity(), "Koneksi gagal", Toast.LENGTH_SHORT).show();
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
        };

        VolleySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);

    }

    void fetchTujuan(int id, ListView listView, TextView textView, ShimmerFrameLayout shimmerFrameLayout){
        agenKeberankatanArrayList.clear();
        shimmerFrameLayout.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.SELECT_AGEN_TUJUAN_NOT_ID_TIKET+"/"+id,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("onResponse: ", response);
                        try {
                            //converting response to json object
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i <jsonArray.length(); i++){
                                JSONObject object = jsonArray.getJSONObject(i);
                                Agen agen = new Agen();
                                agen.setId(object.getInt("id"));
                                agen.setNamaAgen(object.getString("nama"));
                                agens.add(object.getString("nama"));
                                agenKeberankatanArrayList.add(agen);

                            }

                        } catch (JSONException e) {
                            Log.e("TAG", e.toString());
                            e.printStackTrace();

                        }
                        if(agenKeberankatanArrayList.size()!=0){
                            listView.setVisibility(View.VISIBLE);
                            textView.setVisibility(View.GONE);
                        }else {
                            listView.setVisibility(View.GONE);
                            textView.setVisibility(View.VISIBLE);
                        }

                        shimmerFrameLayout.setVisibility(View.GONE);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listView.setVisibility(View.GONE);
                        textView.setVisibility(View.VISIBLE);
                        shimmerFrameLayout.setVisibility(View.GONE);
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
        };

        VolleySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);

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

    private void showDialogKeberangkatan(){
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.dialog_searchable_spinner);
        dialog.setCancelable(true);

//        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
//        lp.copyFrom(Objects.requireNonNull(dialog.getWindow()).getAttributes());
//        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
//        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        recyclerView = dialog.findViewById(R.id.recyclerView);
        adapter = new ListFameAdapter(getContext(), lokasiKeberangkatanList);
        llm = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        did = new DividerItemDecoration(recyclerView.getContext(), llm.getOrientation());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(llm);
        recyclerView.addItemDecoration(did);
        recyclerView.setAdapter(adapter);

        TextView dialogTitle = dialog.findViewById(R.id.tvTitle);
        TextView emptyView = dialog.findViewById(R.id.empty);
        dialogTitle.setText("Pilih Lokasi Keberangkatan");
        ShimmerFrameLayout shimmerFrameLayout = dialog.findViewById(R.id.shimmer_view_container);
        dialog.show();

//        final ProgressDialog progressDialog = new ProgressDialog(getContext());
//        progressDialog.setMessage("Mengambil Data ...");
//        progressDialog.show();
        shimmerFrameLayout.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Agen agen = lokasiKeberangkatanList.get(position);
                etKeberankatan.setText(agen.getNamaAgen());
                keberangkatanAgenId = agen.getId();
                etTujuan.setEnabled(true);
                dialog.hide();
            }
        }));

        StringRequest jsonObjectRequest = new StringRequest(Request.Method.GET, URLs.SELECT_AGEN_BY_ID_TIKET, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("response get nik : ", response);
                lokasiKeberangkatanList.clear();
                try {
                    //converting response to json object
                    JSONArray jsonArray = new JSONArray(response);
                    if(jsonArray.length() > 0) {
                        emptyView.setVisibility(View.GONE);
                        shimmerFrameLayout.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        for (int i = 0; i <jsonArray.length(); i++){
                            JSONObject object = jsonArray.getJSONObject(i);
                            Agen agen = new Agen();
                            agen.setId(object.getInt("id"));
                            agen.setNamaAgen(object.getString("nama_agen"));
                            lokasiKeberangkatanList.add(agen);
                        }
                    }else {
                        emptyView.setVisibility(View.VISIBLE);
                        shimmerFrameLayout.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.GONE);
                    }

                } catch (JSONException e) {
                    Log.e("TAG", e.toString());
                    e.printStackTrace();

                }
                adapter.notifyDataSetChanged();
//                progressDialog.dismiss();
                recyclerView.setVisibility(View.VISIBLE);
                shimmerFrameLayout.setVisibility(View.GONE);
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("VolleyError", error.toString());
//                progressDialog.dismiss();
                shimmerFrameLayout.setVisibility(View.GONE);
                recyclerView.setVisibility(View.GONE);
                dialog.hide();
                if (error instanceof TimeoutError) {
                    Toast.makeText(getContext(), "Server sedang sibuk, siahkan coba kembali 😊", Toast.LENGTH_SHORT).show();
                }else if(error instanceof NoConnectionError){
                    Toast.makeText(getContext(), "koneksi tidak tersedia, pastikan koneksi internet tersedia dan coba kembali 😊", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError
                        || error instanceof ServerError
                        || error instanceof NetworkError){
                    Toast.makeText(getContext(), "Koneksi Error. silakan coba lagi 😊", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "VolleyError : " + error.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
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

        EditText editText=dialog.findViewById(R.id.edit_text);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                recyclerView = dialog.findViewById(R.id.recyclerView);
                adapter = new ListFameAdapter(getContext(), lokasiKeberangkatanList);
                llm = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
                did = new DividerItemDecoration(recyclerView.getContext(), llm.getOrientation());
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(llm);
                recyclerView.addItemDecoration(did);
                recyclerView.setAdapter(adapter);
                final ProgressDialog progressDialog = new ProgressDialog(getContext());
                progressDialog.setMessage("Mengambil Data ...");
                progressDialog.show();
                recyclerView.setVisibility(View.GONE);
                emptyView.setVisibility(View.GONE);
//                shimmerFrameLayout.setVisibility(View.VISIBLE);
                StringRequest jsonObjectRequest = new StringRequest(Request.Method.GET, URLs.SELECT_AGEN_BY_ID_TIKET+"?q="+s, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("response get nik : ", response);
                        lokasiKeberangkatanList.clear();
                        try {
                            //converting response to json object
                            JSONArray jsonArray = new JSONArray(response);
                            if(jsonArray.length() > 0) {
                                shimmerFrameLayout.setVisibility(View.GONE);
                                recyclerView.setVisibility(View.VISIBLE);
                                emptyView.setVisibility(View.GONE);
                                progressDialog.dismiss();
                                for (int i = 0; i <jsonArray.length(); i++){
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    Agen agen = new Agen();
                                    agen.setId(object.getInt("id"));
                                    agen.setNamaAgen(object.getString("nama_agen"));
                                    lokasiKeberangkatanList.add(agen);
                                }
                            }else {
                                emptyView.setVisibility(View.VISIBLE);
                                recyclerView.setVisibility(View.GONE);
                                progressDialog.dismiss();
                            }

                        } catch (JSONException e) {
                            Log.e("TAG", e.toString());
                            e.printStackTrace();

                        }
                        adapter.notifyDataSetChanged();
                        progressDialog.dismiss();
                        recyclerView.setVisibility(View.VISIBLE);
                        shimmerFrameLayout.setVisibility(View.GONE);
                    }

                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("VolleyError", error.toString());
//                        progressDialog.dismiss();
                        shimmerFrameLayout.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.GONE);
                        dialog.hide();
                        if (error instanceof TimeoutError) {
                            Toast.makeText(getContext(), "Server sedang sibuk, siahkan coba kembali 😊", Toast.LENGTH_SHORT).show();
                        }else if(error instanceof NoConnectionError){
                            Toast.makeText(getContext(), "koneksi tidak tersedia, pastikan koneksi internet tersedia dan coba kembali 😊", Toast.LENGTH_SHORT).show();
                        } else if (error instanceof AuthFailureError
                                || error instanceof ServerError
                                || error instanceof NetworkError){
                            Toast.makeText(getContext(), "Koneksi Error. silakan coba lagi 😊", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "VolleyError : " + error.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }) {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> headers = new HashMap<>();
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

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

//        SearchView searchView = dialog.findViewById(R.id.searchView);
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                lokasiKeberangkatanList.clear();
//                recyclerView = dialog.findViewById(R.id.recyclerView);
//                adapter = new ListFameAdapter(getContext(), lokasiKeberangkatanList);
//                llm = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
//                did = new DividerItemDecoration(recyclerView.getContext(), llm.getOrientation());
//                recyclerView.setHasFixedSize(true);
//                recyclerView.setLayoutManager(llm);
//                recyclerView.addItemDecoration(did);
//                recyclerView.setAdapter(adapter);
//                final ProgressDialog progressDialog = new ProgressDialog(getContext());
//                progressDialog.setMessage("Mengambil Data ...");
//                progressDialog.show();
//                StringRequest jsonObjectRequest = new StringRequest(Request.Method.GET, URLs.SELECT_AGEN_BY_ID_TIKET+"?q="+query, new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        Log.e("response get nik : ", response);
//                        try {
//                            //converting response to json object
//                            JSONArray jsonArray = new JSONArray(response);
//                            for (int i = 0; i <jsonArray.length(); i++){
//                                JSONObject object = jsonArray.getJSONObject(i);
//                                Agen agen = new Agen();
//                                agen.setId(object.getInt("id"));
//                                agen.setNamaAgen(object.getString("nama_agen"));
//                                lokasiKeberangkatanList.add(agen);
//                            }
//
//
//                        } catch (JSONException e) {
//                            Log.e("TAG", e.toString());
//                            e.printStackTrace();
//
//                        }
//                        adapter.notifyDataSetChanged();
//                        progressDialog.dismiss();
//                        recyclerView.setVisibility(View.VISIBLE);
//                        shimmerFrameLayout.setVisibility(View.GONE);
//                    }
//
//                }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Log.e("VolleyError", error.toString());
//                        progressDialog.dismiss();
//                        shimmerFrameLayout.setVisibility(View.GONE);
//                        recyclerView.setVisibility(View.GONE);
//                        dialog.hide();
//                        if (error instanceof TimeoutError) {
//                            Toast.makeText(getContext(), "Server sedang sibuk, siahkan coba kembali 😊", Toast.LENGTH_SHORT).show();
//                        }else if(error instanceof NoConnectionError){
//                            Toast.makeText(getContext(), "koneksi tidak tersedia, pastikan koneksi internet tersedia dan coba kembali 😊", Toast.LENGTH_SHORT).show();
//                        } else if (error instanceof AuthFailureError
//                                || error instanceof ServerError
//                                || error instanceof NetworkError){
//                            Toast.makeText(getContext(), "Koneksi Error. silakan coba lagi 😊", Toast.LENGTH_SHORT).show();
//                        } else {
//                            Toast.makeText(getContext(), "VolleyError : " + error.toString(), Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                }) {
//                    @Override
//                    public Map<String, String> getHeaders() throws AuthFailureError {
//                        Map<String, String> headers = new HashMap<>();
//                        headers.put("x-access-token" , sharedPrefManager.getUser().getAccessToken().toString());
//                        return headers;
//                    }
//                };
//                jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
//                        60000,
//                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//                RequestQueue requestQueue = Volley.newRequestQueue(getContext());
//                requestQueue.add(jsonObjectRequest);
//                return true;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                if(newText.equals("")){
//                    this.onQueryTextSubmit("");
//                }
//                return true;
//            }
//        });
    }

    private void showDialogTujuan(int id) {
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.dialog_searchable_spinner);
        dialog.setCancelable(true);

//        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
//        lp.copyFrom(Objects.requireNonNull(dialog.getWindow()).getAttributes());
//        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
//        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        recyclerView = dialog.findViewById(R.id.recyclerView);
        adapter = new ListFameAdapter(getContext(), tujuanKeberangkatanList);
        llm = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        did = new DividerItemDecoration(recyclerView.getContext(), llm.getOrientation());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(llm);
        recyclerView.addItemDecoration(did);
        recyclerView.setAdapter(adapter);

        TextView dialogTitle = dialog.findViewById(R.id.tvTitle);
        TextView emptyView = dialog.findViewById(R.id.empty);
        dialogTitle.setText("Pilih Lokasi Tujuan");
        ShimmerFrameLayout shimmerFrameLayout = dialog.findViewById(R.id.shimmer_view_container);
        dialog.show();

        shimmerFrameLayout.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Agen agen = tujuanKeberangkatanList.get(position);
                etTujuan.setText(agen.getNamaAgen());
                tujuanAgenId = agen.getId();
                dialog.hide();
            }
        }));

        StringRequest jsonObjectRequest = new StringRequest(Request.Method.GET, URLs.SELECT_AGEN_TUJUAN_NOT_ID_TIKET+"/"+id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("response get nik : ", response);
                tujuanKeberangkatanList.clear();
                try {
                    //converting response to json object
                    JSONArray jsonArray = new JSONArray(response);
                    if(jsonArray.length() > 0){
                        emptyView.setVisibility(View.GONE);
                        shimmerFrameLayout.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        for (int i = 0; i <jsonArray.length(); i++){
                            JSONObject object = jsonArray.getJSONObject(i);
                            Agen agen = new Agen();
                            agen.setId(object.getInt("id"));
                            agen.setNamaAgen(object.getString("nama"));
                            tujuanKeberangkatanList.add(agen);
                        }
                    }else {
                        emptyView.setVisibility(View.VISIBLE);
                        shimmerFrameLayout.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.GONE);
                    }

                } catch (JSONException e) {
                    Log.e("TAG", e.toString());
                    e.printStackTrace();

                }
                adapter.notifyDataSetChanged();
//                progressDialog.dismiss();
                recyclerView.setVisibility(View.VISIBLE);
                shimmerFrameLayout.setVisibility(View.GONE);
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("VolleyError", error.toString());
//                progressDialog.dismiss();
                shimmerFrameLayout.setVisibility(View.GONE);
                recyclerView.setVisibility(View.GONE);
                dialog.hide();
                if (error instanceof TimeoutError) {
                    Toast.makeText(getContext(), "Server sedang sibuk, siahkan coba kembali 😊", Toast.LENGTH_SHORT).show();
                }else if(error instanceof NoConnectionError){
                    Toast.makeText(getContext(), "koneksi tidak tersedia, pastikan koneksi internet tersedia dan coba kembali 😊", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError
                        || error instanceof ServerError
                        || error instanceof NetworkError){
                    Toast.makeText(getContext(), "Koneksi Error. silakan coba lagi 😊", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "VolleyError : " + error.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
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

        EditText editText=dialog.findViewById(R.id.edit_text);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                setQuery_tujuan(s.toString().trim());
            }

            @Override
            public void afterTextChanged(Editable s) {
                recyclerView = dialog.findViewById(R.id.recyclerView);
                adapter = new ListFameAdapter(getContext(), tujuanKeberangkatanList);
                llm = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
                did = new DividerItemDecoration(recyclerView.getContext(), llm.getOrientation());
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(llm);
                recyclerView.addItemDecoration(did);
                recyclerView.setAdapter(adapter);
                final ProgressDialog progressDialog = new ProgressDialog(getContext());
                progressDialog.setMessage("Mengambil Data ...");
                progressDialog.show();
                recyclerView.setVisibility(View.GONE);
                emptyView.setVisibility(View.GONE);
//                shimmerFrameLayout.setVisibility(View.VISIBLE);
                StringRequest jsonObjectRequest = new StringRequest(Request.Method.GET, URLs.SELECT_AGEN_TUJUAN_NOT_ID_TIKET+"/"+id+"?q="+getQuery_tujuan(), new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("response get nik : ", response);
                        tujuanKeberangkatanList.clear();
                        try {
                            //converting response to json object
                            JSONArray jsonArray = new JSONArray(response);
                            if(jsonArray.length() > 0){
                                emptyView.setVisibility(View.GONE);
//                                shimmerFrameLayout.setVisibility(View.GONE);
                                recyclerView.setVisibility(View.VISIBLE);
                                progressDialog.dismiss();
                                for (int i = 0; i <jsonArray.length(); i++){
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    Agen agen = new Agen();
                                    agen.setId(object.getInt("id"));
                                    agen.setNamaAgen(object.getString("nama"));
                                    tujuanKeberangkatanList.add(agen);
                                }
                            }else {
                                emptyView.setVisibility(View.VISIBLE);
                                shimmerFrameLayout.setVisibility(View.GONE);
                                recyclerView.setVisibility(View.GONE);
                                progressDialog.dismiss();
                            }

                        } catch (JSONException e) {
                            Log.e("TAG", e.toString());
                            e.printStackTrace();

                        }
                        adapter.notifyDataSetChanged();
                        progressDialog.dismiss();
                        recyclerView.setVisibility(View.VISIBLE);
                        shimmerFrameLayout.setVisibility(View.GONE);
                    }

                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("VolleyError", error.toString());
//                        progressDialog.dismiss();
                        shimmerFrameLayout.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.GONE);
                        dialog.hide();
                        if (error instanceof TimeoutError) {
                            Toast.makeText(getContext(), "Server sedang sibuk, siahkan coba kembali 😊", Toast.LENGTH_SHORT).show();
                        }else if(error instanceof NoConnectionError){
                            Toast.makeText(getContext(), "koneksi tidak tersedia, pastikan koneksi internet tersedia dan coba kembali 😊", Toast.LENGTH_SHORT).show();
                        } else if (error instanceof AuthFailureError
                                || error instanceof ServerError
                                || error instanceof NetworkError){
                            Toast.makeText(getContext(), "Koneksi Error. silakan coba lagi 😊", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "VolleyError : " + error.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }) {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> headers = new HashMap<>();
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
        });
//        SearchView searchView = dialog.findViewById(R.id.searchView);
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                tujuanKeberangkatanList.clear();
//                recyclerView = dialog.findViewById(R.id.recyclerView);
//                adapter = new ListFameAdapter(getContext(), tujuanKeberangkatanList);
//                llm = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
//                did = new DividerItemDecoration(recyclerView.getContext(), llm.getOrientation());
//                recyclerView.setHasFixedSize(true);
//                recyclerView.setLayoutManager(llm);
//                recyclerView.addItemDecoration(did);
//                recyclerView.setAdapter(adapter);
//                final ProgressDialog progressDialog = new ProgressDialog(getContext());
//                progressDialog.setMessage("Mengambil Data ...");
//                progressDialog.show();
//                StringRequest jsonObjectRequest = new StringRequest(Request.Method.GET, URLs.SELECT_AGEN_TUJUAN_NOT_ID_TIKET+"/"+id+"?q="+query, new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        Log.e("response get nik : ", response);
//                        try {
//                            //converting response to json object
//                            JSONArray jsonArray = new JSONArray(response);
//                            for (int i = 0; i <jsonArray.length(); i++){
//                                JSONObject object = jsonArray.getJSONObject(i);
//                                Agen agen = new Agen();
//                                agen.setId(object.getInt("id"));
//                                agen.setNamaAgen(object.getString("nama"));
//                                tujuanKeberangkatanList.add(agen);
//
//                            }
//
//                        } catch (JSONException e) {
//                            Log.e("TAG", e.toString());
//                            e.printStackTrace();
//
//                        }
//                        adapter.notifyDataSetChanged();
//                        progressDialog.dismiss();
//                        recyclerView.setVisibility(View.VISIBLE);
//                        shimmerFrameLayout.setVisibility(View.GONE);
//                    }
//
//                }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Log.e("VolleyError", error.toString());
//                        progressDialog.dismiss();
//                        shimmerFrameLayout.setVisibility(View.GONE);
//                        recyclerView.setVisibility(View.GONE);
//                        dialog.hide();
//                        if (error instanceof TimeoutError) {
//                            Toast.makeText(getContext(), "Server sedang sibuk, siahkan coba kembali 😊", Toast.LENGTH_SHORT).show();
//                        }else if(error instanceof NoConnectionError){
//                            Toast.makeText(getContext(), "koneksi tidak tersedia, pastikan koneksi internet tersedia dan coba kembali 😊", Toast.LENGTH_SHORT).show();
//                        } else if (error instanceof AuthFailureError
//                                || error instanceof ServerError
//                                || error instanceof NetworkError){
//                            Toast.makeText(getContext(), "Koneksi Error. silakan coba lagi 😊", Toast.LENGTH_SHORT).show();
//                        } else {
//                            Toast.makeText(getContext(), "VolleyError : " + error.toString(), Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                }) {
//                    @Override
//                    public Map<String, String> getHeaders() throws AuthFailureError {
//                        Map<String, String> headers = new HashMap<>();
//                        headers.put("x-access-token" , sharedPrefManager.getUser().getAccessToken().toString());
//                        return headers;
//                    }
//                };
//                jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
//                        60000,
//                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//                RequestQueue requestQueue = Volley.newRequestQueue(getContext());
//                requestQueue.add(jsonObjectRequest);
//                return true;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                if(newText.equals("")){
//                    this.onQueryTextSubmit("");
//                }
//                return true;
//            }
//        });
    }
}