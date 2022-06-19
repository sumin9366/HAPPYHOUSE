package com.mycom.happyhouse.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycom.happyhouse.dao.HouseinfoDao;
import com.mycom.happyhouse.dto.BookmarkAptDto;
import com.mycom.happyhouse.dto.BookmarkDongDto;
import com.mycom.happyhouse.dto.HouseinfoDto;
import com.mycom.happyhouse.dto.HouseinfoParamDto;
import com.mycom.happyhouse.dto.HouseinfoResultDto;

@Service
public class HouseServiceImpl implements HouseService{

	@Autowired
	HouseinfoDao hidao;
//	@Autowired
//	HousedealDao hddao;
//	@Autowired
//	DongDao ddao;
	
	private static final int SUCCESS = 1;
	private static final int FAIL = -1;
	
	@Override
	public HouseinfoResultDto list(HouseinfoParamDto houseinfoParamDto) {
		
		HouseinfoResultDto houseinfoResultDto = new HouseinfoResultDto();
		
		try {
			List<HouseinfoDto> list = hidao.list(houseinfoParamDto);
			int count = hidao.listTotalCount();
			
			houseinfoResultDto.setList(list);
			houseinfoResultDto.setCount(count);
			houseinfoResultDto.setResult(SUCCESS);
			
		} catch (Exception e) {
			e.printStackTrace();
			houseinfoResultDto.setResult(FAIL);
		}
		
		return houseinfoResultDto;
	}
	
	@Override
	public HouseinfoResultDto search(HouseinfoParamDto houseinfoParamDto) {
		
		HouseinfoResultDto houseinfoResultDto = new HouseinfoResultDto();
		
		try {
			List<HouseinfoDto> list = hidao.search(houseinfoParamDto);
			int count = hidao.searchTotalCount(houseinfoParamDto);
			
			houseinfoResultDto.setList(list);
			houseinfoResultDto.setCount(count);
			houseinfoResultDto.setResult(SUCCESS);
			
		} catch (Exception e) {
			e.printStackTrace();
			houseinfoResultDto.setResult(FAIL);
		}
		
		return houseinfoResultDto;
	}
	
	@Override
	public HouseinfoResultDto listByBookmarkApt(HouseinfoParamDto houseinfoParamDto) {
		HouseinfoResultDto houseinfoResultDto = new HouseinfoResultDto();
		
		try {
			List<HouseinfoDto> list = hidao.listByBookmarkApt(houseinfoParamDto);
			int count = hidao.listByBookmarkAptTotalCount(houseinfoParamDto);
			
			houseinfoResultDto.setList(list);
			houseinfoResultDto.setCount(count);
			houseinfoResultDto.setResult(SUCCESS);
			
		} catch (Exception e) {
			e.printStackTrace();
			houseinfoResultDto.setResult(FAIL);
		}
		
		return houseinfoResultDto;
	}
	
	@Override
	public HouseinfoResultDto listByBookmarkDong(HouseinfoParamDto houseinfoParamDto) {
		HouseinfoResultDto houseinfoResultDto = new HouseinfoResultDto();
		
		try {
			List<HouseinfoDto> list = hidao.listByBookmarkDong(houseinfoParamDto);
			int count = hidao.listByBookmarkDongTotalCount(houseinfoParamDto);
			
			houseinfoResultDto.setList(list);
			houseinfoResultDto.setCount(count);
			houseinfoResultDto.setResult(SUCCESS);
			
		} catch (Exception e) {
			e.printStackTrace();
			houseinfoResultDto.setResult(FAIL);
		}
		
		return houseinfoResultDto;
	}

	@Override
	public HouseinfoResultDto detail(HouseinfoParamDto houseinfoParamDto) {
		HouseinfoResultDto houseinfoResultDto = new HouseinfoResultDto();
		
		try {
			HouseinfoDto houseInfoDto = hidao.detail(houseinfoParamDto);
			houseInfoDto.setHousedealList(hidao.dealList(houseinfoParamDto));
			int count = hidao.dealListTotalCount(houseinfoParamDto);
			
			houseinfoResultDto.setDto(houseInfoDto);
			houseinfoResultDto.setCount(count);
			houseinfoResultDto.setResult(SUCCESS);
			
		} catch (Exception e) {
			e.printStackTrace();
			houseinfoResultDto.setResult(FAIL);
		}
		
		return houseinfoResultDto;
	}
	
	@Override
	public HouseinfoResultDto insertBookmarkApt(BookmarkAptDto bookmarkAptDto) {
		HouseinfoResultDto houseinfoResultDto = new HouseinfoResultDto();
		try {
			hidao.insertBookmarkApt(bookmarkAptDto);
			houseinfoResultDto.setResult(SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			houseinfoResultDto.setResult(FAIL);
		}
		return houseinfoResultDto;
	}
	
	@Override
	public HouseinfoResultDto insertBookmarkDong(BookmarkDongDto bookmarkDongDto) {
		HouseinfoResultDto houseinfoResultDto = new HouseinfoResultDto();
		try {
			hidao.insertBookmarkDong(bookmarkDongDto);
			houseinfoResultDto.setResult(SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			houseinfoResultDto.setResult(FAIL);
		}
		return houseinfoResultDto;
	}
	
	@Override
	public HouseinfoResultDto deleteBookmarkApt(BookmarkAptDto bookmarkAptDto) {
		HouseinfoResultDto houseinfoResultDto = new HouseinfoResultDto();
		try {
			hidao.deleteBookmarkApt(bookmarkAptDto);
			houseinfoResultDto.setResult(SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			houseinfoResultDto.setResult(FAIL);
		}
		return houseinfoResultDto;
	}
	
	@Override
	public HouseinfoResultDto deleteBookmarkDong(BookmarkDongDto bookmarkDongDto) {
		HouseinfoResultDto houseinfoResultDto = new HouseinfoResultDto();
		try {
			hidao.deleteBookmarkDong(bookmarkDongDto);
			houseinfoResultDto.setResult(SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			houseinfoResultDto.setResult(FAIL);
		}
		return houseinfoResultDto;
	}
	
	@Override
	public HouseinfoResultDto myhouse(HouseinfoParamDto houseinfoParamDto) {
		
		HouseinfoResultDto houseinfoResultDto = new HouseinfoResultDto();
		
		try {
			long aptCode = hidao.getUserAptCode(houseinfoParamDto);
			
			if (aptCode == 0) {
				houseinfoResultDto.setResult(FAIL);
			} else {
				houseinfoParamDto.setAptCode(aptCode);
				houseinfoParamDto.setLimit(5);
				houseinfoParamDto.setOffset(0);
				List<HouseinfoDto> list = hidao.search(houseinfoParamDto);
				int count = hidao.searchTotalCount(houseinfoParamDto);
				
				houseinfoResultDto.setList(list);
				houseinfoResultDto.setCount(count);
				houseinfoResultDto.setResult(SUCCESS);
			}
		} catch (Exception e) {
			e.printStackTrace();
			houseinfoResultDto.setResult(FAIL);
		}
		
		return houseinfoResultDto;
	}
}
