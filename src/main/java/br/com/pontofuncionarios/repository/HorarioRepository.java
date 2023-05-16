package br.com.pontofuncionarios.repository;

import java.util.List;

import br.com.pontofuncionarios.domain.Horario;
import br.com.pontofuncionarios.domain.HorarioTrabalho;
import br.com.pontofuncionarios.domain.Marcacao;

public interface HorarioRepository {

	List<Horario> calcularAtraso(List<HorarioTrabalho> horariosTrabalho, List<Marcacao> marcacoes);

	List<Horario> calcularHoraExtra(List<HorarioTrabalho> horariosTrabalho, List<Marcacao> marcacoes);
}
