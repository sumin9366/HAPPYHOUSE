package com.mycom.happyhouse.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mycom.happyhouse.dto.UserDto;
import com.mycom.happyhouse.service.LoginService;



	@RestController
	@CrossOrigin(
		    // localhost:5500 과 127.0.0.1 구분
		    origins = "http://localhost:5500", // allowCredentials = "true" 일 경우, orogins="*" 는 X
		    allowCredentials = "true", 
		    allowedHeaders = "*", 
		    methods = {RequestMethod.GET,RequestMethod.POST,RequestMethod.DELETE,RequestMethod.PUT,RequestMethod.HEAD,RequestMethod.OPTIONS}
		)
	public class LoginController {
	
		@Autowired
		LoginService service;
		
		@PostMapping(value="/login")
		public ResponseEntity<Map<String, Object>> login(@RequestBody UserDto dto, HttpSession session){
			System.out.println("dto: "+ dto);
			UserDto userDto = service.login(dto);
			Map<String, Object> map = new HashMap<>();
			if( userDto != null ) {
				session.setAttribute("userDto", userDto);
				
			    map.put("result", "success"); //성공메시지
			    map.put("user", userDto);
			    System.out.println(userDto.getUserId());
				return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
			}
			map.put("result", "fail");
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.NOT_FOUND);
		}
	}


