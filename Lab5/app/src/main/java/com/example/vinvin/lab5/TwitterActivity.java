package com.example.vinvin.lab5;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.provider.Browser;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import twitter4j.*;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.ConfigurationBuilder;

/**
 * Created by vinvin on 26.2.2018.
 */

public class TwitterActivity extends Activity {

    SharedPreferences preferences;
    Twitter twitter;
    private static final String PREF_KEY_TWITTER_LOGIN = "isLoggedIn";
    FrameLayout view;

    static String TWITTER_OAUTH_KEY;
    static String TWITTER_OAUTH_SECRET;

    static RequestToken authToken;

    ImageView imageView;
    TextView textView;
    ListView listView;
    List<Status> tweets;
    ArrayAdapter<Status> adapter;

    static final StringBuilder builder = new StringBuilder();

    static final String ACTION_TWITTER_AUTHENTICATE = "ACTION_TWITTER_AUTHENTICATE";
    static final String ACTION_TWITTER_ACCESS = "ACTION_TWITTER_ACCESS";
    String EXTRA_OAUTH_DATA = "EXTRA_OAUTH_DATA";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tweet_row);
        view = (FrameLayout) findViewById(R.id.list_item);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        twitter = getTwitter();

        //imageView = (ImageView) findViewById(R.id.tweet_picture);
        listView = (ListView) findViewById(R.id.main_list);

        /*if (getIntent().getAction().equalsIgnoreCase(ACTION_TWITTER_AUTHENTICATE)) {
            getTwitterAccess();
        }
        if (getIntent().getAction().equalsIgnoreCase(ACTION_TWITTER_ACCESS)) {
            getTwitterAccess();
        }*/
        getTweets();

    }

    private boolean isAuthenticated() {
        return preferences.getBoolean(PREF_KEY_TWITTER_LOGIN, false);
    }

    private Twitter getTwitter() {

        ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
        configurationBuilder.setOAuthConsumerKey(getString(R.string.app_key_consumer))
                .setOAuthConsumerSecret(getString(R.string.app_key_secret))
                .setOAuthAccessToken(getString(R.string.access_token))
                .setOAuthAccessTokenSecret(getString(R.string.access_token_secret));

        TwitterFactory factory = new TwitterFactory(configurationBuilder.build());
        twitter = factory.getInstance();
        return twitter;
    }

    private void getTweets() {

        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    tweets = getTwitter().getHomeTimeline();
                    final List<String> list_tweets = new ArrayList<>();
                    for (final Status status : tweets) {
                        list_tweets.add(status.getText());
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // not pretty solution
                            ListAdapter adapter = new ArrayAdapter<String>(getApplicationContext(),
                                    R.layout.list_item, list_tweets);
                            listView.setAdapter(adapter);
                        }
                    });
                } catch (twitter4j.TwitterException e) {
                    // no fancy error handling
                    Toast.makeText(getApplicationContext(), "Something went wrong",
                            Toast.LENGTH_SHORT).show();
                }
            }
        }).start();
    }

    public void authenticate() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    authToken = twitter.getOAuthRequestToken();
                    Intent auth = new Intent(Intent.ACTION_VIEW);
                    auth.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    auth.setData(Uri.parse(authToken.getAuthenticationURL()));
                    auth.putExtra(Browser.EXTRA_APPLICATION_ID, getPackageName());
                    startActivity(auth);
                    finish();
                } catch (twitter4j.TwitterException e) {
                    finish();
                }
            }
        }).start();
        getTwitterAccess();
    }

    private void getTwitterAccess() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    AccessToken accessToken = twitter.getOAuthAccessToken(twitter.getOAuthRequestToken(),
                            getIntent().getStringExtra(EXTRA_OAUTH_DATA));
                    preferences.edit()
                            .putString(TWITTER_OAUTH_KEY, accessToken.getToken())
                            .putString(TWITTER_OAUTH_SECRET, accessToken.getTokenSecret())
                            .apply();
                    finish();
                } catch (twitter4j.TwitterException e) {
                    finish();
                }
            }
        }).start();
    }

}
