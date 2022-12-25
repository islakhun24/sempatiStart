package sempati.star.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import sempati.star.app.R;
import sempati.star.app.constants.URLs;
import sempati.star.app.services.VolleySingleton;
import sempati.star.app.widget.DialogWidget;

public class AktivasiAct extends AppCompatActivity {
    ImageView back;
    Button btnAktivasi;
    EditText etKodeAktivasi, etPassword1, etPassword2;
    ProgressBar progressBar;
    private DialogWidget dialogWidget;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aktivasi);
        btnAktivasi = findViewById(R.id.btnAktivasi);
        etKodeAktivasi = findViewById(R.id.etKodeAktivasi);
        etPassword1 = findViewById(R.id.etPassword1);
        etPassword2 = findViewById(R.id.etPassword2);
        progressBar = findViewById(R.id.progressBar);
        back = findViewById(R.id.back);
        dialogWidget = new DialogWidget();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnAktivasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String kodeAktivasi = etKodeAktivasi.getText().toString().trim();
                String password1 = etPassword1.getText().toString().trim();
                String password2 = etPassword2.getText().toString().trim();

                if (TextUtils.isEmpty(kodeAktivasi)) {
                    etKodeAktivasi.setError("Kode AKtivasi tidak boleh kosong");
                    etKodeAktivasi.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(password1)) {
                    etPassword1.setError("Password tidak boleh kosong");
                    etPassword1.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(password2)) {
                    etPassword2.setError("Ulangi Password tidak boleh kosong");
                    etPassword2.requestFocus();
                    return;
                }
                if (password1.length() < 6) {
                    etPassword1.setError("Password kurang dari 6 karakter");
                    etPassword1.requestFocus();
                    return;
                }
                if (password2.length() < 6) {
                    etPassword2.setError("Ulangi Password kurang dari 6 karakter");
                    etPassword2.requestFocus();
                    return;
                }

                submitAktivasi(kodeAktivasi, password1, password2);
            }
        });
    }

    void submitAktivasi(String kodeAtivasi, String password1, String password2){
        progressBar.setVisibility(View.VISIBLE);
        btnAktivasi.setEnabled(false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.AKTIVASI,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressBar.setVisibility(View.GONE);
                        Log.d("LOGIN RESPONSE", response);
                        try {
                            //converting response to json object
                            JSONObject obj = new JSONObject(response);
                            String message = obj.getString("message");
                            dialogWidget.succesDialog(AktivasiAct.this, "Berhasil", message, new Intent(AktivasiAct.this, LoginAct.class));
                        } catch (JSONException e) {
                            Log.e("TAG JSONException", e.toString());
                            e.printStackTrace();
                            progressBar.setVisibility(View.GONE);
                            btnAktivasi.setEnabled(true);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        NetworkResponse response = error.networkResponse;
                        try {
                            String responseData = new String(response.data,"UTF-8");
                            JSONObject object = new JSONObject(responseData);
                            dialogWidget.errorDialog(AktivasiAct.this, "Proses Gagal",  object.getString("message"));
                            Log.e("TAG VolleyError", object.getString("message"));
                        } catch (UnsupportedEncodingException | JSONException e) {
                            e.printStackTrace();
                        }
                        progressBar.setVisibility(View.GONE);
                        btnAktivasi.setEnabled(true);
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("kode_unik", kodeAtivasi);
                params.put("password", password1);
                params.put("password2", password2);
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }


}