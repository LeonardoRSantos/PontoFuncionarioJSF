package br.com.pontofuncionarios.service;

import java.util.*;
import java.util.stream.Collectors;

import br.com.pontofuncionarios.domain.Horario;
import br.com.pontofuncionarios.domain.HorarioTrabalho;
import br.com.pontofuncionarios.domain.Marcacao;
import br.com.pontofuncionarios.repository.HorarioRepository;

public class HorarioServiceImpl implements HorarioRepository {

	@Override
	public List<Horario> calcularAtraso(List<HorarioTrabalho> horarios, List<Marcacao> marcacoes) {		
		// Ordenar os objetos horarios pela entrada
		List<Horario> horariosOrdenados = horarios.stream().sorted(Comparator.comparing(Horario::getEntrada))
				.collect(Collectors.toList());

		// Ordenar as marcações pela entrada
		List<Marcacao> marcacoesOrdenadas = marcacoes.stream().sorted(Comparator.comparing(Marcacao::getEntrada))
				.collect(Collectors.toList());

		List<Horario> atrasos = new ArrayList<>();

		for (int i = 0; i < horarios.size(); i++) {
			if (marcacoesOrdenadas.isEmpty()) { // Se não houver marcações, adiciona o horário como atraso
				atrasos.add(horarios.get(i));
			} else {

				if (i >= marcacoesOrdenadas.size()) {// Caso a marcação não tenha acompanhado a quantidad de horários,
														// por exemplo, marcou 2 horários e registrou apenas 1
					// {08 - 12, 14 - 17}H {07-16}M
					if (i > 0) {
						// Se tem mais agendamento do que marcação
						Horario marcacaoAnterior = marcacoes.get(i - 1);
						Horario agendamento = horarios.get(i);
						if (marcacaoAnterior.getEntrada().compareTo(agendamento.getEntrada()) > 0) {
							Horario atraso = new Horario(1, agendamento.getEntrada(), marcacaoAnterior.getEntrada());
							atrasos.add(atraso);
						}
						if (marcacaoAnterior.getSaida().compareTo(agendamento.getSaida()) < 0) {
							if (marcacaoAnterior.getSaida().compareTo(agendamento.getEntrada()) > 0) {
								Horario atraso = new Horario(1, marcacaoAnterior.getSaida(), agendamento.getSaida());
								atrasos.add(atraso);
							}
							if (marcacaoAnterior.getSaida().compareTo(agendamento.getEntrada()) < 0) {
								Horario atraso = new Horario(1, agendamento.getEntrada(), agendamento.getSaida());
								atrasos.add(atraso);
							}
						}
					}

				} else {

					if (marcacoes.get(i).getEntrada().compareTo(horarios.get(i).getEntrada()) > 0) {
						Horario atraso = new Horario(1, horarios.get(i).getEntrada(), marcacoes.get(i).getEntrada());
						atrasos.add(atraso);
					}
					if (marcacoes.get(i).getSaida().compareTo(horarios.get(i).getSaida()) < 0) {
						Horario atraso = new Horario(1, marcacoes.get(i).getSaida(), horarios.get(i).getSaida());
						atrasos.add(atraso);
					}
				}

			}
		}

		return atrasos;

	}

	@Override
	public List<Horario> calcularHoraExtra(List<HorarioTrabalho> horarios, List<Marcacao> marcacoes) {

		// Ordenar os objetos horarios pela entrada
		List<Horario> horariosOrdenados = horarios.stream().sorted(Comparator.comparing(Horario::getEntrada))
				.collect(Collectors.toList());

		// Ordenar as marcações pela entrada
		List<Marcacao> marcacoesOrdenadas = marcacoes.stream().sorted(Comparator.comparing(Marcacao::getEntrada))
				.collect(Collectors.toList());

		List<Horario> horasExtras = new ArrayList<>();

		// Ordenar os objetos horarios e marcacoes antes desse bloco pela entrada da
		// hora inicial

		for (int i = 0; i < horarios.size(); i++) {
			if (marcacoes.isEmpty()) { // Se não houver marcações, adiciona o horário completo como hora extra
				break;
			} else {
				if (i >= marcacoes.size()) { // Caso a marcação não tenha acompanhado a quantidade de horários
					if (i > 0) {
						Horario marcacaoAnterior = marcacoes.get(i - 1);
						if (marcacaoAnterior.getSaida().compareTo(horarios.get(i).getSaida()) > 0) {
							Horario horaExtra = new Horario(1, horarios.get(i).getSaida(), marcacaoAnterior.getSaida());
							horasExtras.add(horaExtra);
						}
					}
				} else {
					if (marcacoes.get(i).getEntrada().compareTo(horarios.get(i).getEntrada()) < 0) {
						Horario horaExtra = new Horario(1, marcacoes.get(i).getEntrada(), horarios.get(i).getEntrada());
						horasExtras.add(horaExtra);
					}
					if (marcacoes.get(i).getSaida().compareTo(horarios.get(i).getSaida()) > 0) {
						if (i < (horarios.size() - 1)) { // Existe um próximo agendamento?
							Horario proximoAgendamento = horarios.get(i+1);
							if (marcacoes.get(i).getSaida().compareTo(proximoAgendamento.getEntrada()) > 0) {
								Horario horaExtra = new Horario(1, horarios.get(i).getSaida(),
										proximoAgendamento.getEntrada());
								horasExtras.add(horaExtra);
							} else {
								Horario horaExtra = new Horario(1, horarios.get(i).getSaida(),
										marcacoes.get(i).getSaida());
								horasExtras.add(horaExtra);
							}
							
						} else {
							Horario horaExtra = new Horario(1, horarios.get(i).getSaida(), marcacoes.get(i).getSaida());
							horasExtras.add(horaExtra);
						}
					}
				}

			}
		}

		return horasExtras;
	}
}