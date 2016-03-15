package com.haritzmedina.sia.webpersonal;

import com.haritzmedina.sia.twitter.TwitterProperties;
import com.haritzmedina.sia.twitter.auth.TwitterRequestToken;
import twitter4j.auth.AccessToken;

/**
 * Created by Haritz Medina on 15/03/2016.
 */
public class TwitterGetAuthorization {

    private static String TWITTER_PROPERTIES_FILE = "properties/twitter.properties";

    public static void main(String[] args) throws Exception{
        TwitterProperties.load(TWITTER_PROPERTIES_FILE);
        TwitterRequestToken twitterRequestToken = new TwitterRequestToken(
                TwitterProperties.getAppKey(),
                TwitterProperties.getAppSecret()
        );
        AccessToken accessToken = twitterRequestToken.requestAccessToken();
        System.out.println(accessToken.toString());
        System.out.println("twitter.user.token="+accessToken.getToken());
        System.out.println("twitter.user.secret="+accessToken.getTokenSecret());
    }
}
