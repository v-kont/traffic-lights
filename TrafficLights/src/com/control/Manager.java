package com.control;

import com.log.virtual.Logger;
import com.trafficlights.TrafficLights;
import java.util.Comparator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class Manager implements Runnable {

    private boolean runMode;
    ConcurrentHashMap<Integer, TrafficLights> TLList = new ConcurrentHashMap<>(); // cause HashMap is not thread-safe
    
    public void addTrafficLights(TrafficLights tl){
        Integer ID = getNextID(); //to prevent interferring IDs and thus make IDs unique
        tl.setID(ID);
        TLList.put(ID, tl);
        
        if(runMode){
            tl.start();
        }
    }
    
    private int getNextID(){
        Set<Integer> keyset = TLList.keySet();
        int max = keyset.stream().max(Comparator.naturalOrder()).orElse(0);
        return ++max;
    }
       
    public void removeTrafficLights(int index){
        TrafficLights tl = TLList.get(index);
        tl.stop();
        TLList.remove(index);
    }
    
    private void setStartupState(){
        System.out.println("Setting startup state:");
        String defLight = null;
        
        for(Map.Entry<Integer, TrafficLights> entry : TLList.entrySet()){
            TrafficLights tl = entry.getValue();
            if(defLight == null || defLight.equalsIgnoreCase("green")){
                tl.setRedLightOn();
                defLight = "red";
            } else if(defLight.equalsIgnoreCase("red")){
                tl.setGreenLightOn();
                defLight = "green";
            }
        }
    }
    
    private void setYellowLightOn(){
        
        TLList.entrySet().stream().map((entry) -> entry.getValue()).forEachOrdered((tl) -> {
            tl.setEvent(new Event("setYellowLightOn", 0));
        });
    }
        
    private void setAllActiveTraffciLigthsSleep(long millis){
        TLList.entrySet().stream().map((entry) -> entry.getValue()).forEachOrdered((tl) -> {
            if(tl.getCurState().equalsIgnoreCase("started")){
                try {
                    tl.getThread().sleep(millis);
                } catch (InterruptedException ex) {
                    System.out.println(ex.getStackTrace());
                }
            }
        });
    }
    
    public void start(){
        System.out.println("Manager starting...");
        setStartupState();

        this.runMode = true;
        new Thread(this).start();
        System.out.println("Starting traffic lights:");
        
        TLList.entrySet().stream().map((entry) -> entry.getValue()).forEachOrdered((tl) -> {
            tl.start();
        });
    }
    
    private void stop(){
        System.out.println("Stopping manager...");
        System.out.println("Stopping running traffic lights:");

        TLList.entrySet().stream().map((entry) -> entry.getValue()).forEachOrdered((tl) -> {
            tl.stop();
        });
        
        this.runMode = false;
    }
    
    private void startTL(int id){
        TLList.get(id).start();
    }
    
    private void stopTL(int id){
        TLList.get(id).stop();
    }
    
    private void help(){
        System.out.println("\nTo get this help type 'help'");
        System.out.println("To stop Manager type 'stop manager'");
        System.out.println("To get list and states of traffic lights type 'list'");
        System.out.println("To add traffic light type 'add <langcode>'. For example, 'add ru'");
        System.out.println("To start specific traffic light type 'start <ID of traffic light>'. For example, 'start 1'");
        System.out.println("To stop specific traffic light type 'stop <ID of traffic light>'. For example, 'stop 1'");
        System.out.println("To remove traffic light type 'remove <ID of traffic light>'. For example, 'remove 1'");
        System.out.println("To change traffic light current light type 'change'");
    }
    
    private void list(){
        System.out.println("\nList of traffic lights:");
        
        for(Map.Entry<Integer, TrafficLights> entry : TLList.entrySet()){
            TrafficLights tl = entry.getValue();
            System.out.println("ID: " + tl.getID() + ", current light: " + tl.getCurLight() + ", state: " + tl.getCurState());
        }
        
        System.out.println("Number of active threads is " + Thread.activeCount());
    }
    
    public void addLogger(Logger logger){
        
        for(Map.Entry<Integer, TrafficLights> entry : TLList.entrySet()){
            TrafficLights tl = entry.getValue();
            tl.addLogger(logger);
        }
    }
    
    public void removeLogger(Logger logger) {
        
        for(Map.Entry<Integer, TrafficLights> entry : TLList.entrySet()){
            TrafficLights tl = entry.getValue();
            tl.removeLogger(logger);
        }
    }
    
    public void removeLoggersAll() {
        
        TLList.entrySet().stream().map((entry) -> entry.getValue()).forEachOrdered((tl) -> {
            tl.removeLoggersAll();
        });
    }
    
    @Override
    public void run() {
        if(TLList.isEmpty()){
            //throw new Exception("Add at leat one Traffic light!");
        }
        
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Manager started! To stop Manager type 'stop manager'");
        System.out.println("To get help type 'help' >>");
        System.out.println("Number of active threads is " + Thread.activeCount());
        
        while(this.runMode){
            String cmd = "";
            if (scanner.hasNext()
                    && (cmd = scanner.nextLine().trim()) != null
                    && !cmd.isEmpty()) {
                String id = "";
                switch(cmd){
                    case ("help"):
                        help();
                        break;
                    case ("stop manager"):
                        stop();
                        break;
                    case ("list"):
                        list();
                        break;
                    case ("change"):
                        setYellowLightOn();
                        break;
                    default:
                        if(cmd.matches("^stop \\d")){
                            id = cmd.replaceAll("\\D", "");
                            stopTL(Integer.valueOf(id));
                        } else if(cmd.matches("^start \\d")) {
                            id = cmd.replaceAll("\\D", "");
                            startTL(Integer.valueOf(id));
                        } else {
                            System.out.println("Incorrect command!");
                        }
                }
            }
        }
    }
    
}
