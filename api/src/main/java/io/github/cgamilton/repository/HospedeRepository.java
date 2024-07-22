package io.github.cgamilton.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import io.github.cgamilton.entity.Hospede;

@Repository
public interface HospedeRepository extends JpaRepository<Hospede, Long> {

	@Query("SELECT e FROM Hospede e WHERE e.nome = ?1 OR e.documento = ?2 OR e.telefone = ?3")
	public Optional<Hospede>  findHospede(String nome, Long documento, Long telefone);
}
