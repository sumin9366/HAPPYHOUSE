package com.mycom.happyhouse.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CommentParamDto {
	
	private int limit;
	private int offset;
	private int boardId;
	private int commentId;
	private String userId;
	
	
	
}
