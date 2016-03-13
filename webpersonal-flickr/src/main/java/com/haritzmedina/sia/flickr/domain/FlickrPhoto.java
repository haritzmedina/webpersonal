package com.haritzmedina.sia.flickr.domain;

/**
 * Created by Haritz Medina on 13/03/2016.
 */
public class FlickrPhoto {

    private static final String flickrBaseURI = "https://www.flickr.com/photos/";

    private String id;
    private String owner;
    private String secret;
    private String farm;
    private String title;
    private Boolean isPublic;
    private Boolean isFriend;
    private Boolean isFamily;

    public Boolean getFamily() {
        return isFamily;
    }

    public void setFamily(Boolean family) {
        isFamily = family;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getFarm() {
        return farm;
    }

    public void setFarm(String farm) {
        this.farm = farm;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getPublic() {
        return isPublic;
    }

    public void setPublic(Boolean aPublic) {
        isPublic = aPublic;
    }

    public Boolean getFriend() {
        return isFriend;
    }

    public void setFriend(Boolean friend) {
        isFriend = friend;
    }

    public FlickrPhoto() {
    }

    @Override
    public String toString() {
        return "FlickrPhoto{" +
                "id='" + id + '\'' +
                ", owner='" + owner + '\'' +
                ", secret='" + secret + '\'' +
                ", farm='" + farm + '\'' +
                ", title='" + title + '\'' +
                ", isPublic=" + isPublic +
                ", isFriend=" + isFriend +
                ", isFamily=" + isFamily +
                '}';
    }

    public String getPhotoURI(){
        return this.flickrBaseURI+this.owner+"/"+this.id;
    }
}
