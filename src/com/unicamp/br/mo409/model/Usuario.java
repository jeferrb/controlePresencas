package com.unicamp.br.mo409.model;

import com.unicamp.br.mo409.controller.MainActivity;
import com.unicamp.br.mo409.controller.MainActivity.AlterarSenhaFragment;


public class Usuario {
	public int idTurma = 0;
	public String type;
	public String userName;
	public int token = 0;
	
	public boolean isInAula() {
		return (!(idTurma==0));
	}

	public void setInAula(int disciplina) {
		this.idTurma = disciplina;
	}


	public long getTokenLogado() {
		return token;
	}

	public void setTokenLogado(int token) {
		this.token = token;
	}


	public void alterarSenha(int position, MainActivity mainActivity) {
		// TODO Auto-generated method stub
		mainActivity.callNewFragment(position, new AlterarSenhaFragment());
		
	}

}
