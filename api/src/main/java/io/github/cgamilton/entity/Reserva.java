package io.github.cgamilton.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import io.github.cgamilton.enumeration.StatusReserva;
import io.github.cgamilton.enumeration.TipoDiaria;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "reserva")
public class Reserva {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column
	@NotNull
	private Long idHospede;
	@Column
	private Long idDiaria;
	@Column
	private StatusReserva statusReserva;
	@NotNull
	@Column
	private LocalDate dataCheckin;
	@Column
	private LocalDateTime dataCheckout;
	@Column
	private boolean taxaGaragem;
	@Column
	private Double valorTotal;
	@Column
	private Double valorFinalCobrado;
	@Column
	private Double valorAcrescimoCheckout;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdHospede() {
		return idHospede;
	}

	public void setIdHospede(Long idHospede) {
		this.idHospede = idHospede;
	}

	public Long getIdDiaria() {
		return idDiaria;
	}

	public void setIdDiaria(Long idDiaria) {
		this.idDiaria = idDiaria;
	}

	public StatusReserva getStatusReserva() {
		return statusReserva;
	}

	public void setStatusReserva(StatusReserva statusReserva) {
		this.statusReserva = statusReserva;
	}

	public LocalDate getDataCheckin() {
		return dataCheckin;
	}

	public void setDataCheckin(LocalDate dataCheckin) {
		this.dataCheckin = dataCheckin;
	}

	public LocalDateTime getDataCheckout() {
		return dataCheckout;
	}

	public void setDataCheckout(LocalDateTime dataCheckout) {
		this.dataCheckout = dataCheckout;
	}

	public boolean isTaxaGaragem() {
		return taxaGaragem;
	}

	public void setTaxaGaragem(boolean taxaGaragem) {
		this.taxaGaragem = taxaGaragem;
	}

	public Double getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(Double valorTotal) {
		this.valorTotal = valorTotal;
	}

	public Double getValorFinalCobrado() {
		return valorFinalCobrado;
	}

	public void setValorFinalCobrado(Double valorFinalCobrado) {
		this.valorFinalCobrado = valorFinalCobrado;
	}

	public Double getValorAcrescimoCheckout() {
		return valorAcrescimoCheckout;
	}

	public void setValorAcrescimoCheckout(Double valorAcrescimoCheckout) {
		this.valorAcrescimoCheckout = valorAcrescimoCheckout;
	}

}
