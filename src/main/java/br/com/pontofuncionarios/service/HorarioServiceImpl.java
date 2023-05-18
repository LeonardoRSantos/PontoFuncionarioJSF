package br.com.pontofuncionarios.service;

import java.util.*;
import java.util.stream.Collectors;

import br.com.pontofuncionarios.domain.Horario;
import br.com.pontofuncionarios.domain.HorarioTrabalho;
import br.com.pontofuncionarios.domain.Marcacao;
import br.com.pontofuncionarios.repository.HorarioRepository;

public class HorarioServiceImpl implements HorarioRepository {

	private List<Horario> obterAtrasos(List<Horario> marcacoes, Horario agendamento) {
		List<Horario> atrasos = new ArrayList<>();
		for(int i = 1; i < marcacoes.size(); i++) {
			if(marcacoes.get(i).getEntrada().isAfter(agendamento.getEntrada())) {
				Horario atraso = new Horario(1, agendamento.getEntrada(), marcacoes.get(i).getEntrada());
				atrasos.add(atraso);
				agendamento.setEntrada(marcacoes.get(i).getSaida());
			}
		}
		return atrasos;
	}
	@Override
	public List<Horario> calcularAtraso(List<HorarioTrabalho> horariosNaoOrdenados, List<Marcacao> marcacoesNaoOrdenadas) {
		// Ordenar os objetos horarios pela entrada
		List<Horario> horarios = horariosNaoOrdenados.stream().sorted(Comparator.comparing(Horario::getEntrada))
				.collect(Collectors.toList());

		// Ordenar as marcações pela entrada
		List<Marcacao> marcacoes = marcacoesNaoOrdenadas.stream().sorted(Comparator.comparing(Marcacao::getEntrada))
				.collect(Collectors.toList());

		List<Horario> atrasos = new ArrayList<>();
		horarios.forEach(horario -> {
			Horario marcacaoIntersecaoAntes =
			marcacoes.stream().filter(marcacao ->
				marcacao.getEntrada().compareTo(horario.getEntrada()) <= 0
						&& marcacao.getSaida().compareTo(horario.getEntrada()) > 0
						&& marcacao.getSaida().compareTo(horario.getSaida()) < 0
			).findFirst().orElse(null);
			List<Horario> marcacoesEntreHorario =
					marcacoes.stream().filter(marcacao ->
							marcacao.getEntrada().compareTo(horario.getEntrada()) > 0
									&& marcacao.getSaida().compareTo(horario.getSaida()) < 0
					).collect(Collectors.toList());
			Horario marcacaoIntersecaoDepois =
					marcacoes.stream().filter(marcacao ->
							marcacao.getEntrada().isAfter(horario.getEntrada())
									&& marcacao.getEntrada().compareTo(horario.getSaida()) < 0
									&& marcacao.getSaida().compareTo(horario.getSaida()) > 0
					).findFirst().orElse(null);

			Horario iniMarcAtraso = Optional.ofNullable(marcacaoIntersecaoAntes).orElseGet(() ->{
				if(!marcacoesEntreHorario.isEmpty()) {
					return marcacoesEntreHorario.get(0);
				} else {
					return marcacaoIntersecaoDepois;
				}
			});
			if(Objects.isNull(iniMarcAtraso)){
				Optional<Marcacao> algumaMarcacao = marcacoes.stream().filter(marcacao ->
						marcacao.getEntrada().compareTo(horario.getEntrada()) <= 0
								&& marcacao.getSaida().compareTo(horario.getSaida()) >= 0
				).findAny();
				if(!algumaMarcacao.isPresent()) {
					Horario atraso = new Horario(1, horario.getEntrada(), horario.getSaida());
					atrasos.add(atraso);
				}
			} else {
				if (Objects.nonNull(marcacaoIntersecaoAntes) && marcacoesEntreHorario.isEmpty() && Objects.isNull(marcacaoIntersecaoDepois)) {
					Horario atraso = new Horario(1, marcacaoIntersecaoAntes.getSaida(), horario.getSaida());
					atrasos.add(atraso);
				}
				if (Objects.nonNull(marcacaoIntersecaoDepois) && marcacoesEntreHorario.isEmpty() && Objects.isNull(marcacaoIntersecaoAntes)) {
					Horario atraso = new Horario(1, horario.getEntrada(), marcacaoIntersecaoDepois.getEntrada());
					atrasos.add(atraso);
				}
				if (Objects.nonNull(marcacaoIntersecaoDepois) && marcacoesEntreHorario.isEmpty() && Objects.nonNull(marcacaoIntersecaoAntes)) {
					if(marcacaoIntersecaoAntes.getSaida().isBefore(marcacaoIntersecaoDepois.getEntrada())) {
						Horario atraso = new Horario(1, marcacaoIntersecaoAntes.getSaida(), marcacaoIntersecaoDepois.getEntrada());
						atrasos.add(atraso);
					}
				}
				if (Objects.isNull(marcacaoIntersecaoAntes) && !marcacoesEntreHorario.isEmpty() && Objects.isNull(marcacaoIntersecaoDepois)) {
					Horario atrasoInicial = new Horario(1, horario.getEntrada(), marcacoesEntreHorario.get(0).getEntrada());
					atrasos.add(atrasoInicial);
					Horario agendamento = new Horario(horario.getId(), marcacoesEntreHorario.get(0).getSaida(), horario.getSaida());
					atrasos.addAll(obterAtrasos(marcacoesEntreHorario, agendamento));
					Horario ultimaMarc = marcacoesEntreHorario.get(marcacoesEntreHorario.size() - 1);
					Horario atrasoFinal = new Horario(1, ultimaMarc.getSaida(), horario.getSaida());
					atrasos.add(atrasoFinal);
				}
				if (Objects.isNull(marcacaoIntersecaoAntes) && !marcacoesEntreHorario.isEmpty() && Objects.nonNull(marcacaoIntersecaoDepois)) {
					Horario atrasoInicial = new Horario(1, horario.getEntrada(), marcacoesEntreHorario.get(0).getEntrada());
					atrasos.add(atrasoInicial);
					Horario agendamento = new Horario(horario.getId(), marcacoesEntreHorario.get(0).getSaida(), horario.getSaida());
					atrasos.addAll(obterAtrasos(marcacoesEntreHorario, agendamento));
					Horario ultimaMarc = marcacoesEntreHorario.get(marcacoesEntreHorario.size() - 1);
					if(ultimaMarc.getSaida().isBefore(marcacaoIntersecaoDepois.getEntrada())) {
						Horario atrasoFinal = new Horario(1, ultimaMarc.getSaida(), marcacaoIntersecaoDepois.getEntrada());
						atrasos.add(atrasoFinal);
					}
				}
				if (Objects.nonNull(marcacaoIntersecaoAntes) && !marcacoesEntreHorario.isEmpty() && Objects.isNull(marcacaoIntersecaoDepois)) {
					if(marcacaoIntersecaoAntes.getSaida().isBefore(marcacoesEntreHorario.get(0).getEntrada())) {
						Horario atraso = new Horario(1, marcacaoIntersecaoAntes.getSaida(), marcacoesEntreHorario.get(0).getEntrada());
						atrasos.add(atraso);
					}
					Horario agendamento = new Horario(horario.getId(), marcacoesEntreHorario.get(0).getSaida(), horario.getSaida());
					atrasos.addAll(obterAtrasos(marcacoesEntreHorario, agendamento));
					Horario ultimaMarc = marcacoesEntreHorario.get(marcacoesEntreHorario.size() - 1);
					Horario atrasoFinal = new Horario(1, ultimaMarc.getSaida(), horario.getSaida());
					atrasos.add(atrasoFinal);
				}
				if (Objects.nonNull(marcacaoIntersecaoAntes) && !marcacoesEntreHorario.isEmpty() && Objects.nonNull(marcacaoIntersecaoDepois)) {
					if(marcacaoIntersecaoAntes.getSaida().isBefore(marcacoesEntreHorario.get(0).getEntrada())) {
						Horario atraso = new Horario(1, marcacaoIntersecaoAntes.getSaida(), marcacoesEntreHorario.get(0).getEntrada());
						atrasos.add(atraso);
					}
					Horario agendamento = new Horario(horario.getId(), marcacoesEntreHorario.get(0).getSaida(), horario.getSaida());
					atrasos.addAll(obterAtrasos(marcacoesEntreHorario, agendamento));
					Horario ultimaMarc = marcacoesEntreHorario.get(marcacoesEntreHorario.size() - 1);
					if(ultimaMarc.getSaida().isBefore(marcacaoIntersecaoDepois.getEntrada())) {
						Horario atrasoFinal = new Horario(1, ultimaMarc.getSaida(), marcacaoIntersecaoDepois.getEntrada());
						atrasos.add(atrasoFinal);
					}
				}
			}
		});
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

		if(marcacoes.isEmpty() || horarios.isEmpty()) return horasExtras;

		Horario horarioAtual = horarios.get(0);
		for (int i = 0; i < marcacoes.size(); i++) {
			if(i < horarios.size()) {
				horarioAtual = horarios.get(i);
			}
			if (i >= horarios.size()) { // Caso a marcação não tenha acompanhado a quantidade de horários
				if (marcacoes.get(i).getSaida().isAfter(horarioAtual.getSaida())) {
					if(marcacoes.get(i).getEntrada().isAfter(horarioAtual.getSaida())) {
						Horario horaExtra = new Horario(1, marcacoes.get(i).getEntrada(), marcacoes.get(i).getSaida());
						horasExtras.add(horaExtra);
					} else {
						Horario horaExtra = new Horario(1, horarioAtual.getSaida(), marcacoes.get(i).getSaida());
						horasExtras.add(horaExtra);
					}
				}
			} else {
				if (marcacoes.get(i).getEntrada().isBefore(horarioAtual.getEntrada())) {
					if(marcacoes.get(i).getSaida().isBefore(horarioAtual.getEntrada())) {
						Horario horaExtra = new Horario(1, marcacoes.get(i).getEntrada(), marcacoes.get(i).getSaida());
						horasExtras.add(horaExtra);
					} else {
						Horario horaExtra = new Horario(1, marcacoes.get(i).getEntrada(), horarioAtual.getEntrada());
						horasExtras.add(horaExtra);
					}
				}
				if (marcacoes.get(i).getSaida().isAfter(horarios.get(i).getSaida())) {
					if (i < (horarios.size() - 1)) { // Existe um próximo horário?
						Horario proximoAgendamento = horarios.get(i+1);
						if(marcacoes.get(i).getSaida().isBefore(proximoAgendamento.getEntrada())) {
							Horario horaExtra = new Horario(1, horarioAtual.getSaida(),
									marcacoes.get(i).getEntrada());
							horasExtras.add(horaExtra);
						}
						if(!marcacoes.get(i).getSaida().isBefore(proximoAgendamento.getEntrada())) {
							Horario horaExtra = new Horario(1, horarioAtual.getSaida(),
									proximoAgendamento.getEntrada());
							horasExtras.add(horaExtra);
						}
						if(marcacoes.get(i).getSaida().isAfter(proximoAgendamento.getSaida())) {
							Horario horaExtra = new Horario(1, proximoAgendamento.getSaida(),
									marcacoes.get(i).getSaida());
							horasExtras.add(horaExtra);
						}
					} else {
						if(marcacoes.get(i).getEntrada().isAfter(horarioAtual.getSaida())) {
							Horario horaExtra = new Horario(1, marcacoes.get(i).getEntrada(), marcacoes.get(i).getSaida());
							horasExtras.add(horaExtra);
						} else {
							Horario horaExtra = new Horario(1, horarioAtual.getSaida(), marcacoes.get(i).getSaida());
							horasExtras.add(horaExtra);
						}
					}
				}
			}
		}

		return horasExtras;
	}
}