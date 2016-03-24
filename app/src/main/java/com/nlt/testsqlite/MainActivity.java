package com.nlt.testsqlite;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements singleton.setBitmap
{
    private static final String TAG = "test";
    private ImageView im;
    private singleton.MyAsyncTask task;
    private String url = "http://img.my.csdn.net/uploads/201504/12/1428806103_9476.png";


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        im = (ImageView) findViewById(R.id.im);

        singleton mSingleton = singleton.getInstance();

        try
        {
            URL mURL = new URL(url);
            mSingleton.setBitmapInterface(this);

            task = mSingleton.new MyAsyncTask();
            task.execute(mURL);
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        if(!task.isCancelled())
        {
            task.cancel(true);
            Log.i(TAG, "onDestroy");
        }
    }

    @Override
    public void setBitmap(Bitmap bmp)
    {
        Log.i(TAG, "setBmp");
        im.setImageBitmap(bmp);
    }
}
