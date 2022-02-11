package com.Spring.Webflux.Handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.Spring.Webflux.dao.EmployeeDao;
import com.Spring.Webflux.dto.EmployeeDTO;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class EmployeeHandler {
	
	@Autowired
	private EmployeeDao empDao;
	
	public Mono<ServerResponse> loadEmployees(ServerRequest request) {
		
		Flux<EmployeeDTO> empDto = empDao.getEmployeeWithoutDelay();
		return ServerResponse.ok()
				.contentType(MediaType.TEXT_EVENT_STREAM)
				.body(empDto, EmployeeDTO.class);
	}
	
	public Mono<ServerResponse> loadEmployeeById(ServerRequest request) {
		int empId = Integer.valueOf(request.pathVariable("input"));
		Mono<EmployeeDTO> empDto = empDao.getEmployeeWithoutDelay().filter(e -> e.getId() == empId).next(); // in place of next we can also write take(1).single()
		return ServerResponse.ok()
				.body(empDto, EmployeeDTO.class);
	}
	
	public Mono<ServerResponse> saveEmployees(ServerRequest request) {
		Mono<EmployeeDTO> empDto = request.bodyToMono(EmployeeDTO.class);
		Mono<String> savedResponse = empDto.map(dto -> dto.getId() + " " + dto.getName());
		return ServerResponse.ok()
				.body(savedResponse, String.class);
	}

}
