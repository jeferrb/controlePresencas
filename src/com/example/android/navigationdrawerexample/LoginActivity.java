package com.example.android.navigationdrawerexample;

import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class LoginActivity extends Activity{
	private static String TAG = "LoginActivity";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_login,
					container, false);
			return rootView;
		}
	}
	
	public void login(View view){
		Boolean debug = true;
		EditText login = (EditText)findViewById(R.id.textLogin);
		String loginS =login.getText().toString();
		EditText senha = (EditText)findViewById(R.id.textSenha);
		String senhaS = senha.getText().toString();
	    if(debug||(loginS != null && loginS.length()>0 && senhaS != null && senhaS.length()>0)) {
	    	RestClient obj = new RestClient();
	    	String pathLogin = "login/usuario/"+loginS+"/senha/"+senhaS;
	    	pathLogin = "login/usuario/Joao/senha/12345";
	    	String[] request = {"get", pathLogin};
	    	try {
				String retorno = obj.execute(request).get();
			} catch (InterruptedException e) {
				Log.e(TAG, "ERROR: " + e.toString() + "\nMSG: " + e.getMessage());
				showPopUpMessage("ERROR: " + e.toString() + "\nMSG: " + e.getMessage()+"\nPor favor tenete novamente mais tarde");
			} catch (ExecutionException e) {
				Log.e(TAG, "ERROR: " + e.toString() + "\nMSG: " + e.getMessage());
				showPopUpMessage("ERROR: " + e.toString() + "\nMSG: " + e.getMessage()+"\nPor favor tenete novamente mais tarde");
			}
	    	
			Intent intent = new Intent(view.getContext(), MainActivity.class);
			intent.putExtra("professor", false);
			startActivity(intent);
	    }else {
	    	showPopUpMessage("Login e Senha são campos obligatórios");
	    }
		
	}
	public void recuperaSenha(View view){
		showPopUpMessage("Por favor verifique a conexão e tente novamente");
	}
	private void showPopUpMessage(String message) {
		AlertDialog alertDialog = new AlertDialog.Builder(this).create();
		alertDialog.setMessage(message);
		//alertDialog.setIcon(R.drawable.icon);
		alertDialog.show();
	}
}
