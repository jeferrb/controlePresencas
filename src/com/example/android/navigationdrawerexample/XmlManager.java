package com.example.android.navigationdrawerexample;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.xml.sax.InputSource;

import android.util.Log;
import android.util.Xml;

public class XmlManager {
	static String TAG = "XmlManager";
	
	public static Xml manageXml(String rawXml){
		try {
			Document document = (Document) loadXMLFromString(rawXml);
			org.jdom2.Element rootNode = document.getRootElement();
			List<org.jdom2.Element> list = rootNode.getChildren("staff");
			for (int i = 0; i < list.size(); i++) {
				org.jdom2.Element node = (org.jdom2.Element) list.get(i);
				System.out.println("First Name : "
						+ node.getChildText("firstname"));
				System.out.println("Last Name : "
						+ node.getChildText("lastname"));
				System.out.println("Nick Name : "
						+ node.getChildText("nickname"));
				System.out.println("Salary : " + node.getChildText("salary"));
			}
		} catch (IOException io) {
			Log.e(TAG, "ERROR: " + io.toString() + "\nMSG: " + io.getMessage());
		} catch (JDOMException jdomex) {
			Log.e(TAG, "ERROR: " + jdomex.toString() + "\nMSG: " + jdomex.getMessage());
		} catch (Exception e) {
			Log.e(TAG, "ERROR: " + e.toString() + "\nMSG: " + e.getMessage());
			e.printStackTrace();
		}
		return null;
    }
	
	public static org.w3c.dom.Document loadXMLFromString(String xml) throws Exception
	{
	    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder builder = factory.newDocumentBuilder();
	    InputSource is = new InputSource(new StringReader(xml));
	    return builder.parse(is);
	}
	
}
