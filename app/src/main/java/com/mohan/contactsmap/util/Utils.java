package com.mohan.contactsmap.util;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Build;
import android.util.Log;

/**
 * Created by mohan on 29/8/16.
 */
public class Utils {

    private static final String TAG = "Utils";

    public static Integer getBatteryLevel(Context context) {

        Log.d(TAG, "inside getBatteryLevel()");

        int sdkVersion = android.os.Build.VERSION.SDK_INT;
        int batteryLevel = 0;
        try {

            IntentFilter intentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
            Intent batteryStatus;
            if (sdkVersion <= 18) {
                //16 - 18 jelly bean
                batteryStatus = context.getApplicationContext().registerReceiver(null, intentFilter);
            } else {
                //for versions above jelly bean
                batteryStatus = context.registerReceiver(null, intentFilter);
            }

            batteryLevel = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
            Log.d(TAG, "battery level : " + batteryLevel);

        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }

/*
        // How are we charging?
        int chargePlug = batteryStatus.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
        boolean usbCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_USB;
        boolean acCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_AC;
*/
        return Integer.valueOf(batteryLevel);
    }


    /**
     * Uses static final constants to detect if the device's platform version is Gingerbread or
     * later.
     */
    public static boolean hasGingerbread() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD;
    }

    /**
     * Uses static final constants to detect if the device's platform version is Honeycomb or
     * later.
     */
    public static boolean hasHoneycomb() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
    }

    /**
     * Uses static final constants to detect if the device's platform version is Honeycomb MR1 or
     * later.
     */
    public static boolean hasHoneycombMR1() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1;
    }

    /**
     * Uses static final constants to detect if the device's platform version is ICS or
     * later.
     */
    public static boolean hasICS() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH;
    }
}
