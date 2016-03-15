package com.haritzmedina.sia.delicious;

import com.haritzmedina.sia.utils.RestCall;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Haritz Medina on 15/03/2016.
 */
public class DeliciousRequest {

    private String username;
    private String password;
    private DocumentBuilder docBuilder = null;

    public DeliciousRequest(String username, String password){
        this.username = username;
        this.password = password;
        DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
        try {
            docBuilder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

        /*Authenticator.setDefault(new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication (username, password.toCharArray());
            }
        });*/
    }

    public Document executeRequest(TreeMap<String, String> params){
        // Prepare request header for HTTP authentication in delicious
        Map<String, String> header = new HashMap<>();
        String userPassword = this.username + ":" + password;
        String encoding = new sun.misc.BASE64Encoder().encode(userPassword.getBytes());
        header.put("Authorization", "Basic " + encoding);

        // Execute remote rest query
        String response_string = null;
        try {
            RestCall rc = new RestCall();
            response_string = rc.callRestfulWebService("https://api.del.icio.us/v1/posts/add", params, header);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(response_string==null){
            return null;
        }
        System.out.println(response_string);
        // Retrieve xml document from response string
        Document response = null;
        try {
            response = docBuilder.parse( new InputSource( new StringReader(response_string)));
        } catch (SAXException | IOException e) {
            e.printStackTrace();
        }
        return response;
    }
}
