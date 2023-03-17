package br.com.desafio_neki.exceptions;

public class NoSuchElementException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public NoSuchElementException(String message) {
		super(message);
	}
}