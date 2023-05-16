package br.com.pontofuncionarios.domain;

import java.io.Serializable;
import java.time.LocalTime;

public class Marcacao extends Horario implements Serializable {
    private static final long serialVersionUID = 1L;

    
    public Marcacao() {
    }

    public Marcacao(Integer id,LocalTime entrada, LocalTime saida) {
    	super(id,entrada,saida);
    }
    
    public void adicionarMarcacao(LocalTime entrada, LocalTime saida) {
        super.setEntrada(entrada);
        super.setSaida(saida);
    }
}