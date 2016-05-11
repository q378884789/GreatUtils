package com.great.design.observer;



public class ObsStock extends Observer {

    private static final String TAG = "StockObserver";
    
    public ObsStock(String name, Subject subject) {

        super(name, subject);
    }

    
    @Override
    public String getAction() {

        return "关闭股票软件";
    }

    
    
}
