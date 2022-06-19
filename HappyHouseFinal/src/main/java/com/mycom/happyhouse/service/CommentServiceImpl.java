package com.mycom.happyhouse.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.mycom.happyhouse.dao.CommentDao;
import com.mycom.happyhouse.dto.CommentDto;
import com.mycom.happyhouse.dto.CommentParamDto;
import com.mycom.happyhouse.dto.CommentResultDto;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	CommentDao dao;

	private static final int SUCCESS = 1;
	private static final int FAIL = -1;

	@Override
	@Transactional
	public CommentResultDto commentInsert(CommentDto dto, MultipartHttpServletRequest request) {

		dao.commentInsert(dto);
		CommentResultDto commentResultDto = new CommentResultDto();
		commentResultDto.setResult(SUCCESS);

		return commentResultDto;
	}

	@Override
	@Transactional
	public CommentResultDto commentUpdate(CommentDto dto, MultipartHttpServletRequest request) {

		CommentResultDto commentResultDto = new CommentResultDto();
		dao.commentUpdate(dto);

		commentResultDto.setResult(SUCCESS);
		return commentResultDto;
	}

	@Override
	@Transactional
	public CommentResultDto commentDelete(int commentId) {

		CommentResultDto commentResultDto = new CommentResultDto();

		try {
		
			dao.commentDelete(commentId);
			commentResultDto.setResult(SUCCESS);

		} catch (Exception e) {
			e.printStackTrace();
			commentResultDto.setResult(FAIL);
		}

		return commentResultDto;
	}

	@Override
	public CommentResultDto commentDetail(CommentParamDto commentParamDto) {

		CommentResultDto commentResultDto = new CommentResultDto();

		try {
		

			CommentDto commentDto = dao.commentDetail(commentParamDto);

			if (commentDto.getUserId().equals(commentParamDto.getUserId())) {
				commentDto.setSameUser(true);
			} else {
				commentDto.setSameUser(false);
			}

			commentResultDto.setDto(commentDto);

			commentResultDto.setResult(SUCCESS);

		} catch (Exception e) {
			e.printStackTrace();
			commentResultDto.setResult(FAIL);
		}

		return commentResultDto;
	}

	@Override
	public CommentResultDto commentList(CommentParamDto commentParamDto) {

		CommentResultDto commentResultDto = new CommentResultDto();

		try {
			List<CommentDto> list = dao.commentList(commentParamDto);
			int count = dao.commentListTotalCount(commentParamDto);
			commentResultDto.setList(list);
			commentResultDto.setCount(count);
			commentResultDto.setResult(SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			commentResultDto.setResult(FAIL);
		}

		return commentResultDto;
	}

	
	
}
