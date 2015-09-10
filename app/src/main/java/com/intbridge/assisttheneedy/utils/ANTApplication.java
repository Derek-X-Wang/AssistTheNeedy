package com.intbridge.assisttheneedy.utils;

import android.app.Application;

import com.parse.Parse;

/**
 * Created by Derek on 9/7/2015.
 */
public class ANTApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        Parse.initialize(this, "h38Vo1YClryJ5kFIi3PDcLwvkzvOc4HsmwBCxR4d", "7gnFQMoHFRP0ZoKKfbVWwDzEgcd1VyYoBWA7jgJD");

    }
}
