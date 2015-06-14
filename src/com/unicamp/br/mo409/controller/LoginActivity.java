package com.unicamp.br.mo409.controller;

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
import android.widget.Toast;

import com.example.android.navigationdrawerexample.R;

public class LoginActivity extends Activity{
	public final String TAG = "LoginActivity";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction().add(R.id.container, new PlaceholderFragment()).commit();
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
		EditText login = (EditText)findViewById(R.id.textLogin);
		EditText senha = (EditText)findViewById(R.id.textSenha);
		tentarLogar(view, login.getText().toString(), senha.getText().toString());
	}
	public void recuperaSenha(View view){
		
	}
	public void debugProf(View view){
		callNewIntent(view, 4, 0);//professor
		
	}

	public void debugAlun(View view){
		callNewIntent(view, 3, 0);//student
		
	}

	public void showPopUpMessage(String message) {
		AlertDialog alertDialog = new AlertDialog.Builder(this).create();
		alertDialog.setMessage(message);
		alertDialog.show();
	}
	
	public void showToastMessage(String message){
    	Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }
	
	public void callNewIntent(View view, int type, int token) {
		Intent intentAluno = new Intent(view.getContext(), MainActivity.class);
		intentAluno.putExtra("type", type);
		intentAluno.putExtra("token", token);
		startActivity(intentAluno);
		finish();
	}
	
	public void tentarLogar(View view, String login, String senha){
		if(!(login != null && login.length()>0 && senha != null && senha.length()>0)) {
			((LoginActivity) this).showPopUpMessage("Login e Senha são campos obligatórios");
			return;
		}
		
		//return success, fail or the type of the user.
		RestClient obj = new RestClient();
		String pathLogin = "login/usuario/" + login + "/senha/" + senha;
		String[] request = { "get", pathLogin };
		String retorno = null;
		try {
			retorno = obj.execute(request).get();
		} catch (InterruptedException e) {
			Log.e(TAG, "ERROR: " + e.toString() + "\nMSG: " + e.getMessage());
		} catch (ExecutionException e) {
			Log.e(TAG, "ERROR: " + e.toString() + "\nMSG: " + e.getMessage());
		}
		int[] ret = XmlManager.manageXmlLogin(retorno);
		
		int resultado = ret[0];

		switch (resultado) {
		case 0:
			this.showToastMessage("Ocorreu um erro desconhecido, por favor tente novamente");
			break;
		case 1:
			this.showPopUpMessage("Login ou Senha inválidos");
			break;
		case 3:
			this.callNewIntent(view, 3, ret[1]);//student
			break;
		case 4:
			this.callNewIntent(view, 4, ret[1]); //professor
			break;
		default:
			Log.e(TAG, "ERROR: Invalid value \nMSG: Invalid value from XmlManager.manageXmlLogin(retorno);");
			this.showPopUpMessage("Ocerreu um erro inesperado, por favor tente novamente");
			break;
		}
	}

}
