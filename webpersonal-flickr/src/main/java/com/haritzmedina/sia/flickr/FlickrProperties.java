package com.haritzmedina.sia.flickr;

import java.io.*;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * Created by Haritz Medina on 01/03/2016.
 */
public class FlickrProperties {

    private static Properties properties;

    public static void load(String filePath){
        // Open file input stream
        InputStream in = null;
        try {
            in = new FileInputStream(new File(filePath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        };
        // Load properties from stream
        FlickrProperties.properties = new Properties();
        try {
            FlickrProperties.properties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getApiKey(){
        return properties.getProperty("flickr.api_key");
    }

    public static String getToken(){
        return properties.getProperty("flickr.token");
    }
    public static String getSecret(){
        return properties.getProperty("flickr.secret");
    }
}
