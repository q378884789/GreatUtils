package com.great.design.observer;

import android.util.Log;

/**
 * 被观察者
 * 
 * @author zhanghao_c
 * 
 */
public abstract class Observer {

    protected String TAG = "Observer";
    
    protected String mName;
    protected Subject mSubject;

    public Observer(String name, Subject subject) {

        this.mName = name;
        this.mSubject = subject;
    }

    /**
     * 每个被观察的人动作都不一样，有被观察者自己实现
     */
    public void update(){
        Log.i(TAG, mSubject.getName() + "提醒:" + mName + getAction());
    }
    
    /**
     * 被观察者的动作
     * @return
     */
    public abstract String getAction();
}
