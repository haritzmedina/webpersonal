package com.haritzmedina.sia.delicious;

import java.io.*;
import java.net.*;
import java.util.*;

import javax.xml.parsers.*;
import javax.xml.xpath.*;

import com.haritzmedina.sia.utils.RestCall;
import org.w3c.dom.*;


public class DeliciousCaller {
    private static DocumentBuilder docBuilder;
    private static XPathFactory xpathFactory;
    private static void configureDelicious(){
        Authenticator.setDefault(new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                //DeliciousProperties deliciousProperties = new DeliciousProperties();
                //deliciousProperties.load();
                //System.out.println(deliciousProperties.getUsername()+deliciousProperties.getPassword());
                return new PasswordAuthentication ("tagmas","onekin1".toCharArray());
            }
        });
    }

    private static void createDocumentBuilder(){
        try {
            DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
            docBuilder = factory.newDocumentBuilder();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    private static void createXpathFactory(){
        try {
            xpathFactory=XPathFactory.newInstance();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {
        configureDelicious();

        DeliciousCaller dc=new DeliciousCaller();
        //System.out.println(dc.getTagsv3());
        dc.createPost("http://www.google.com", "Google main", "google search");
        //dc.deletePost("http://www.onekin.org");
        //System.out.println(dc.getTagsv1());
        //dc.program();
    }

    public String getTagsv1() throws Exception {
        URL u=new URL("https://api.del.icio.us/v1/posts/get?url=http://en.wikipedia.org/wiki/Madrid");
        URLConnection uc =  u.openConnection();
        InputStream res1 = uc.getInputStream();
        return createString(res1);
    }
    public String getTagsv2() throws Exception {
        URL u=new URL("https://api.del.icio.us/v1/posts/get?url=http://en.wikipedia.org/wiki/Madrid");
        URLConnection uc =  u.openConnection();
        InputStream res1 = uc.getInputStream();

        createDocumentBuilder();
        createXpathFactory();

        Document res1XML= docBuilder.parse(res1);
        XPathExpression expr1 = xpathFactory.newXPath().compile("//posts/post/@tag");
        String tags=((NodeList)expr1.evaluate(res1XML,XPathConstants.NODESET)).item(0).getNodeValue();
        StringTokenizer resourceTags = new StringTokenizer(tags, " ");


        while(resourceTags.hasMoreTokens()){ //foreach del.icio.us tag
            String tag=resourceTags.nextToken();
            System.out.println(tag);

        }
        return null;
    }
    public String getTagsv3() throws Exception {
        Map<String, String> params=new Hashtable<String, String>();

        params.put("url","http://en.wikipedia.org/wiki/Madrid");
        RestCall rc=new RestCall();
        try {
            return rc.callRestfulWebService("https://api.del.icio.us/v1/posts/get?", params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public void createPost(String url, String description, String tags){
        Map<String, String> params=new Hashtable<String, String>();

        params.put("url", url);
        params.put("description",description);
        params.put("tags",tags);
        RestCall rc=new RestCall();
        try {
            rc.callRestfulWebService("https://api.del.icio.us/v1/posts/add?", params);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void deletePost(String url){

    }
    public String createString(InputStream inputStream){
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
    public void program(){
        try {
            String url;
            URL u=new URL("Primera llamada");
            URLConnection uc =  u.openConnection();
            InputStream res1 = uc.getInputStream();

            createDocumentBuilder();
            createXpathFactory();

            Document res1XML= docBuilder.parse(res1);
            XPathExpression expr1 = xpathFactory.newXPath().compile("//posts/post/@href");
            NodeList urls=((NodeList)expr1.evaluate(res1XML,XPathConstants.NODESET));
            for(int i=0;i<urls.getLength();i++)	{
                url=urls.item(i).getNodeValue();
                u=new URL("Segunda llamada");
                uc =  u.openConnection();
                res1 = uc.getInputStream();
                res1XML= docBuilder.parse(res1);
                expr1 = xpathFactory.newXPath().compile("//posts/post/@tag");
                String tags=((NodeList)expr1.evaluate(res1XML,XPathConstants.NODESET)).item(0).getNodeValue();
                StringTokenizer resourceTags = new StringTokenizer(tags, " ");

                int nTags=0;
                while(resourceTags.hasMoreTokens()){ //foreach del.icio.us tag
                    resourceTags.nextToken();
                    nTags++;
                }
                if(nTags==1) {
                    Map<String, String> params=new Hashtable<String, String>();

                    params.put("url",url);
                    RestCall rc=new RestCall();
                    rc.callRestfulWebService("Tercera llamada",params);
                }
            }//end for
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
