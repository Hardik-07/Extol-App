package com.example.extol;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Intent i = new Intent(this,LoginActivity.class);
        Thread t = new Thread();
        try {
            t.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        startActivity(i);
        finish();
    }
}
