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
public class HouseinfoResultDto {
	private int result;
	private HouseinfoDto dto;
	private List<HouseinfoDto> list;
	private int count;
}
