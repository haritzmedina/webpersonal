package com.haritzmedina.sia.flickr;

import com.haritzmedina.sia.utils.XpathUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * Created by Haritz Medina on 03/03/2016.
 */
public class FlickrPhoto {
    /**
     *
     * @param apiKey
     * @param token
     * @param secret
     * @param username
     * @param tag
     * @return
     */
    public static List<String> getPhotoByTagAndUser(String apiKey, String token, String secret, String username, String tag){
        TreeMap<String, String> params = new TreeMap<>();
        params.put("api_key",apiKey);

        params.put("username", username);
        params.put("auth_token", token);

        FlickrRequest flickrRequest = new FlickrRequest(secret);
        Document response = flickrRequest.executeRequest(params);
        // Xpath extract userid
        NodeList photoNodes = (NodeList) XpathUtil.extractXpath(response, "//user/@id");
        if(photoNodes!=null){
            List<String> photos = new ArrayList<>();
            for(int i=0; i<photoNodes.getLength();i++) {
                Node photoNode = photoNodes.item(i);
                photos.add(photoNode.getNodeValue());
            }
            return photos;
        }
        else{
            return null;
        }
    }
    public static List<String> listPhotoByUser(){
        TreeMap<String, String> params = new TreeMap<>();
        params.put("method","flickr.photosets.getList");
        return null;
    }
}
