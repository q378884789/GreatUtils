package com.great.design.observer;

/**
 * 通知者
 * @author zhanghao_c
 *
 */
public interface Subject {
    
    
    /**
     * 增加被观察者
     */
    public void attach( Observer observer );
    
    /**
     * 取消观察者
     */
    public void detach( Observer observer );
    
    /**
     * 通知被观察者
     */
    public void notifyObservable();
    
    /**
     * 得到通知者的名字
     */
    public String getName();
    
}
