package com.tasklist.vinvin.lab1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 *
 * Created by vinvin on 23.1.2018.
 */

public class LoginActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        setupButtons();

    }

    private void setupButtons() {

        Button login_accept = (Button) findViewById(R.id.login_accept);

        final EditText username = (EditText) findViewById(R.id.login_username);
        final EditText password = (EditText) findViewById(R.id.login_password);

        login_accept.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if (username.getText().length() < 4 || password.getText().length() < 5){
                   Toast.makeText(getApplicationContext(), "Too short username or password",
                           Toast.LENGTH_LONG).show();
               } else {
                   startMain(username.getText().toString(),
                           password.getText().toString());
               }
           }
       });
    }

    private void startMain(String username, String password) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("username", username);
        intent.putExtra("password", password);
        startActivity(intent);
    }

    @Override
    protected void onPause() {
        finish();
        super.onPause();
    }
}
