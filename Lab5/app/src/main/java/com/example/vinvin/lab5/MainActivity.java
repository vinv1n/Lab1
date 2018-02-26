package com.example.vinvin.lab5;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setupControls();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // Controls for buttons etc
    private void setupControls(){

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // no need for this
            }
        });
    }

    private void lauchTwitterStream(){
        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        final Intent twitterAuth = new Intent(this, TwitterActivity.class);

        if (preferences.contains(TwitterActivity.TWITTER_OAUTH_KEY) &&
                preferences.contains(TwitterActivity.TWITTER_OAUTH_SECRET)){

            Intent tweet = new Intent(this, TwitterActivity.class);
            startActivity(tweet);

        } else {
            // nothing in the layout
            setContentView(R.layout.login_layout);
            // looks horrible :DDD
            ImageView login = (ImageView) findViewById(R.id.login_button);
            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    twitterAuth.setAction(TwitterActivity.ACTION_TWITTER_AUTHENTICATE);
                    startService(twitterAuth);
                }
            });
        }
    }
}
