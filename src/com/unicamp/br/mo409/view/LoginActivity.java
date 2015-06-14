package com.unicamp.br.mo409.view;

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
import com.unicamp.br.mo409.model.Usuario;

public class LoginActivity extends Activity{
	private static String TAG = "LoginActivity";
	
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
		String loginS =login.getText().toString();
		EditText senha = (EditText)findViewById(R.id.textSenha);
		String senhaS = senha.getText().toString();
		
		if(!(loginS != null && loginS.length()>0 && senhaS != null && senhaS.length()>0)) {
			showPopUpMessage("Login e Senha são campos obligatórios");
			return;
		}

    	byte resultado = Usuario.TentarLogar(loginS, senhaS);

    	switch (resultado) {
		case 0:
			showToastMessage("Ocorreu um erro desconhecido, por favor tente novamente");
			break;
		case 1:
			showPopUpMessage("Login ou Senha inválidos");
			break;
		case 3:
			Intent intentAluno = new Intent(view.getContext(), MainActivity.class);
			intentAluno.putExtra("type", 3); //student
			startActivity(intentAluno);
			break;
		case 4:
			Intent intentProfessor = new Intent(view.getContext(), MainActivity.class);
			intentProfessor.putExtra("type", 4); //professor
			startActivity(intentProfessor);
			break;
		default:
			Log.e(TAG, "ERROR: Invalid value \nMSG: Invalid value from XmlManager.manageXmlLogin(retorno);");
			showPopUpMessage("Ocerreu um erro inesperado, por favor tente novamente");
			break;
		}
		
	}
	public void recuperaSenha(View view){
		
	}
	
	private void showPopUpMessage(String message) {
		AlertDialog alertDialog = new AlertDialog.Builder(this).create();
		alertDialog.setMessage(message);
		alertDialog.show();
		
	}
	public void showProgressDialog(){
		//TODO
	}
	
	private void showToastMessage(String message){
    	Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

}
