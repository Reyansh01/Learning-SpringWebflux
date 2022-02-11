package com.Spring.Webflux.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Spring.Webflux.dao.EmployeeDao;
import com.Spring.Webflux.dto.EmployeeDTO;

import reactor.core.publisher.Flux;

@Service
public class EmployeeService {
	
	@Autowired
	EmployeeDao empDao;
	
	public Flux<EmployeeDTO> getDetails() {
		long start = System.currentTimeMillis();
		Flux<EmployeeDTO> employees = empDao.getEmployee();
		long end = System.currentTimeMillis();
		System.out.println("Total execution time is: " + (end-start));
		return employees;		
	}

}
