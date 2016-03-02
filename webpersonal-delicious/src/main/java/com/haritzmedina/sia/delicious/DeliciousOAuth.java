package com.haritzmedina.sia.delicious;


import java.io.*;
import java.net.*;
import java.util.Hashtable;
import java.util.Map;
import java.util.StringTokenizer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;


public class DeliciousOAuth {
    DocumentBuilder docBuilder;
    XPathFactory xpathFactory;
    String clientID="4d92932e794906911d393897f80300b2";
    String clientSecret="bd39b166fd6009bf8d915cbeaf98dd83";
    String code="6e606388081960b0ef8f39668170b7ce";
    String accessToken="184447-a52530ff768a9ef7b092435504a2b409";

    public static void main(String[] args) throws Exception {

        DeliciousOAuth dc=new DeliciousOAuth();
        dc.getPhotosv3();
    }
    //https://delicious.com/auth/authorize?client_id=4d92932e794906911d393897f80300b2&redirect_uri=localhost

    public void getPhotosv3() throws Exception {
        try {
            Map<String, String> params=new Hashtable<String, String>();

            params.put("client_id",clientID);
            params.put("client_secret",clientSecret);
            params.put("grant_type","code");
            params.put("code",code);
            params.put("redirect_uri","localhost");
            RestCall rc=new RestCall();
            InputStream response;
            //String response;
            response = rc.callRestfulWebServiceStream("POST","https://avosapi.delicious.com/api/v1/oauth/token", params);

            createDocumentBuilder();
            createXpathFactory();
            System.out.println(createString(response));
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

    public void getPhotosv4() throws Exception {
        try {
            Map<String, String> params=new Hashtable<String, String>();

            RestCall rc=new RestCall();
            InputStream response;
            Map<String, String> header=new Hashtable<String, String>();
            header.put("Authorization", "Bearer "+accessToken);

            //String response;
            response = rc.callRestfulWebServiceStream("GET","https://api.del.icio.us/v1/posts/all", params, header);

            System.out.println(createString(response));

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


        public String callRestfulWebService(String method,String address, Map<String, String> parameters) throws Exception {
            return callRestfulWebService(method,address,parameters,new Hashtable<String,String>());
        }

        public String callRestfulWebService(String method,String address, Map<String, String> parameters,Map<String, String> header) throws Exception {
            String query = buildWebQuery(parameters);

            String curl=address;
            if(query.length()>1){
                curl=curl+"?"+query;
            }
            URL url = new URL(curl);

            // make post mode connection
            HttpURLConnection urlc = (HttpURLConnection) url.openConnection();
            urlc.setRequestMethod(method);
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
        public InputStream callRestfulWebServiceStream(String method,String address, Map<String, String> parameters) throws Exception {
            return callRestfulWebServiceStream(method,address,parameters,new Hashtable<String,String>());

        }

        public InputStream callRestfulWebServiceStream(String method,String address, Map<String, String> parameters,Map<String, String> header) throws Exception {
            String query = buildWebQuery(parameters);

            String curl=address;
            if(query.length()>1){
                curl=curl+"?"+query;
            }
            URL url = new URL(curl);

            // make post mode connection
            HttpURLConnection urlc = (HttpURLConnection) url.openConnection();
            urlc.setRequestMethod(method);
            for (Map.Entry<String, String> entry : header.entrySet()) {
                ((HttpURLConnection)urlc).setRequestProperty(entry.getKey(), entry.getValue());
            }

            // retrieve result
            return urlc.getInputStream();

        }


    }
}