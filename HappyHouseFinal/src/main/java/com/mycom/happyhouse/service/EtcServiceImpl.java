//package com.mycom.happyhouse.service;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.mycom.happyhouse.dao.EtcDao;
//import com.mycom.happyhouse.dto.DongDto;
//import com.mycom.happyhouse.dto.GugunDto;
//import com.mycom.happyhouse.dto.SidoDto;
//
//@Service
//public class EtcServiceImpl implements EtcService{
//
//@Autowired
//EtcDao dao;
//
//@Override
//public List<SidoDto> sidoList() {
//    return dao.sidoList();
//}
//
//@Override
//public List<GugunDto> gugunList(String sidoCode) {
//    return dao.gugunList(sidoCode);
//}
//
//@Override
//public List<DongDto> dongList(String gugunCode) {
//    return dao.dongList(gugunCode);
//}
//}