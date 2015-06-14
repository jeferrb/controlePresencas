package com.unicamp.br.mo409.controller;

import com.unicamp.br.mo409.model.Usuario;
import com.unicamp.br.mo409.view.LoginActivity;

import android.app.Activity;
import android.util.Log;
import android.view.View;

public class ControllerLogin {
	public static String TAG = "ControllerLogin";
	
	public static void tentarLogar(Activity activity, View view, String login, String senha){
		if(!(login != null && login.length()>0 && senha != null && senha.length()>0)) {
			((LoginActivity) activity).showPopUpMessage("Login e Senha são campos obligatórios");
			return;
		}
		
		Usuario user = new Usuario();
		int resultado = user.tentarLogar(login, senha);

		switch (resultado) {
		case 0:
			((LoginActivity) activity).showToastMessage("Ocorreu um erro desconhecido, por favor tente novamente");
			break;
		case 1:
			((LoginActivity) activity).showPopUpMessage("Login ou Senha inválidos");
			break;
		case 3:
			((LoginActivity) activity).callNewIntent(view, 3);//student
			break;
		case 4:
			((LoginActivity) activity).callNewIntent(view, 4); //professor
			break;
		default:
			Log.e(TAG, "ERROR: Invalid value \nMSG: Invalid value from XmlManager.manageXmlLogin(retorno);");
			((LoginActivity) activity).showPopUpMessage("Ocerreu um erro inesperado, por favor tente novamente");
			break;
		}
	}

}
