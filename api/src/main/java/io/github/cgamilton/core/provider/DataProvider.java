package io.github.cgamilton.core.provider;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
@Service
public class DataProvider {

	public LocalDate date() {
		return LocalDate.now();
	}

	public LocalDateTime dateTime() {
		return LocalDateTime.now();
	}
}
