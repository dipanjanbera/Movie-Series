package info.dipanjan.app.app;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.List;

/**
 * Created by Lincoln on 04/04/16.
 */
public class AppController extends Application {

    public static final String TAG = AppController.class
            .getSimpleName();

    private RequestQueue mRequestQueue;

    private static AppController mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static synchronized AppController getInstance() {
        return mInstance;
    }



    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

    public static void openMagneturi(String url, final Context c){
        Log.e("TAG","open Magneturi magnet");

        if(url.startsWith("magnet:")) {
            Log.e("TAG","url starts with magnet");
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            browserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            PackageManager manager = c.getPackageManager();
            List<ResolveInfo> infos = manager.queryIntentActivities(browserIntent, 0);
            if (infos.size() > 0) {
                c.startActivity(browserIntent);
                Log.e("TAG","yes act to handle");
            } else {
                Toast.makeText(c,"No Torrent client found to perform this task",Toast.LENGTH_SHORT).show();


            }
        }else{
            Log.e("TAG","url does not starts with magnet");

        }
    }


}
