package com.mycom.happyhouse.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mycom.happyhouse.dto.BookmarkAptDto;
import com.mycom.happyhouse.dto.BookmarkDongDto;
import com.mycom.happyhouse.dto.HousedealDto;
import com.mycom.happyhouse.dto.HouseinfoDto;
import com.mycom.happyhouse.dto.HouseinfoParamDto;

@Mapper
public interface HouseinfoDao {
	
	public List<HouseinfoDto> list(HouseinfoParamDto houseinfoParamDto);
	public int listTotalCount();
	public List<HouseinfoDto> search(HouseinfoParamDto houseinfoParamDto);
	public int searchTotalCount(HouseinfoParamDto houseinfoParamDto);
	public List<HouseinfoDto> listByBookmarkApt(HouseinfoParamDto houseinfoParamDto);
	public int listByBookmarkAptTotalCount(HouseinfoParamDto houseinfoParamDto);
	public List<HouseinfoDto> listByBookmarkDong(HouseinfoParamDto houseinfoParamDto);
	public int listByBookmarkDongTotalCount(HouseinfoParamDto houseinfoParamDto);
	public long getUserAptCode(HouseinfoParamDto houseinfoParamDto);
	
	public HouseinfoDto detail(HouseinfoParamDto houseinfoParamDto);
	public List<HousedealDto> dealList(HouseinfoParamDto houseinfoParamDto);
	public int dealListTotalCount(HouseinfoParamDto houseinfoParamDto);
	
	public int insertBookmarkApt(BookmarkAptDto bookmarkAptDto);
	public int insertBookmarkDong(BookmarkDongDto bookmarkDongDto);
	public int deleteBookmarkApt(BookmarkAptDto bookmarkAptDto);
	public int deleteBookmarkDong(BookmarkDongDto bookmarkDongDto);
	
}
