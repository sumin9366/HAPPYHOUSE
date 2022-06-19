package com.mycom.happyhouse.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.mycom.happyhouse.dto.CommentDto;
import com.mycom.happyhouse.dto.CommentParamDto;
import com.mycom.happyhouse.dto.CommentResultDto;
import com.mycom.happyhouse.dto.UserDto;
import com.mycom.happyhouse.service.CommentService;



@RestController
@CrossOrigin(
	    // localhost:5500 과 127.0.0.1 구분
	    origins = "http://localhost:5500", // allowCredentials = "true" 일 경우, orogins="*" 는 X
	    allowCredentials = "true", 
	    allowedHeaders = "*", 
	    methods = {RequestMethod.GET,RequestMethod.POST,RequestMethod.DELETE,RequestMethod.PUT,RequestMethod.HEAD,RequestMethod.OPTIONS}
	)
public class CommentController {

	@Autowired
	CommentService service;
	
	private static final int SUCCESS = 1;
	
	@GetMapping(value="/comments")
	private ResponseEntity<CommentResultDto> commentList(CommentParamDto commentParamDto){
		
		CommentResultDto commentResultDto;
		commentResultDto = service.commentList(commentParamDto);
		
		
		if( commentResultDto.getResult() == SUCCESS ) {
			return new ResponseEntity<CommentResultDto>(commentResultDto, HttpStatus.OK);
		}else {
			return new ResponseEntity<CommentResultDto>(commentResultDto, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	
	@GetMapping(value="/comments/{commentId}")
	private ResponseEntity<CommentResultDto> commentDetail(@PathVariable int commentId, HttpSession session){

		CommentParamDto commentParamDto = new CommentParamDto();
		commentParamDto.setCommentId(commentId);
		commentParamDto.setUserId( ((UserDto) session.getAttribute("userDto")).getUserId());
		System.out.println(commentParamDto);
		CommentResultDto commentResultDto = service.commentDetail(commentParamDto);
//		System.out.println(commentResultDto);
		if( commentResultDto.getResult() == SUCCESS ) {
			return new ResponseEntity<CommentResultDto>(commentResultDto, HttpStatus.OK);
		}else {
			return new ResponseEntity<CommentResultDto>(commentResultDto, HttpStatus.INTERNAL_SERVER_ERROR);
		}		 
	}
	
	@PostMapping(value="/comments")
	private ResponseEntity<CommentResultDto> commentInsert(
			CommentDto commentDto, 
			MultipartHttpServletRequest request) {
		
		// LoginFilter 먼저 적용 필요!!
		HttpSession session = request.getSession();
		UserDto userDto = (UserDto) session.getAttribute("userDto");
		
		commentDto.setUserId(userDto.getUserId());
		System.out.println(commentDto);
		CommentResultDto commentResultDto = service.commentInsert(commentDto, request);
		
		if( commentResultDto.getResult() == SUCCESS ) {
			return new ResponseEntity<CommentResultDto>(commentResultDto, HttpStatus.OK);
		}else {
			return new ResponseEntity<CommentResultDto>(commentResultDto, HttpStatus.INTERNAL_SERVER_ERROR);
		}		 
	}
	
	// PUT + multipart/form-data (X)
	// In RESTful,
	// PUT & DELETE methods are defined to be idempotent
	// 만약 글만 수정한다면?? PUT mapping OK  여러 번 수정해도 back-end 결과가 같음
	// 첨부파일이 포함되면 idempotent 하지 않음.
	
	@PostMapping(value="/comments/{commentId}") 
	private ResponseEntity<CommentResultDto> commentUpdate(
			CommentDto commentDto, 
			MultipartHttpServletRequest request){

		HttpSession session = request.getSession();
		UserDto userDto = (UserDto) session.getAttribute("userDto");
		
		commentDto.setUserId(userDto.getUserId());
		
		CommentResultDto commentResultDto = service.commentUpdate(commentDto, request);
		
		if( commentResultDto.getResult() == SUCCESS ) {
			return new ResponseEntity<CommentResultDto>(commentResultDto, HttpStatus.OK);
		}else {
			return new ResponseEntity<CommentResultDto>(commentResultDto, HttpStatus.INTERNAL_SERVER_ERROR);
		}		
	}
	
	@DeleteMapping(value="/comments/{commentId}") 
	private ResponseEntity<CommentResultDto> commentDelete(@PathVariable int commentId){
		CommentResultDto commentResultDto = service.commentDelete(commentId);
		
		if( commentResultDto.getResult() == SUCCESS ) {
			return new ResponseEntity<CommentResultDto>(commentResultDto, HttpStatus.OK);
		}else {
			return new ResponseEntity<CommentResultDto>(commentResultDto, HttpStatus.INTERNAL_SERVER_ERROR);
		}		 
	}
}
