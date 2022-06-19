package com.mycom.happyhouse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mycom.happyhouse.dto.BookmarkAptDto;
import com.mycom.happyhouse.dto.HouseinfoParamDto;
import com.mycom.happyhouse.dto.HouseinfoResultDto;
import com.mycom.happyhouse.service.HouseService;

@RestController
@CrossOrigin(
	// localhost:5500 과 127.0.0.1 구분
	origins = "http://localhost:5500", // allowCredentials = "true" 일 경우, orogins="*" 는 X
	allowCredentials = "true", 
	allowedHeaders = "*", 
	methods = {RequestMethod.GET,RequestMethod.POST,RequestMethod.DELETE,RequestMethod.PUT,RequestMethod.HEAD,RequestMethod.OPTIONS}
)
public class HouseController {

	@Autowired
	HouseService service;
	
	private static final int SUCCESS = 1;
	
	@GetMapping(value = "/houses")
	public ResponseEntity<HouseinfoResultDto> list(HouseinfoParamDto houseinfoParamDto) {
		HouseinfoResultDto houseinfoResultDto;
		
		houseinfoResultDto = service.list(houseinfoParamDto);
		
		if (houseinfoResultDto.getResult() == SUCCESS) {
			return new ResponseEntity<HouseinfoResultDto>(houseinfoResultDto, HttpStatus.OK);
		} else {
			return new ResponseEntity<HouseinfoResultDto>(houseinfoResultDto, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(value = "/houses/search")
	private ResponseEntity<HouseinfoResultDto> search(HouseinfoParamDto houseinfoParamDto) {
		HouseinfoResultDto houseinfoResultDto;
		
		String bookmark = houseinfoParamDto.getBookmark();
		
		if (bookmark != null && !bookmark.isEmpty()) {
			if (bookmark.equals("apt")) {
				houseinfoResultDto = service.listByBookmarkApt(houseinfoParamDto);
			} else {
				houseinfoResultDto = service.listByBookmarkDong(houseinfoParamDto);
			}
		} else {
			houseinfoResultDto = service.search(houseinfoParamDto);
		}
		
		if (houseinfoResultDto.getResult() == SUCCESS) {
			return new ResponseEntity<HouseinfoResultDto>(houseinfoResultDto, HttpStatus.OK);
		} else {
			return new ResponseEntity<HouseinfoResultDto>(houseinfoResultDto, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(value = "/houses/myhouse")
	private ResponseEntity<HouseinfoResultDto> myhouse(HouseinfoParamDto houseinfoParamDto) {
		HouseinfoResultDto houseinfoResultDto;
		
		houseinfoResultDto = service.myhouse(houseinfoParamDto);
		
		if (houseinfoResultDto.getResult() == SUCCESS) {
			return new ResponseEntity<HouseinfoResultDto>(houseinfoResultDto, HttpStatus.OK);
		} else {
			return new ResponseEntity<HouseinfoResultDto>(houseinfoResultDto, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(value = "/houses/detail")
	public ResponseEntity<HouseinfoResultDto> detail(HouseinfoParamDto houseinfoParamDto) {
		HouseinfoResultDto houseinfoResultDto;
		
		houseinfoResultDto = service.detail(houseinfoParamDto);
		
		if (houseinfoResultDto.getResult() == SUCCESS) {
			return new ResponseEntity<HouseinfoResultDto>(houseinfoResultDto, HttpStatus.OK);
		} else {
			return new ResponseEntity<HouseinfoResultDto>(houseinfoResultDto, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping(value = "/bookmark/apt")
	public ResponseEntity<HouseinfoResultDto> insertBookmarkApt(@RequestBody BookmarkAptDto bookmarkAptDto) {
		HouseinfoResultDto houseinfoResultDto;
		
		houseinfoResultDto = service.insertBookmarkApt(bookmarkAptDto);
		
		if (houseinfoResultDto.getResult() == SUCCESS) {
			return new ResponseEntity<HouseinfoResultDto>(houseinfoResultDto, HttpStatus.OK);
		} else {
			return new ResponseEntity<HouseinfoResultDto>(houseinfoResultDto, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@DeleteMapping(value = "/bookmark/apt/{userId}/{aptCode}")
	public ResponseEntity<HouseinfoResultDto> deleteBookmarkApt(@PathVariable String userId, @PathVariable long aptCode) {
		BookmarkAptDto bookmarkAptDto = new BookmarkAptDto();
		bookmarkAptDto.setUserId(userId);
		bookmarkAptDto.setAptCode(aptCode);
		
		HouseinfoResultDto houseinfoResultDto;
		
		houseinfoResultDto = service.deleteBookmarkApt(bookmarkAptDto);
		
		if (houseinfoResultDto.getResult() == SUCCESS) {
			return new ResponseEntity<HouseinfoResultDto>(houseinfoResultDto, HttpStatus.OK);
		} else {
			return new ResponseEntity<HouseinfoResultDto>(houseinfoResultDto, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
