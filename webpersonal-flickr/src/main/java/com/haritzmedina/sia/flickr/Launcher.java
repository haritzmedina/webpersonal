package com.haritzmedina.sia.flickr;

/**
 * Created by Haritz Medina on 01/03/2016.
 */
public class Launcher {

    public static void main(String[] args) throws Exception {
        // Load properties from file
        FlickrProperties.load();
        // Request user id
        String userid = FlickrUser.getUserIdByName(
                "haritzmedina",
                FlickrProperties.getApiKey(),
                FlickrProperties.getToken(),
                FlickrProperties.getSecret())
        ;
        System.out.print(userid);
    }
}
