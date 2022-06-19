package com.mycom.happyhouse.service;

import com.mycom.happyhouse.dto.BookmarkAptDto;
import com.mycom.happyhouse.dto.BookmarkDongDto;
import com.mycom.happyhouse.dto.HouseinfoParamDto;
import com.mycom.happyhouse.dto.HouseinfoResultDto;

public interface HouseService {
	// 리스트
	public HouseinfoResultDto list(HouseinfoParamDto houseinfoParamDto);
	public HouseinfoResultDto search(HouseinfoParamDto houseinfoParamDto);
	public HouseinfoResultDto listByBookmarkApt(HouseinfoParamDto houseinfoParamDto);
	public HouseinfoResultDto listByBookmarkDong(HouseinfoParamDto houseinfoParamDto);
	public HouseinfoResultDto myhouse(HouseinfoParamDto houseinfoParamDto);
	
	public HouseinfoResultDto detail(HouseinfoParamDto houseinfoParamDto);
	
	public HouseinfoResultDto insertBookmarkApt(BookmarkAptDto bookmarkAptDto);
	public HouseinfoResultDto insertBookmarkDong(BookmarkDongDto bookmarkDongDto);
	public HouseinfoResultDto deleteBookmarkApt(BookmarkAptDto bookmarkAptDto);
	public HouseinfoResultDto deleteBookmarkDong(BookmarkDongDto bookmarkDongDto);
	
}
