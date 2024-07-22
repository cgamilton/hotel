package io.github.cgamilton.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.github.cgamilton.entity.Diaria;

@Repository
public interface DiariaRepository extends JpaRepository<Diaria, Long> {

}
