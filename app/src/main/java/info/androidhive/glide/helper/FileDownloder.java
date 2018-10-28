package info.androidhive.glide.helper;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by LENOVO on 07-09-2018.
 */

public class FileDownloder {



    public static String downLoadFromUrl(String urlStg){
        //Log.d("QWERTY",urlStg+"    ss");
        StringBuffer sb = new StringBuffer();
        try
        {
            URL url = new URL(urlStg);
            HttpsURLConnection httpcon = (HttpsURLConnection) url.openConnection();
            httpcon.addRequestProperty("User-Agent", "Mozilla/4.76");

            BufferedReader br = new BufferedReader(new InputStreamReader
                    (httpcon.getInputStream()));
            String i;

          //  Log.d("QWERTY",""+httpcon.getInputStream());

            while ((i = br.readLine()) != null)
            {
//                Log.d("QWERTY",i);
                sb.append(i);
            }



        }

        catch (Exception e)
        {
            e.printStackTrace();
        }
        return sb.toString();
    }


    public static void copyURLToFile(URL url, File file) {

        try {
            InputStream input = url.openStream();
            if (file.exists()) {
                if (file.isDirectory())
                    throw new IOException("File '" + file + "' is a directory");

                if (!file.canWrite())
                    throw new IOException("File '" + file + "' cannot be written");
            } else {
                File parent = file.getParentFile();
                if ((parent != null) && (!parent.exists()) && (!parent.mkdirs())) {
                    throw new IOException("File '" + file + "' could not be created");
                }
            }

            FileOutputStream output = new FileOutputStream(file);

            byte[] buffer = new byte[4096];
            int n = 0;
            while (-1 != (n = input.read(buffer))) {
                output.write(buffer, 0, n);
            }

            input.close();
            output.close();

            System.out.println("File '" + file + "' downloaded successfully!");
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
        }
    }

}