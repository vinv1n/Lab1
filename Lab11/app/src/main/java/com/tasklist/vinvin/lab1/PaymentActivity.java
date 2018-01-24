package com.tasklist.vinvin.lab1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Random;

/**
 *
 * Created by vinvin on 23.1.2018.
 */

public class PaymentActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_payment);

        setupControls();

    }

    private void setupControls() {

        Button accept_payment = (Button) findViewById(R.id.accept_payment);

        accept_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Exiting",
                        Toast.LENGTH_SHORT).show();
                stopActivity();
            }
        });
    }
    private void stopActivity(){
        Intent intent = new Intent();
        intent.putExtra("id", generateID());
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    private String generateID(){

        Random rand = new Random();

        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < 10; i++){
            builder.append(rand.nextInt(9));
        }
        return builder.toString();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
    @Override
    protected void onStop() {
        super.onStop();
    }

}
