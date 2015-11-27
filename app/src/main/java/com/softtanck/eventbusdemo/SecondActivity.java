package com.softtanck.eventbusdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import de.greenrobot.event.EventBus;

/**
 * Created by winterfell on 11/27/2015.
 */
public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_layout);

    }

    public void sendMsg(View view) {
        EventBus.getDefault().post("----");
    }
}
