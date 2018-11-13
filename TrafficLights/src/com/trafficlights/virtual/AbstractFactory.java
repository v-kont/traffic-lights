package com.trafficlights.virtual;

import com.lights.BlueLight;
import com.lights.GreenLight;
import com.lights.RedLight;
import com.lights.YellowLight;
import com.trafficlights.TrafficLights;
import java.io.IOException;

public abstract class AbstractFactory {
    
    protected abstract String getPropFilename();
    
    protected RedLight createRedLight(TrafficLights tl, String msg) {
        return new RedLight(tl, msg);
    }
    
    protected YellowLight createYellowLight(TrafficLights tl, String msg) {
        return new YellowLight(tl, msg);
    }
    
    protected GreenLight createGreenLight(TrafficLights tl, String msg) {
        return new GreenLight(tl, msg);
    }
    
    protected BlueLight createBlueLight(TrafficLights tl, String msg) {
        return new BlueLight(tl, msg);
    }
    
    public abstract TrafficLights createTrafficLights() throws IOException;
    
}