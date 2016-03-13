package com.haritzmedina.sia.utils;

import java.io.*;
import java.util.*;
import java.net.*;

import javax.net.ssl.HttpsURLConnection;

public class RestCall {

    public static String buildWebQuery(Map<String, String> parameters) throws Exception {
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

    public static Map<String, String> parseWebResult(String parameters) throws Exception {
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
        System.out.println("la url es:");
        System.out.println(url.toString());
        // make post mode connection
        URLConnection urlc = url.openConnection();
        for (Map.Entry<String, String> entry : header.entrySet()) {
            ((HttpURLConnection)urlc).setRequestProperty(entry.getKey(), entry.getValue());
        }
        urlc.setAllowUserInteraction(false);
        //System.out.println("los parametros post son:");
        //System.out.println(((HttpURLConnection)urlc).getHeaderFields().toString());
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
    public String callSecureRestfulWebService(String address, Map<String, String> parameters) throws Exception {
        String query = buildWebQuery(parameters);

        String curl=address;
        if(query.length()>1){
            curl=curl+"?"+query;
        }
        URL url = new URL(curl);
        //System.out.println("la url es:");
        System.out.println("Called URI: "+url.toString());

        // make post mode connection
        HttpsURLConnection urlc = (HttpsURLConnection)url.openConnection();

        urlc.setAllowUserInteraction(false);

        //System.out.println((urlc).getHeaderFields().toString());
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
}

