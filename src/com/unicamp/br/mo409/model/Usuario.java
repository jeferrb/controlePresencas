package com.unicamp.br.mo409.model;

import com.unicamp.br.mo409.controller.ControllerMain;


public class Usuario {
	private boolean isInAula = false;
	public int type;
	private int token = 0;
	
	public boolean isInAula() {
		return isInAula;
	}

	public void setInAula(boolean isInAula) {
		this.isInAula = isInAula;
	}


	public long getTokenLogado() {
		return token;
	}

	public void setTokenLogado(int token) {
		this.token = token;
	}

	public void getDisciplinas() {
		ControllerMain.getDisciplinas(token);
	}

}
