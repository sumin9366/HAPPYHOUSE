package com.mycom.happyhouse.service;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mycom.happyhouse.dto.UserDto;
import com.mycom.happyhouse.dto.UserResultDto;

@Mapper
public interface UserService {
	// 회원 조회
	List<UserDto> list();
	// 상세 조회
	UserDto detail(String userId);
	// 회원 등록
	UserResultDto register(UserDto dto);
	// 회원 수정
	int update(UserDto dto);
	// 회원 삭제
	int delete(String userId);
	
}
