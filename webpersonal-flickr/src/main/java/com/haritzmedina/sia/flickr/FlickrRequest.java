package com.haritzmedina.sia.flickr;

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

    public FlickrRequest(){
        DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
        try {
            docBuilder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }
    public Document executeRequest(Map params){
        Document xml = null;
        InputStream response;
        try {
            RestCall rc = new RestCall();
            response = rc.callRestfulWebServiceStream("https://api.flickr.com/services/rest", params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return xml;
    }

    private class RestCall {

        public  String buildWebQuery(Map<String, String> parameters) throws Exception {
            String res=null;
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, String> entry : parameters.entrySet()) {
                String key = URLEncoder.encode(entry.getKey(), "UTF-8");
                String value = URLEncoder.encode(entry.getValue(), "UTF-8");
                sb.append(key).append("=").append(value).append("&");
            }
            if(sb.length()>0){
                res=sb.toString().substring(0, sb.length() - 1);
            }else{
                res=sb.toString();
            }
            return res;
        }

        public  Map<String, String> parseWebResult(String parameters) throws Exception {
            StringTokenizer amp = new StringTokenizer(parameters,"&");
            Map<String, String> res=new Hashtable<String, String>();
            while (amp.hasMoreTokens()) {
                String paramVal=amp.nextToken();
                int ind = paramVal.indexOf("=");
                String key = URLDecoder.decode(paramVal.substring(0,ind), "UTF-8");
                String value = URLDecoder.decode(paramVal.substring(ind+1), "UTF-8");
                res.put(key, value);
            }
            return res;
        }


        public String callRestfulWebService(String address, Map<String, String> parameters) throws Exception {
            return callRestfulWebService(address,parameters,new Hashtable<String,String>());
        }

        public String callRestfulWebService(String address, Map<String, String> parameters,Map<String, String> header) throws Exception {
            String query = buildWebQuery(parameters);

            String curl=address;
            if(query.length()>1){
                curl=curl+"?"+query;
            }
            URL url = new URL(curl);

            // make post mode connection
            URLConnection urlc = url.openConnection();
            for (Map.Entry<String, String> entry : header.entrySet()) {
                ((HttpURLConnection)urlc).setRequestProperty(entry.getKey(), entry.getValue());
            }
            urlc.setAllowUserInteraction(false);

            // retrieve result
            BufferedReader br = new BufferedReader(new InputStreamReader(urlc.getInputStream(), "UTF-8"));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }
            br.close();

            return sb.toString();
        }
        public InputStream callRestfulWebServiceStream(String address, Map<String, String> parameters) throws Exception {
            String query = buildWebQuery(parameters);

            String curl=address;
            if(query.length()>1){
                curl=curl+"?"+query;
            }
            URL url = new URL(curl);

            // make post mode connection
            URLConnection urlc = url.openConnection();

            // retrieve result
            return urlc.getInputStream();

        }


    }
}
