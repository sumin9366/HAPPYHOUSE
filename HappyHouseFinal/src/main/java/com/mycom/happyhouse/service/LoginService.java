package com.mycom.happyhouse.service;

import org.apache.ibatis.annotations.Mapper;

import com.mycom.happyhouse.dto.UserDto;

@Mapper
public interface LoginService {
	public UserDto login(UserDto dto);
}
