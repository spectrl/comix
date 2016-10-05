package com.spectrl.comix.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Kavi @ SPECTRL Ltd. on 06/10/2016.
 */

public class Connectivity implements Connection {

    private final Context context;

    public Connectivity(Context context) {
        this.context = context;
    }

    public boolean connected() {
        ConnectivityManager cm =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }
}
