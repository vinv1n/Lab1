package com.tasklist.vinvin.lab1;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        startLogin();

        setContentView(R.layout.activity_main);

        setupControls();
    }

    private void startPayment() {

        Intent intent = new Intent(this, PaymentActivity.class);
        startActivityForResult(intent, 2);

    }

    private void startLogin(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivityForResult(intent, 1337);
    }

    // other buttons do not have function
    private void setupControls() {

        Button new_payment = (Button) findViewById(R.id.new_payment);
        Button bank_details = (Button) findViewById(R.id.bank_details);
        Button my_info = (Button) findViewById(R.id.my_info);
        Button bank_history = (Button) findViewById(R.id.history);

        new_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPayment();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1337 && resultCode == Activity.RESULT_OK) {
            Toast.makeText(getApplicationContext(), "Here!",
                    Toast.LENGTH_SHORT).show();
        } else if (requestCode == 2 && resultCode == Activity.RESULT_OK) {
            Toast.makeText(getApplicationContext(),
                    "Payment ID: " + data.getStringExtra("id"), Toast.LENGTH_LONG).show();
        }
    }
}
