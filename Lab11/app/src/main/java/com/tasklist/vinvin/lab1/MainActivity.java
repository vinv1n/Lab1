package com.tasklist.vinvin.lab1;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
    }

    private void startPayment() {

        Intent intent = new Intent(this, PaymentActivity.class);
        startActivity(intent);

    }

    private void setupControls() {

    }
}
