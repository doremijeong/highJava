package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * dp.properties파일의 내용으로 DB정보를 설정하는 방법
 * 방법1) Properties객체 이용하기
 */
public class JDBCUtil2 {
	static Properties prop; //Properties 객체변수 선언
	
	static {
		prop = new Properties(); //객체 새성
		
		try {
			prop.load(new FileInputStream("res/db.properties"));
			//load를 이용하면 파일을 가져와 읽을 수 있다.
			
			Class.forName(prop.getProperty("driver"));
			//oracle.jdbc.driver.OracleDriver => driver
			
			//load하다가 예외발생할 수 있기때문에
		} catch (IOException e) {
			System.out.println("파일이 없거나 입출력 오류입니다.");
			e.printStackTrace();
		}catch (ClassNotFoundException ex) {
			System.out.println("드라이버 로딩 실패!!!");
			ex.printStackTrace();
		}
	}
	
	/**
	 * 커넥션 객체 생성
	 * @return Connection 객체
	 */
	public static Connection getConnection() {
		
		try {
			return DriverManager.getConnection(
					prop.getProperty("url")
					,prop.getProperty("user")
					,prop.getProperty("password"));
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
