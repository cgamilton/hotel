package io.github.cgamilton.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.cgamilton.dto.HospedeDTO;
import io.github.cgamilton.dto.ReservaDTO;
import io.github.cgamilton.service.ReservaService;
import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/reserva")
public class ReservaController {

	@Autowired
	ReservaService reservaService;
	
	@PostMapping("/criar")
	public ReservaDTO criarReserva(@Valid @RequestBody ReservaDTO reservaDTO) {
		return reservaService.criarReserva(reservaDTO);
	}
	@GetMapping("/listar")
	public List<ReservaDTO> listarTodas(){
		return reservaService.listarTodas();
	}
	
	@PutMapping("/checkin")
	public ReservaDTO checkin(@RequestBody HospedeDTO dto){
		return reservaService.checkin(dto);
	}
	
	@PutMapping("/{idReserva}/checkout")
	public ReservaDTO checkout(@PathVariable Long idReserva) {
		return reservaService.checkout(idReserva);
	}
}
