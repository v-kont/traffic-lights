package com.lights.virtual;

import com.log.virtual.Logger;
import com.trafficlights.TrafficLights;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

public abstract class Light {
    
    protected abstract String getMsg();
    protected abstract TrafficLights getCurrentTrafficLight();
    
    public void toLight(){
        ArrayList<Logger> loggers = getLoggers();
        
        if(!loggers.isEmpty()){
            Iterator<Logger> iterator = loggers.iterator();
            
            while(iterator.hasNext()){
                iterator.next().write("Traffic light ID " + getCurrentTrafficLight().getID() + " set light: " + getMsg());
            }
        }
    }
    
    public abstract void addLogger(Logger logger);
    
    public abstract void removeLogger(Logger logger);
    
    public abstract void removeLoggersAll();
    
    protected abstract ArrayList<Logger> getLoggers();
    
    @Override
    public int hashCode() {
        return getMsg().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Light other = (Light) obj;
        return Objects.equals(getMsg(), other.getMsg());
    }
    
}
