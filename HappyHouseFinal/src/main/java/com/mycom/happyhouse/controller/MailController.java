package com.mycom.happyhouse.controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mycom.happyhouse.dto.UserDto;
import com.mycom.happyhouse.service.MailServiceImpl;

import lombok.AllArgsConstructor;


@AllArgsConstructor
@RestController
@CrossOrigin(
	    // localhost:5500 과 127.0.0.1 구분
	    origins = "http://localhost:5500", // allowCredentials = "true" 일 경우, orogins="*" 는 X
	    allowCredentials = "true", 
	    allowedHeaders = "*", 
	    methods = {RequestMethod.GET,RequestMethod.POST,RequestMethod.DELETE,RequestMethod.PUT,RequestMethod.HEAD,RequestMethod.OPTIONS}
	)
public class MailController {
    private final MailServiceImpl mailService;

    @GetMapping("/mail")
    public String dispMail() {
        return "mail";
    }

    @PostMapping("/mail")
    public void execMail(@RequestBody UserDto dto) {
    	System.out.println("컨트롤러:"+ dto);
        mailService.mailSend(dto);
    }
}