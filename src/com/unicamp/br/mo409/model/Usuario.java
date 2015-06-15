package com.unicamp.br.mo409.model;

import com.unicamp.br.mo409.controller.MainActivity;
import com.unicamp.br.mo409.controller.MainActivity.AlterarSenhaFragment;


public class Usuario {
	public boolean isInAula = false;
	public String type;
	public String userName;
	public int token = 0;
	
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
