package br.com.pontofuncionarios.domain;

import java.io.Serializable;
import java.time.LocalTime;

public class Horario implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	Integer id;
	LocalTime entrada;
	LocalTime saida;
	
	public Horario() {
		
	}
	public Horario(Integer id, LocalTime entrada, LocalTime saida) {
		this.id = id;
		this.entrada = entrada;
		this.saida = saida;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public LocalTime getEntrada() {
		return entrada;
	}
	public void setEntrada(LocalTime entrada) {
		this.entrada = entrada;
	}
	public LocalTime getSaida() {
		return saida;
	}
	public void setSaida(LocalTime saida) {
		this.saida = saida;
	}
	
	
	

}
