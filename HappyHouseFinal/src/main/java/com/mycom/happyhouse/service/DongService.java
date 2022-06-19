package com.mycom.happyhouse.service;

import java.util.List;

import com.mycom.happyhouse.entity.Dong;

public interface DongService {
	
	public List<Dong> findByGugunCode(String gugunCode);//구군코드
	
}
