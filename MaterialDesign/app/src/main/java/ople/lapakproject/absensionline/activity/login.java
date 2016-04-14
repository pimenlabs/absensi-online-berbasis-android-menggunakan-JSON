package ople.lapakproject.absensionline.activity;

/**
 * Created by op'le on 11/3/2015.
 */


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.app.ProgressDialog;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;
import ople.lapakproject.absensionline.R;
import ople.lapakproject.absensionline.activity.helper.AppConfig;
import ople.lapakproject.absensionline.activity.helper.JSONParser;

public class login extends Activity {


    Button login;
    Intent a;
    EditText email, password;
    String url, success;
    SessionManager session;
    public static final String url_serve = AppConfig.SERVER;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        session = new SessionManager(getApplicationContext());

        login = (Button) findViewById(R.id.btnLogin);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);

        login.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                url = url_serve + "api_login.php?" + "nip="
                        + email.getText().toString() + "&pass="
                        + password.getText().toString();

                if (email.getText().toString().trim().length() > 0
                        && password.getText().toString().trim().length() > 0) {
                    if (cek_status(getApplication()))
                    { new Masuk().execute();
                    } else {
                        Toast.makeText(getApplicationContext(), "Koneksi tidak tersedia.!!", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Nip/password belum terisi.!!", Toast.LENGTH_LONG).show();
                }
            }
        });



    }
    public boolean cek_status(Context cek) {
        ConnectivityManager cm   = (ConnectivityManager) cek.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();

        if (info != null && info.isConnected())
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    public class Masuk extends AsyncTask<String, String, String>
    {
        ArrayList<HashMap<String, String>> contactList = new ArrayList<HashMap<String, String>>();
        ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();

            pDialog = new ProgressDialog(login.this);
            pDialog.setMessage("Sedang Process...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
        @Override
        protected String doInBackground(String... arg0) {
            JSONParser jParser = new JSONParser();

            JSONObject json = jParser.getJSONFromUrl(url);

            try {
                success = json.getString("success");

                Log.e("error", "nilai sukses=" + success);

                JSONArray hasil = json.getJSONArray("login");

                if (success.equals("1")) {

                    for (int i = 0; i < hasil.length(); i++) {

                        JSONObject c = hasil.getJSONObject(i);

                        String idg = c.getString("idg").trim();
                        String nip = c.getString("nip").trim();
                        String nama = c.getString("nama").trim();
                        String jk = c.getString("jk").trim();
                        String alamat = c.getString("alamat").trim();
                        String idk = c.getString("idk").trim();

                        session.createLoginSession(idg,nip,nama,jk,alamat,idk);
                        Log.e("ok", " ambil data");

                    }
                } else {
                    Log.e("erro", "tidak bisa ambil data 0");
                }

            } catch (Exception e) {
                // TODO: handle exception
                Log.e("erro", "tidak bisa ambil data 1");
            }

            return null;

        }
        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            pDialog.dismiss();
            if (success.equals("1")) {
                a = new Intent(login.this, MainActivity.class);
                startActivity(a);
                finish();
            }else if (success.equals("0")){

                Toast.makeText(getApplicationContext(), "Email anda belum terdaftar atau belum di konfirmasi admin !!", Toast.LENGTH_LONG).show();
            }

        }

    }

}
