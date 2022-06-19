package com.mycom.happyhouse.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.mycom.happyhouse.dao.UserDao;
import com.mycom.happyhouse.dto.UserDto;
import com.mycom.happyhouse.dto.UserResultDto;

@Service
@Primary
public class UserServiceImpl implements UserService{
	
	@Autowired
	UserDao dao;
	
	@Override
	public List<UserDto> list() {
		
		return dao.list();
	}

	@Override
	public UserDto detail(String userId) {
		// TODO Auto-generated method stub
		return dao.detail(userId);
	}

//	@Override
//	//@Transactional
//	public int insert(UserDto dto) {
//		dao.insert(dto);
//		String str = null;
//		str.length();
//		return dao.insert(dto);
//	}

	@Override
	public int update(UserDto dto) {
//		dto.setUserPw(dto.getUserPw().hashCode()+"");
		return dao.update(dto);
	}

	@Override
	public int delete(String userId) {
		// TODO Auto-generated method stub
		return dao.delete(userId);
	}

	private static final int SUCCESS = 1;
	private static final int FAIL = -1;
	
	@Override
	public UserResultDto register(UserDto userDto) {
		UserResultDto userResultDto = new UserResultDto();
//		userDto.setUserPw(userDto.getUserPw().hashCode()+"");
		if( dao.register(userDto) == 1 ) {
			userResultDto.setResult(SUCCESS);
		}else {
			userResultDto.setResult(FAIL);
		}
		
		return userResultDto;
	}
	

}
