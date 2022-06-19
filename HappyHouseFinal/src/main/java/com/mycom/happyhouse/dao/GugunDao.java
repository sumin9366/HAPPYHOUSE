package com.mycom.happyhouse.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mycom.happyhouse.entity.Gugun;


public interface GugunDao extends JpaRepository<Gugun, Integer>{
	
//	@Query(value="select g from Gugun g where g.sido_code = :sidoCode")
	public List<Gugun> findBySido_SidoCodeOrderByGugunName(String sidoCode);
	
	
}
