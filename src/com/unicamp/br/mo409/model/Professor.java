package com.unicamp.br.mo409.model;

public class Professor extends Usuario {
	
	public Professor(int token){
		super.setTokenLogado(token);
	}
	public boolean abrirAula(String disciplina){
		return true;
	}

}
