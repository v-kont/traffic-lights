package com.log;

import com.log.virtual.Logger;

public final class FileLogger implements Logger {

    private static FileLogger _instance = null;

    private FileLogger() {}

    public static synchronized FileLogger getInstance() { // Singleton pattern
        if (_instance == null)
            _instance = new FileLogger();
        return _instance;
    }
    
    @Override
    public void write(String msg) {
        
    }

}
