package com.great.design.observer;

import android.app.Activity;
import android.os.Bundle;


public class TestActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
    
        super.onCreate(savedInstanceState);
         
        SubBoss sb = new SubBoss();
        sb.attach(new ObsLookBoll("小三", sb));
        sb.attach(new ObsStock("小四  ", sb));
        sb.attach(new ObsLookBoll("小五", sb));
        
        sb.notifyObservable();  
    }
    
}
