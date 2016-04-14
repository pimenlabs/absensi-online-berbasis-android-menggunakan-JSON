package ople.lapakproject.absensionline.activity;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Spinner;
import android.widget.Toast;

import ople.lapakproject.absensionline.R;
import ople.lapakproject.absensionline.activity.helper.AppConfig;
import ople.lapakproject.absensionline.activity.tampung.JSONfunctions;
import ople.lapakproject.absensionline.activity.tampung.daftar_kelas_tersedia;
import ople.lapakproject.absensionline.activity.tampung.daftar_mapel_tersedia;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.HashMap;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Bundle;
import android.app.Activity;
import android.text.Html;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import  android.support.v4.app.DialogFragment;
import android.os.AsyncTask;

import android.view.View.OnClickListener;
public class MainActivity extends AppCompatActivity {

    SessionManager session;
    String  idkelas,id_matapelajaran,id_kelas;
    JSONObject jsonobject;
    JSONArray jsonarray;
    private static String TAG = MainActivity.class.getSimpleName();

    ArrayList<daftar_kelas_tersedia> snama_kelas;
    ArrayList<daftar_mapel_tersedia> snama_mapel;

    ArrayList<String> nama_kelas,nama_mapel;
    Spinner mySpinner,mySpinner1;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (cek_status(getApplication()))
        { setContentView(R.layout.activity_main);

        session = new SessionManager(getApplicationContext());
        session.checkLogin();
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setSubtitle("SMA NEGERI 2 PARE-PARE");
        setSupportActionBar(mToolbar);

        HashMap<String, String> user = session.getUserDetails();
        idkelas = user.get(SessionManager.KEY_idk);

           mToolbar = (Toolbar) findViewById(R.id.toolbar);

            FloatingActionButton fab1 = (FloatingActionButton) findViewById(R.id.fab);
            fab1.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {

                    AlertDialog.Builder alertDialog1 = new AlertDialog.Builder(MainActivity.this);
                    // Setting Dialog Title
                    alertDialog1.setTitle("info");
                    // Setting Dialog Message
                    alertDialog1.setMessage("Lihat Absen");
                     alertDialog1.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Write your code here to invoke NO event
                            dialog.cancel();


                        }
                    });
                    alertDialog1.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Write your code here to invoke NO event

                            Intent in = new Intent(MainActivity.this, absensi.class);

                            in.putExtra("idmapel", id_matapelajaran);
                            in.putExtra("idkelas", id_kelas);
                             startActivityForResult(in, 100);


                        }
                    });

                    // Showing Alert Message


                    alertDialog1.show();
                }
            });


            new jsnama_kelas().execute();
            new jsnama_mapel().execute();
        }
        else
        {
            setContentView(R.layout.layout_server);

        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
            alertDialog.setTitle("Logout");

            alertDialog.setMessage("Yakin ??");
            // Setting Positive "Yes" Button
            alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                    // Write your code here to invoke YES event
                    session.logoutUser();
                    finish();

                }
            });

            // Setting Negative "NO" Button
            alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // Write your code here to invoke NO event
                    dialog.cancel();
                }
            });

            // Showing Alert Message
            alertDialog.show();
            return true;
        }



        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private class jsnama_kelas extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            //mengambil nilai dari session manager

            // Locate the WorldPopulation Class
            snama_kelas = new ArrayList<daftar_kelas_tersedia>();
            // Create an array to populate the spinner
            nama_kelas = new ArrayList<String>();
            // JSON file URL address
            jsonobject = JSONfunctions
                    .getJSONfromURL(AppConfig.SERVER + "api_kelas.php?idk=" + idkelas );

            try {
                // Locate the NodeList name
                jsonarray = jsonobject.getJSONArray("kelas");
                for (int i = 0; i < jsonarray.length(); i++) {
                    jsonobject = jsonarray.getJSONObject(i);

                    daftar_kelas_tersedia worldpop = new daftar_kelas_tersedia();

                    worldpop.setNama_kelas(jsonobject.optString("nama"));

                    worldpop.setId_kelas(jsonobject.optString("id"));
                    snama_kelas.add(worldpop);

                    // Populate spinner with country names

                    nama_kelas.add(jsonobject.optString("nama"));


                }
            } catch (Exception e) {
               // Log.e("Error", e.getMessage());
               // e.printStackTrace();
                if (cek_status(getApplication()))
                {

                }else{
                    setContentView(R.layout.layout_server);

                }

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void args) {
            // Locate the spinner in activity_main.xml
            mySpinner = (Spinner) findViewById(R.id.kelas);

            // Spinner adapter
            mySpinner.setAdapter(new ArrayAdapter<String>(MainActivity.this,
                    android.R.layout.simple_spinner_dropdown_item,
                    nama_kelas));

            // Spinner on item click listener
            mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> arg0,
                                           View arg1, int position, long arg3) {
                    // TODO Auto-generated method stub
                    // Locate the textviews in activity_main.xml
                   id_kelas=snama_kelas.get(mySpinner.getSelectedItemPosition()).getId_kelas();


                }

                @Override
                public void onNothingSelected(AdapterView<?> arg0) {
                    // TODO Auto-generated method stub
                }
            });
        }
    }
    private class jsnama_mapel extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            //mengambil nilai dari session manager

            // Locate the WorldPopulation Class
            snama_mapel = new ArrayList<daftar_mapel_tersedia>();
            // Create an array to populate the spinner
            nama_mapel = new ArrayList<String>();
            // JSON file URL address
            jsonobject = JSONfunctions
                    .getJSONfromURL(AppConfig.SERVER + "api_mapel.php?idk=" + idkelas );

            try {
                // Locate the NodeList name
                jsonarray = jsonobject.getJSONArray("mapel");
                for (int i = 0; i < jsonarray.length(); i++) {
                    jsonobject = jsonarray.getJSONObject(i);

                    daftar_mapel_tersedia worldpop = new daftar_mapel_tersedia();

                    worldpop.setNama_mapel(jsonobject.optString("nama"));

                    worldpop.setId_mapel(jsonobject.optString("idm"));
                    snama_mapel.add(worldpop);

                    // Populate spinner with country names

                    nama_mapel.add(jsonobject.optString("nama"));


                }
            } catch (Exception e) {
               // Log.e("Error", e.getMessage());
                //e.printStackTrace();
                if (cek_status(getApplication()))
                {

                }else{
                    setContentView(R.layout.layout_server);

                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void args) {
            // Locate the spinner in activity_main.xml
            mySpinner1 = (Spinner) findViewById(R.id.mapel);

            // Spinner adapter
            mySpinner1.setAdapter(new ArrayAdapter<String>(MainActivity.this,
                    android.R.layout.simple_spinner_dropdown_item,
                    nama_mapel));

            // Spinner on item click listener
            mySpinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> arg0,
                                           View arg1, int position, long arg3) {
                    // TODO Auto-generated method stub
                    // Locate the textviews in activity_main.xml
                    id_matapelajaran = snama_mapel.get(mySpinner1.getSelectedItemPosition()).getId_mapel();

                }

                @Override
                public void onNothingSelected(AdapterView<?> arg0) {
                    // TODO Auto-generated method stub
                }
            });
        }
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

}