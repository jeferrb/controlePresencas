package com.unicamp.br.mo409.controller;

import java.util.concurrent.ExecutionException;

import android.util.Log;

public class ControllerMain {
	private static String TAG = "ControllerMain";

	public static void getDisciplinas(int token) {
		RestClient obj = new RestClient();
		//TODO
		String pathLogin = String.valueOf(token);
		String[] request = { "get", pathLogin };
		String retorno = null;
		try {
			retorno = obj.execute(request).get();
		} catch (InterruptedException e) {
			Log.e(TAG, "ERROR: " + e.toString() + "\nMSG: " + e.getMessage());
		} catch (ExecutionException e) {
			Log.e(TAG, "ERROR: " + e.toString() + "\nMSG: " + e.getMessage());
		}
		String[][] ret = XmlManager.manageXmlTurmas(retorno);
	}
	
}
