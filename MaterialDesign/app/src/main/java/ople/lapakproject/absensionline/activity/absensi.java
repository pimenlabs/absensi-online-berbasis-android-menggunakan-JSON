package ople.lapakproject.absensionline.activity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import java.util.ArrayList;

import ople.lapakproject.absensionline.R;
import ople.lapakproject.absensionline.activity.helper.AppConfig;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ople.lapakproject.absensionline.activity.tampung.ServiceHandler;
import ople.lapakproject.absensionline.activity.tampung.sisw;

public class absensi extends ActionBarActivity {


    private static final String kidmapel = "idmapel";
    private static final String kidkelas = "idkelas";
    String idmapel,idkelas,nis_siswa,nama_siswa,status_kehadiran;


    Button btnHadir,btnIzin,btnSakit,btnAlfa;
    private SwipeRefreshLayout refreshLayout;
    TextView txtNama, txtNo;
    EditText stambuk;
    private List<sisw> tampung = new ArrayList<sisw>();

    int jawabanYgDiPilih[] = null;
    ArrayList<String> jawabananda = new ArrayList<String>();

    int jawabanYgBenar[] = null;
    boolean cekPertanyaan = false;
    int urutanPertanyaan = 0;
    List<sisw> getsiswa;
    JSONArray soal = null;
    CounterClass mCountDownTimer;
    private ProgressDialog pDialog;
    private static final String TAG_DAFTAR = "siswa";
    private static final String TAG_IDS = "ids";
    private static final String TAG_NIS = "nis";
    private static final String TAG_NAMA = "nama";
    String id_siswa, responseServer;
    Toolbar toolbar;
    Intent a;
    EditText statuskehadiran;
    private static String url,simpan_jawaban;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_absen);

        toolbar= (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Absensi Siswa");
        toolbar.setSubtitle("SMA NEGERI 2 PARE-PARE");
        setSupportActionBar(toolbar);
        getsiswa = new ArrayList<sisw>();
        txtNo = (TextView) findViewById(R.id.textViewNo);
        stambuk = (EditText) findViewById(R.id.stambuk);
        statuskehadiran = (EditText) findViewById(R.id.status_kehadiran);

        FloatingActionButton fab1 = (FloatingActionButton) findViewById(R.id.fabprev);
        fab1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                urutanPertanyaan--;
                if (urutanPertanyaan < 0)
                    urutanPertanyaan = 0;

                statuskehadiran.setText(jawabananda.get(urutanPertanyaan));
                tunjukanPertanyaan(urutanPertanyaan, cekPertanyaan);
            }
        });

        FloatingActionButton fab2 = (FloatingActionButton) findViewById(R.id.fabnext);
        fab2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                urutanPertanyaan++;
                if (urutanPertanyaan >= getsiswa.size())
                    urutanPertanyaan = getsiswa.size() - 1;
                jawabananda.add(statuskehadiran.getText().toString());
                statuskehadiran.setText("");
                tunjukanPertanyaan(urutanPertanyaan, cekPertanyaan);
            }
        });


        //set keterangan absen
        btnHadir = (Button) findViewById(R.id.btnHadir);
        btnHadir.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {


                status_kehadiran = "hadir";
                statuskehadiran.setText(status_kehadiran);
              //  Toast.makeText(getApplicationContext(), nis_siswa+"/"+nama_siswa+"status = Hadir", Toast.LENGTH_LONG).show();

            }
        });
        btnIzin = (Button) findViewById(R.id.btnIzin);
        btnIzin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                status_kehadiran = "izin";
                statuskehadiran.setText(status_kehadiran);

               // Toast.makeText(getApplicationContext(), nis_siswa+"/"+nama_siswa+"status = Izin", Toast.LENGTH_LONG).show();

            }
        });
        btnSakit = (Button) findViewById(R.id.btnSakit);
        btnSakit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                status_kehadiran = "sakit";
               // Toast.makeText(getApplicationContext(), nis_siswa+"/"+nama_siswa+"status = Sakit", Toast.LENGTH_LONG).show();
                statuskehadiran.setText(status_kehadiran);

            }
        });
        btnAlfa = (Button) findViewById(R.id.btnAlfa);
        btnAlfa.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                status_kehadiran = "alfa";
               // Toast.makeText(getApplicationContext(), nis_siswa+"/"+nama_siswa+"status = Alfa", Toast.LENGTH_LONG).show();
                statuskehadiran.setText(status_kehadiran);

            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                AsyncT asyncT = new AsyncT();
                asyncT.execute();
                int jumlahJawabanYgBenar = 0;
                for (int i = 0; i < jawabanYgBenar.length; i++) {
                    if ((jawabanYgBenar[i] != -1) && (jawabanYgBenar[i] == jawabanYgDiPilih[i]))
                        jumlahJawabanYgBenar++;

                }
                AlertDialog tampilKotakAlert;
                tampilKotakAlert = new AlertDialog.Builder(absensi.this)
                        .create();
                tampilKotakAlert.setTitle("Info");
                tampilKotakAlert.setIcon(R.mipmap.ic_launcher);
                tampilKotakAlert.setMessage("Absensi siswa berhasil disimpan");


                tampilKotakAlert.setButton(AlertDialog.BUTTON_POSITIVE, "Info",
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                mCountDownTimer.cancel();
                                cekPertanyaan = true;
                                urutanPertanyaan = 0;
                                tunjukanPertanyaan(0, cekPertanyaan);
                                AlertDialog.Builder alertDialog1 = new AlertDialog.Builder(absensi.this);
                                // Setting Dialog Title
                                alertDialog1.setTitle("info");
                                // Setting Dialog Message
                                alertDialog1.setMessage("Data tersimpan");

                                alertDialog1.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        a = new Intent(absensi.this, MainActivity.class);
                                        startActivity(a);
                                    }
                                });

                                // Showing Alert Message


                                alertDialog1.show();
                            }
                        });

                tampilKotakAlert.show();
            }
        });
        Intent i = getIntent();
        idkelas = i.getStringExtra(kidkelas);
        idmapel = i.getStringExtra(kidmapel);
        url = AppConfig.SERVER + "api_absensi.php?idk="+idkelas;
        new GetSoal().execute();


    }

    private class GetSoal extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(absensi.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            // Creating service handler class instance

            ServiceHandler sh = new ServiceHandler();
            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);
            Log.d("Response: ", "> " + jsonStr);
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    // Getting JSON Array node
                    soal = jsonObj.getJSONArray(TAG_DAFTAR);
                    sisw s = null;
                    // looping through All Contacts
                    for (int i = 0; i < soal.length(); i++) {
                        JSONObject c = soal.getJSONObject(i);
                        s = new sisw();

                        String ids = c.getString(TAG_IDS);
                        String nis= c.getString(TAG_NIS);
                        String nama = c.getString(TAG_NAMA);

                        s.setIds(ids);
                        s.setNis(nis);
                        s.setNamal(nama);
                        getsiswa.add(s);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("ServiceHandler", "Couldn't get any data from the url");
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();

            jawabanYgDiPilih = new int[getsiswa.size()];
            java.util.Arrays.fill(jawabanYgDiPilih, -1);
            jawabanYgBenar = new int[getsiswa.size()];
            java.util.Arrays.fill(jawabanYgBenar, -1);
            setUpSoal();
        }
    }

    private void setUpSoal() {
        Collections.shuffle(getsiswa);
        tunjukanPertanyaan(0, cekPertanyaan);
    }

    private void tunjukanPertanyaan(int urutan_soal_soal, boolean review) {
        if(urutan_soal_soal == 0)
            setUpWaktu();

        try {
            sisw soal = new sisw();
            soal = getsiswa.get(urutan_soal_soal);

            nis_siswa = soal.getNis();
            nama_siswa = soal.getNamal();
            stambuk.setText(nis_siswa+"/"+nama_siswa);

            Log.d("", jawabanYgDiPilih[urutan_soal_soal] + "");

            pasangLabelDanNomorUrut();


            if (review) {
                mCountDownTimer.cancel();
                Log.d("priksa", jawabanYgDiPilih[urutan_soal_soal] + ""
                        + jawabanYgBenar[urutan_soal_soal]);

            }

        } catch (Exception e) {
            Log.e(this.getClass().toString(), e.getMessage(), e.getCause());
        }
    }



    private void pasangLabelDanNomorUrut() {
        txtNo.setText("No. " + (urutanPertanyaan + 1)+ " dari "
                + getsiswa.size() + " siswa");
    }

    private void setUpWaktu() {
        mCountDownTimer = new CounterClass(480000, 1000);
        mCountDownTimer.start();
    }

    @SuppressLint("DefaultLocale")
    public class CounterClass extends CountDownTimer {
        public CounterClass(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            AsyncT asyncT = new AsyncT();
            asyncT.execute();


        }

        @SuppressLint("NewApi")
        @TargetApi(Build.VERSION_CODES.GINGERBREAD)
        @Override
        public void onTick(long millisUntilFinished) {
            long millis = millisUntilFinished;
            String hms = String.format(
                    "%02d:%02d:%02d",
                    TimeUnit.MILLISECONDS.toHours(millis),
                    TimeUnit.MILLISECONDS.toMinutes(millis)
                            - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS
                            .toHours(millis)),
                    TimeUnit.MILLISECONDS.toSeconds(millis)
                            - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS
                            .toMinutes(millis)));
            //     txtWaktu.setText(hms);
        }
    }

    /* Inner class to get response */
    class AsyncT extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(AppConfig.SERVER + "api_simpan_absensi.php");

            try {

                JSONObject jsonobj = new JSONObject();

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                jsonobj.put("ids", getsiswa.get(0).getIds() );
                jsonobj.put("idm", idmapel );
                jsonobj.put("idk", idkelas );

                for (int i =0; i < getsiswa.size(); i ++){
                    jsonobj.put("ket"+i, jawabananda.get(i));
                }


                nameValuePairs.add(new BasicNameValuePair("req", jsonobj.toString()));

                Log.e("mainToPost", "mainToPost" + nameValuePairs.toString());

                // Use UrlEncodedFormEntity to send in proper format which we need
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                // Execute HTTP Post Request
                HttpResponse response = httpclient.execute(httppost);
                InputStream inputStream = response.getEntity().getContent();
                InputStreamToStringExample str = new InputStreamToStringExample();
                responseServer = str.getStringFromInputStream(inputStream);
                Log.e("response", "response -----" + responseServer);


            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

        }
    }

    public static class InputStreamToStringExample {

        public static void main(String[] args) throws IOException {

            // intilize an InputStream
            InputStream is =
                    new ByteArrayInputStream("file content..blah blah".getBytes());

            String result = getStringFromInputStream(is);

            System.out.println(result);
            System.out.println("Done");

        }

        // convert InputStream to String
        private static String getStringFromInputStream(InputStream is) {

            BufferedReader br = null;
            StringBuilder sb = new StringBuilder();

            String line;
            try {

                br = new BufferedReader(new InputStreamReader(is));
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return sb.toString();
        }

    }




}
