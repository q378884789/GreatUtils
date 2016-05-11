package com.great.design.observer;



public class ObsLookBoll extends Observer {
    
    
    public ObsLookBoll(String name, Subject subject) {

        super(name, subject);
    }



    @Override
    public String getAction() {

        return "该关闭主球直播了";
    }

}
