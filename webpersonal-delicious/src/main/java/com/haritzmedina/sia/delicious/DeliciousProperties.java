package com.haritzmedina.sia.delicious;

/**
 * Created by Haritz Medina on 02/03/2016.
 */

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Haritz Medina on 01/03/2016.
 */
public class DeliciousProperties {

    private static String FILE_NAME = "delicious.properties";
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

    public String getPassword(){
        return properties.getProperty("delicious.username");
    }
    public String getUsername(){
        return properties.getProperty("delicious.password");
    }
}
