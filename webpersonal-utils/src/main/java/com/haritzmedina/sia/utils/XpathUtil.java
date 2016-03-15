package com.haritzmedina.sia.utils;

import org.w3c.dom.Document;

import javax.xml.namespace.QName;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

/**
 * Xpath utils functions
 * Created by Haritz Medina on 03/03/2016.
 */
public class XpathUtil {

    public static Object extractXpath(Document document, String xpath, QName resultContent){
        XPathFactory xpathFactory = XPathFactory.newInstance();
        try {
            XPathExpression expr1 = xpathFactory.newXPath().compile(xpath);
            return expr1.evaluate(document, resultContent);
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
        return null;
    }
}
