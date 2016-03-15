package com.haritzmedina.sia.flickr;

import com.haritzmedina.sia.utils.RestCall;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.net.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Hashtable;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

/**
 * Created by Haritz Medina on 01/03/2016.
 */
public class FlickrRequest {
    private DocumentBuilder docBuilder = null;
    private String secret;

    public FlickrRequest(String secret){
        this.secret = secret;
        DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
        try {
            docBuilder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }
    public Document executeRequest(TreeMap<String, String> params){
        // Create signature based on params ordered alphabetically
        String signature_string = this.secret;
        for(String key : params.keySet()){
            signature_string += key+params.get(key);
        }
        System.out.println(signature_string);
        // MD5 hash to signature
        String signature = null;
        try {
             signature = MD5(signature_string);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        // Add signature to the params ready to send
        params.put("api_sig", signature);

        // Execute remote rest query
        String response_string = null;
        try {
            RestCall rc = new RestCall();
            response_string = rc.callRestfulWebService("https://api.flickr.com/services/rest", params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(response_string==null){
            return null;
        }
        // Retrieve xml document from response string
        Document response = null;
        try {
            response = docBuilder.parse( new InputSource( new StringReader(response_string)));
        } catch (SAXException | IOException e) {
            e.printStackTrace();
        }
        return response;
    }


    public String MD5(String text)
            throws NoSuchAlgorithmException {
        MessageDigest md;
        md = MessageDigest.getInstance("MD5");
        byte[] md5hash = new byte[32];
        try {
            md.update(text.getBytes("iso-8859-1"), 0, text.length());
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        md5hash = md.digest();
        return convertToHex(md5hash);
    }

    private  String convertToHex(byte[] data) {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < data.length; i++) {
            int halfbyte = (data[i] >>> 4) & 0x0F;
            int two_halfs = 0;
            do {
                if ((0 <= halfbyte) && (halfbyte <= 9))
                    buf.append((char) ('0' + halfbyte));
                else
                    buf.append((char) ('a' + (halfbyte - 10)));
                halfbyte = data[i] & 0x0F;
            } while(two_halfs++ < 1);
        }
        return buf.toString();
    }

}
