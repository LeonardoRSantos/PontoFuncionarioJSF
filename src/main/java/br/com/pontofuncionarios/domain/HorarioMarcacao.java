package br.com.pontofuncionarios.domain;

import java.time.Duration;
import java.time.LocalTime;
import java.util.logging.Logger;

public class HorarioMarcacao {
	private HorarioTrabalho horario;
	private Marcacao marcacao;
	private String horarioAtrasoFormatado;
	private String horarioHoraExtraFormatado;
	private static final Logger logger = Logger.getLogger(HorarioMarcacao.class.getName());

	public HorarioMarcacao(HorarioTrabalho horario, Marcacao marcacao) {
		this.horario = horario;
		this.marcacao = marcacao;
		calcularAtraso();
		calcularHoraExtra();
	}
	public HorarioMarcacao() {
		
	}
	
	private void calcularAtraso() {
	    LocalTime entradaEsperada = marcacao.getEntrada();
	    LocalTime saidaEsperada = marcacao.getSaida();
	    LocalTime entradaReal = horario.getEntrada();
	    LocalTime saidaReal = horario.getSaida();
	    Duration atraso = Duration.ZERO;

	    if (entradaReal.isAfter(saidaReal) || entradaReal.isBefore(entradaEsperada)) {
	        // Entrada atrasada ou não registrada
	        atraso = Duration.between(entradaReal, entradaEsperada);
	        logger.info("Atraso: " + atraso.toString());
	    } else if (saidaReal.isAfter(saidaEsperada)) {
	        // Saída atrasada ou antecipada
	        atraso = Duration.between(saidaEsperada, saidaReal);
	        logger.info("Atraso: " + atraso.toString());
	    }

	    horarioAtrasoFormatado = formatarHorario(atraso.abs());
	}
	
	private void calcularHoraExtra() {
	    LocalTime entradaEsperada = marcacao.getEntrada();
	    LocalTime saidaEsperada = marcacao.getSaida();
	    LocalTime entradaReal = horario.getEntrada();
	    LocalTime saidaReal = horario.getSaida();
	    Duration horaExtra = Duration.ZERO;

	    if (saidaEsperada.isAfter(saidaReal) && entradaEsperada.isBefore(entradaReal)) {
	        horaExtra = Duration.between(saidaReal, saidaEsperada);
	        logger.info("Hora extra: " + horaExtra.toString());
	    }

	    horarioHoraExtraFormatado = formatarHorario(horaExtra.abs());
	}


	private String formatarHorario(Duration duracao) {
		long horas = duracao.toHours();
		long minutos = duracao.minusHours(horas).toMinutes();
		return String.format("%02d:%02d", horas, minutos);
	}
	
	public String formatarHorarioPublic(Duration duracao) {
		  return formatarHorario(duracao);
		}

	public HorarioTrabalho getHorario() {
		return horario;
	}

	public void setHorario(HorarioTrabalho horario) {
		this.horario = horario;
	}

	public Marcacao getMarcacao() {
		return marcacao;
	}

	public void setMarcacao(Marcacao marcacao) {
		this.marcacao = marcacao;
	}

	public String getHorarioAtrasoFormatado() {
		return horarioAtrasoFormatado;
	}
	
	

	public String getHorarioHoraExtraFormatado() {
		return horarioHoraExtraFormatado;
	}

	public void setHorarioAtrasoFormatado(String horarioAtrasoFormatado) {
		this.horarioAtrasoFormatado = horarioAtrasoFormatado;
	}
}
