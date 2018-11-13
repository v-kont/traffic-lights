package com.lights;

import com.lights.virtual.Light;
import com.log.virtual.Logger;
import com.trafficlights.TrafficLights;
import java.util.ArrayList;

public class YellowLight extends Light {

    private final TrafficLights tl;
    private final String msg;
    private ArrayList<Logger> loggers;
    
    public YellowLight(TrafficLights trafficlight, String msg){
        this.msg = msg;
        loggers = new ArrayList<>();
        this.tl = trafficlight;
    }
        
    @Override
    protected String getMsg() {
        return this.msg;
    }

    @Override
    protected TrafficLights getCurrentTrafficLight(){
        return this.tl;
    }
    
    @Override
    public void addLogger(Logger logger) {
        this.loggers.add(logger);
    }

    @Override
    public void removeLogger(Logger logger) {
        this.loggers.remove(logger);
    }

    @Override
    public void removeLoggersAll() {
        this.loggers.clear();
    }

    @Override
    protected ArrayList<Logger> getLoggers() {
        return this.loggers;
    }
    
}
