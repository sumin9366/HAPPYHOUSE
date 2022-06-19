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
public class HouseinfoParamDto {
	private int limit;
	private int offset;
	private String aptName;
	private String dongCode;
	
	private long aptCode;
	
	private String bookmark;
	private String userId;
	
	private int priceMin;
	private int priceMax;
	
	private int buildYearMin;
	private int buildYearMax;
}
