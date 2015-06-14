package com.unicamp.br.mo409.model;

public class Aluno extends Usuario {
	
	public Aluno(int token){
		super.setTokenLogado(token);
		///super.type = "Aluno";
	}
	
	public boolean enviarTick(String Disciplina){
		return true;
	}
}
