package io.github.cgamilton.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.cgamilton.dto.HospedeDTO;
import io.github.cgamilton.service.HospedeService;
import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/hospede")
public class HospedeController {

	@Autowired
	HospedeService hospedeService;

	@PostMapping("/novo")
	public void novoHospede(@Valid @RequestBody HospedeDTO hospedeDTO) {
		hospedeService.novoHospede(hospedeDTO);
	}
	@GetMapping("/retornaHospede")
	public HospedeDTO retornaHospede(@RequestBody HospedeDTO hospedeDTO) {
		return hospedeService.retornaHospede(hospedeDTO);
	}
}
