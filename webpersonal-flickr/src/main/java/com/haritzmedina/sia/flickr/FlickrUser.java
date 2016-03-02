package com.haritzmedina.sia.flickr;

import java.util.Map;
import java.util.TreeMap;

/**
 * Flickr user functions
 * Created by Haritz Medina on 01/03/2016.
 */
public class FlickrUser {
    public static String getUserIdByName(String username, String apiKey, String token, String secret){
        Map<String, String> params = new TreeMap<>();
        params.put("api_key",apiKey);
        params.put("method","flickr.people.findByUsername");
        params.put("username", username);
        params.put("auth_token", token);

        FlickrRequest flickrRequest = new FlickrRequest(secret);
        flickrRequest.executeRequest(params);
        // TODO Xpath extract userid
        return null;
    }
}
