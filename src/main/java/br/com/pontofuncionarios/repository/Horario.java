package br.com.pontofuncionarios.repository;

import java.time.LocalTime;

public interface Horario {
	 	public LocalTime getEntrada();
	    public void setEntrada(LocalTime entrada);
	    public LocalTime getSaida();
	    public void setSaida(LocalTime saida);
}
