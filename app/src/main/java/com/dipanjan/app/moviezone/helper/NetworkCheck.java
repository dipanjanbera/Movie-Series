package com.dipanjan.app.moviezone.helper;

import android.os.AsyncTask;
import android.widget.Toast;

import com.dipanjan.app.moviezone.util.Constant;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by LENOVO on 03-11-2018.
 */

public class NetworkCheck extends AsyncTask<String, Void, Integer> {


    public static Integer TIMEOUT_DURATION = 10000;
    public static String DISPLAY_SNACBAR_MSG_IF_HOST_NOT_RESOLVE = "Host is not responding";
    public static String DISPLAY_MSG_IF_HOST_NOT_RESOLVE = "Host(yts.am) is not responding.\nMay be it is down now or blocked in your country.\nYou may use VPN to connect with host.";
    public interface AsyncResponse {
        Integer processFinish(Integer urlIndexPos);
    }

    public NetworkCheck(AsyncResponse delegate){
        this.delegate = delegate;
    }

    public AsyncResponse delegate = null;

    protected Integer doInBackground(String... params) {
        Integer indexPosition =-1;
        for (int index = 0; index < Constant.BASE_URL.length; index++) {
            boolean pingResult = pingURL(Constant.BASE_URL[index], TIMEOUT_DURATION);
            if (pingResult) {
                indexPosition = index;
                break;
            }

        }
        return indexPosition;
    }

    @Override
    protected void onPostExecute(Integer result) {
        delegate.processFinish(result);
    }



    public static boolean pingURL(String url, int timeout) {
        url = url.replaceFirst("^https", "http"); // Otherwise an exception may be thrown on invalid SSL certificates.

        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setConnectTimeout(timeout);
            connection.setReadTimeout(timeout);
            connection.setRequestMethod("HEAD");
            int responseCode = connection.getResponseCode();
            return (200 <= responseCode && responseCode <= 399);
        } catch (IOException exception) {
            return false;
        }

    }


}