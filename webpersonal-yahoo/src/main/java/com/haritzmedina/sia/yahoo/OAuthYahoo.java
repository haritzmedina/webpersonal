package com.haritzmedina.sia.yahoo;

import java.io.*;
import java.net.*;
import java.util.*;
import java.security.*;

import javax.crypto.*;
import javax.crypto.spec.*;

import com.haritzmedina.sia.utils.RestCall;
import sun.misc.*;

public class OAuthYahoo {

    //private static String secret="f76a1b2c1547c254df07ac774ba50f9fde6b3a97";
    //private static String api_key="dj0yJmk9bE1rWGxkT3FndGFNJmQ9WVdrOWFHVnNNa2RqTm5NbWNHbzlNQS0tJnM9Y29uc3VtZXJzZWNyZXQmeD1iMA--";

    private static String secret="cfa68d671d812f44ab66815e7bdf452e9c903ced";
    private static String api_key="dj0yJmk9a2hFUUM1eURFUjE4JmQ9WVdrOWVHcHFSRzVGTm5VbWNHbzlNQS0tJnM9Y29uc3VtZXJzZWNyZXQmeD0xOQ--";

    public static void main(String[] args) throws Exception {

        String rtoken=getRequestToken();
        System.out.println("Request Token: "+rtoken);

        Map<String,String> params=RestCall.parseWebResult(rtoken);
        String auth_url=params.get("xoauth_request_auth_url");

        String auth_token=params.get("oauth_token");
        String auth_token_secret=params.get("oauth_token_secret");
        auth_url=auth_url+"&oauth_nonce="+Long.toString(System.nanoTime())+"&oauth_timestamp="+Long.toString(System.currentTimeMillis()/ 1000)+
                "&oauth_consumer_key="+api_key+"&oauth_signature_method=plaintext&oauth_signature="+secret+"&oauth_version=1.0&xoauth_lang_pref=en-us&oauth_callback=http://localhost/";

        System.out.println("Please, validate this url:");
        System.out.println(auth_url);

        System.out.println("Please, introduce a verifier code received from yahoo");
        String verifier=readString();

        String token=getToken(verifier,auth_token,auth_token_secret);

        System.out.println("The token is:");
        System.out.println("Token: "+token);

        Map<String,String> params2=RestCall.parseWebResult(token);


        //String session=params2.get("oauth_session_handle");
        auth_token=params2.get("oauth_token");
        auth_token_secret=params2.get("oauth_token_secret");
        String guid=params2.get("xoauth_yahoo_guid");
        guid=guid.substring(0,guid.length()-1);


        //Devuelve el guid del usuario
        String resp=makeCall("https://social.yahooapis.com/v1/me/guid",new Hashtable<String, String>(),auth_token,auth_token_secret);

        System.out.println("RESPONSE!!: "+resp);

        //Aqui teneis que sacar su guid. En mi caso WPMN5AA2UNJABR46YF7FJYNYW4
        //String guidUser="WPMN5AA2UNJABR46YF7FJYNYW4";
        //String guidUser="EOXBPTZO2NPYFZYK2F4BBZWGII";
        //System.out.println("WPMN5AA2UNJABR46YF7FJYNYW4");
        String guidUser= resp.substring(221, 247);
        System.out.println("user guid: "+guidUser);


        // A continuaci�n contruir la URL para obtener el perfil del usuario
        // En mi caso String url="http://social.yahooapis.com/v1/user/WPMN5AA2UNJABR46YF7FJYNYW4/profile";

        String url="https://social.yahooapis.com/v1/user/"+guidUser+"/profile";
        //String url="https://social.yahooapis.com/v1/user/EOXBPTZO2NPYFZYK2F4BBZWGII/profile";



        String resp2=makeCall(url,new Hashtable<String, String>(),auth_token,auth_token_secret);

        System.out.println("EL PERFIL ES!!: "+resp2);

        System.exit(0);

        System.exit(0);
    }

    private static String getRequestToken() throws Exception{
        String url="https://api.login.yahoo.com/oauth/v2/get_request_token";
        Map<String, String> extraparams=new Hashtable<String, String>();
        extraparams.put("oauth_callback","http://localhost/");
        return URLDecoder.decode(authCall(url,new Hashtable<String, String>(),extraparams,secret+"&"),"UTF-8");
    }

    public static String getToken(String verifier,String token,String token_secret) throws Exception{
        String url="https://api.login.yahoo.com/oauth/v2/get_token";
        Map<String, String> extraparams=new Hashtable<String, String>();
        extraparams.put("oauth_token",token);
        extraparams.put("oauth_verifier",verifier);
        return URLDecoder.decode(authCall(url,new Hashtable<String, String>(),extraparams,secret+"&"+token_secret),"UTF-8");
    }

