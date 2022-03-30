package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import util.JDBCUtil;
import vo.BoardVO;

public class BoardDaoImpl implements IBoardDao {

	private Connection conn;
	private Statement stmt;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	@Override
	public int insertBoard(BoardVO bv) {
		
		int cnt = 0;
		
		try {
			
			conn = JDBCUtil.getConnection();
			
			String sql ="insert into jdbc_board"
					  + " values(board_seq.nextval, ?, ?, sysdate, ?)";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, bv.getTitle());
			pstmt.setString(2, bv.getBoardwriter());
			pstmt.setString(3, bv.getContent());
			
			cnt = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.disConnect(conn, stmt, pstmt, rs);
		}
		return cnt;
	}

	@Override
	public int updateBoard(BoardVO bv) {
		
		int cnt = 0;
		
		try {
			
			conn = JDBCUtil.getConnection();
			
			String sql = "update jdbc_board"
					   + "   set board_title=?"
					   + "     , board_content=?"
					   + " where board_no=?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, bv.getTitle());
			pstmt.setString(2, bv.getContent());
			pstmt.setString(3, bv.getBoardno());
			
			cnt = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.disConnect(conn, stmt, pstmt, rs);
		}
		
		return cnt;
	}

	@Override
	public int deleteBoard(String boardno) {
		
		int cnt = 0;
		
		try {
			
			conn = JDBCUtil.getConnection();
			
			String sql = " delete from jdbc_board where board_no=?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, boardno);
			
			cnt = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.disConnect(conn, stmt, pstmt, rs);
		}
		
		return cnt;
	}

	@Override
	public List<BoardVO> AllBoardList() {

		List<BoardVO> boardList = new ArrayList<BoardVO>();
		
		try {
			
			conn = JDBCUtil.getConnection();
			
			String sql = " select * from jdbc_board order by board_no";
			
			stmt = conn.createStatement();
			
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				String boardno = rs.getString("board_no");
				String title = rs.getString("board_title");
				String boardwriter = rs.getString("board_writer");
				String date = rs.getString("board_date");
				
			
				BoardVO bv = new BoardVO();
				bv.setBoardno(boardno);
				bv.setTitle(title);
				bv.setBoardwriter(boardwriter);
				bv.setDate(date);
			
				boardList.add(bv);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.disConnect(conn, stmt, pstmt, rs);
		}
		return boardList;
	}

	@Override
	public boolean checkBoard(String boardno) {
		
		boolean chk = false;
		
		try {
			conn = JDBCUtil.getConnection();
			
			String sql = "select count(*) as cnt from jdbc_board"
					   + " where board_no=?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, boardno);
			
			rs = pstmt.executeQuery();
			
			int cnt = 0;
			if(rs.next()) {
				cnt = rs.getInt("cnt");
			}
			
			if(cnt > 0) {
				chk = true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.disConnect(conn, stmt, pstmt, rs);
		}
		
		return chk;
	}

	@Override
	public List<BoardVO> searchBoard(BoardVO bv) {
		
		List<BoardVO> boardList = new ArrayList<BoardVO>();
		
		try {
			
			conn = JDBCUtil.getConnection();
			
			String sql = " select * from jdbc_board where 1=1";
			
			if(bv.getBoardno() != null && !bv.getBoardno().equals("")) {
				sql += " and board_no=?";
			}
			if(bv.getTitle() != null && !bv.getTitle().equals("")) {
				sql += " and board_title=?";
			}
			if(bv.getContent() != null && !bv.getContent().equals("")) {
				sql += " and board_content=?";
			}
			if(bv.getBoardwriter() != null && !bv.getBoardwriter().equals("")) {
				sql += " and boare_writer=?";
			}
			if(bv.getDate() != null && bv.getDate().equals("")) {
				sql += " and board_date where '%' || ? || '%'";
			}
			
			pstmt = conn.prepareStatement(sql);
			
			int index = 1;
			if(bv.getBoardno() != null && !bv.getBoardno().equals("")) {
				pstmt.setString(index++, bv.getBoardno());
			}
			if(bv.getTitle() != null && !bv.getTitle().equals("")) {
				pstmt.setString(index++, bv.getTitle());
			}
			if(bv.getContent() != null && !bv.getContent().equals("")) {
				pstmt.setString(index++, bv.getContent());
			}
			if(bv.getBoardwriter() != null && !bv.getBoardwriter().equals("")) {
				pstmt.setString(index++, bv.getBoardwriter());
			}
			if(bv.getDate() != null && bv.getDate().equals("")) {
				pstmt.setString(index++, bv.getDate());
			}
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				BoardVO bv2 = new BoardVO();
				bv2.setBoardno(rs.getString("board_no"));
				bv2.setTitle(rs.getString("board_title"));
				bv2.setContent(rs.getString("board_content"));
				bv2.setBoardwriter(rs.getString("board_writer"));
				bv2.setDate(rs.getNString("board_date"));
				
				boardList.add(bv2);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return boardList;
		
		
	}

}
