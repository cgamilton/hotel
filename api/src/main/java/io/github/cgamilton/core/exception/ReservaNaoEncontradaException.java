package io.github.cgamilton.core.exception;

public class ReservaNaoEncontradaException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String RESERVA_NAO_ENCONTRADA = "Reserva não encontrada para o hóspede informado.";

	public ReservaNaoEncontradaException() {
		super(RESERVA_NAO_ENCONTRADA);
	}
}
