package com.nlt.testsqlite;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by HuangYW on 2016/3/22.
 */
public class singleton
{
    private static final String TAG = "test";
    private static class inner
    {
        static singleton instance = new singleton();
    }

    public static singleton getInstance()
    {
        return inner.instance;
    }


    private setBitmap mSetBitmap = null;

    public singleton()
    {

    }

    public class MyAsyncTask extends AsyncTask<URL, Integer, Bitmap>
    {
        @Override
        protected Bitmap doInBackground(URL... params)
        {
            Bitmap bmp = null;
            try
            {
                HttpURLConnection connection = (HttpURLConnection) params[0].openConnection();
                connection.setConnectTimeout(3000);
                if(connection.getResponseCode() == HttpURLConnection.HTTP_OK)
                {
                    InputStream in = connection.getInputStream();
                    BitmapFactory.Options ops = new BitmapFactory.Options();
                    ops.inJustDecodeBounds = true;

                    BitmapFactory.decodeStream(in, null, ops);
                    Log.i(TAG, "width: " + ops.outWidth + " height: " + ops.outHeight);

                    in.close();
                    connection.disconnect();

                    connection = (HttpURLConnection) params[0].openConnection();
                    connection.setConnectTimeout(3000);
                    if(connection.getResponseCode() == HttpURLConnection.HTTP_OK)
                    {
                        in = connection.getInputStream();
                        ops.inJustDecodeBounds = false;
                        ops.inSampleSize = 4;
                        bmp = BitmapFactory.decodeStream(in, null, ops);

                        in.close();
                        connection.disconnect();
                    }
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }


            return bmp;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap)
        {
            if(mSetBitmap != null)
                mSetBitmap.setBitmap(bitmap);
        }

    }



    public void setBitmapInterface(setBitmap sBmp)
    {
        this.mSetBitmap = sBmp;
    }

    public interface setBitmap
    {
        void setBitmap(Bitmap bmp);
    }
}
