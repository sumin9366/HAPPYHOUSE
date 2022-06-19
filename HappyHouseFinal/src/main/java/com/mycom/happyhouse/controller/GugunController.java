package com.mycom.happyhouse.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mycom.happyhouse.entity.Gugun;
import com.mycom.happyhouse.service.GugunService;

@RestController
@CrossOrigin(
	// localhost:5500 과 127.0.0.1 구분
	origins = "http://localhost:5500", // allowCredentials = "true" 일 경우, orogins="*" 는 X
	allowCredentials = "true", 
	allowedHeaders = "*", 
	methods = {RequestMethod.GET,RequestMethod.POST,RequestMethod.DELETE,RequestMethod.PUT,RequestMethod.HEAD,RequestMethod.OPTIONS}
)
public class GugunController {
	
	@Autowired
	GugunService service;
	
	@GetMapping(value = "/guguns")
	public List<Gugun> findBySidoCode(String sidoCode) {
		return service.findBySidoCode(sidoCode);
	}
}
