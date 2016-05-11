package com.my.greatmodule;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.great.module.communication.ICommunication;
import com.great.module.communication.MyService;

public class MainActivity extends Activity implements ICommunication {

    TextView tvBindService;

    ServiceConnection mConn = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

            MyService myService = ((MyService.CommunicationBinder) service).getService();

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvBindService = (TextView) findViewById(R.id.bindService);
        tvBindService.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, MyService.class);
                bindService(intent, mConn, Context.BIND_AUTO_CREATE);
            }
        });

    }

    @Override
    public void say() {

        // TODO Auto-generated method stub

    }
}
