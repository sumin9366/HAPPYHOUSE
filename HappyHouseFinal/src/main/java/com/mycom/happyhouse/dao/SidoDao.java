package com.mycom.happyhouse.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mycom.happyhouse.entity.Sido;


public interface SidoDao extends JpaRepository<Sido, Integer> {
	public List<Sido> findAll();
	
}
