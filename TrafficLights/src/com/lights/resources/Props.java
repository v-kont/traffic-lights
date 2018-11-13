package com.lights.resources;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
 
public class Props {
    
    private final static String PATH_TO_PROPERTIES = "src/com/lights/resources/";
 
    public static Properties getPropertiesFile(String filename) throws IOException {
 
        FileInputStream fileInputStream;
        Properties prop = new Properties();
 
        try {
            fileInputStream = new FileInputStream(PATH_TO_PROPERTIES.concat(filename).concat(".properties"));
            prop.load(fileInputStream);
            
        } catch (IOException e) {
            throw e;
        }
        return prop;
    }
 
}
