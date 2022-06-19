package com.mycom.happyhouse.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mycom.happyhouse.dto.UserDto;
import com.mycom.happyhouse.dto.UserResultDto;
import com.mycom.happyhouse.service.UserService;


@RestController
@CrossOrigin(
	    // localhost:5500 과 127.0.0.1 구분
	    origins = "http://localhost:5500", // allowCredentials = "true" 일 경우, orogins="*" 는 X
	    allowCredentials = "true", 
	    allowedHeaders = "*", 
	    methods = {RequestMethod.GET,RequestMethod.POST,RequestMethod.DELETE,RequestMethod.PUT,RequestMethod.HEAD,RequestMethod.OPTIONS}
	)
public class UserController {
	private static final int SUCCESS = 1;
	
	@Autowired
	UserService service;
	
	//목록
	@GetMapping(value="/users")
	//ResponseBody
	public List<UserDto> list(){
		List<UserDto> list = service.list();
		for (UserDto userDto : list) {
			System.out.println(userDto);
		}
		return list;
	}
	
	//상세
	//get /users/123
	@GetMapping(value="/users/{userId}")	
	//ResponseBody
	public UserDto detail( @PathVariable String userId){
		System.out.println("userId: " + userId);
		return service.detail(userId);
	}
	

	@PostMapping(value="/users")
	public ResponseEntity<Map<String, String>> register(@RequestBody UserDto dto){
		System.out.println("userDto: "+ dto);
		UserResultDto userResultDto = service.register(dto);
		Map<String, String> map = new HashMap<>();
		
		// 중복 확인 후 map.put("result", "duplicated"); 아직 미구현
		System.out.println(userResultDto.getResult());
		
		if( userResultDto.getResult() == SUCCESS ) {
			
			map.put("result", "success");
			return new ResponseEntity<Map<String, String>>(map, HttpStatus.OK);
		}else {
			map.put("result", "fail");
			return new ResponseEntity<Map<String, String>>(map, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	//수정
	// put /users dto... <= parameter로 put 구현부분확인
	@PutMapping(value="/users/{userId}")
	public ResponseEntity<Map<String, String>> update(@PathVariable String userId, UserDto dto) {
		System.out.println(dto);
		System.out.println(userId);
		int ret = service.update(dto);
		Map<String, String> map = new HashMap<>();
		if( ret == 1 ) {
			
			map.put("result", "success");
			return new ResponseEntity<Map<String, String>>(map, HttpStatus.OK);
		}else {
			map.put("result", "fail");
			return new ResponseEntity<Map<String, String>>(map, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	//삭제
	//delete 
	@DeleteMapping(value="/users/{userId}")
	public ResponseEntity<Map<String, String>> delete(@PathVariable String userId) {
		System.out.println(userId);
		int ret = service.delete(userId);
		Map<String, String> map = new HashMap<>();
		
		if( ret == 1 ) {
			
			map.put("result", "success");
			return new ResponseEntity<Map<String, String>>(map, HttpStatus.OK);
		}else {
			map.put("result", "fail");
			return new ResponseEntity<Map<String, String>>(map, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
