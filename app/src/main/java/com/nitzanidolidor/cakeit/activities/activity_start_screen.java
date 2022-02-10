package com.nitzanidolidor.cakeit.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.nitzanidolidor.cakeit.R;

public class activity_start_screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startscreen);

        new Thread(){
            public void run() {
                try {
                    sleep(3000);
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                } finally {
                    toLoginPage();
                }
            }
        }.start();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        finish();
    }

    private void toLoginPage() {
        Intent intent = new Intent(this, login_page.class);
        startActivity(intent);
    }
}