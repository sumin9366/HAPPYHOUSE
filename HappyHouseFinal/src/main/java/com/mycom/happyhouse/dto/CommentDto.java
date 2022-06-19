package com.mycom.happyhouse.dto;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
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
public class CommentDto {
	private int boardId;
	private int commentId;
	private String userId;
	private String userName;
	private String commentContent;
	private LocalDateTime commentRegDt;

	private boolean sameUser;
	


	
	public void setRegDt(Date regDt) {
		this.commentRegDt = LocalDateTime.ofInstant(
				regDt.toInstant(), ZoneId.systemDefault()
		);	// for Mybatis Date Mapping
	}
	
}

