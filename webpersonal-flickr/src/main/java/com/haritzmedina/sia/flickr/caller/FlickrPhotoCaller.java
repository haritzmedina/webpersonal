package com.haritzmedina.sia.flickr.caller;

import com.haritzmedina.sia.flickr.FlickrRequest;
import com.haritzmedina.sia.flickr.domain.FlickrPhoto;
import com.haritzmedina.sia.flickr.domain.FlickrPhotoFactory;
import com.haritzmedina.sia.flickr.domain.FlickrUser;
import com.haritzmedina.sia.utils.XpathUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPathConstants;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * Created by Haritz Medina on 03/03/2016.
 */
public class FlickrPhotoCaller {
    public static List<FlickrPhoto> getPhotosByTagAndUser(FlickrUser user, String tag, String apiKey, String token, String secret){

        // Get userid from username
        String userId = FlickrUserCaller.getUserIdByName(user, apiKey, token, secret);

        // Retrieve photos by user and tag
        TreeMap<String, String> params = new TreeMap<>();
        params.put("method","flickr.photos.search");
        params.put("api_key",apiKey);
        params.put("auth_token", token);
        params.put("user_id", userId);
        params.put("tags", tag);
        FlickrRequest flickrRequest = new FlickrRequest(secret);
        Document response = flickrRequest.executeRequest(params);
        NodeList photoNodes = (NodeList) XpathUtil.extractXpath(response, "//photos/photo", XPathConstants.NODESET);

        List<FlickrPhoto> flickrPhotos = new ArrayList<>();
        for(int i=0; i<photoNodes.getLength(); i++){
            flickrPhotos.add(FlickrPhotoFactory.newInstance(photoNodes.item(i)));
        }

        return flickrPhotos;
    }

    public static boolean removeTagToPhoto(String tag_id, String apiKey, String token, String secret){
        // Prepare parameters to send
        TreeMap<String, String> params = new TreeMap<>();
        params.put("method","flickr.photos.removeTag");
        params.put("api_key",apiKey);
        params.put("auth_token", token);
        params.put("tag_id", tag_id);
        // Execute request
        FlickrRequest flickrRequest = new FlickrRequest(secret);
        Document response = flickrRequest.executeRequest(params);
        // Check the response from server
        Node result = (Node) XpathUtil.extractXpath(response, "/rsp/@stat", XPathConstants.NODE);
        if(result.getNodeValue().compareTo("ok")==0){
            return true;
        }
        else{
            return false;
        }
    }

    public static boolean addTagsToPhoto(FlickrPhoto photo, String tags, String apiKey, String token, String secret){
        TreeMap<String, String> params = new TreeMap<>();
        params.put("method","flickr.photos.addTags");
        params.put("api_key",apiKey);
        params.put("auth_token", token);
        params.put("photo_id", photo.getId());
        params.put("tags", tags);
        FlickrRequest flickrRequest = new FlickrRequest(secret);
        Document response = flickrRequest.executeRequest(params);
        Node result = (Node) XpathUtil.extractXpath(response, "/rsp/@stat", XPathConstants.NODE);
        if(result.getNodeValue().compareTo("ok")==0){
            return true;
        }
        else{
            return false;
        }
    }

    public static String retrieveTagIdFromPhotoAndTag(FlickrPhoto photo, String tag, String apiKey, String token, String secret){
        TreeMap<String, String> params = new TreeMap<>();
        params.put("method","flickr.photos.getInfo");
        params.put("api_key",apiKey);
        params.put("auth_token", token);
        params.put("photo_id", photo.getId());
        FlickrRequest flickrRequest = new FlickrRequest(secret);
        Document response = flickrRequest.executeRequest(params);
        Node result = (Node) XpathUtil.extractXpath(response, "//tags/tag[@raw='"+tag+"']/@id", XPathConstants.NODE);
        return result.getNodeValue();
    }

}
