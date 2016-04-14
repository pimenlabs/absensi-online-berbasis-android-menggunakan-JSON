package ople.lapakproject.absensionline.activity;

/**
 * Created by ople on 6/27/2015.
 */

import android.content.Context;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.content.SharedPreferences;

import ople.lapakproject.absensionline.R;

public class splashscreen extends Activity {
    SharedPreferences sharedPreferences;
    String nama = "nama";
    String email = "email";

    //Set waktu lama splashscreen
    private static int splashInterval = 2500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.splashscreen);
        sharedPreferences = this.getSharedPreferences(getResources().getString(R.string.name), Context.MODE_PRIVATE);
        nama = sharedPreferences.getString(getResources().getString(R.string.name), getResources().getString(R.string.name));
        sharedPreferences = this.getSharedPreferences(getResources().getString(R.string.hint_email), Context.MODE_PRIVATE);
        email = sharedPreferences.getString(getResources().getString(R.string.hint_email), getResources().getString(R.string.hint_email));


        new Handler().postDelayed(new Runnable() {


            @Override
            public void run() {
                // TODO Auto-generated method stub
                Intent i = new Intent(splashscreen.this, login.class);
                startActivity(i);


                //jeda selesai Splashscreen
                this.finish();

                Intent intent;
                if(nama.equals("id_pengguna") || email.equals("email_pengguna")){
                    intent = new Intent(splashscreen.this,login.class);
                    intent.putExtra("logout","logout");
                    intent.putExtra("id_pengguna","id_pengguna");
                    intent.putExtra("email_pengguna","email_pengguna");
                    splashscreen.this.startActivity(intent);
                    splashscreen.this.finish();
                }else{
                    intent = new Intent(splashscreen.this,MainActivity.class);
                    intent.putExtra("nama",nama);
                    intent.putExtra("email",email);
                    splashscreen.this.startActivity(intent);
                    splashscreen.this.finish();
                }
            }

            private void finish() {
                // TODO Auto-generated method stub

            }
        }, splashInterval);

    };

}
