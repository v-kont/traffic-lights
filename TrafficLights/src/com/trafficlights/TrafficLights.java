package com.trafficlights;

import com.control.Event;
import com.lights.BlueLight;
import com.lights.GreenLight;
import com.lights.RedLight;
import com.lights.YellowLight;
import com.lights.virtual.Light;
import com.log.virtual.Logger;
import java.util.ArrayList;
import java.util.Iterator;


public class TrafficLights implements Runnable {
    private int id;
    private String description; // to store lang (if it will be need)
    private int timePeriod;
    
    private RedLight red;                               // TODO: move lights from members to List
    private YellowLight yellow;
    private GreenLight green;
    private BlueLight blue;
    
    private Light curState;
    private Light prevState;
    
    private boolean runMode;
    private Thread thread;
    private long lastTimeChanged;
    
    private ArrayList<Logger> loggers;
    
    private Event curEvent; 

    public TrafficLights(){
        thread = new Thread();
        loggers = new ArrayList<>();
        timePeriod = 120_000;
        lastTimeChanged = System.currentTimeMillis();
    }
    
    protected void setRed(RedLight red) {
        this.red = red;
    }

    protected void setYellow(YellowLight yellow) {
        this.yellow = yellow;
    }

    protected void setGreen(GreenLight green) {
        this.green = green;
    }
    
    protected void setBlue(BlueLight blue) {
        this.blue = blue;
    }

    private RedLight getRed() {
        return red;
    }

    private YellowLight getYellow() {
        return yellow;
    }

    private Light getGreen() {
        return (green == null)?blue:green;
    }
    
    public void setID(int id){
        this.id = id;
    }
    
    public int getID(){
        return this.id;
    }
    
    public String getCurLight(){
        return this.curState.getClass().getSimpleName();
    }
    
    public String getCurState(){
        return runMode?"started":"stopped";
    }
    
    public Thread getThread(){
        return this.thread;
    }
    
    private long getLastTimeChanged(){
        return this.lastTimeChanged;
    }
    
    private void setLastTimeChanged(long value){
        this.lastTimeChanged = value;
    }
    
    public void setEvent(Event ev){
        this.curEvent = ev;
    }
    
    private Event getEvent(){
        return this.curEvent;
    }
    
    private void processEvent() throws InterruptedException{ // consider to use Reflection (action = method name)
        Event ev = getEvent();
        if(ev != null){
            String action = ev.getAction();
            switch(action){
                case ("setYellowLightOn"):
                    setYellowLightOn();
                    break;
                default:
                    print("Traffic light ID " + getID() + " get unindentified event: " + action);
            }
        }
        setEvent(null);
    }
    
    private int getTimePeriod(){
        return this.timePeriod;
    }
    
    public void setRedLightOn(){
        this.prevState = getGreen();
        this.curState = getRed();
        this.curState.toLight();
        
        setLastTimeChanged(System.currentTimeMillis());
    }
    
    public void setYellowLightOn() throws InterruptedException{
        this.curState = getYellow();
        this.curState.toLight();
        
        getThread().sleep(10_000);
        inverseState();
    }
    
    public void setGreenLightOn(){
        this.prevState = getRed();
        this.curState = getGreen();
        this.curState.toLight();
        
        setLastTimeChanged(System.currentTimeMillis());
    }
    
    public void inverseState(){
        if(this.prevState.equals(getRed())){
           setRedLightOn();
        } else {
           setGreenLightOn();
        }
    }
    
    public void addLogger(Logger logger){
        getRed().addLogger(logger);
        getYellow().addLogger(logger);
        getGreen().addLogger(logger);
        this.loggers.add(logger);
    }
    
    public void removeLogger(Logger logger) {
        getRed().removeLogger(logger);
        getYellow().removeLogger(logger);
        getGreen().removeLogger(logger);
        this.loggers.remove(logger);
    }
    
    public void removeLoggersAll() {
        getRed().removeLoggersAll();
        getYellow().removeLoggersAll();
        getGreen().removeLoggersAll();
        this.loggers.clear();
    }
    
    public void print(String msg){
        if(!loggers.isEmpty()){
            Iterator<Logger> iterator = loggers.iterator();
            
            while(iterator.hasNext()){
                iterator.next().write(msg);
            }
        }
    }
    
    public void start(){
        this.runMode = true;
        this.thread = new Thread(this);
        this.thread.start();
    }
    
    public void stop(){
        this.runMode = false;
        print("Traffic light ID " + getID() + " stopped");
    }
    
    @Override
    public void run() {
        print("Traffic light ID " + getID() + " started");
        setLastTimeChanged(System.currentTimeMillis());
        
        while(this.runMode){
            try {
                // TODO: implement threads synchonization
                getThread().sleep(300);
                processEvent();
                
                if((getLastTimeChanged() + getTimePeriod()) < System.currentTimeMillis()){ // checking if time period is passed
                    setYellowLightOn();
                }
            } catch (InterruptedException ex) {
                print(ex.getMessage());
            }
        }
    }

}
