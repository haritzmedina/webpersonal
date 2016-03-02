package com.haritzmedina.sia.flickr;

import com.haritzmedina.sia.utils.RestCall;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;
import java.util.Hashtable;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Created by Haritz Medina on 01/03/2016.
 */
public class FlickrRequest {
    private DocumentBuilder docBuilder = null;
    private Map params;
    private String secret;

    public FlickrRequest(String secret){
        this.params = params;
        this.secret = secret;
        DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
        try {
            docBuilder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }
    public Document executeRequest(Map<String, String> params){
        Document xml = null;
        // TODO Create signature

        // Execute remote rest query
        Document response = null;
        try {
            RestCall rc = new RestCall();
            String result = rc.callRestfulWebService("https://api.flickr.com/services/rest", params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

}
