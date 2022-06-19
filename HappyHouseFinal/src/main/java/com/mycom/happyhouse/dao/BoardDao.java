package com.mycom.happyhouse.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mycom.happyhouse.dto.BoardDto;
import com.mycom.happyhouse.dto.BoardFileDto;
import com.mycom.happyhouse.dto.BoardParamDto;



@Mapper
public interface BoardDao {
	
	public BoardDto boardDetail(BoardParamDto boardParamDto);
	public List<BoardFileDto> boardDetailFileList(int boardId);	//첨부파일여러개
	
	// map - Dto
	public int boardUserReadCount(BoardParamDto boardParamDto); 	//특정개시글 클릭->조회수
	
	// map - @param
	public int boardUserReadInsert(		//게시글을 아직 안읽음. 읽음표시
			@Param("boardId") int boardId, 
			@Param("userId") String userId ); 
	
	public int boardReadCountUpdate(int boardId);	//게시글을 아직 안봤은까 읽으면 조회수 +1
	
	
	public int boardDelete(int boardId);	
	public int boardFileDelete(int boardId);	//테이블에 있는 데이터 삭제
	public List<String> boardFileUrlDeleteList(int boardId);	//업로드폴더에 있는 삭제할 물리적인 파일 목록
	public int boardReadCountDelete(int boardId);
	
	public int boardInsert(BoardDto dto);
	public int boardFileInsert(BoardFileDto dto);
	
	public List<BoardDto> boardList(BoardParamDto boardParamDto);
	public int boardListTotalCount(BoardParamDto boardParamDto);
	
	public List<BoardDto> boardListSearchWord(BoardParamDto boardParamDto);
	public int boardListSearchWordTotalCount(BoardParamDto boardParamDto);
	

	public int boardUpdate(BoardDto dto);
	

	
}
