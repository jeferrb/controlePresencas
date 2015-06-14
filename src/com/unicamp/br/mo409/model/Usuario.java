package com.unicamp.br.mo409.model;

import com.unicamp.br.mo409.controller.MainActivity;
import com.unicamp.br.mo409.controller.MainActivity.AlterarSenhaFragment;


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
		MainActivity.getDisciplinas(token);
	}

	public void checkInOut() {
		if (isInAula) {
			//check-out
			
		}else{
			//check-in
			
		}
	}

	public void alterarSenha(int position, MainActivity mainActivity) {
		// TODO Auto-generated method stub
		mainActivity.callNewFragment(position, new AlterarSenhaFragment());
		
	}

}
