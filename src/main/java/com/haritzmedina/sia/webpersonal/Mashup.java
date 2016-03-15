package com.haritzmedina.sia.webpersonal;


import com.haritzmedina.sia.delicious.DeliciousProperties;
import com.haritzmedina.sia.delicious.caller.DeliciousLinkCaller;
import com.haritzmedina.sia.flickr.caller.FlickrPhotoCaller;
import com.haritzmedina.sia.flickr.FlickrProperties;
import com.haritzmedina.sia.flickr.domain.FlickrPhoto;
import com.haritzmedina.sia.flickr.domain.FlickrUser;
import com.haritzmedina.sia.twitter.TwitterProperties;
import com.haritzmedina.sia.twitter.TwitterRequest;
import twitter4j.TwitterException;

import java.util.List;

/**
 * Mashup main application
 * Created by Haritz Medina on 03/03/2016.
 */
public class Mashup {

    private static String DELICIOUS_PROPERTIES_FILE = "properties/delicious.properties";
    private static String FLICKR_PROPERTIES_FILE = "properties/flickr.properties";
    private static String TWITTER_PROPERTIES_FILE = "properties/twitter.properties";

    private static String FLICKR_USERNAME = "haritzmedina";
    private static String FLICKR_TAG_TO_PROCESS = "publish";
    private static String FLICKR_TAG_PROCESSED = "published";

    public static void main(String[] args) throws Exception {

        // STEP 1: Get photos to mashup
        FlickrProperties.load(FLICKR_PROPERTIES_FILE);
        FlickrUser mockUser = new FlickrUser();
        mockUser.setUsername(FLICKR_USERNAME);

        List<FlickrPhoto> photos = FlickrPhotoCaller.getPhotosByTagAndUser(
                mockUser,
                FLICKR_TAG_TO_PROCESS,
                FlickrProperties.getApiKey(),
                FlickrProperties.getToken(),
                FlickrProperties.getSecret()
        );

        // STEP 2: Post on delicious
        DeliciousProperties.load(DELICIOUS_PROPERTIES_FILE);
        photos.forEach((photo) -> {
            // Call delicious link adder
            DeliciousLinkCaller.postURI(
                    photo.getPhotoURI(),
                    "Photo named "+photo.getTitle()+" shared.",
                    "shared,photo",
                    DeliciousProperties.getUsername(),
                    DeliciousProperties.getPassword()
                    );
        });

        // STEP 3: Share tweet on twitter
        TwitterProperties.load(TWITTER_PROPERTIES_FILE);
        TwitterRequest twitterRequest = new TwitterRequest(
                TwitterProperties.getAppKey(),
                TwitterProperties.getAppSecret()
        );
        photos.forEach((photo) -> {
            try {
                twitterRequest.updateStatus(
                        "Flickr photo uploaded: "+photo.getPhotoURI(),
                        TwitterProperties.getUserToken(),
                        TwitterProperties.getUserSecret());
            } catch (TwitterException e) {
                e.printStackTrace();
            }
        });

        // STEP 4: Set flickr photos as shared
        photos.forEach((photo) -> {
            // Retrieve tag id to remove
            String tag_id = FlickrPhotoCaller.retrieveTagIdFromPhotoAndTag(
                    photo,
                    FLICKR_TAG_TO_PROCESS,
                    FlickrProperties.getApiKey(),
                    FlickrProperties.getToken(),
                    FlickrProperties.getSecret()
            );
            // Remove tag to process
            Boolean result = FlickrPhotoCaller.removeTagToPhoto(
                    tag_id,
                    FlickrProperties.getApiKey(),
                    FlickrProperties.getToken(),
                    FlickrProperties.getSecret()
            );
            if(result){
                System.out.println("Flickr removed tag "+FLICKR_TAG_TO_PROCESS+" from photo "+photo.getId());
            }
            // Add tag processed
            result = FlickrPhotoCaller.addTagsToPhoto(
                    photo,
                    FLICKR_TAG_PROCESSED,
                    FlickrProperties.getApiKey(),
                    FlickrProperties.getToken(),
                    FlickrProperties.getSecret()
            );
            if(result){
                System.out.println("Flickr added tag "+ FLICKR_TAG_PROCESSED +" from photo "+photo.getId());
            }
        });

    }

}
