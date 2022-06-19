package com.mycom.happyhouse.dto;



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
public class UserDto {
	private String userId;
	private String userPw;
	private String userName;
	private String userEmail;
	private String userPhone;
	private String userRegisterDate;
	private String userAddress;
	private long userAptCode;

	private String userClsf; // 회원 구분 코드: 001
//	private String userClsfName; // 회원 구분 이름: 일반 회원

}
