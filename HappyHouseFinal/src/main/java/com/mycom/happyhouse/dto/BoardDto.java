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
public class BoardDto {
	private int boardId;
	private String boardClsf;
	private String userId;
	private String userName;
	private String boardTitle;
	private String boardContent;
	private LocalDateTime boardRegDt;
	private int boardReadCount;

	private boolean sameUser;
	
	private List<BoardFileDto> fileList;

	
//	public void setUserProfileImageUrl(String userProfileImageUrl) {
//		
//		if( userProfileImageUrl == null || "null".equals(userProfileImageUrl) || "".equals(userProfileImageUrl)) {
//			this.userProfileImageUrl = "/img/noProfile.png";
//		}else {
//			this.userProfileImageUrl = userProfileImageUrl;
//		}
//	}
	
	public void setRegDt(Date regDt) {
		this.boardRegDt = LocalDateTime.ofInstant(
				regDt.toInstant(), ZoneId.systemDefault()
		);	// for Mybatis Date Mapping
	}
	
}

