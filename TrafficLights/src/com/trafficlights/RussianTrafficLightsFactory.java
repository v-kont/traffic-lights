package com.trafficlights;

import com.lights.resources.Props;
import com.trafficlights.virtual.AbstractFactory;
import java.io.IOException;
import java.util.Properties;

public class RussianTrafficLightsFactory extends AbstractFactory {
       
    @Override
    protected String getPropFilename(){
        return "lights_ru";
    }
    
    @Override
    public TrafficLights createTrafficLights() throws IOException {
        TrafficLights tl = new TrafficLights();
        
        Properties prop = Props.getPropertiesFile(getPropFilename());
        
        String msgRed = prop.getProperty("red");
        String msgYellow = prop.getProperty("yellow");
        String msgGreen = prop.getProperty("green");
        
        tl.setRed(createRedLight(tl, msgRed));
        tl.setYellow(createYellowLight(tl, msgYellow));
        tl.setGreen(createGreenLight(tl, msgGreen));
        return tl;
    }
    
}