package com.haritzmedina.sia.flickr;

import com.haritzmedina.sia.utils.XpathUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import java.util.Map;
import java.util.TreeMap;

/**
 * Flickr user functions
 * Created by Haritz Medina on 01/03/2016.
 */
public class FlickrUser {
    public static String getUserIdByName(String username, String apiKey, String token, String secret){
        TreeMap<String, String> params = new TreeMap<>();
        params.put("api_key",apiKey);
        params.put("method","flickr.people.findByUsername");
        params.put("username", username);
        params.put("auth_token", token);

        FlickrRequest flickrRequest = new FlickrRequest(secret);
        Document response = flickrRequest.executeRequest(params);
        // Xpath extract userid
        Node id = (Node)XpathUtil.extractXpath(response, "//user/@id");
        if(id!=null){
            return id.getNodeValue();
        }
        else{
            return null;
        }
    }
}
