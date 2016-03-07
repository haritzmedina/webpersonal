package com.haritzmedina.sia.webpersonal;

import com.haritzmedina.sia.flickr.FlickrProperties;
import com.haritzmedina.sia.flickr.FlickrUser;

/**
 * Created by Haritz Medina on 03/03/2016.
 */
public class Mashup {

    private static String DELICIOUS_PROPERTIES_FILE = "delicious.properties";
    private static String FLICKR_PROPERTIES_FILE = "flickr.properties";
    private static String TWITTER_PROPERTIES_FILE = "twitter.properties";
    private static String YAHOO_PROPERTIES_FILE = "yahoo.properties";

    public static void main(String[] args) throws Exception {
        FlickrProperties.load(FLICKR_PROPERTIES_FILE);
        String id = FlickrUser.getUserIdByName(
                "haritzmedina",
                FlickrProperties.getApiKey(),
                FlickrProperties.getToken(),
                FlickrProperties.getSecret()
        );
        System.out.println(id);
    }

}
