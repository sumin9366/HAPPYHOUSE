package com.mycom.happyhouse.dto;

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
public class HousedealDto {

	private long dealNo;
	private int dealAmount;
	private int dealYear;
	private int dealMonth;
	private int dealDay;
	private String dealDate;
	private String dealArea;
	private String dealFloor;
	private String cancelDealType;
	private long aptCode;
	
}
