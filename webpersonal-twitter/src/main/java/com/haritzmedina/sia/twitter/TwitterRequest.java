package com.haritzmedina.sia.twitter;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

/**
 * Created by Haritz Medina on 15/03/2016.
 */
public class TwitterRequest {

    private Twitter twitter;

    public TwitterRequest(String appKey, String appSecret){
        this.twitter = new TwitterFactory().getInstance();
        this.twitter.setOAuthConsumer(appKey, appSecret);
    }

    public void updateStatus(String status, String userToken, String userSecret) throws TwitterException {
        twitter.setOAuthAccessToken(new AccessToken(userToken, userSecret));
        twitter.updateStatus(status);
    }
}
