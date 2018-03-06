package com.example.vinvin.lab6;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

/**
 *
 * Created by vinvin on 6.3.2018.
 */

public class LoggedIn extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loggedin);

        ImageView imageView = (ImageView) findViewById(R.id.face_image);
        setupControls();

        Intent intent = getIntent();
        Bitmap bitmap = intent.getParcelableExtra("face");

        imageView.setImageBitmap(bitmap);
    }
    private void setupControls(){

        Button button = (Button) findViewById(R.id.get_out);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
