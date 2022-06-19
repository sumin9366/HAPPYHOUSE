package com.mycom.happyhouse.service;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.mycom.happyhouse.dto.BoardDto;
import com.mycom.happyhouse.dto.BoardParamDto;
import com.mycom.happyhouse.dto.BoardResultDto;



public interface BoardService {
	
	public BoardResultDto boardDetail(BoardParamDto boardParamDto);
	
	public BoardResultDto boardDelete(int boardId);
	
	public BoardResultDto boardUpdate(BoardDto dto, MultipartHttpServletRequest request);

	public BoardResultDto boardInsert(BoardDto dto, MultipartHttpServletRequest request);

	public BoardResultDto boardList(BoardParamDto boardParamDto);
	//public int boardListTotalCount();	
	public BoardResultDto boardListSearchWord(BoardParamDto boardParamDto);
	//public int boardListSearchWordTotalCount(boardParamDto boardParamDto);

}
