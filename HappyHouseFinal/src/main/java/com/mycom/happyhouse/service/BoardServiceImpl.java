package com.mycom.happyhouse.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.mycom.happyhouse.dao.BoardDao;
import com.mycom.happyhouse.dao.CommentDao;
import com.mycom.happyhouse.dto.BoardDto;
import com.mycom.happyhouse.dto.BoardFileDto;
import com.mycom.happyhouse.dto.BoardParamDto;
import com.mycom.happyhouse.dto.BoardResultDto;



	@Service
	public class BoardServiceImpl implements BoardService {
	
		@Autowired
		BoardDao dao;
		@Autowired
		CommentDao commentdao;
	
		/* for production code */
		//uploadPath = getServletContext().getRealPath("/");
		// F:\SSAFY\ssafy5\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps\BoardWebFileUpload\
		
		/* for eclipse development code */
		@Value("${app.fileupload.uploadPath}")
		String uploadPath;
		/* 업로드 후 upload 폴더 refresh 하거나 preferences / workspace - refresh... 2개 option check */
		
		@Value("${app.fileupload.uploadDir}")
		String uploadFolder;
		
	private static final int SUCCESS = 1;
	private static final int FAIL = -1;

	// 기존 Servlet, JSP 와 다른 점
	// - 기존은 Controller -> Service 전달되는 객체가 request.getParts()이고, Collection<Part> 타입임.
	//   Part 에는 getOriginalFilename()가 없다. 그래서 별도의 FileManager 클래스에서 Part 에서 잘라오는 메소드를 만듦
	// - 파일 확장자를 가져오는 것도 commons.io 를 이용 ( jar 파일관리가 기존은 귀찮고, 또 FileNamager 를 만들었으니 그곳에 확장자를 가져오는 메소드를 추가해서 사용 )
	//   Spring 은 pom.xml 에 추가만 하면 되므로 commons.io 사용
	// - 저장도 MultipartFile의 transferTo() 를 사용
	@Override
	@Transactional
	public BoardResultDto boardInsert(BoardDto dto, MultipartHttpServletRequest request) {
		
		BoardResultDto boardResultDto = new BoardResultDto();
		
		try {
			dao.boardInsert(dto); // useGeneratedKeys="true" keyProperty="boardId"
			
			List<MultipartFile> fileList = request.getFiles("file");
	
//			String s = null;
//			s.length();
			
			File uploadDir = new File(uploadPath + File.separator + uploadFolder);
			if (!uploadDir.exists()) uploadDir.mkdir();

			for (MultipartFile partFile : fileList) {

				int boardId = dto.getBoardId();
				
				String fileName = partFile.getOriginalFilename();
				
				//Random File Id
				UUID uuid = UUID.randomUUID();
				
				//file extension
				String extension = FilenameUtils.getExtension(fileName); // vs FilenameUtils.getBaseName()
			
				String savingFileName = uuid + "." + extension;
			
				File destFile = new File(uploadPath + File.separator + uploadFolder + File.separator + savingFileName);
				
				System.out.println(uploadPath + File.separator + uploadFolder + File.separator + savingFileName);
				partFile.transferTo(destFile);
		    
			    // Table Insert
			    BoardFileDto boardFileDto = new BoardFileDto();
			    boardFileDto.setBoardId(boardId);
			    boardFileDto.setFileName(fileName);
			    boardFileDto.setFileSize(partFile.getSize());
				boardFileDto.setFileContentType(partFile.getContentType());
				String boardFileUrl = uploadFolder + "/" + savingFileName;
				boardFileDto.setFileUrl(boardFileUrl);
				System.out.println(boardFileDto);
				dao.boardFileInsert(boardFileDto);
			}

			System.out.println(boardResultDto);
			boardResultDto.setResult(SUCCESS);
			System.out.println(boardResultDto);
			
		}catch(IOException e) {
			e.printStackTrace();
			boardResultDto.setResult(FAIL);
		}
		return boardResultDto;
	}

	@Override
	@Transactional
	public BoardResultDto boardUpdate(BoardDto dto, MultipartHttpServletRequest request){
		
		BoardResultDto boardResultDto = new BoardResultDto();

		
		try {
			dao.boardUpdate(dto);

			List<MultipartFile> fileList = request.getFiles("file");
			
			File uploadDir = new File(uploadPath + File.separator + uploadFolder);
			if (!uploadDir.exists()) uploadDir.mkdir();
			
			// 물리 파일 삭제, 첨부파일 여러개 고려
	    	List<String> fileUrlList = dao.boardFileUrlDeleteList(dto.getBoardId());	
			for(String fileUrl : fileUrlList) {	
				File file = new File(uploadPath + File.separator, fileUrl);
				if(file.exists()) {
					file.delete();
				}
			}
	    	dao.boardFileDelete(dto.getBoardId()); // 테이블 파일 삭제
	    	
	    	
			for (MultipartFile part : fileList) {
				int boardId = dto.getBoardId();
				
				String fileName = part.getOriginalFilename();
				
				//Random File Id
				UUID uuid = UUID.randomUUID();
				
				//file extension
				String extension = FilenameUtils.getExtension(fileName); // vs FilenameUtils.getBaseName()
			
				String savingFileName = uuid + "." + extension;
			
				File destFile = new File(uploadPath + File.separator + uploadFolder + File.separator + savingFileName);
				
				System.out.println(uploadPath + File.separator + uploadFolder + File.separator + savingFileName);
				part.transferTo(destFile);
		    
			    // Table Insert
			    BoardFileDto boardFileDto = new BoardFileDto();
			    boardFileDto.setBoardId(boardId);
			    boardFileDto.setFileName(fileName);
			    boardFileDto.setFileSize(part.getSize());
				boardFileDto.setFileContentType(part.getContentType());
				String boardFileUrl = uploadFolder + "/" + savingFileName;
				boardFileDto.setFileUrl(boardFileUrl);
				
				dao.boardFileInsert(boardFileDto);
			}

			boardResultDto.setResult(SUCCESS);
			
		}catch(IOException e) {
			e.printStackTrace();
			boardResultDto.setResult(FAIL);
		}
		
		return boardResultDto;
	}

	@Override
	@Transactional
	public BoardResultDto boardDelete(int boardId) {
		
		BoardResultDto boardResultDto = new BoardResultDto();
		
		try {
			List<String> fileUrlList = dao.boardFileUrlDeleteList(boardId);
			for(String fileUrl : fileUrlList) {
				File file = new File(uploadPath + File.separator, fileUrl);				
				if(file.exists()) {
					file.delete();
				}
			}

			commentdao.commentDeleteAll(boardId);
			dao.boardFileDelete(boardId);
			dao.boardReadCountDelete(boardId);
			dao.boardDelete(boardId);		
			boardResultDto.setResult(SUCCESS);
			
		}catch(Exception e) {
			e.printStackTrace();
			boardResultDto.setResult(FAIL);
		}
		
		return boardResultDto;
	}

	@Override
	public BoardResultDto boardDetail(BoardParamDto boardParamDto) {
		
		BoardResultDto boardResultDto = new BoardResultDto();
		
		try {
			int userReadCnt = dao.boardUserReadCount(boardParamDto);
			if( userReadCnt == 0 ) {
				dao.boardUserReadInsert(boardParamDto.getBoardId(), boardParamDto.getUserId());
				dao.boardReadCountUpdate(boardParamDto.getBoardId());
			}
			
			BoardDto boardDto = dao.boardDetail(boardParamDto);
			
			if( boardDto.getUserId().equals(boardParamDto.getUserId()) ) {
				boardDto.setSameUser(true);
			}else {
				boardDto.setSameUser(false);
			}
			
			List<BoardFileDto> fileList = dao.boardDetailFileList(boardDto.getBoardId());

			boardDto.setFileList(fileList);
			boardResultDto.setDto(boardDto);

			boardResultDto.setResult(SUCCESS);
			
		}catch(Exception e) {
			e.printStackTrace();
			boardResultDto.setResult(FAIL);
		}
		
		return boardResultDto;
	}

	@Override
	public BoardResultDto boardList(BoardParamDto boardParamDto) {
		
		BoardResultDto boardResultDto = new BoardResultDto();
		
		try {
			
			System.out.println(boardParamDto);
			List<BoardDto> list = dao.boardList(boardParamDto);
			int count = dao.boardListTotalCount(boardParamDto);			
			boardResultDto.setList(list);
			boardResultDto.setCount(count);
			boardResultDto.setResult(SUCCESS);
			
		}catch(Exception e) {
			e.printStackTrace();
			boardResultDto.setResult(FAIL);
		}
		
		return boardResultDto;
	}

	@Override
	public BoardResultDto boardListSearchWord(BoardParamDto boardParamDto) {
		
		BoardResultDto boardResultDto = new BoardResultDto();
		
		try {
			List<BoardDto> list = dao.boardListSearchWord(boardParamDto);
			int count = dao.boardListSearchWordTotalCount(boardParamDto);
			
			boardResultDto.setList(list);
			boardResultDto.setCount(count);
			
			boardResultDto.setResult(SUCCESS);
		
		}catch(Exception e) {
			e.printStackTrace();
			boardResultDto.setResult(FAIL);
		}
		
		return boardResultDto;
	}

//	@Override
//	public int boardListSearchWordTotalCount(ParamDto paramDto) {
//		return dao.boardListSearchWordTotalCount(paramDto);
//	}
}
