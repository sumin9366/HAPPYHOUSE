package com.mycom.happyhouse.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.mycom.happyhouse.dao.UserDao;
import com.mycom.happyhouse.dto.UserDto;
import com.mycom.happyhouse.dto.UserResultDto;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MailServiceImpl {
    private JavaMailSender mailSender;
    private static final String FROM_ADDRESS = "sumin9366@naver.com";
    

	@Autowired
	UserDao dao;

    public void mailSend(UserDto dto) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(dto.getUserEmail());	//받는사람주소
        message.setFrom(MailServiceImpl.FROM_ADDRESS);	//보내는사람주소
        message.setSubject("HAPPYHOUSE에서 보낸 메시지 입니다.");	//제목
        message.setText(dto.getUserName()+"님 비밀번호는"+ dao.getPw(dto.getUserEmail())+"입니다!!!");	//메시지내용

        mailSender.send(message);
    }

	
}
