package com.haritzmedina.sia.delicious.caller;

import com.haritzmedina.sia.delicious.DeliciousRequest;

import java.util.Hashtable;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Haritz Medina on 15/03/2016.
 */
public class DeliciousLinkCaller {
    public static void postURI(String uri, String description, String tags, String username, String password){
        TreeMap<String, String> params = new TreeMap<>();
        params.put("url", uri);
        params.put("description",description);
        params.put("tags",tags);
        DeliciousRequest deliciousRequest = new DeliciousRequest(username, password);
        deliciousRequest.executeRequest(params);
    }
}
