package sempati.star.app.fragment;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
import com.dantsu.escposprinter.EscPosPrinter;
import com.dantsu.escposprinter.connection.bluetooth.BluetoothConnection;
import com.dantsu.escposprinter.connection.bluetooth.BluetoothPrintersConnections;
import com.dantsu.escposprinter.exceptions.EscPosBarcodeException;
import com.dantsu.escposprinter.exceptions.EscPosConnectionException;
import com.dantsu.escposprinter.exceptions.EscPosEncodingException;
import com.dantsu.escposprinter.exceptions.EscPosParserException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import sempati.star.app.BuildConfig;
import sempati.star.app.R;
import sempati.star.app.constants.URLs;
import sempati.star.app.databinding.FragmentReportBinding;
import sempati.star.app.databinding.FragmentTransaksiBinding;
import sempati.star.app.models.Agen;
import sempati.star.app.services.SharedPrefManager;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReportFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReportFrag extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ReportFrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ReportFrag.
     */
    // TODO: Rename and change types and number of parameters
    public static ReportFrag newInstance(String param1, String param2) {
        ReportFrag fragment = new ReportFrag();
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

    FragmentReportBinding v;
    SharedPrefManager sharedPrefManager;
    BluetoothConnection connection;
    EscPosPrinter printer;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = FragmentReportBinding.inflate(inflater, container, false);

        sharedPrefManager = new SharedPrefManager(getContext());

        getDetail();
        v.tvWebManajemen.setText("www.example.com");
        v.tvVersiApk.setText("Versi Apk : "+BuildConfig.VERSION_NAME);
        v.btnLogout.setOnClickListener(x -> {
            sharedPrefManager.logout();
        });
        v.btnTestPrinter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connection = BluetoothPrintersConnections.selectFirstPaired();
                if (connection != null) {
                    if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.BLUETOOTH}, 1);
                    } else if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.BLUETOOTH_ADMIN) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.BLUETOOTH_ADMIN}, 1);
                    } else if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S && ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.BLUETOOTH_CONNECT}, 1);
                    } else if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S && ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.BLUETOOTH_SCAN}, 1);
                    } else {
                        try {

                            printer = new EscPosPrinter(connection, 203, 48f, 32);
                            printer.printFormattedText("[L]======================== \n");
                            printer.printFormattedText("[C]Test Print \n");
                            printer.printFormattedText("[L]======================== \n");

                        } catch (EscPosConnectionException e) {
                            e.printStackTrace();
                        } catch (EscPosEncodingException e) {
                            e.printStackTrace();
                        } catch (EscPosBarcodeException e) {
                            e.printStackTrace();
                        } catch (EscPosParserException e) {
                            e.printStackTrace();
                        }
                    }
                }
                else {
                    Toast.makeText(getActivity(), "No printer was connected!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return v.getRoot();
    }

    private void getDetail(){
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Mengambil Data ...");
        progressDialog.show();
        StringRequest jsonObjectRequest = new StringRequest(Request.Method.GET, URLs.DETAIL_AKUN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("response get nik : ", response);
                try {
                    //converting response to json object
                    JSONObject obj = new JSONObject(response);
                    v.tvNamaAgen.setText("Nama Agen : "+ obj.getString("full_name"));
                    String address = obj.getString("address").toString();
                    String phone = obj.getString("phone").toString();
                    if(address.equalsIgnoreCase("null")){
                        v.tvAlamat.setText("Alamat : -");
                    }else {
                        v.tvAlamat.setText("Alamat : "+obj.getString("address"));
                    }
                    if(phone.equalsIgnoreCase("null")){
                        v.tvNoTelp.setText("No Telp : 0");
                    }else {
                        v.tvNoTelp.setText("No Telp : "+obj.getString("phone"));
                    }

                } catch (JSONException e) {
                    Log.e("TAG", e.toString());
                    e.printStackTrace();

                }
                progressDialog.dismiss();
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("VolleyError", error.toString());
                progressDialog.dismiss();
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

}