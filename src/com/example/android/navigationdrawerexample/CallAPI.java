package com.example.android.navigationdrawerexample;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.os.AsyncTask;

class CallAPI extends AsyncTask<String, String, String> {
    @Override
    protected String doInBackground(String... params) {
      String urlString=params[0]; // URL to call
      String resultToDisplay = "";
      InputStream in = null;
      emailVerificationResult result = null;
        
      // HTTP Get
      try {
        URL url = new URL(urlString);
    	HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
    	in = new BufferedInputStream(urlConnection.getInputStream());
      } catch (Exception e ) {
        System.out.println(e.getMessage());
    	return e.getMessage();
      }
    	   
       // Parse XML
       XmlPullParserFactory pullParserFactory;
       try {
    	    pullParserFactory = XmlPullParserFactory.newInstance();
    	    XmlPullParser parser = pullParserFactory.newPullParser();
    	    parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false); 
    	    parser.setInput(in, null);
    	    result = parseXML(parser);
       } catch (XmlPullParserException e) {
    	   e.printStackTrace();
       } catch (IOException e) {
    	   e.printStackTrace();
       }

       // Simple logic to determine if the email is dangerous, invalid, or valid
       if (result != null ) {
         if( result.hygieneResult.equals("Spam Trap")) {
    	   resultToDisplay = "Dangerous email, please correct";
    	 }
    	 else if( Integer.parseInt(result.statusNbr) >= 300) {
    	   resultToDisplay = "Invalid email, please re-enter";
    	 }
    	 else {
    	   resultToDisplay = "Thank you for your submission";
    	 }
    		
       }
       else {
         resultToDisplay = "Exception Occured";
       }
     
       return resultToDisplay;

    }
    
    
    private emailVerificationResult parseXML( XmlPullParser parser ) throws XmlPullParserException, IOException {
      int eventType = parser.getEventType();
      emailVerificationResult result = new emailVerificationResult(); 
        
      while( eventType!= XmlPullParser.END_DOCUMENT) {
      String name = null;
      switch(eventType)
      {
        case XmlPullParser.START_TAG:
      	  name = parser.getName();
      	  if( name.equals("Error")) {
      	    System.out.println("Web API Error!");
      	  }
      	  else if ( name.equals("StatusNbr")) {
      	    result.statusNbr = parser.nextText();
      	  }
      	  else if (name.equals("HygieneResult")) {
      	    result.hygieneResult = parser.nextText();
      	  }
      	  break;
      	case XmlPullParser.END_TAG:
      	  break;
      	} // end switch
      	
      	eventType = parser.next();	
      } // end while
        return result;		
     }
	private class emailVerificationResult {
		public String statusNbr;
		public String hygieneResult;	
	}

} // end CallAPI
