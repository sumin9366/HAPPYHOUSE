package com.mycom.happyhouse.service;

import java.util.List;

import com.mycom.happyhouse.entity.Gugun;

public interface GugunService {
	
	public List<Gugun> findBySidoCode(String sidoCode);
	
}
