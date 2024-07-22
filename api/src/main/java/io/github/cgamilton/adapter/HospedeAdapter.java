package io.github.cgamilton.adapter;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import io.github.cgamilton.dto.HospedeDTO;
import io.github.cgamilton.entity.Hospede;

@Component
public class HospedeAdapter {

	public Hospede toEntity(HospedeDTO dto) {
		var entity = new Hospede();
		entity.setId(dto.getId());
		entity.setNome(dto.getNome());
		entity.setTelefone(dto.getTelefone());
		entity.setDocumento(dto.getDocumento());
		return entity;
	}

	public HospedeDTO toDTO(Hospede entity) {
		var dto = new HospedeDTO();
		dto.setId(entity.getId());
		dto.setNome(entity.getNome());
		dto.setTelefone(entity.getTelefone());
		dto.setDocumento(entity.getDocumento());
		return dto;
	}

	public List<HospedeDTO> toDTO(List<Hospede> entities) {
		return entities.stream().map(this::toDTO).collect(Collectors.toList());
	}

	public List<Hospede> toEntity(List<HospedeDTO> dtos) {
		return dtos.stream().map(this::toEntity).collect(Collectors.toList());
	}
}
