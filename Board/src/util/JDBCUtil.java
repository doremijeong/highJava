package util;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCUtil {
	
	static {//static블럭은 처음에 로딩되는 시점에 딱 한번만 실행된다. 언제? JDBCUtil이 실행되어질때.
		try {
			//오라클에서 제공해주는 Class 파일을 확인해주기위해 호출하고 있음. 점검중...
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//아무 문제없으면..
			System.out.println("드라이버 로딩 완료!");
			
		} catch (ClassNotFoundException e) {
			//문제가 생겼으면...
			System.out.println("드라이버 로딩 실패!!!");
			e.printStackTrace();
		}
	}
	
	/**
	 * 커넥션 객체 생성
	 * @return Connection 객체
	 */
	public static Connection getConnection() {
		
		try {
			return DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe"
											,"LMS"
											,"java");
		} catch (SQLException ex) {
			System.out.println("DB 연결 실패!!!");
			ex.printStackTrace();
			
			return null;
		}
	}
	
	
	/**
	 * 자원 반납
	 * @param conn
	 * @param stmt
	 * @param pstmt
	 * @param rs
	 */
	public static void disConnect(Connection conn
			, Statement stmt
			, PreparedStatement pstmt
			, ResultSet rs
			) {
		if(rs !=null) try {rs.close();} catch (Exception e) {}
		if(pstmt !=null) try {pstmt.close();} catch (Exception e) {}
		if(stmt !=null) try {stmt.close();} catch (Exception e) {}
		if(conn !=null) try {conn.close();} catch (Exception e) {}
		
		
	}
}


