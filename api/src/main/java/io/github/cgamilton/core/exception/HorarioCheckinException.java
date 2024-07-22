package io.github.cgamilton.core.exception;

public class HorarioCheckinException extends RuntimeException{

	private static final String HORARIO = "O checkin deve ser realizado a partir das 14h.";
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public HorarioCheckinException() {
		super(HORARIO);
	}
}
