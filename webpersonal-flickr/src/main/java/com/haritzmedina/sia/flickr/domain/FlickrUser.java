package com.haritzmedina.sia.flickr.domain;

/**
 * Created by Haritz Medina on 13/03/2016.
 */
public class FlickrUser {

    private String id;

    public FlickrUser() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    private String username;
}
