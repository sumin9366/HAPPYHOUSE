package com.mycom.happyhouse.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mycom.happyhouse.entity.Dong;

public interface DongDao extends JpaRepository<Dong, Integer>{
	public List<Dong> findByGugun_GugunCodeOrderByDongName(String gugunCode);//구군코드\
	
}
