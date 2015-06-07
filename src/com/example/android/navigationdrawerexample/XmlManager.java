package com.example.android.navigationdrawerexample;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.xml.sax.InputSource;

import android.util.Log;

public class XmlManager {
	static String TAG = "XmlManager";
	
	public static byte manageXmlLogin(String rawXml){
		/*
		 * return:
		 * 	0 in case of fail
		 * 	1 in case of professor
		 * 	2 in case of studant
		*/
		
		/*	imput exemple:
		 * <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
		 *	<LoginUsuario>
		 * 		<sucess>true</sucess>
		 * 		<tipo>Aluno</tipo>
		 * 	</LoginUsuario> 
		*/
		
		try {
			Document document = (Document) loadXMLFromString(rawXml);
			org.jdom2.Element rootNode = document.getRootElement();
			List<org.jdom2.Element> list = rootNode.getChildren("staff");
			for (int i = 0; i < list.size(); i++) {
				org.jdom2.Element node = (org.jdom2.Element) list.get(i);
				if (node.getChildText("sucess").equals("true")){
					if (node.getChildText("tipo").equals("Aluno")){
						return 2;
					}
					if (node.getChildText("tipo").equals("Professor")){
						return 1;
					}
				}
			}
		} catch (IOException io) {
			Log.e(TAG, "ERROR: " + io.toString() + "\nMSG: " + io.getMessage());
		} catch (JDOMException jdomex) {
			Log.e(TAG, "ERROR: " + jdomex.toString() + "\nMSG: " + jdomex.getMessage());
		} catch (Exception e) {
			Log.e(TAG, "ERROR: " + e.toString() + "\nMSG: " + e.getMessage());
		}
		return 0;
    }
	
	public static org.w3c.dom.Document loadXMLFromString(String xml) throws Exception
	{
	    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder builder = factory.newDocumentBuilder();
	    InputSource is = new InputSource(new StringReader(xml));
	    return builder.parse(is);
	}
	
	
}
