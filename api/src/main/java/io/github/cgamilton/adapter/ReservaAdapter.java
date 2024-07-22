package io.github.cgamilton.adapter;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import io.github.cgamilton.dto.ReservaDTO;
import io.github.cgamilton.entity.Reserva;

@Component
public class ReservaAdapter {
	public Reserva toEntity(ReservaDTO dto) {
		var entity = new Reserva();
		entity.setId(dto.getId());
		entity.setDataCheckin(dto.getDataCheckin());
		entity.setDataCheckout(dto.getDataCheckout());
		entity.setIdDiaria(dto.getIdDiaria());
		entity.setIdHospede(dto.getIdHospede());
		entity.setStatusReserva(dto.getStatusReserva());
		entity.setTaxaGaragem(dto.isTaxaGaragem());
		entity.setValorAcrescimoCheckout(dto.getValorAcrescimoCheckout());
		entity.setValorFinalCobrado(dto.getValorFinalCobrado());
		entity.setValorTotal(dto.getValorTotal());
		return entity;
	}

	public ReservaDTO toDTO(Reserva entity) {
		var dto = new ReservaDTO();
		dto.setId(entity.getId());
		dto.setDataCheckin(entity.getDataCheckin());
		dto.setDataCheckout(entity.getDataCheckout());
		dto.setIdDiaria(entity.getIdDiaria());
		dto.setIdHospede(entity.getIdHospede());
		dto.setStatusReserva(entity.getStatusReserva());
		dto.setTaxaGaragem(entity.isTaxaGaragem());
		dto.setValorAcrescimoCheckout(entity.getValorAcrescimoCheckout());
		dto.setValorFinalCobrado(entity.getValorFinalCobrado());
		dto.setValorTotal(entity.getValorTotal());
		return dto;
	}

	public List<ReservaDTO> toDTO(List<Reserva> entities) {
		return entities.stream().map(this::toDTO).collect(Collectors.toList());

	}

	public List<Reserva> toEntity(List<ReservaDTO> dtos) {
		return dtos.stream().map(this::toEntity).collect(Collectors.toList());
	}
}
