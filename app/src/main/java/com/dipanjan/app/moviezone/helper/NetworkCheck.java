package com.dipanjan.app.moviezone.helper;

import android.os.AsyncTask;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by LENOVO on 03-11-2018.
 */

public class NetworkCheck extends AsyncTask<String, Void, Boolean> {

    private String url;
    private Integer timeOutDuration;
    public static Integer TIMEOUT_DURATION = 10000;
    public static String DISPLAY_SNACBAR_MSG_IF_HOST_NOT_RESOLVE = "Host is not responding";
    public static String DISPLAY_MSG_IF_HOST_NOT_RESOLVE = "Host(yts.am) is not responding.\nMay be it is down now or blocked in your country.\nYou may use VPN to connect with host.";
    public interface AsyncResponse {
        Boolean processFinish(Boolean output);
    }

    public NetworkCheck(String url,Integer timeOutDuration,AsyncResponse delegate){
        this.url=url;
        this.timeOutDuration=timeOutDuration;
        this.delegate = delegate;
    }

    public AsyncResponse delegate = null;

    protected Boolean doInBackground(String... params) {
        boolean result=pingURL(url,timeOutDuration);
        return result;
    }

    @Override
    protected void onPostExecute(Boolean result) {
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
