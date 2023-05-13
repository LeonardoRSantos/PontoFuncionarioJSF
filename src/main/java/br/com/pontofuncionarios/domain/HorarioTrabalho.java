package br.com.pontofuncionarios.domain;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.*;

import br.com.pontofuncionarios.repository.Horario;

public class HorarioTrabalho implements Horario,Serializable {
    private static final long serialVersionUID = 1L;

    private LocalTime entrada;
    private LocalTime saida;
    private List<Marcacao> marcacoes = new ArrayList<>();

    public HorarioTrabalho() {
    }

    public HorarioTrabalho(LocalTime entrada, LocalTime saida) {
        this.entrada = entrada;
        this.saida = saida;
    }

    public List<Marcacao> getMarcacoes() {
        return marcacoes;
    }

    public void setMarcacoes(List<Marcacao> marcacoes) {
        this.marcacoes = marcacoes;
    }

    public void adicionarMarcacao(LocalTime entrada, LocalTime saida) {
        this.entrada = entrada;
        this.saida = saida;
    }

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
		return Objects.hash(entrada, marcacoes, saida);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HorarioTrabalho other = (HorarioTrabalho) obj;
		return Objects.equals(entrada, other.entrada) && Objects.equals(marcacoes, other.marcacoes)
				&& Objects.equals(saida, other.saida);
	}
    
    
}


//public class HorarioTrabalho implements Serializable {
//    private static final long serialVersionUID = 1L;
//
//    private LocalTime entrada;
//    private LocalTime saida;
//    private List<Marcacao> marcacoes = new ArrayList<>();
//
//    // Construtor vazio
//    public HorarioTrabalho() {
//    }
//
//    // Construtor com parâmetros
//    public HorarioTrabalho(LocalTime entrada, LocalTime saida) {
//        this.entrada = entrada;
//        this.saida = saida;
//    }
//    
//    public void adicionarMarcacao(LocalTime horario) {
//        Marcacao marcacao = new Marcacao();
//        marcacao.setEntrada(horario);
//        marcacao.setSaida(horario);
//        this.marcacoes.add(marcacao);
//    }
//
//    public List<Marcacao> getMarcacoes() {
//        return marcacoes;
//    }
//
//    public void setMarcacoes(List<Marcacao> marcacoes) {
//        this.marcacoes = marcacoes;
//    }
//
//    // Getters e setters
//
//    public LocalTime getEntrada() {
//        return entrada;
//    }
//
//    public void setEntrada(LocalTime entrada) {
//        this.entrada = entrada;
//    }
//
//    public LocalTime getSaida() {
//        return saida;
//    }
//
//    public void setSaida(LocalTime saida) {
//        this.saida = saida;
//    }
//}


