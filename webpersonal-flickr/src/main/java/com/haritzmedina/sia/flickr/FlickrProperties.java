package com.haritzmedina.sia.flickr;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Haritz Medina on 01/03/2016.
 */
public class FlickrProperties {

    private static String FILE_NAME = "flickr.properties";
    private static Properties properties;

    public void load(){
        InputStream in = getClass().getClassLoader().getResourceAsStream(FILE_NAME);
        this.properties = new Properties();
        try {
            this.properties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getApiKey(){
        return properties.getProperty("flickr.api_key");
    }
}
