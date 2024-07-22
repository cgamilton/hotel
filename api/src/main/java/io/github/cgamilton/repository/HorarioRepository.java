package io.github.cgamilton.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.github.cgamilton.entity.Horario;

@Repository
public interface HorarioRepository extends JpaRepository<Horario, Long> {

}
