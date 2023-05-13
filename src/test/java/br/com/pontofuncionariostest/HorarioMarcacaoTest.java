package br.com.pontofuncionariostest;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import java.time.LocalTime;
import java.util.*;


import org.junit.jupiter.api.Test;

import br.com.pontofuncionarios.domain.HorarioMarcacao;
import br.com.pontofuncionarios.domain.HorarioTrabalho;
import br.com.pontofuncionarios.domain.Marcacao;

class HorarioMarcacaoTest {

	@Test
	public void testCalcularAtraso() {
	    // Lista com horários de trabalho
	    List<HorarioTrabalho> horarios = new ArrayList<>();
	    horarios.add(new HorarioTrabalho(LocalTime.of(8, 0), LocalTime.of(12, 0)));
	    horarios.add(new HorarioTrabalho(LocalTime.of(9, 0), LocalTime.of(13, 0)));
	    horarios.add(new HorarioTrabalho(LocalTime.of(10, 0), LocalTime.of(14, 0)));

	    // Lista com marcações de entrada e saída
	    List<Marcacao> marcacoes = new ArrayList<>();
	    marcacoes.add(new Marcacao(LocalTime.of(7, 0), LocalTime.of(11, 0)));
	    marcacoes.add(new Marcacao(LocalTime.of(8, 30), LocalTime.of(12, 30)));
	    marcacoes.add(new Marcacao(LocalTime.of(10, 0), LocalTime.of(14, 0)));

	    // Lista com os atrasos esperados
	    List<String> atrasosEsperados = new ArrayList<>();
	    atrasosEsperados.add("01:00");
	    atrasosEsperados.add("00:30");
	    atrasosEsperados.add("00:00");

	    // Loop para testar cada item das listas
	    for (int i = 0; i < horarios.size(); i++) {
	        HorarioMarcacao horarioMarcacao = new HorarioMarcacao(horarios.get(i), marcacoes.get(i));
	        assertEquals(atrasosEsperados.get(i), horarioMarcacao.getHorarioAtrasoFormatado());
	    }
	}
	
	
	@Test
	public void testCalcularHoraExtra() {
	    // Lista com horários de trabalho
	    List<HorarioTrabalho> horarios = new ArrayList<>();
	    horarios.add(new HorarioTrabalho(LocalTime.of(8, 0), LocalTime.of(12, 0)));
	    horarios.add(new HorarioTrabalho(LocalTime.of(13, 0), LocalTime.of(17, 30)));
	    

	    List<Marcacao> marcacoes = new ArrayList<>();
	    marcacoes.add(new Marcacao(LocalTime.of(6, 20), LocalTime.of(20, 0)));
	    marcacoes.add(new Marcacao(LocalTime.of(6, 20), LocalTime.of(20, 0)));
	    

	    List<String> horasExtrasEsperadas = Arrays.asList("08:00", "02:30");

	    for (int i = 0; i < marcacoes.size(); i++) {
	        HorarioMarcacao horarioMarcacao = new HorarioMarcacao(horarios.get(i), marcacoes.get(i));
	        assertEquals(horasExtrasEsperadas.get(i), horarioMarcacao.getHorarioHoraExtraFormatado());
	    }
	}


	

	@Test
	void testFormatarHorario() {
	    HorarioMarcacao hm = new HorarioMarcacao();
	    Duration duracao = Duration.ofMinutes(135);
	    String horarioFormatado = hm.formatarHorarioPublic(duracao);
	    assertEquals("02:15", horarioFormatado);
	}

}
