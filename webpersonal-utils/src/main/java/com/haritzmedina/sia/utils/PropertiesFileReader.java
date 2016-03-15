package com.haritzmedina.sia.utils;

import java.io.*;
import java.util.Properties;

/**
 * Created by Haritz Medina on 15/03/2016.
 */
public class PropertiesFileReader {
    public static Properties load(String filePath){
        // Open file input stream
        InputStream in = null;
        try {
            in = new FileInputStream(new File(filePath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        };
        // Load properties from stream
        Properties properties = new Properties();
        try {
            properties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }
}
