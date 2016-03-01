package com.haritzmedina.sia.flickr;

import java.util.Hashtable;
import java.util.Map;

/**
 * Created by Haritz Medina on 01/03/2016.
 */
public class FlickrUser {
    public String getUserIdByName(String username, String apiKey){
        Map<String, String> params = new Hashtable<String, String>();
        params.put("api_key",apiKey);
        params.put("method","flickr.people.findByUsername");
        params.put("username", username);

        FlickrRequest flickrRequest = new FlickrRequest();
        flickrRequest.executeRequest(params);
        // TODO Xpath extract userid
        return null;
    }
}
