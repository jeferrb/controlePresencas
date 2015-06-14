package com.unicamp.br.mo409.controller;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

public class XmlManager {
	static String TAG = "XmlManager";
	//static String test = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n<LoginUsuario>\n<sucess>true</sucess>\n<tipo>Aluno</tipo>\n</LoginUsuario>";

	public static byte manageXmlLogin(String rawXml){
		//rawXml = test;
		/*
		 * return:
		 * 	0 in case of fail
		 * 	1 in case of Login or Password incorrect
		 * 	2 reserved
		 * 	3 in case of student
		 * 	4 in case of professor
		*/
		
		/*	input example:
		 * <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
		 *	<LoginUsuario>
		 * 		<sucess>true</sucess>
		 * 		<tipo>Aluno</tipo>
		 * 	</LoginUsuario> 
		*/
		XmlPullParserFactory xmlFactoryObject;
        int event;
        String text=null;
        try {
    		InputStream stream = new ByteArrayInputStream(rawXml.getBytes("UTF-8"));
            xmlFactoryObject = XmlPullParserFactory.newInstance();
            XmlPullParser myParser = xmlFactoryObject.newPullParser();
            
            myParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            myParser.setInput(stream, null);
           event = myParser.getEventType();
           while (event != XmlPullParser.END_DOCUMENT) {
              String name=myParser.getName();
              switch (event){
                 case XmlPullParser.START_TAG:
                 break;
                 case XmlPullParser.TEXT:
                 text = myParser.getText();
                 break;
                 case XmlPullParser.END_TAG:
                	 if (name.equals("sucess")){
                		 if(!text.equals("true")){
                			 return 1; 
                		 }
                	 }
                	 if (name.equals("tipo")){
                		 if(!text.equals("Aluno")){
                			 return 3; 
                		 }
                		 if(!text.equals("Professor")){
                			 return 4; 
                		 }
                	 }
                 break;
              }
              event = myParser.next();
           }
        }
        catch (Exception e) {
           e.printStackTrace();
        }
		return 0;
    }
	
	public static String[][] manageXmlTurmas(String rawXml){
		/*
		 * return a array of classes
		 * with each [i] corresponding a each discipline
		 * an j: 0 = idTurma; 1 = discipline name; 2 = opened or not
		 * 
		*/
		
		/* input example:
		*	<turmaLogins>
		*		<LoginTurma>
		*			<chamadaAberta>false</chamadaAberta>
		*			<idTurma>1</idTurma>
		*			<nomeDisciplina>Engenharia de software</nomeDisciplina>
		*		</LoginTurma>
		*	</turmaLogins>
		*/
		XmlPullParserFactory xmlFactoryObject;
        int event;
        String text=null;
        try {
    		InputStream stream = new ByteArrayInputStream(rawXml.getBytes("UTF-8"));
            xmlFactoryObject = XmlPullParserFactory.newInstance();
            XmlPullParser myParser = xmlFactoryObject.newPullParser();
            
            myParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            myParser.setInput(stream, null);
           event = myParser.getEventType();
           while (event != XmlPullParser.END_DOCUMENT) {
              String name=myParser.getName();
              switch (event){
                 case XmlPullParser.START_TAG:
                 break;
                 case XmlPullParser.TEXT:
                 text = myParser.getText();
                 break;
                 case XmlPullParser.END_TAG:
                	 if (name.equals("sucess")){
                		 if(!text.equals("true")){
                			 return null; 
                		 }
                	 }
                	 if (name.equals("tipo")){
                		 if(!text.equals("Aluno")){
                			 return null; 
                		 }
                		 if(!text.equals("Professor")){
                			 return null; 
                		 }
                	 }
                 break;
              }
              event = myParser.next();
           }
        }
        catch (Exception e) {
           e.printStackTrace();
        }
		return null;
    }
}
