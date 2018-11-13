package com.control;

public class Event {
    
    private final String action;
    private final Object value;
    
    public Event(String action, Object value){
        this.action = action;
        this.value = value;
    }
    
    public String getAction(){
        return this.action;
    }
    
    public Object getValue(){
        return this.value;
    }
    
}
