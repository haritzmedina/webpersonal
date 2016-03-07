package com.haritzmedina.sia.utils;

import org.w3c.dom.Document;

import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

/**
 * Created by Haritz Medina on 03/03/2016.
 */
public class XpathUtil {

    public static Object extractXpath(Document document, String xpath){
        XPathFactory xpathFactory = XPathFactory.newInstance();
        try {
            XPathExpression expr1 = xpathFactory.newXPath().compile(xpath);
            return expr1.evaluate(document, XPathConstants.NODE);
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
        return null;
    }
}
