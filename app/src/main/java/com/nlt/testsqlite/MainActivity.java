package com.nlt.testsqlite;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.ImageView;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements singleton.setBitmap
{
    private static final String TAG = "test";
    private String url = "http://img.my.csdn.net/uploads/201504/12/1428806103_9476.png";
    private ImageView im;
    private singleton.MyAsyncTask task;
    private singleton mSingleton;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        im = (ImageView) findViewById(R.id.im);

        mSingleton = singleton.getInstance();


    }

    @Override
    protected void onResume()
    {
        super.onResume();
        try
        {
            Log.i(TAG, "onResume");
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
    protected void onPause()
    {
        super.onPause();
        if(task != null && (task.getStatus() == AsyncTask.Status.RUNNING))
        {
            task.cancel(true);
            Log.i(TAG, "onPause");
        }
    }


    @Override
    public void setBitmap(Bitmap bmp)
    {
        Log.i(TAG, "setBmp");
        im.setImageBitmap(bmp);
    }
}
