package dao;

import java.util.List;

import vo.BoardVO;

public interface IBoardDao {
	
	//DB에 insert하는 메서드
	public int insertBoard(BoardVO bv);
	
	//BoardVO 객체자료를 이용해 DB에 update하는 메서드
	public int updateBoard(BoardVO bv);
	
	//게시글 번호를 입력받아 해당 게시글 삭제하는 메서드
	public int deleteBoard(String boardno);
	
	//게시판 전체 게시글을 가져와 List로 반환
	public List<BoardVO> AllBoardList();
	
	//게시글 존재여부 체크하기 위한 메서드
	public boolean checkBoard(String boardno);
	
	//게시글 검색하기 위한 메서드
	public List<BoardVO> searchBoard(BoardVO bv);
	
	
}
