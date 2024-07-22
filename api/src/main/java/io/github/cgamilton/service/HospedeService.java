package io.github.cgamilton.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.cgamilton.adapter.HospedeAdapter;
import io.github.cgamilton.core.exception.HospedeNotFoundException;
import io.github.cgamilton.dto.HospedeDTO;
import io.github.cgamilton.repository.HospedeRepository;

@Service
public class HospedeService {

	@Autowired
	HospedeRepository hospedeRepository;

	@Autowired
	HospedeAdapter hospedeAdapter;

	public HospedeDTO novoHospede(HospedeDTO hospedeDTO) {
		var entity = hospedeAdapter.toEntity(hospedeDTO);
		hospedeRepository.save(entity);
		return hospedeAdapter.toDTO(entity);
	}

	public HospedeDTO retornaHospede(HospedeDTO hospedeDTO) {
		var entity = hospedeRepository
				.findHospede(hospedeDTO.getNome(), hospedeDTO.getDocumento(), hospedeDTO.getTelefone())
				.orElseThrow(() -> new HospedeNotFoundException());
		return hospedeAdapter.toDTO(entity);
	}
}
