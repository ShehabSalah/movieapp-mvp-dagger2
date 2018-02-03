/*
 * Copyright (C) 2018 Shehab Salah Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.shehabsalah.movieappmvpdagger2.datalayer.source.remote.request;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by ShehabSalah on 1/8/18.
 *
 */

public class NetworkStateChangeReceiver extends BroadcastReceiver {
    private static HashMap<String, ConnectivityReceiverListener> connectivityReceiverListeners;


    public static void setConnectivityReceiverListener(String tag, ConnectivityReceiverListener connectivityReceiverListener) {
        if (connectivityReceiverListeners == null) {
            connectivityReceiverListeners = new HashMap<>();
        }
        connectivityReceiverListeners.put(tag, connectivityReceiverListener);
    }

    public static void removeConnectivityReceiverListener(String tag) {
        if (connectivityReceiverListeners != null) {
            connectivityReceiverListeners.remove(tag);
        }

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        boolean isOPen = isOpenWifi(context);
        if (connectivityReceiverListeners != null) {

            for (Iterator<Map.Entry<String, ConnectivityReceiverListener>> it = connectivityReceiverListeners.entrySet().iterator(); it.hasNext(); ) {
                Map.Entry<String, ConnectivityReceiverListener> entry = it.next();
                if (entry.getValue() != null) {
                    entry.getValue().isOPenWifi(isOPen);
                } else {
                    it.remove();
                }
            }

        }
    }

    public static boolean isOpenWifi(Context context) {

        ConnectivityManager conMan = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMan.getActiveNetworkInfo();
        if (netInfo != null && netInfo.getType() == ConnectivityManager.TYPE_WIFI) {
         //   UiUtil.Log("WifiReceiver", "Have Wifi Connection");
            return true;
        } else {
         //   UiUtil.Log("WifiReceiver", "Don't have Wifi Connection");
            return false;
        }
    }

    public static boolean isOpenMobileData(Context context) {

        ConnectivityManager conMan = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMan.getActiveNetworkInfo();
        if (netInfo != null && netInfo.getType() == ConnectivityManager.TYPE_MOBILE ) {
        //    UiUtil.Log("WifiReceiver", "Have Mobile Data Connection");
            return true;
        } else {
        //    UiUtil.Log("WifiReceiver", "Don't have Wifi Connection");
            return false;
        }
    }

    public interface ConnectivityReceiverListener {
        void isOPenWifi(boolean isConnected);
    }
}