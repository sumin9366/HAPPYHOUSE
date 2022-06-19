package com.mycom.happyhouse.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mycom.happyhouse.dto.CommentDto;
import com.mycom.happyhouse.dto.CommentParamDto;



@Mapper
public interface CommentDao {
	
	public CommentDto commentDetail(CommentParamDto commentParamDto);
	
	
	public int commentDelete(int commentId);
	public int commentDeleteAll(int boardId);
	
	public int commentInsert(CommentDto dto);
	
	public List<CommentDto> commentList(CommentParamDto commentParamDto);
	public int commentListTotalCount(CommentParamDto commentParamDto);
	
	

	public int commentUpdate(CommentDto dto);
	

	
}
