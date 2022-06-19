package com.mycom.happyhouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.mycom.happyhouse.dao.LoginDao;
import com.mycom.happyhouse.dto.UserDto;

@Service
@Primary
public class LoginServiceImpl implements LoginService{
	
	@Autowired
	LoginDao loginDao;
	
	@Override
	public UserDto login(UserDto dto) {
		//dto.setUserPw(dto.getUserPw().hashCode()+"");
		UserDto userDto = loginDao.login(dto.getUserId());
		
		if( userDto != null && userDto.getUserPw().equals(dto.getUserPw())) {
			return userDto;
		}else {
			return null;
		}
	}

}