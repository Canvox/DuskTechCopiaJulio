package net.dusktech.com.dusktechcopiajulio;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 4000;
    // MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.ss);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent startIntent = new Intent(MainActivity.this, StartMainActivity.class);
                startActivity(startIntent);
                //mediaPlayer.start();
                finish();
            }
        }, SPLASH_TIME_OUT);

    }
}