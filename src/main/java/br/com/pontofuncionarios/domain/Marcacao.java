package br.com.pontofuncionarios.domain;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.Objects;

import br.com.pontofuncionarios.repository.Horario;

public class Marcacao implements Horario,Serializable {
    private static final long serialVersionUID = 1L;

    private LocalTime entrada;
    private LocalTime saida;

    // Construtor vazio
    public Marcacao() {
    }

    // Construtor com parâmetros
    public Marcacao(LocalTime entrada, LocalTime saida) {
        this.entrada = entrada;
        this.saida = saida;
    }

    // Getters e setters
    
    @Override
    public LocalTime getEntrada() {
        return entrada;
    }
    @Override
    public void setEntrada(LocalTime entrada) {
        this.entrada = entrada;
    }
    @Override
    public LocalTime getSaida() {
        return saida;
    }
    @Override
    public void setSaida(LocalTime saida) {
        this.saida = saida;
    }

	@Override
	public int hashCode() {
		return Objects.hash(entrada, saida);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Marcacao other = (Marcacao) obj;
		return Objects.equals(entrada, other.entrada) && Objects.equals(saida, other.saida);
	}
    
    
}
