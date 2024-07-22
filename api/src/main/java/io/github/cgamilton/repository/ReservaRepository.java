package io.github.cgamilton.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import io.github.cgamilton.entity.Reserva;
import io.github.cgamilton.enumeration.StatusReserva;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {
	@Query("SELECT e FROM Reserva e where e.dataCheckin = ?1 and idHospede = ?2")
	public Optional<Reserva> findReserva(LocalDate dataCheckin, Long idHospede);
	
	@Query("SELECT e FROM Reserva e where e.statusReserva =?1")
	public List<Reserva> findReservaPorStatu(StatusReserva statusReserva);
}
