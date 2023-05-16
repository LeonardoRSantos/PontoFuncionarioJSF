package br.com.pontofuncionariostest;



import java.time.LocalTime;
import java.util.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import br.com.pontofuncionarios.domain.Horario;
import br.com.pontofuncionarios.domain.HorarioTrabalho;
import br.com.pontofuncionarios.domain.Marcacao;
import br.com.pontofuncionarios.service.HorarioServiceImpl;



class HorarioServiceImplTest {

	@Test
    public void testCalcularHoraExtra() {
		// Criar instância da classe HorarioServiceImpl
	    HorarioServiceImpl horarioService = new HorarioServiceImpl();

	    // Criar a lista de horários de trabalho
	    List<HorarioTrabalho> horarios = new ArrayList<>();
	    horarios.add(new HorarioTrabalho(null, LocalTime.of(8, 0), LocalTime.of(12, 0)));
	    horarios.add(new HorarioTrabalho(null, LocalTime.of(13, 30), LocalTime.of(17, 30)));

	    // Criar a lista de marcações
	    List<Marcacao> marcacoes = new ArrayList<>();
	    marcacoes.add(new Marcacao(null, LocalTime.of(6, 0), LocalTime.of(20, 0)));

	    // Chamar o método calcularHoraExtra da classe HorarioServiceImpl e obter o resultado
	    List<Horario> resultado = horarioService.calcularHoraExtra(horarios, marcacoes);

	    // Criar a lista de horas extras esperadas
	    List<String> horasExtrasEsperadas = Arrays.asList("06:00 - 08:00", "12:00 - 13:30", "17:30 - 20:00");

	    

	    // Verificar cada hora extra calculada com base nas horas extras esperadas
	    for (int i = 0; i < resultado.size(); i++) {
	        String horaExtraFormatada = resultado.get(i).getEntrada().toString() + " - " + resultado.get(i).getSaida().toString();
	        Assertions.assertEquals(horasExtrasEsperadas.get(i), horaExtraFormatada,
	                "A hora extra calculada não coincide com a hora extra esperada.");
	    }
	}	
	
	@Test
    public void testCalcularAtraso() {
		// Criar instância da classe HorarioServiceImpl
	    HorarioServiceImpl horarioService = new HorarioServiceImpl();

	    // Criar a lista de horários de trabalho
	    List<HorarioTrabalho> horarios = new ArrayList<>();
	    horarios.add(new HorarioTrabalho(null, LocalTime.of(8, 0), LocalTime.of(12, 0)));
	    

	    // Criar a lista de marcações
	    List<Marcacao> marcacoes = new ArrayList<>();
	    marcacoes.add(new Marcacao(null, LocalTime.of(07, 0), LocalTime.of(11, 0)));

	    // Chamar o método calcularAtraso da classe HorarioServiceImpl e obter o resultado
	    List<Horario> resultado = horarioService.calcularAtraso(horarios, marcacoes);

	    // Criar a lista de Atrasos esperadas
	    List<String> horasAtrasosEsperadas = Arrays.asList("11:00 - 12:00");
	    
	    
	    // Verificar cada hora extra calculada com base nas horas extras esperadas
	    for (int i = 0; i < resultado.size(); i++) {
	        String horaExtraFormatada = resultado.get(i).getEntrada().toString() + " - " + resultado.get(i).getSaida().toString();
	        Assertions.assertEquals(horasAtrasosEsperadas.get(i), horaExtraFormatada,
	                "A hora extra calculada não coincide com a hora extra esperada.");
	    }
	}
	
	@Test
	public void testCalcularHoraExtraEcalcularAtraso() {
	    // Criar instância da classe HorarioServiceImpl
	    HorarioServiceImpl horarioService = new HorarioServiceImpl();

	    // Criar a lista de horários de trabalho
	    List<HorarioTrabalho> horarios = new ArrayList<>();
	    horarios.add(new HorarioTrabalho(null, LocalTime.of(8, 0), LocalTime.of(12, 0)));
	    horarios.add(new HorarioTrabalho(null, LocalTime.of(13, 30), LocalTime.of(17, 30)));

	    // Criar a lista de marcações
	    List<Marcacao> marcacoes = new ArrayList<>();
	    marcacoes.add(new Marcacao(null, LocalTime.of(7, 0), LocalTime.of(12, 30)));
	    marcacoes.add(new Marcacao(null, LocalTime.of(14, 0), LocalTime.of(17, 00)));

	    // Chamar o método calcularHoraExtra da classe HorarioServiceImpl e obter o resultado
	    List<Horario> resultadoHoraExtra = horarioService.calcularHoraExtra(horarios, marcacoes);

	    // Chamar o método calcularAtraso da classe HorarioServiceImpl e obter o resultado
	    List<Horario> resultadoAtraso = horarioService.calcularAtraso(horarios, marcacoes);

	    // Criar a lista de horas extras esperadas
	    List<String> horasExtrasEsperadas = Arrays.asList("07:00 - 08:00", "12:00 - 12:30");

	    // Criar a lista de atrasos esperados
	    List<String> atrasosEsperados = Arrays.asList("13:30 - 14:00", "17:00 - 17:30");

	    // Verificar cada hora extra calculada com base nas horas extras esperadas
	    for (int i = 0; i < resultadoHoraExtra.size(); i++) {
	        String horaExtraFormatada = resultadoHoraExtra.get(i).getEntrada().toString() + " - " + resultadoHoraExtra.get(i).getSaida().toString();
	        Assertions.assertEquals(horasExtrasEsperadas.get(i), horaExtraFormatada,
	                "A hora extra calculada não coincide com a hora extra esperada.");
	    }

	    // Verificar cada atraso calculado com base nos atrasos esperados
	    for (int i = 0; i < resultadoAtraso.size(); i++) {
	        String atrasoFormatado = resultadoAtraso.get(i).getEntrada().toString() + " - " + resultadoAtraso.get(i).getSaida().toString();
	        Assertions.assertEquals(atrasosEsperados.get(i), atrasoFormatado,
	                "O atraso calculado não coincide com o atraso esperado.");
	    }
	}

}
