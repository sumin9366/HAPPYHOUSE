package com.mycom.happyhouse.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class HousedealResultDto {
	private int result;
	private HousedealDto dto;
	private List<HousedealDto> list;
	private int count;	
	
}
