package com.great.design.observer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 秘书实现观察者接口（属于观察者）
 * @author zhanghao_c
 *
 */
public class SubBoss implements Subject {

    private List<Observer> mSubject = new ArrayList<Observer>();
    
    /**
     * 增加被观察的人 
     */
    @Override
    public void attach(Observer observer) {

        // TODO Auto-generated method stub
        mSubject.add(observer);

    }

    @Override
    public void detach(Observer observer) {

        // TODO Auto-generated method stub
        mSubject.remove(observer);
    }

    @Override
    public void notifyObservable() {

        // TODO Auto-generated method stub
        for (int i = 0; i < mSubject.size(); i++) {
            mSubject.get(i).update();
        }
    }

    @Override
    public String getName() {

        return "Boss";
    }

  


}
