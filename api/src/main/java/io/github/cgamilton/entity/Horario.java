package io.github.cgamilton.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
@Entity
public class Horario {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column
	private int checkin;
	@Column
	private int checkout;
	public int getCheckin() {
		return checkin;
	}
	public void setCheckin(int checkin) {
		this.checkin = checkin;
	}
	public int getCheckout() {
		return checkout;
	}
	public void setCheckout(int checkout) {
		this.checkout = checkout;
	}
	
}
