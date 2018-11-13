package com.log;

import com.log.virtual.Logger;

public final class ConsoleLogger implements Logger {

    private static ConsoleLogger _instance = null;

    private ConsoleLogger() {}

    public static synchronized ConsoleLogger getInstance() { // Singleton pattern
        if (_instance == null)
            _instance = new ConsoleLogger();
        return _instance;
    }
    
    @Override
    public void write(String msg) {
         System.out.println(msg);
    }
    
}
