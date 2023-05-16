package br.com.pontofuncionarios.bean;

import javax.annotation.PostConstruct;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;

import br.com.pontofuncionarios.domain.Horario;
import br.com.pontofuncionarios.domain.HorarioTrabalho;
import br.com.pontofuncionarios.domain.Marcacao;
import br.com.pontofuncionarios.service.HorarioServiceImpl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@SessionScoped
public class HorarioTrabalhoBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<HorarioTrabalho> horarios;
    private List<Marcacao> marcacoes;
    private HorarioTrabalho horarioAtual;
    private Marcacao marcacao;
    private List<Horario> atrasos;
    private List<Horario> horasExtras;
    
    private HorarioServiceImpl horarioService;
    
    

    @PostConstruct
    public void init() {
    	horarios = new ArrayList<>();
        horarioAtual = new HorarioTrabalho();
        marcacao = new Marcacao();
        marcacoes = new ArrayList<>();
        atrasos = new ArrayList<>();
        horasExtras = new ArrayList<>();
        horarioService = new HorarioServiceImpl();
        
    }

    public void cadastrarHorario() {
        horarios.add(horarioAtual);
//        horariosMarcacoes.add(new HorarioMarcacao(horarioAtual, null));
        horarioAtual = new HorarioTrabalho(); 
    }
    
    public void cadastrarMarcacao() {
        marcacoes.add(marcacao);        
        atrasos = horarioService.calcularAtraso(horarios, marcacoes);
        horasExtras = horarioService.calcularHoraExtra(horarios, marcacoes);
        marcacao = new Marcacao(); 
    }
  

    
    public void limpar() {    	
    	horarios.clear();
        marcacoes.clear();
        horarioAtual = new HorarioTrabalho();
        marcacao = new Marcacao();
        horasExtras.clear();
        atrasos.clear();
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
    
    public List<Horario> getAtrasos() {
		return atrasos;
	}
    public List<Horario> getHorasExtras() {
		return horasExtras;
	}
}
