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
public class HouseinfoDto {
	private long aptCode;
	private int aptBuildYear;
	private String roadName;
	private String roadNameBonbun;
	private String roadNameBubun;
	private String roadNameSeq;
	private String roadNameBasementCode;
	private String roadNameCode;
	private String dongName;
	private String dongBonbun;
	private String dongBubun;
	private String gugunCode;
	private String eubmyundongCode;
	private String dongCode;
	private String landCode;
	private String aptName;
	private String aptJibun;
	private String aptLat;
	private String aptLng;
	
	private String address;
	// concat(d.sido_name, ' ', d.gugun_name, ' ', hi.road_name) as roadAddress
//	private String roadAddress;
	
	private int recentDealAmount;
	private String recentDealArea;
	private String recentDealDate;
	private List<HousedealDto> housedealList;
	
	private String bookmarked;
}
