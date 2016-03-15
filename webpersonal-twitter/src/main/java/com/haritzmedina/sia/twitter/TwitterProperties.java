package com.haritzmedina.sia.twitter;

/**
 * Created by Haritz Medina on 02/03/2016.
 */

import java.io.*;
import java.util.Properties;

/**
 * Created by Haritz Medina on 01/03/2016.
 */
public class TwitterProperties {

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
        TwitterProperties.properties = new Properties();
        try {
            TwitterProperties.properties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getAppKey(){
        return properties.getProperty("twitter.app.key");
    }

    public static String getAppSecret(){
        return properties.getProperty("twitter.app.secret");
    }

    public static String getUserToken(){
        return properties.getProperty("twitter.user.token");
    }

    public static String getUserSecret(){
        return properties.getProperty("twitter.user.secret");
    }
}

