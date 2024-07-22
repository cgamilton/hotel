package io.github.cgamilton.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.github.cgamilton.adapter.ReservaAdapter;
import io.github.cgamilton.core.exception.HorarioCheckinException;
import io.github.cgamilton.core.exception.HospedeNotFoundException;
import io.github.cgamilton.core.exception.ReservaNaoEncontradaException;
import io.github.cgamilton.core.provider.DataProvider;
import io.github.cgamilton.dto.HospedeDTO;
import io.github.cgamilton.dto.ReservaDTO;
import io.github.cgamilton.entity.Diaria;
import io.github.cgamilton.entity.Horario;
import io.github.cgamilton.entity.Reserva;
import io.github.cgamilton.enumeration.StatusReserva;
import io.github.cgamilton.enumeration.TipoDiaria;
import io.github.cgamilton.repository.DiariaRepository;
import io.github.cgamilton.repository.HospedeRepository;
import io.github.cgamilton.repository.ReservaRepository;
@SpringBootTest
@ActiveProfiles("test")
public class ReservaServiceTest {

	@Mock
	private ReservaRepository reservaRepository;
	@Mock
	private DiariaRepository diariaRepository;
	@Mock
	private ReservaAdapter reservaAdapter;
	@Mock
	private DataProvider dataProvider;
	@Mock
	private HorarioService horarioService;
	@Mock
	private HospedeRepository hospedeRepository;
	@InjectMocks
	private ReservaService reservaService;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
		setupMockDiarias();
		setupMockMapDiarias();
	}

	@Test
	void testCheckinHospedeNotFoundException() {
		HospedeDTO dto = createHospedeDTO(1234L, null);
		assertThrows(HospedeNotFoundException.class, () -> reservaService.checkin(dto));
	}

	@Test
	void testCheckinReservaNaoEncontradaException() {
		HospedeDTO dto = createHospedeDTO(1234L, null);
		when(hospedeRepository.findHospede(null, 1234L, null)).thenReturn(Optional.of(dto));
		assertThrows(ReservaNaoEncontradaException.class, () -> reservaService.checkin(dto));
	}

	@Test
	void testCheckinHorarioCheckinException() {
		HospedeDTO dto = createHospedeDTO(1234L, 1L);
		setupMockHorario(14, 12);
		LocalDate dataTeste = LocalDate.of(2024, 7, 18);
		setupMockReserva(dataTeste, 8L, 0D, 120D, 1L, 1L);
		LocalTime checkinTime = LocalTime.of(11, 0);
		when(dataProvider.date()).thenReturn(dataTeste);
		when(dataProvider.dateTime()).thenReturn(dataTeste.atTime(checkinTime));
		when(hospedeRepository.findHospede(null, 1234L, null)).thenReturn(Optional.of(dto));
		assertThrows(HorarioCheckinException.class, () -> reservaService.checkin(dto));
	}

	@Test
	void testCheckinRealizado() {
		HospedeDTO dto = createHospedeDTO(1234L, 2L);
		setupMockHorario(14, 12);
		LocalDate dataTeste = LocalDate.of(2024, 7, 18);
		setupMockReserva(dataTeste, 9L, 0D, 120D, 1L, 2L);
		LocalTime checkinTime = LocalTime.of(14, 1);
		when(dataProvider.date()).thenReturn(dataTeste);
		when(dataProvider.dateTime()).thenReturn(dataTeste.atTime(checkinTime));
		when(hospedeRepository.findHospede(null, 1234L, null)).thenReturn(Optional.of(dto));
		ReservaDTO reservaDto = reservaService.checkin(dto);

		assertEquals(StatusReserva.CHECKIN, reservaDto.getStatusReserva());
		assertEquals(2L, reservaDto.getIdHospede());
		assertEquals(9L, reservaDto.getId());
	}

	@Test
	public void testCheckoutComAcrescimoSemana() throws Exception {
		runCheckoutTest(LocalDate.of(2024, 7, 18), 1L, 0D, 120D, 1L, LocalTime.of(15, 0), 180D);
	}

	@Test
	public void testCheckoutSemAcrescimoSemana() throws Exception {
		runCheckoutTest(LocalDate.of(2024, 7, 18), 2L, 0D, 120D, 1L, LocalTime.of(11, 0), 120D);
	}

	@Test
	public void testCheckoutComAcrescimoFimDeSemana() throws Exception {
		runCheckoutTest(LocalDate.of(2024, 7, 20), 3L, 0D, 180D, 2L, LocalTime.of(15, 0), 270D);
	}

	@Test
	public void testCheckoutSemAcrescimoFimDeSemana() throws Exception {
		runCheckoutTest(LocalDate.of(2024, 7, 20), 3L, 0D, 180D, 2L, LocalTime.of(11, 0), 180D);
	}

	@Test
	public void testCheckoutComAcrescimoSemanaGaragem() throws Exception {
		runCheckoutTest(LocalDate.of(2024, 7, 18), 4L, 15D, 120D, 1L, LocalTime.of(15, 0), 195D);
	}

	@Test
	public void testCheckoutSemAcrescimoSemanaGaragem() throws Exception {
		runCheckoutTest(LocalDate.of(2024, 7, 18), 5L, 15D, 120D, 1L, LocalTime.of(11, 0), 135D);
	}

	@Test
	public void testCheckoutComAcrescimoFimDeSemanaGaragem() throws Exception {
		runCheckoutTest(LocalDate.of(2024, 7, 20), 6L, 20D, 180D, 2L, LocalTime.of(15, 0), 290D);
	}

	@Test
	public void testCheckoutSemAcrescimoFimDeSemanaGaragem() throws Exception {
		runCheckoutTest(LocalDate.of(2024, 7, 20), 7L, 20D, 180D, 2L, LocalTime.of(11, 0), 200D);
	}

	private void runCheckoutTest(LocalDate dataTeste, Long reservaId, Double taxaGaragem, Double valorDiaria,
			Long idDiaria, LocalTime checkoutTime, Double expectedFinalValue) throws Exception {
		setupMockReserva(dataTeste, reservaId, taxaGaragem, valorDiaria, idDiaria, null);
		when(dataProvider.date()).thenReturn(dataTeste.plusDays(1));
		when(dataProvider.dateTime()).thenReturn(dataTeste.plusDays(1).atTime(checkoutTime));
		setupMockHorario(14, 12);

		ReservaDTO result = reservaService.checkout(reservaId);

		verify(reservaRepository).findById(reservaId);
		verify(diariaRepository).findById(idDiaria);
		verify(reservaRepository).save(any(Reserva.class));
		verify(reservaAdapter).toDTO(any(Reserva.class));

		assertNotNull(result);
		assertEquals(reservaId, result.getId());
		assertEquals(expectedFinalValue, result.getValorFinalCobrado());
		assertEquals(StatusReserva.CHECKOUT, result.getStatusReserva());
	}

	private HospedeDTO createHospedeDTO(Long documento, Long id) {
		HospedeDTO dto = new HospedeDTO();
		dto.setDocumento(documento);
		dto.setId(id);
		return dto;
	}

	private void setupMockReserva(LocalDate dataCheckin, Long id, Double taxaGaragem, Double valorDiaria, Long idDiaria,
			Long idHospede) {
		Reserva mockReserva = new Reserva();
		mockReserva.setId(id);
		mockReserva.setIdDiaria(idDiaria);
		mockReserva.setDataCheckin(dataCheckin);
		mockReserva.setValorTotal(valorDiaria + taxaGaragem);
		mockReserva.setIdHospede(idHospede);
		when(reservaRepository.findById(id)).thenReturn(Optional.of(mockReserva));
		when(reservaRepository.findReserva(dataCheckin, idHospede)).thenReturn(Optional.of(mockReserva));
		when(reservaAdapter.toDTO(any(Reserva.class))).thenCallRealMethod();
	}

	private void setupMockDiarias() {
		setupMockDiaria(TipoDiaria.SEMANA, 1L);
		setupMockDiaria(TipoDiaria.FINAL_DE_SEMANA, 2L);
	}

	private void setupMockDiaria(TipoDiaria tipoDiaria, Long id) {
		Diaria mockDiaria = new Diaria();
		mockDiaria.setTipoDiaria(tipoDiaria);
		when(diariaRepository.findById(id)).thenReturn(Optional.of(mockDiaria));
	}

	private void setupMockMapDiarias() {
		var d1 = createDiaria(1L, 15D, TipoDiaria.SEMANA, 120D);
		var d2 = createDiaria(2L, 20D, TipoDiaria.FINAL_DE_SEMANA, 180D);
		reservaService
				.setMapDiarias(Stream.of(d1, d2).collect(Collectors.toMap(Diaria::getTipoDiaria, Function.identity())));
	}

	private Diaria createDiaria(Long id, Double taxaGaragem, TipoDiaria tipoDiaria, Double valorDiaria) {
		Diaria diaria = new Diaria();
		diaria.setId(id);
		diaria.setTaxaGaragem(taxaGaragem);
		diaria.setTipoDiaria(tipoDiaria);
		diaria.setValorDiaria(valorDiaria);
		return diaria;
	}

	private void setupMockHorario(int checkin, int checkout) {
		Horario mockHorario = new Horario();
		mockHorario.setCheckin(checkin);
		mockHorario.setCheckout(checkout);
		when(horarioService.getHorario()).thenReturn(mockHorario);
	}
}
