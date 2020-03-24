package sec02.ex01;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

//데이터베이스 연결 및 데이터베이스 작업(insert, select,delete,update 등등) 
public class MemberDAO {

	// 자바 애플리케이션의 DBMS 연동 순서
	// 1. import java.sql.* 과 DBMS연동 관련 네가지 정보 설정하기
	// 드라이버명 -> oracle.jdbc.driver.OracleDriver.class
	// DB접속 주소 정보 -> jdbc:oracle:thin:@ip주소:포트번호:SID
	// DB접속 사용자 아이디 -> scott
	// DB접속 사용자 비밀번호 -> tiger

	/*
	 * // ojdbc6.jar내부에 있는 OracleDriver.class 경로 정보 private static final String
	 * driver = "oracle.jdbc.driver.OracleDriver"; // 오라클 DB접속 주소 정보 저장 private
	 * static final String url = "jdbc:oracle:thin:@localhost:1521:XE"; // DB접속 사용자
	 * 아이디 private static final String user = "scott"; // DB접속 사용자 비밀번호 private
	 * static final String pwd = "tiger";
	 */
	// 2.드라이버 로딩
	// 위 네가지 설정값을 이용해서 오라클DB와 접속한 정보를 지니고 있는 Connection객체를 저장할 변수 선언
	private Connection con;

	// DB와 접속 후 우리가 만든 SQL문을 생성 후 실행할 실행객체를 담을 변수 선언
	/*
	 * private Statement stmt;
	 */
	
	//Statement인터페이스를 구현한 자식객체를 저장하는 대신에 
	//PreparedStatement인터페이스를 구현한 자식객체를 저장할 용도의 변수 선언 
	private PreparedStatement pstmt;
	
	private DataSource dataFactory;
	
	public MemberDAO() {
		try {
			Context ctx = new InitialContext();
			Context envContext = (Context)ctx.lookup("java:/comp/env");
			
			//톰캣 context.xml에 설정한 name값인 jdbc/oracle을 이용해 톰캣이 미리 연결한
			//DataSource객체(커넥션풀 역할을 하는 객체)을 받아옵니다.
			dataFactory = (DataSource)envContext.lookup("jdbc/oracle");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	//listMembers메소드는 어떤 클래스 내부에서 호출하는 메소드인가? MemberServlet 
	
	// DB의 모든 회원정보 검색(조회) 담담하는 메소드 만들기 list.Member
	public ArrayList listMembers() {
		
		ArrayList list = new ArrayList();
	try {
		//connDB(); //네가지 정보(오라클 드라이버, 오라클 DB접속 주소정보, 오라클접속 ID, 오라클 접속 PW)로 DB연결작업할당
		
		con = dataFactory.getConnection();
		
		//5.Query작성하기 
		String query ="select * from t_member";
		System.out.println(query);
		
		//4. Connection객체의 PreparedStatement()메소드 호출시 ...SQL문을 전달해 
		//미리 로딩한 OraclePreparedStatementWrapper실행객체 얻기 
		pstmt = con.prepareStatement(query);
		
		//6.Query를 DB의 테이블에 전송하여 실행하기
		//->Statement객체의 executeQuery메소드 호출시...select구문을 전달하면...
		//검색한 회원정보를 테이블형식으로
		//ResultSet인터페이스의 자식객체인? OracleResultSetImpl 임시 메모리 공간에 저장하여 반환받는다.
		ResultSet rs = pstmt.executeQuery();
		
		while(rs.next()) {	//검색할 줄이 존재할때까지 반복 
			//7.select문일 경우 검색할 결과값 데이터들이 ResultSet일시 메모리 저장소에 저장되어 있기때문에 
			//rs.next()메소드를 호출하여 그 다음 줄의 검색한 한 줄의 레코드를 가리키게 해야함.
			//현재 가리키고 있는 한 줄의 정보를 얻어올때...ResultSet객체의 get으로 시작하는 메소드 활용함.
			String id = rs.getString("id");
			String pwd = rs.getString("pwd");
			String name = rs.getString("name");
			String email = rs.getString("email");
			Date joinDate = rs.getDate("joindate");
			
			//위 변수에 저장된 각 컬럼값을 다시 MemberVo객체를 생성하여
			//각 변수에 저장 
			MemberVO vo = new MemberVO();
			//setter메소드 호출~! 
			vo.setId(id);
			vo.setPwd(pwd);
			vo.setName(name);
			vo.setEmail(email);
			vo.setJoindate(joinDate);
			
			//MemberVO객체 자체를? ArrayList가변길이 배열에 추가해서 저장
			list.add(vo);
		}//while반복문 끝
		
		//자원해제~ 
		rs.close();
		pstmt.close();
		con.close();
		
		}catch(Exception e) {
									// 오류 메세지 출력 
			System.out.println("listMembers메소드 내부에서 오류: " + e);
		}
		return list; //DB로부터 검색한 레코드의 갯수만큼 MemberVO객체들이 저장되어 있는 ArrayList배열공간을 반환함.
	}//listMembers()메소드 닫는 기호 

}//MemberDAO 클래스 닫는 기호 
