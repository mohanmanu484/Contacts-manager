package com.mohan.contactsmap;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by mohan on 6/10/16.
 */

public class App extends Application {

    private static Dbclass db;
    private static Context appContext;
    private static RequestQueue volleyQueue;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext=this;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static Context getAppContext() {
        return appContext;
    }
    public static synchronized Dbclass getDb() {
        if (db == null) {
            db = Dbclass.getInstance(getAppContext());
        }
        return db;
    }

    public static RequestQueue getVolleyQueue() {
        if (volleyQueue == null) {
            volleyQueue = Volley.newRequestQueue(getAppContext());
        }
        return volleyQueue;
    }
}
