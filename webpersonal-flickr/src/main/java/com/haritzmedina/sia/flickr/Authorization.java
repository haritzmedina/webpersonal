package com.haritzmedina.sia.flickr;

/**
 * Created by Haritz Medina on 01/03/2016.
 */

import com.haritzmedina.sia.utils.RestCall;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.Hashtable;
import java.security.*;
import java.util.*;

public class Authorization {

    public static void main(String[] args) throws NoSuchAlgorithmException, MalformedURLException {
        String api_key="7a238fc816a0f792938c886d6a610228";

        String secret="21deddc3646c7913";

        Authorization auth=new Authorization();

        /*String frob=auth.getFrob(api_key,secret);
        System.out.println("frob: "+frob);
        String api_sig2 = auth.MD5(secret+"api_key"+api_key+"frob"+frob+"permsdelete");
        String call="https://flickr.com/services/auth/?api_key="+api_key;
        call=call+"&perms=delete&frob="+frob+"&api_sig="+api_sig2;

        System.out.println(call);
        try {
            System.in.read();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        String token=auth.getToken(frob,api_key,secret);
        System.out.println("token"+token);*/

        String token="72157662978336623-0311d1cf2c2762f9";

        //System.out.println("user foto: "+auth.getPhotoInfo(api_key,"2983051469"));

        //addTags(token,api_key,secret,"5158761696","apiTag");
        //System.out.println(auth.getInfo(token, api_key, secret, "32292284@N04"));
        System.out.println(auth.getPhotoInfo(token, api_key, secret, "24751537893"));
        //auth.addTags(token, api_key, secret, "24751537893", "Adios,Buenas");

        auth.removeTags(token, api_key, secret, "140677395-24751537893-102527");
        //System.out.println("user fotos: "+auth.userPhotos(token,api_key,secret,"32292284@N04"));
    }

    private String removeTags(String token, String api_key, String secret, String tag_id) {
        Map<String, String> params= new TreeMap<>();
        String api_sig;
        try {
            api_sig = MD5(secret+"api_key"+api_key+"auth_token"+token+"methodflickr.photos.removeTag"+"tag_id"+tag_id);
            params.put("api_key",api_key);
            params.put("method","flickr.photos.removeTag");
            params.put("auth_token",token);
            params.put("api_sig",api_sig);
            params.put("tag_id",tag_id);
            RestCall rc=new RestCall();
            String xmlResp= rc.callRestfulWebService("https://api.flickr.com/services/rest/", params);
            return xmlResp;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public  String getFrob(String api_key, String secret){
        //String secret="95c297fbb792e5a5";
        Map<String, String> params=new Hashtable<String, String>();
        params.put("api_key",api_key);
        String api_sig;
        try {
            api_sig = MD5(secret+"api_key"+api_key+"methodflickr.auth.getFrob");
            params.put("api_key",api_key);
            params.put("method","flickr.auth.getFrob");
            params.put("api_sig",api_sig);
            RestCall rc=new RestCall();
            String xmlResp= rc.callRestfulWebService("https://api.flickr.com/services/rest/", params);
            System.out.println("resp:"+xmlResp);
            return xmlResp.substring(62, 103);

        } catch (Exception  e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
    public  String getToken(String frob,String api_key,String secret){
        //String secret="95c297fbb792e5a5";
        //String api_key="08b3d85de2b5c232b65c5b61033053dd";

        Map<String, String> params=new Hashtable<String, String>();
        params.put("api_key",api_key);
        String api_sig;
        try {
            api_sig = MD5(secret+"api_key"+api_key+"frob"+frob+"methodflickr.auth.getToken");
            params.put("api_key",api_key);
            params.put("method","flickr.auth.getToken");
            params.put("frob",frob);
            params.put("api_sig",api_sig);
            RestCall rc=new RestCall();
            String xmlResp= rc.callRestfulWebService("https://api.flickr.com/services/rest/", params);
            //System.out.println("token:"+xmlResp);
            return xmlResp.substring(71,105);

        } catch (Exception  e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
    public  String getInfo(String token,String api_key, String secret,String user_id){

        Map<String, String> params=new Hashtable<String, String>();
        params.put("api_key",api_key);
        String api_sig;
        try {
            api_sig = MD5(secret+"api_key"+api_key+"auth_token"+token+"methodflickr.people.getInfo"+"user_id"+user_id);

            params.put("api_key",api_key);
            params.put("method","flickr.people.getInfo");
            params.put("auth_token",token);
            params.put("api_sig",api_sig);
            params.put("user_id",user_id);
            RestCall rc=new RestCall();
            String xmlResp= rc.callRestfulWebService("https://api.flickr.com/services/rest/", params);
            //System.out.println("token:"+xmlResp);
            return xmlResp;

        } catch (Exception  e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public  String getPhotoInfo(String token, String api_key, String secret, String photo_id){

        Map<String, String> params=new Hashtable<String, String>();
        String api_sig;
        try {
            api_sig = MD5(secret+"api_key"+api_key+"auth_token"+token+"methodflickr.photos.getInfo"+"photo_id"+photo_id);

            params.put("api_key",api_key);
            params.put("method","flickr.photos.getInfo");
            params.put("auth_token",token);
            params.put("api_sig",api_sig);
            params.put("photo_id",photo_id);
            RestCall rc=new RestCall();
            String xmlResp= rc.callRestfulWebService("https://api.flickr.com/services/rest/", params);
            return xmlResp;

        } catch (Exception  e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public  String addTags(String token,String api_key, String secret,String photo_id,String tags){
        Map<String, String> params=new Hashtable<String, String>();
        String api_sig;
        try {
            api_sig = MD5(secret+"api_key"+api_key+"auth_token"+token+"methodflickr.photos.addTags"+"photo_id"+photo_id+"tags"+tags);
            params.put("api_key",api_key);
            params.put("method","flickr.photos.addTags");
            params.put("auth_token",token);
            params.put("api_sig",api_sig);
            params.put("photo_id",photo_id);
            params.put("tags",tags);
            RestCall rc=new RestCall();
            String xmlResp= rc.callRestfulWebService("https://api.flickr.com/services/rest/", params);
            return xmlResp;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public  String userPhotos(String token,String api_key, String secret,String user_id){


        Map<String, String> params=new Hashtable<String, String>();
        String api_sig;
        try {
            api_sig = MD5(secret+"api_key"+api_key+"auth_token"+token+"methodflickr.people.getPhotos"+"user_id"+user_id);
            params.put("api_key",api_key);
            params.put("user_id",user_id);

            params.put("method","flickr.people.getPhotos");
            params.put("auth_token",token);
            params.put("api_sig",api_sig);
            RestCall rc=new RestCall();
            String xmlResp= rc.callRestfulWebService("https://api.flickr.com/services/rest/", params);
            //System.out.println("token:"+xmlResp);
            return xmlResp;

        } catch (Exception  e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
    public  String getPhotoInfo(String api_key, String photo_id){

        Map<String, String> params=new Hashtable<String, String>();

        try {
            params.put("api_key",api_key);
            params.put("method","flickr.photos.getInfo");
            params.put("photo_id",photo_id);
            RestCall rc=new RestCall();
            String xmlResp= rc.callRestfulWebService("https://api.flickr.com/services/rest/", params);
            //System.out.println("token:"+xmlResp);
            return xmlResp;

        } catch (Exception  e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
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

    public String MD5(String text)
            throws NoSuchAlgorithmException  {
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
}

