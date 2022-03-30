package service;

import java.util.List;

import dao.BoardDaoImpl;
import dao.IBoardDao;
import vo.BoardVO;

public class BoardServiceImpl implements IBoardService{

	private IBoardDao boardDao;
	
	public BoardServiceImpl() {
		boardDao = new BoardDaoImpl();
	}
	
	@Override
	public int insertBoard(BoardVO bv) {
		return boardDao.insertBoard(bv);
	}

	@Override
	public int updateBoard(BoardVO bv) {
		return boardDao.updateBoard(bv);
	}

	@Override
	public int deleteBoard(String boardno) {
		return boardDao.deleteBoard(boardno);
	}

	@Override
	public List<BoardVO> AllBoardList() {
		return boardDao.AllBoardList();
	}

	@Override
	public boolean checkBoard(String boardno) {
		return boardDao.checkBoard(boardno);
	}

	@Override
	public List<BoardVO> searchBoard(BoardVO bv) {
		return boardDao.searchBoard(bv);
	}
	
	

}
