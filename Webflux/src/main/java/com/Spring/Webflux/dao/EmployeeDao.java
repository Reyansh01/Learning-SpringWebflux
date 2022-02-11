package com.Spring.Webflux.dao;

import java.time.Duration;

import org.springframework.stereotype.Component;

import com.Spring.Webflux.dto.EmployeeDTO;

import reactor.core.publisher.Flux;

@Component
public class EmployeeDao {

	public Flux<EmployeeDTO> getEmployee() {
		
		return Flux.range(1, 20)
				.delayElements(Duration.ofSeconds(1))
				.doOnNext(i -> System.out.println("processing count number is : " + i))
				.map(i -> new EmployeeDTO(i, "employee" + i));
		
	}
	
	public Flux<EmployeeDTO> getEmployeeWithoutDelay() {
		
		return Flux.range(1, 20)
				.doOnNext(i -> System.out.println("processing count number is : " + i))
				.map(i -> new EmployeeDTO(i, "employee" + i));
		
	}
	
}
