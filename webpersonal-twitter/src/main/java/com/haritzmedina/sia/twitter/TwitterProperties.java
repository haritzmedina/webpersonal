package com.haritzmedina.sia.twitter;

import com.haritzmedina.sia.utils.PropertiesFileReader;

import java.io.*;
import java.util.Properties;

/**
 * Twitter properties container
 * Created by Haritz Medina on 02/03/2016.
 */

public class TwitterProperties {

    private static Properties properties;

    public static void load(String filePath){
        TwitterProperties.properties = PropertiesFileReader.load(filePath);
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

