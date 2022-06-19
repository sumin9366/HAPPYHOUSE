package com.mycom.happyhouse.service;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.mycom.happyhouse.dto.CommentDto;
import com.mycom.happyhouse.dto.CommentParamDto;
import com.mycom.happyhouse.dto.CommentResultDto;

public interface CommentService{

	public CommentResultDto commentDetail(CommentParamDto CommentParamDto);

	public CommentResultDto commentDelete(int CommentId);

	public CommentResultDto commentUpdate(CommentDto dto, MultipartHttpServletRequest request);

	public CommentResultDto commentInsert(CommentDto dto, MultipartHttpServletRequest request);

	public CommentResultDto commentList(CommentParamDto CommentParamDto);


}
