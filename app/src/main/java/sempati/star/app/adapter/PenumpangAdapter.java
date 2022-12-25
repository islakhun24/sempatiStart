package sempati.star.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import sempati.star.app.R;
import sempati.star.app.activity.PembayaranAct;
import sempati.star.app.activity.SeatAct;
import sempati.star.app.constants.URLs;
import sempati.star.app.models.PembayranDetail;
import sempati.star.app.models.Trayek;
import sempati.star.app.services.SharedPrefManager;
import sempati.star.app.services.VolleySingleton;

public class PenumpangAdapter extends RecyclerView.Adapter<PenumpangAdapter.MyViewHolder> {
    String statusUser;
    String statusBayar = "1";
    SharedPrefManager sharedPrefManager;
    private ArrayList<PembayranDetail> pembayranDetails;
    private Context context;
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView noKursi;
        LinearLayout lnPrice;
        EditText nohpPenumpang, namaPenumpang, potonganHarga,etStatusPay;
        public MyViewHolder(View itemView) {
            super(itemView);
            this.nohpPenumpang = (EditText) itemView.findViewById(R.id.nohpPenumpang);
            this.namaPenumpang = (EditText) itemView.findViewById(R.id.namaPenumpang);
            this.potonganHarga = (EditText) itemView.findViewById(R.id.potonganHarga);
            this.noKursi = (TextView) itemView.findViewById(R.id.no_kursi);
            this.lnPrice = (LinearLayout) itemView.findViewById(R.id.lnPrice);
            this.etStatusPay = (EditText) itemView.findViewById(R.id.etStatusPay);


        }
    }

    public PenumpangAdapter(ArrayList<PembayranDetail> pembayranDetails, Context context, String statusUser) {
        this.pembayranDetails = pembayranDetails;
        this.context = context;
        this.statusUser = statusUser;
    }

    @Override
    public PenumpangAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                                int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_penumpang, parent, false);


        PenumpangAdapter.MyViewHolder myViewHolder = new PenumpangAdapter.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final PenumpangAdapter.MyViewHolder holder, final int listPosition) {
        TextView noKursi = holder.noKursi;
        LinearLayout lnPrice = holder.lnPrice;
        EditText nohpPenumpang = holder.nohpPenumpang;
        EditText namaPenumpang = holder.namaPenumpang;
        EditText potonganHarga = holder.potonganHarga;
        EditText etStatusPay = holder.etStatusPay;
        sharedPrefManager = new SharedPrefManager(context);


        if(statusUser.equalsIgnoreCase("1")){
            lnPrice.setVisibility(View.VISIBLE);
        }else {
            lnPrice.setVisibility(View.GONE);
        }
        noKursi.setText(pembayranDetails.get(listPosition).getNo_kursi());

        nohpPenumpang.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                pembayranDetails.get(listPosition).setPenumpang_hp(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        namaPenumpang.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                pembayranDetails.get(listPosition).setPenumpang_umum(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        potonganHarga.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                pembayranDetails.get(listPosition).setHarga_potongan(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        if(sharedPrefManager.getUser().getStatusUser() == 0) {
            statusBayar = "1";
            ubahStatusBayar(sharedPrefManager, String.valueOf(pembayranDetails.get(listPosition).getId()), "1");
            etStatusPay.setText("Lunas");
        }else {
            etStatusPay.setOnClickListener(x -> {
                statusBayar(etStatusPay, listPosition, sharedPrefManager);
            });
        }

    }

    @Override
    public int getItemCount() {
        return pembayranDetails.size();
    }

    private void statusBayar(EditText statusBayarS, final int listPosition, SharedPrefManager sharedPrefManager2){
        String[] pilihan = {
                "Lunas",
                "Panjar",
                "Traveloka",
                "Transit"
        };

        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle("Status Bayar")
                .setItems(pilihan, (dialog, which) -> {
                    switch (which) {
                        case 0:
                        default:
                            statusBayar = "1";
                            ubahStatusBayar(sharedPrefManager2, String.valueOf(pembayranDetails.get(listPosition).getId()), "1");
                            statusBayarS.setText("Lunas");
                            break;
                        case 1:
                            statusBayar = "2";
                            ubahStatusBayar(sharedPrefManager2, String.valueOf(pembayranDetails.get(listPosition).getId()), "2");
                            statusBayarS.setText("Panjar");
                            break;
                        case 2:
                            statusBayar = "3";
                            ubahStatusBayar(sharedPrefManager2, String.valueOf(pembayranDetails.get(listPosition).getId()), "3");
                            statusBayarS.setText("Traveloka");
                            break;
                        case 3:
                            statusBayar = "4";
                            ubahStatusBayar(sharedPrefManager2, String.valueOf(pembayranDetails.get(listPosition).getId()), "4");
                            statusBayarS.setText("Transit");
                            break;
                    }
                });
        alert.show();
    }

    void ubahStatusBayar(SharedPrefManager sharedPrefManager2, String id, String statusBayar){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.POST_DATA_STATUS_BAYAR, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {;
                    JSONObject object = new JSONObject(response);
                    if (object.getBoolean("success")==true) {
                        Log.e("TAG", "onResponse: "+ object.getString("message"));
//                        ((PembayaranAct) context).fechData();
                    }else {
                        Log.e("TAG", "onResponse: "+ object.getJSONObject("message") );
                        Toast.makeText(context, object.getString("message").toString(), Toast.LENGTH_SHORT).show();
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                    Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("TAG ERR", error.toString());
                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("x-access-token" , sharedPrefManager2.getUser().getAccessToken().toString());
                return headers;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", id);
                params.put("status_bayar", statusBayar);
                return params;
            }
        };

        VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);
    }
}