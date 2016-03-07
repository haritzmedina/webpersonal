package com.haritzmedina.sia.yql;

import com.haritzmedina.sia.utils.RestCall;

import java.net.URLEncoder;
import java.util.Hashtable;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.xpath.XPathFactory;


public class YQLCaller {

    DocumentBuilder docBuilder;
    XPathFactory xpathFactory;
    public static void main(String[] args) throws Exception {

        YQLCaller fc=new YQLCaller();
        fc.getSearchWeb();
    }
    public void getSearchWeb() throws Exception {
        Map<String, String> params=new Hashtable<String, String>();

        //params.put("q","select * from search.web where query=\"donostia\"");
        //params.put("q","select content from html where url=\"http://elpais.com\" and xpath=\"//h2/a\"");
        params.put("q","select content from html where url=\"http://elpais.com\" and xpath=\"//h2/a\"");
        //params.put("q", URLEncoder.encode(entry.getKey(), "UTF-8");)
        RestCall rc=new RestCall();
        String response="";
        try {
            response = rc.callRestfulWebService("https://query.yahooapis.com/v1/public/yql", params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(response);

    }

}