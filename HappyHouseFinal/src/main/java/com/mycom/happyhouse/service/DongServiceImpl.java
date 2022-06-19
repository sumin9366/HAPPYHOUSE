package com.mycom.happyhouse.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycom.happyhouse.dao.DongDao;
import com.mycom.happyhouse.entity.Dong;


@Service
public class DongServiceImpl implements DongService{
	
	@Autowired
	DongDao dao;
	
	@Override
	public List<Dong> findByGugunCode(String gugunCode)//구군코드
	{
		return dao.findByGugun_GugunCodeOrderByDongName(gugunCode);
	}
}
