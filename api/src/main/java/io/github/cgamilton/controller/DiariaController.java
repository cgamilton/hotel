package io.github.cgamilton.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.cgamilton.entity.Diaria;
import io.github.cgamilton.entity.Horario;
import io.github.cgamilton.enumeration.TipoDiaria;
import io.github.cgamilton.repository.DiariaRepository;
import io.github.cgamilton.repository.HorarioRepository;

@RestController
@RequestMapping("/diaria")
public class DiariaController {
	@Autowired
	DiariaRepository diariaRepository;
	@Autowired
	HorarioRepository horarioRepository;
	@GetMapping("/gravarvalores")
	public void gravarValores() {
		var diaria = new Diaria();
		diaria.setTaxaGaragem(15D);
		diaria.setValorDiaria(120D);
		diaria.setTipoDiaria(TipoDiaria.SEMANA);

		var diaria2 = new Diaria();
		diaria2.setTaxaGaragem(20D);
		diaria2.setValorDiaria(180D);
		diaria2.setTipoDiaria(TipoDiaria.FINAL_DE_SEMANA);

		diariaRepository.save(diaria);
		diariaRepository.save(diaria2);

		Horario horario = new Horario();
		horario.setCheckin(14);
		horario.setCheckout(12);
		horarioRepository.save(horario);
	}

	@GetMapping("/listar")
	public List<Diaria> teste() {
		return diariaRepository.findAll();
	}
}
