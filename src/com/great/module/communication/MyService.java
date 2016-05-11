package com.great.module.communication;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class MyService extends Service {

    private ICommunication mCallBack;

    @Override
    public void onCreate() {

        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {

        // 返回binder对象
        return new CommunicationBinder();
    }

    // 注册接口
    public void registCommunicationInterface(ICommunication callback) {

        this.mCallBack = callback;
    }

    public class CommunicationBinder extends Binder {

        public MyService getService() {

            return MyService.this;
        }

    }

}
