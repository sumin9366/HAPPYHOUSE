package com.mycom.happyhouse.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycom.happyhouse.dao.GugunDao;
import com.mycom.happyhouse.dao.SidoDao;
import com.mycom.happyhouse.entity.Gugun;

@Service
public class GugunServiceImpl implements GugunService{

	@Autowired
	GugunDao dao;
	@Autowired
	SidoDao sidoDao;
	
	@Override
	public List<Gugun> findBySidoCode(String sidoCode) {
		
		//System.out.println(sidoCode);
		
		return dao.findBySido_SidoCodeOrderByGugunName(sidoCode);
	}

}
