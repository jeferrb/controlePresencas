package com.unicamp.br.mo409.model;

import java.util.concurrent.ExecutionException;

import com.unicamp.br.mo409.controller.RestClient;
import com.unicamp.br.mo409.controller.XmlManager;

import android.util.Log;

public class Usuario {
	static private String TAG = "Model.Usuario";
	private long tokenLogado = 0;

	public static byte TentarLogar(String login, String password) {
		//return success, fail or the type of the user.
		RestClient obj = new RestClient();
		String pathLogin = "login/usuario/" + login + "/senha/" + password;
		String[] request = { "get", pathLogin };
		String retorno = null;
		try {
			retorno = obj.execute(request).get();
		} catch (InterruptedException e) {
			Log.e(TAG, "ERROR: " + e.toString() + "\nMSG: " + e.getMessage());
		} catch (ExecutionException e) {
			Log.e(TAG, "ERROR: " + e.toString() + "\nMSG: " + e.getMessage());
		}

		return XmlManager.manageXmlLogin(retorno);
	}

	public long getTokenLogado() {
		return tokenLogado;
	}

	public void setTokenLogado(long tokenLogado) {
		this.tokenLogado = tokenLogado;
	}

}
