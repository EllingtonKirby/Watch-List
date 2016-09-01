package com.example.ellioc.watchlist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(this, SearchBarActivity.class);
        startActivity(intent);
        finish();
        //setContentView(R.layout.activity_main);
    }
}
