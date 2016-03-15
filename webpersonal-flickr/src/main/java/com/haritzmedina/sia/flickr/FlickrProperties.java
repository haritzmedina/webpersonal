package com.haritzmedina.sia.flickr;

import com.haritzmedina.sia.utils.PropertiesFileReader;

import java.io.*;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * Created by Haritz Medina on 01/03/2016.
 */
public class FlickrProperties {

    private static Properties properties;

    public static void load(String filePath){
        FlickrProperties.properties = PropertiesFileReader.load(filePath);
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
