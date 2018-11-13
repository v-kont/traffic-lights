package com.trafficlights;

import com.lights.resources.Props;
import com.trafficlights.virtual.AbstractFactory;
import java.io.IOException;
import java.util.Properties;

public class JapaneseTrafficLightsFactory extends AbstractFactory {
    
    @Override
    protected String getPropFilename(){
        return "lights_ja";
    }
    
    @Override
    public TrafficLights createTrafficLights() throws IOException{
        TrafficLights tl = new TrafficLights();
        
        Properties prop = Props.getPropertiesFile(getPropFilename());
        
        String msgRed = prop.getProperty("red");
        String msgYellow = prop.getProperty("yellow");
        String msgBlue = prop.getProperty("blue");
        
        tl.setRed(createRedLight(tl, msgRed));
        tl.setYellow(createYellowLight(tl, msgYellow));
        tl.setBlue(createBlueLight(tl, msgBlue));
        return tl;
    }
}
