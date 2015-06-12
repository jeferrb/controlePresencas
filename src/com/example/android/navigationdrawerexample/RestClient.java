package com.example.android.navigationdrawerexample;
/*Based on http://stackoverflow.com/questions/15813910/android-resteasy-mobile-sample-client */
/*I'll try to insert a loading bar like that: http://stackoverflow.com/questions/15585749/progressdialog-spinning-circle*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.os.AsyncTask;
import android.util.Log;

public class RestClient extends AsyncTask<String, String, String> {
	private static String TAG = "RestClient";
    private  String messageReceived = null;
    //private ProgressDialog MyDialog;
    //private ProgressDialog dialog;
    //private LoginActivity activity;

    private static String DIRECCION_GET  = "http://179.184.165.195/projeto/api/";
    private static String DIRECCION_POST = "http://179.184.165.195/projeto/api/";

    public RestClient(LoginActivity activity){
    	//this.activity = activity;
    	//dialog = new ProgressDialog(activity);
    	//MyDialog = ProgressDialog.show(activity, " " , " Loading. Please wait ... ", true);
    }
    @Override
    protected  void onPreExecute(){
		//MyDialog.show();
		//this.dialog.setMessage("Please wait");
        //this.dialog.show();
    	//activity.showProgressDialog();
    }
    @Override
    protected String doInBackground(String... params) {
        HttpClient httpclient = new DefaultHttpClient();
        try {
            if(params[0].equals("get")){
            	Log.d(TAG, "GET");
            	HttpGet request = new HttpGet(DIRECCION_GET+params[1]);
                HttpResponse response = httpclient.execute(request);
                HttpEntity entity = response.getEntity();
                InputStream instream = entity.getContent();
                messageReceived = read(instream);
                Log.d(TAG, messageReceived);
                return(messageReceived);
            } else if(params[0].equals("post")){
            	Log.d(TAG, "POST");
            	HttpPost post = new HttpPost(DIRECCION_POST);
                post.setHeader("Content-Type", "application/json");
                StringEntity se = new StringEntity(params[1]);
                post.setEntity(se);
                HttpResponse httpResponse = new DefaultHttpClient().execute(post);
                String retSrc = EntityUtils.toString(httpResponse.getEntity());
                Log.d(TAG, retSrc);
			}
        } catch (ClientProtocolException e) {
            Log.e(TAG, "ERROR: " + e.toString() + "\nMSG: " + e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, "ERROR: " + e.toString() + "\nMSG: " + e.getMessage());
        } catch (NullPointerException e) {
        	Log.e(TAG, "ERROR: " + e.toString() + "\nMSG: " + e.getMessage());
		}catch (Exception e){
            Log.e(TAG, "ERROR: " + e.toString() + "\nMSG: " + e.getMessage());
        }finally {
            httpclient.getConnectionManager().shutdown();
        }
        return "true";
    }
    @Override
    protected void onPostExecute(String result) {
    	//MyDialog.dismiss();
        //dialog.dismiss();
        
    }

    private static String read(InputStream instream) {
        StringBuilder sb = null;
        try {
            sb = new StringBuilder();
            BufferedReader r = new BufferedReader(new InputStreamReader(
                    instream));
            for (String line = r.readLine(); line != null; line = r.readLine()) {
                sb.append(line);
            }
            instream.close();
            r.close();
        } catch (IOException e) {
            Log.e(TAG, "Se ha producido un error: " + e.toString() + "\nMensaje: " + e.getMessage());
        }
        return sb.toString();
    }
}