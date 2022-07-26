package sempati.star.app.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import sempati.star.app.R;
import sempati.star.app.constants.URLs;
import sempati.star.app.models.User;
import sempati.star.app.services.SharedPrefManager;
import sempati.star.app.services.VolleySingleton;
import sempati.star.app.widget.DialogWidget;

import static android.content.ContentValues.TAG;
import static androidx.core.content.PackageManagerCompat.LOG_TAG;

public class LoginAct extends AppCompatActivity {
    Button btnLogin;
    TextView klikDisini;
    EditText etPassword, etUsername;
    ProgressBar progressBar;
    DialogWidget dialogWidget;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, HomeAct.class));
        }
        progressBar = findViewById(R.id.progressBar);
        btnLogin = findViewById(R.id.btnLogin);
        klikDisini = findViewById(R.id.klikDisini);
        etPassword = findViewById(R.id.etPassword);
        etUsername = findViewById(R.id.etUsername);
        dialogWidget = new DialogWidget();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                if (TextUtils.isEmpty(username)) {
                    etUsername.setError("Username tidak boleh kosong");
                    etUsername.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    etPassword.setError("Password tidak boleh kosong");
                    etPassword.requestFocus();
                    return;
                }

                if (password.length() < 6) {
                    etPassword.setError("Password kurang dari 6 karakter");
                    etPassword.requestFocus();
                    return;
                }

                submitLogin(username, password);


            }
        });

        klikDisini.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginAct.this, AktivasiAct.class));
            }
        });
    }

    void submitLogin(String username, String password){
        progressBar.setVisibility(View.VISIBLE);
        btnLogin.setEnabled(false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressBar.setVisibility(View.GONE);
                        Log.d("LOGIN RESPONSE", response);
                        try {
                            //converting response to json object
                            JSONObject obj = new JSONObject(response);
                            User user = new User(
                                    obj.getInt("id"),
                                    obj.getString("username"),
                                    obj.getInt("agenID"),
                                    obj.getInt("statusUser"),
                                    obj.getString("accessToken")
                            );

                            progressBar.setVisibility(View.GONE);
                            SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);
//                                //starting the profile activity
                            finish();
                            startActivity(new Intent(getApplicationContext(), HomeAct.class));

                        } catch (JSONException e) {
                            Log.e("TAG", e.toString());
                            e.printStackTrace();
                            progressBar.setVisibility(View.GONE);
                            btnLogin.setEnabled(true);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("TAG ERR", error.toString());
                        Toast.makeText(LoginAct.this, error.toString(), Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                        btnLogin.setEnabled(true);
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("password", password);
                return params;
            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

}