    static String makeCall(String url,Map<String, String> callparams,String token,String token_secret) throws UnsupportedEncodingException, Exception {
        //System.out.println(url);
        Map<String, String> extraparams=new Hashtable<String, String>();
        extraparams.put("realm","yahooapis.com");
        //extraparams.put("realm","del.icio.us API");
        extraparams.put("oauth_token",token);
        return authCall(url,callparams,extraparams,secret+"&"+token_secret);
    }

    public static String refreshToken(String session,String token,String token_secret) throws Exception{
        String url="https://api.login.yahoo.com/oauth/v2/get_token";
        Map<String, String> extraparams=new Hashtable<String, String>();
        extraparams.put("oauth_token",token);
        extraparams.put("oauth_session_handle",session);
        return URLDecoder.decode(authCall(url,new Hashtable<String, String>(),extraparams,secret+"&"+token_secret),"UTF-8");
    }




    public static String readString()
    {  int ch;
        String r = "";
        boolean done = false;
        while (!done)
        {  try
        {  ch = System.in.read();
            if (ch < 0 || (char)ch == '\n'){
                done = true;
            }else{
                r = r + (char) ch;
            }
        }
        catch(java.io.IOException e)
        {  done = true;
        }
        }
        return r;
    }

    static String authCall(String url,Map<String, String> params,Map<String, String> extraparams,String key) throws UnsupportedEncodingException, Exception{
        String resp=null;
        Map<String, String> auth_params=new Hashtable<String, String>();
        Map<String, String> header=new Hashtable<String, String>();
        auth_params.put("oauth_nonce",Long.toString(System.nanoTime()));
        auth_params.put("oauth_timestamp",Long.toString(System.currentTimeMillis()/ 1000));
        auth_params.put("oauth_consumer_key",api_key);
        auth_params.put("oauth_signature_method","HMAC-SHA1");
        auth_params.put("oauth_version","1.0");
        for (Map.Entry<String, String> entry : extraparams.entrySet()) {
            auth_params.put(entry.getKey(), URLEncoder.encode(entry.getValue(),"UTF-8"));
        }

        for (Map.Entry<String, String> entry : params.entrySet()) {
            auth_params.put(entry.getKey(), URLEncoder.encode(entry.getValue(),"UTF-8"));
        }
        String base=getBaseString("GET",url,auth_params);
        for (Map.Entry<String, String> entry : params.entrySet()) {
            auth_params.remove(entry.getKey());
        }

        auth_params.put("oauth_signature",HMAC_SHA1(base,key));
        header.put("Authorization",getOAuth(auth_params));
        //System.out.println(base);
        //System.out.println(header.get("Authorization"));
        try {
            RestCall rc=new RestCall();
            resp= rc.callRestfulWebService(url, params,header);
        } catch (Exception  e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return resp;


    }


    private static String getOAuth(Map<String, String> auth_params) throws UnsupportedEncodingException {
        StringBuilder sb = new StringBuilder();
        sb.append("OAuth ");
        String realm=auth_params.get("realm");
        if(realm!=null){
            sb.append("realm=\""+auth_params.get("realm")+"\",");
        }
        Object[] keys=auth_params.keySet().toArray();
        Arrays.sort(keys);
        for (int i=0;i<keys.length;i++) {
            String key = keys[i].toString();
            if(!(key.equals("realm")||key.equals("oauth_signature"))){
                String value = auth_params.get(key);
                sb.append(key).append("=\"").append(value).append("\",");
            }
        }
        sb.append("oauth_signature=\""+URLEncoder.encode(auth_params.get("oauth_signature"),"UTF-8")+"\"");
        return sb.toString();
    }

    private static String getBaseString(String method, String call, Map<String, String> params) throws UnsupportedEncodingException, Exception {
        String res=method+"&"+URLEncoder.encode(call,"UTF-8");
        String param=buildBaseQuery(params);
        if(param.length()>0){
            res=res+"&"+param;
        }
        return res;
    }


    private static String buildBaseQuery(Map<String, String> params) throws UnsupportedEncodingException {
        String res=null;

        Object[] keys=params.keySet().toArray();
        Arrays.sort(keys);

        StringBuilder sb = new StringBuilder();
        for (int i=0;i<keys.length;i++) {
            String key = keys[i].toString();
            if(!key.equals("realm")){
                String value = params.get(key);
                sb.append(key).append("=").append(value).append("&");
            }
        }
        if(sb.length()>0){
            res=sb.toString().substring(0, sb.length() - 1);
        }else{
            res=sb.toString();
        }
        return URLEncoder.encode(res,"UTF-8");
    }

    public static String HMAC_SHA1(String data, String key)
            throws SignatureException, NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException
    {
        String result;
        //	 get an hmac_sha1 key from the raw key bytes
        SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(), "HmacSHA1");

        //	 get an hmac_sha1 Mac instance and initialize with the signing key
        Mac mac = Mac.getInstance("HmacSHA1");
        mac.init(signingKey);

        //	 compute the hmac on input data bytes
        byte[] rawHmac = mac.doFinal(data.getBytes());

        //	 base64-encode the hmac
        result = new BASE64Encoder().encode(rawHmac);
        return result;
    }




}



