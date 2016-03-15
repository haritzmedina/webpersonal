package com.haritzmedina.sia.delicious;

/**
 * Created by Haritz Medina on 02/03/2016.
 */

import com.haritzmedina.sia.utils.PropertiesFileReader;

import java.io.*;
import java.util.Properties;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Haritz Medina on 01/03/2016.
 */
public class DeliciousProperties {

    private static Properties properties;

    public static void load(String filePath){
        DeliciousProperties.properties = PropertiesFileReader.load(filePath);
    }

    public static String getUsername(){
        return properties.getProperty("delicious.username");
    }

    public static String getPassword(){
        return properties.getProperty("delicious.password");
    }
}

