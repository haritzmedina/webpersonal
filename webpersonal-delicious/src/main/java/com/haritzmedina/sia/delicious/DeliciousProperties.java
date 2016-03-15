package com.haritzmedina.sia.delicious;

/**
 * Created by Haritz Medina on 02/03/2016.
 */

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
        // Open file input stream
        InputStream in = null;
        try {
            in = new FileInputStream(new File(filePath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        };
        // Load properties from stream
        DeliciousProperties.properties = new Properties();
        try {
            DeliciousProperties.properties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getUsername(){
        return properties.getProperty("delicious.username");
    }

    public static String getPassword(){
        return properties.getProperty("delicious.password");
    }
}

