package br.com.pontofuncionarios.bean;

import javax.annotation.PostConstruct;
import javax.faces.bean.SessionScoped;

import br.com.pontofuncionarios.domain.HorarioMarcacao;
import br.com.pontofuncionarios.domain.HorarioTrabalho;
import br.com.pontofuncionarios.domain.Marcacao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@SessionScoped
public class HorarioTrabalhoBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<HorarioTrabalho> horarios;
    private List<HorarioMarcacao> horariosMarcacoes = new ArrayList<>();
    private HorarioTrabalho horarioAtual;
    private Marcacao marcacao;
    private List<Marcacao> marcacoes;
    

    @PostConstruct
    public void init() {
    	horarios = new ArrayList<>();
        horarioAtual = new HorarioTrabalho();
        marcacao = new Marcacao();
        marcacoes = new ArrayList<>();
        
    }

    public void cadastrarHorario() {
        horarios.add(horarioAtual);
        marcacoes.add(marcacao);
        horariosMarcacoes.add(new HorarioMarcacao(horarioAtual, marcacao));
        horarioAtual = new HorarioTrabalho(); 
        marcacao = new Marcacao();
    }
    
    public void limpar() {    	
    	horariosMarcacoes = new ArrayList<>();
    }
    
    
    public List<HorarioMarcacao> getHorariosMarcacoes() {
        return horariosMarcacoes;
    }
    // Getters e setters
    
    

    public List<HorarioTrabalho> getHorarios() {
        return horarios;
    }    

    public void setHorarios(List<HorarioTrabalho> horarios) {
        this.horarios = horarios;
    }

    public HorarioTrabalho getHorarioAtual() {
        return horarioAtual;
    }

    public void setHorarioAtual(HorarioTrabalho horarioAtual) {
        this.horarioAtual = horarioAtual;
    }
    public Marcacao getMarcacao() {
		return marcacao;
	}
    public void setMarcacao(Marcacao marcacao) {
		this.marcacao = marcacao;
	}
    
    public List<Marcacao> getMarcacoes() {
		return marcacoes;
	}
    public void setMarcacoes(List<Marcacao> marcacoes) {
		this.marcacoes = marcacoes;
	}
}
