package com.haritzmedina.sia.flickr.caller;

import com.haritzmedina.sia.flickr.FlickrRequest;
import com.haritzmedina.sia.flickr.domain.FlickrUser;
import com.haritzmedina.sia.utils.XpathUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import java.util.Map;
import java.util.TreeMap;

/**
 * Flickr user functions
 * Created by Haritz Medina on 01/03/2016.
 */
public class FlickrUserCaller {
    public static String getUserIdByName(FlickrUser user, String apiKey, String token, String secret){
        TreeMap<String, String> params = new TreeMap<>();
        params.put("api_key",apiKey);
        params.put("method","flickr.people.findByUsername");
        params.put("username", user.getUsername());
        params.put("auth_token", token);

        FlickrRequest flickrRequest = new FlickrRequest(secret);
        Document response = flickrRequest.executeRequest(params);
        // Xpath extract userid
        Node id = (Node)XpathUtil.extractXpath(response, "//user/@id", XPathConstants.NODE);
        if(id!=null){
            return id.getNodeValue();
        }
        else{
            return null;
        }
    }
}
