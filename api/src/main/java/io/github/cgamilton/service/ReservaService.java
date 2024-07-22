package io.github.cgamilton.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.cgamilton.adapter.ReservaAdapter;
import io.github.cgamilton.core.exception.HorarioCheckinException;
import io.github.cgamilton.core.exception.HospedeNotFoundException;
import io.github.cgamilton.core.exception.ReservaNaoEncontradaException;
import io.github.cgamilton.core.provider.DataProvider;
import io.github.cgamilton.dto.HospedeDTO;
import io.github.cgamilton.dto.ReservaDTO;
import io.github.cgamilton.entity.Diaria;
import io.github.cgamilton.enumeration.StatusReserva;
import io.github.cgamilton.enumeration.TipoDiaria;
import io.github.cgamilton.repository.DiariaRepository;
import io.github.cgamilton.repository.HospedeRepository;
import io.github.cgamilton.repository.ReservaRepository;

@Service
public class ReservaService {

	public static final double PERCENTUAL_ACRESCIMO = 0.5;

	private Map<TipoDiaria, Diaria> mapDiarias = null;

	@Autowired
	ReservaRepository reservaRepository;
	@Autowired
	ReservaAdapter reservaAdapter;
	@Autowired
	HospedeRepository hospedeRepository;
	@Autowired
	DiariaRepository diariaRepository;
	@Autowired
	HorarioService horarioService;
	@Autowired DataProvider dataProvider;

	public ReservaDTO criarReserva(ReservaDTO reservaDTO) {
		var hospede = hospedeRepository.findById(reservaDTO.getIdHospede())
				.orElseThrow(() -> new HospedeNotFoundException());
		var mapDiarias = retornaMapDiarias();
		var entity = reservaAdapter.toEntity(reservaDTO);
		var valorTotal = calcularValorReserva(reservaDTO.getDataCheckin(), reservaDTO.isTaxaGaragem());
		entity.setValorTotal(valorTotal);
		entity.setStatusReserva(StatusReserva.RESERVADO);
		entity.setIdHospede(hospede.getId());
		entity.setIdDiaria(
				isFimDeSemana(reservaDTO.getDataCheckin()) ? mapDiarias.get(TipoDiaria.FINAL_DE_SEMANA).getId()
						: mapDiarias.get(TipoDiaria.SEMANA).getId());
		reservaRepository.save(entity);
		return reservaAdapter.toDTO(entity);
	}

	public List<ReservaDTO> listarTodas() {
		var entities = reservaRepository.findAll();
		return reservaAdapter.toDTO(entities);

	}

	public ReservaDTO checkin(HospedeDTO hospedeDTO) {
		var horario = horarioService.getHorario();
		var hospede = hospedeRepository
				.findHospede(hospedeDTO.getNome(), hospedeDTO.getDocumento(), hospedeDTO.getTelefone())
				.orElseThrow(() -> new HospedeNotFoundException());
		var reserva = reservaRepository.findReserva(dataProvider.date(), hospede.getId())
				.orElseThrow(() -> new ReservaNaoEncontradaException());
		if (dataProvider.date().isBefore(reserva.getDataCheckin()) || dataProvider.dateTime().getHour() < horario.getCheckin()) {
			throw new HorarioCheckinException();
		}
		reserva.setStatusReserva(StatusReserva.CHECKIN);
		reservaRepository.save(reserva);
		return reservaAdapter.toDTO(reserva);
	}

	public ReservaDTO checkout(Long idReserva) {
		var horario = horarioService.getHorario();
		var reserva = reservaRepository.findById(idReserva).get();
		var tipoDiaria = diariaRepository.findById(reserva.getIdDiaria()).get().getTipoDiaria();
		var dataCheckout = reserva.getDataCheckin().plusDays(1).atTime(horario.getCheckout(), 0);
		var valorAcrescimo = calculaAcrescimo(dataCheckout, tipoDiaria);
		reserva.setValorAcrescimoCheckout(valorAcrescimo);
		reserva.setValorFinalCobrado(reserva.getValorTotal() + valorAcrescimo);
		reserva.setDataCheckout(dataProvider.dateTime());
		reserva.setStatusReserva(StatusReserva.CHECKOUT);
		reservaRepository.save(reserva);
		return reservaAdapter.toDTO(reserva);
	}

	double calcularValorReserva(LocalDate dataCheckin, boolean taxaGaragem) {
		var isFimDeSemana = isFimDeSemana(dataCheckin);
		var diaria = isFimDeSemana ? mapDiarias.get(TipoDiaria.FINAL_DE_SEMANA) : mapDiarias.get(TipoDiaria.SEMANA);
		double valorTotal = diaria.getValorDiaria();
		if (taxaGaragem) {
			valorTotal += diaria.getTaxaGaragem();
		}
		return valorTotal;
	}

	double calculaAcrescimo(LocalDateTime dataCheckout, TipoDiaria tipoDiaria) {
		var mapDiarias = retornaMapDiarias();
		var temAcrescimo = dataProvider.dateTime().isAfter(dataCheckout);
		if (temAcrescimo) {
			return mapDiarias.get(tipoDiaria).getValorDiaria() * PERCENTUAL_ACRESCIMO;
		}
		return 0D;
	}

	boolean isFimDeSemana(LocalDate dataCheckin) {
		DayOfWeek diaDaSemana = dataCheckin.getDayOfWeek();
		return diaDaSemana == DayOfWeek.SATURDAY || diaDaSemana == DayOfWeek.SUNDAY;
	}

	Map<TipoDiaria, Diaria> retornaMapDiarias() {
		if (mapDiarias == null) {

			setMapDiarias(diariaRepository.findAll().stream()
					.collect(Collectors.toMap(Diaria::getTipoDiaria, Function.identity())));
		}
		return mapDiarias;
	}

	void setMapDiarias(Map<TipoDiaria, Diaria> mapDiarias) {
		this.mapDiarias = mapDiarias;
	}
	
	public List<ReservaDTO> listarPorStatus(String statusReserva){
		var statusReservaEnum = StatusReserva.valueOf(statusReserva);
		var entities = reservaRepository.findReservaPorStatu(statusReservaEnum);
		return reservaAdapter.toDTO(entities);
	}
}
