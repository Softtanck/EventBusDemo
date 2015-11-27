package com.softtanck.eventbusdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import de.greenrobot.event.EventBus;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EventBus.getDefault().register(this);
//        EventBus.getDefault().post("--");
    }


    /**
     * onEvent：事件在哪个线程发布onEvent就运行在哪个线程。
     * <p/>
     * onEventMainThread：不管线程在哪个线程发布处理函数都运行在主线程。
     * <p/>
     * onEventBackground：如果发布线程在主线程处理函数就运行在子线程，如果发布在子线程处理函数就运行在改线程。
     * <p/>
     * onEventAsync：不管发布在哪个线程都运行在子线程中。
     *
     * @param event
     */
    public void onEventMainThread(String event) {
        Log.d("Tanck", event);
        Toast.makeText(MainActivity.this, event, Toast.LENGTH_LONG).show();
    }

    public void onEvent(String msg) {
    }

    public void onEventBackgroundThread(String msg) {
    }

    public void onEventAsync(String msg) {
    }

    public void openAc(View view) {
        Intent intent = new Intent(MainActivity.this, SecondActivity.class);
        startActivity(intent);
    }
}
