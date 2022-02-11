package com.Spring.Webflux.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Spring.Webflux.dto.EmployeeDTO;
import com.Spring.Webflux.service.EmployeeService;

import reactor.core.publisher.Flux;

@RestController
public class FluxController {
	
	@Autowired
	EmployeeService empService;
	
	@GetMapping(value = "/flux", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<EmployeeDTO> getAllEmployees() {
		return empService.getDetails();
	}

}
