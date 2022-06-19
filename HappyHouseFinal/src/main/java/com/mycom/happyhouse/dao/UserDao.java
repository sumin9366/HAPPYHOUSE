package com.mycom.happyhouse.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mycom.happyhouse.dto.UserDto;

@Mapper
public interface UserDao {
	// 회원 조회
	List<UserDto> list();
	// 상세 조회
	UserDto detail(String userId);
	// 회원 등록
	int register(UserDto dto);
	// 회원 수정
	int update(UserDto dto);
	// 회원 삭제
	int delete(String userId);
	
	String getPw(String userEmail);
	
}
