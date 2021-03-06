package com.unicamp.br.mo409.controller;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import android.util.Log;

public class XmlManager {
	static String TAG = "XmlManager";
	//static String test = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n<LoginUsuario>\n<sucess>true</sucess>\n<tipo>Aluno</tipo>\n</LoginUsuario>";

	public static String[] manageXmlLogin(String rawXml){
		//rawXml = test;
		/*
		 * return[0]: status
		 * return[1]: 
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
        String[] retorno = new String[2];
        retorno [0] = "Falha descolhecida";
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
                		 if(text.equals("false")){
                			 retorno [0] = "Usuário já conectado";
                			 return retorno;
                		 }
                	 }
                	 if (name.equals("tipo")){
                		 retorno [0] = text; 
                	 }
                	 if (name.equals("chave")){
                		 Log.e(TAG, "Chave: "+text );
                		retorno [1] = text;
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
	
	public static String manageXmlLogout(String rawXml){
		//rawXml = test;
		/*
		 * return:
		 * 	0 in case of fail
		 * 	1 in case of Login or Password incorrect
		 * 	2 reserved
		 * 	3 in case of student
		 * 	4 in case of professor
		*/
		
		/*	input examples:
		*  <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
		*	<LoginUsuario>
		*		<sucess>true</sucess>
		*	</LoginUsuario>
		*
		*<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
		*	<LoginUsuario>
		*		<sucess>false</sucess>
		*		<tipo>Chave errada</tipo>
		*	</LoginUsuario>
		*
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
                		 if(text.equals("true")){
                			 return "Sucesso";
                		 }
                	 }if (name.equals("tipo")){
                			 return text;
                	 }
                 break;
              }
              event = myParser.next();
           }
        }
        catch (Exception e) {
           e.printStackTrace();
        }
		return "Falha de rede!";
    }

	
	public static ArrayList<String[]> manageXmlTurmas(String rawXml){
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
		
		ArrayList<String[]> retorno = new ArrayList<String[]>();
		
		String[] current = new String[3];
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
                	 if (name.equals("chamadaAberta")){
                		 current[2] = text;
                	 }
                	 if (name.equals("idTurma")){
                		 current[0] = text;
                	 }if (name.equals("nomeDisciplina")){
                		 current[1] = text;
                		 retorno.add(current);
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
	
	
	public static String manageXmlCheckIn(String rawXml){
		//TODO
		
		/*
		 * input example: 
		 * 
		 * <InicializaAula>
		 * 		<isInicializada>true</isInicializada>
		 * </InicializaAula>
		 * 
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
                	 if (name.equals("isInicializada")){
                		 if(text.equals("true")){
                			 return "true";
                		 }
                		 return text;
                	 }
                 break;
              }
              event = myParser.next();
           }
        }
        catch (Exception e) {
           e.printStackTrace();
        }
		return "Falha de rede!";
    }
	
	
	
	public static String manageXmlCheckOut(String rawXml){
		//TODO

		/*	input examples:
		* 
		* 
		* 
		* 
		* 
		* 	<InicializaAula>
		*		<causa>Chamada ja aberta</causa>
		*		<isInicializada>false</isInicializada>
		*	</InicializaAula>
		*
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
                		 if(text.equals("true")){
                			 return "true";
                		 }
                	 }if (name.equals("causa")){
                			 return text;
                	 }
                 break;
              }
              event = myParser.next();
           }
        }
        catch (Exception e) {
           e.printStackTrace();
        }
		return "Falha de rede!";
    }
	
	
}
