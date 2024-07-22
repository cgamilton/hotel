package io.github.cgamilton.core.exception;

public class HospedeNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String NOT_FOUND = "Hóspede não cadastrado.";

	public HospedeNotFoundException() {
		super(NOT_FOUND);
	}
}