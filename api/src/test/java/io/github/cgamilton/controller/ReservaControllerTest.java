package io.github.cgamilton.controller;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.utility.TestcontainersConfiguration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.cgamilton.core.provider.DataProvider;
import io.github.cgamilton.dto.HospedeDTO;
import io.github.cgamilton.dto.ReservaDTO;
import io.github.cgamilton.entity.Diaria;
import io.github.cgamilton.entity.Horario;
import io.github.cgamilton.entity.Hospede;
import io.github.cgamilton.entity.Reserva;
import io.github.cgamilton.enumeration.StatusReserva;
import io.github.cgamilton.enumeration.TipoDiaria;
import io.github.cgamilton.repository.DiariaRepository;
import io.github.cgamilton.repository.HorarioRepository;
import io.github.cgamilton.repository.HospedeRepository;
import io.github.cgamilton.repository.ReservaRepository;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
@Import(TestcontainersConfiguration.class)
class ReservaControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private HorarioRepository horarioRepository;

	@Autowired
	private DiariaRepository diariaRepository;

	@Autowired
	private HospedeRepository hospedeRepository;

	@Autowired
	private ReservaRepository reservaRepository;
	
	@Mock
	private DataProvider dataProvider;

	@Test
	@Order(2)
	public void testHospedeNotFound() throws JsonProcessingException, Exception {
		mockMvc.perform(get("/hospede/retornaHospede").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(new HospedeDTO()))).andExpect(status().isNotFound());
	}

	@Test
	@Order(3)
	public void testCriarHospede() throws JsonProcessingException, Exception {
		var hospedeDTO = new HospedeDTO();
		hospedeDTO.setDocumento(12345678L);
		hospedeDTO.setNome("Pedro");
		hospedeDTO.setTelefone(4799999998L);
		mockMvc.perform(post("/hospede/novo").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(hospedeDTO))).andExpect(status().isOk());
	}

	@Test
	@Order(4)
	public void testCriarReserva() throws Exception {
		var reservaDTO = new ReservaDTO();
		reservaDTO.setIdHospede(2L);
		reservaDTO.setDataCheckin(LocalDate.now());

		mockMvc.perform(post("/reserva/criar").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(reservaDTO))).andExpect(status().isOk())
				.andExpect(jsonPath("$.statusReserva", is("RESERVADO")));
	}

	@Test
	@Order(5)
	public void testCheckin() throws Exception {
		var hospede = new HospedeDTO();
		hospede.setDocumento(1020304050L);
		when(dataProvider.dateTime()).thenReturn(LocalDateTime.of(LocalDate.now(), LocalTime.MAX));
		mockMvc.perform(put("/reserva/checkin").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(hospede))).andExpect(status().isOk())
				.andExpect(jsonPath("$.statusReserva", is("CHECKIN")));
	}

	@Test
	@Order(6)
	public void testCheckout() throws Exception {
		mockMvc.perform(put("/reserva/1/checkout").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.statusReserva", is("CHECKOUT")));
	}

	@BeforeAll
	void setup() {
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
		horario.setCheckin(0);
		horario.setCheckout(12);
		horarioRepository.save(horario);

		var hospede = new Hospede();
		hospede.setDocumento(1020304050L);
		hospede.setNome("Amilton");
		hospede.setTelefone(4799999990L);
		hospedeRepository.save(hospede);

		var reserva = new Reserva();
		reserva.setDataCheckin(LocalDate.now());
		reserva.setIdDiaria(1L);
		reserva.setIdHospede(1L);
		reserva.setStatusReserva(StatusReserva.RESERVADO);
		reserva.setValorTotal(120D);
		reservaRepository.save(reserva);
	}
}
