package com.unicamp.br.mo409.controller;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

public class XmlManager {
	static String TAG = "XmlManager";
	//static String test = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n<LoginUsuario>\n<sucess>true</sucess>\n<tipo>Aluno</tipo>\n</LoginUsuario>";

	public static int[] manageXmlLogin(String rawXml){
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
        int[] retorno = new int[2];
        retorno [0] = 0;
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
                			 retorno [0] = 1; 
                			 return retorno;
                		 }
                	 }
                	 if (name.equals("tipo")){
                		 if(!text.equals("Aluno")){
                			 retorno [0] = 3; 
                		 }
                		 if(!text.equals("Professor")){
                			 retorno [0] = 4; 
                		 }
                	 }
                	 if (name.equals("chave")){
                		retorno [1] = Integer.parseInt(text);
                	 }
                 break;
              }
              event = myParser.next();
           }
        }
        catch (Exception e) {
           e.printStackTrace();
        }
		return retorno;
    }
	
	public static boolean manageXmlLogout(String rawXml){
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
		*  <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
		*	<LoginUsuario>
		*		<sucess>true</sucess>
		*	</LoginUsuario>
		*/
		XmlPullParserFactory xmlFactoryObject;
        int event;
        String text=null;
        int[] retorno = new int[2];
        retorno [0] = 0;
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
                			 return false;
                		 }else{
                			 return true;
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
		return false;
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
		String[][] retorno = new String[2][1];
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
           //TODO
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
        //TODO
		return retorno;
    }
}
