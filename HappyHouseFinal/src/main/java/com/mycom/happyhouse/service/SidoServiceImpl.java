package com.mycom.happyhouse.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycom.happyhouse.dao.SidoDao;
import com.mycom.happyhouse.entity.Sido;

@Service
public class SidoServiceImpl implements SidoService{
	@Autowired
	SidoDao dao;
	
	@Override
	public List<Sido> list() {
		
		return dao.findAll();
	}

}
