package io.github.cgamilton.entity;

import io.github.cgamilton.enumeration.TipoDiaria;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "diaria")
public class Diaria {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column
	private TipoDiaria tipoDiaria;
	@Column
	private Double valorDiaria;
	@Column
	private Double taxaGaragem;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TipoDiaria getTipoDiaria() {
		return tipoDiaria;
	}

	public void setTipoDiaria(TipoDiaria tipoDiaria) {
		this.tipoDiaria = tipoDiaria;
	}

	public Double getValorDiaria() {
		return valorDiaria;
	}

	public void setValorDiaria(Double valorDiaria) {
		this.valorDiaria = valorDiaria;
	}

	public Double getTaxaGaragem() {
		return taxaGaragem;
	}

	public void setTaxaGaragem(Double taxaGaragem) {
		this.taxaGaragem = taxaGaragem;
	}

}
