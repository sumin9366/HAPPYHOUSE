package com.mycom.happyhouse.dto;

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
public class CommentResultDto {
	private int result;
	private CommentDto dto;
	private List<CommentDto> list;
	private int count;
	

}
