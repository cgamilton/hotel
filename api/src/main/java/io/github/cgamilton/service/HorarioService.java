package io.github.cgamilton.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.cgamilton.entity.Horario;
import io.github.cgamilton.repository.HorarioRepository;
@Service
public class HorarioService {

	@Autowired
	HorarioRepository horarioRepository;

	private static Horario horario = null;

	public Horario getHorario() {
		if (horario == null) {
			horario = horarioRepository.findById(1L).get();
		}
		return horario;
	}
}
