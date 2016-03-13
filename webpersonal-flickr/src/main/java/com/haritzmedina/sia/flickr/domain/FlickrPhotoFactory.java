package com.haritzmedina.sia.flickr.domain;

import org.w3c.dom.Node;

/**
 * Created by Haritz Medina on 13/03/2016.
 */
public class FlickrPhotoFactory {

    public static FlickrPhoto newInstance(Node photoNode){
        FlickrPhoto photo = new FlickrPhoto();
        photo.setId(photoNode.getAttributes().getNamedItem("id").getNodeValue());
        photo.setOwner(photoNode.getAttributes().getNamedItem("owner").getNodeValue());
        photo.setTitle(photoNode.getAttributes().getNamedItem("title").getNodeValue());
        // TODO Add the rest of the properties
        return photo;
    }
}
