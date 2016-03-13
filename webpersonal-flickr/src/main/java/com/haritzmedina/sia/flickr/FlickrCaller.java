package com.haritzmedina.sia.flickr;

import java.io.*;
import java.net.*;
import java.util.Hashtable;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;


public class FlickrCaller {
    DocumentBuilder docBuilder;
    XPathFactory xpathFactory;
    static FlickrProperties flickrProperties;

    public static void main(String[] args) throws Exception {

        FlickrCaller fc=new FlickrCaller();
        init();
        fc.getPhotosv2();
    }

    private static void init() {
        FlickrProperties.load("properties/flickr.properties");
    }

    public void getPhotosv1() throws Exception {
        String address="https://api.flickr.com/services/rest/?method=flickr.photos.search&";
        address=address+"user_id=40622837@N04&api_key="+flickrProperties.getApiKey();

        URL url = new URL(address);

        URLConnection urlc = url.openConnection();
        InputStream istream=urlc.getInputStream();

        String response=createString(istream);
        System.out.println(response);
    }

    public void getPhotosv2() throws Exception {
        createDocumentBuilder();
        createXpathFactory();
        String address="https://api.flickr.com/services/rest/?method=flickr.photos.search&";
        address=address+"user_id=40622837@N04&api_key="+flickrProperties.getApiKey();

        URL url = new URL(address);

        URLConnection urlc = url.openConnection();
        InputStream istream=urlc.getInputStream();

        Document res1XML= docBuilder.parse(istream);
        XPathExpression expr1 = xpathFactory.newXPath().compile("//photos/photo/@id");
        NodeList photos=((NodeList)expr1.evaluate(res1XML,XPathConstants.NODESET));
        String s;
        for(int i=0;i<photos.getLength();i++){
            s=photos.item(i).getNodeValue();
            System.out.println(s);

        }

    }
    public void getPhotosv3() throws Exception {
        try {
            Map<String, String> params=new Hashtable<String, String>();

            params.put("api_key","TuapiKey");
            params.put("method","flickr.photos.search");
            params.put("user_id","40622837@N04");
            RestCall rc=new RestCall();
            InputStream response;
            //String response;
            response = rc.callRestfulWebServiceStream("http://api.flickr.com/services/rest", params);

            createDocumentBuilder();
            createXpathFactory();

            Document res1XML= docBuilder.parse(response);
            XPathExpression expr1 = xpathFactory.newXPath().compile("//photos/photo/@id");
            NodeList photos=((NodeList)expr1.evaluate(res1XML,XPathConstants.NODESET));
            String s;
            for(int i=0;i<photos.getLength();i++){
                s=photos.item(i).getNodeValue();
                System.out.println(s);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private  void createDocumentBuilder(){
        try {
            DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
            docBuilder = factory.newDocumentBuilder();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    private  void createXpathFactory(){
        try {
            xpathFactory=XPathFactory.newInstance();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    private String createString(InputStream inputStream){
        try {
            BufferedReader br;

            br = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }
            br.close();
            return  sb.toString();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
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