//package com.mycom.happyhouse.controller;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.mycom.happyhouse.dto.DongDto;
//import com.mycom.happyhouse.dto.GugunDto;
//import com.mycom.happyhouse.dto.SidoDto;
//import com.mycom.happyhouse.service.EtcService;
//
//@RestController
//public class EtcController {
//
//@Autowired
//EtcService service;
//
//@GetMapping(value="/sidos")
//public ResponseEntity<List<SidoDto>> sidoList(){
//    
//    List<SidoDto> list = service.sidoList();
//    return new ResponseEntity<List<SidoDto>>(list, HttpStatus.OK);
//}
//
//@GetMapping(value="/guguns/{sidoCode}")
//public ResponseEntity<List<GugunDto>> guguns(@PathVariable String sidoCode){
//    
//    List<GugunDto> list = service.gugunList(sidoCode);
//    return new ResponseEntity<List<GugunDto>>(list, HttpStatus.OK);
//}
//
//@GetMapping(value="/dongs/{gugunCode}")
//public ResponseEntity<List<DongDto>> dongs(@PathVariable String gugunCode){
//    
//    List<DongDto> list = service.dongList(gugunCode);
//    return new ResponseEntity<List<DongDto>>(list, HttpStatus.OK);
//}
//}