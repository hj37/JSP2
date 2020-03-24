package sec02.ex01;

import java.sql.Date;

//VO(Value Object) 역할을 하는 클래스 
//VO역할이란?
//DB의 테이블에서 조회한 레코드의 집합 값을 속성(변수)에 저장하거나 변수에 저장된 값들을
//한꺼번에 DB의 테이블에 추가할 역할을 하는 클래스 

public class MemberVO {
	
	//변수 : 테이블에서 조회한 레코드의 컬럼값을 변수에 저장해야 하므로 
	//컬럼이름과 동일한 자료형과 이름으로 변수를 선언함.
	private String id; 
	private String pwd;
	private String name;
	private String email;
	private Date joindate;
	
	//getter,setter메소드를 각각 만들어준다.
	//getter역할을 하는 메소드란?
		//private으로 선언된 뒤 변수의값을 반환할 목적으로 사용됨
	public MemberVO() {//생성자 
		System.out.println("MemberVO 생성자 호출");
	}
	
	
	public String getId() {
		return id;
	}
	
	//setter역할을 하는 메소드란?
		//private으로 선언된 위 변수값을 새로이 저장(셋팅)할 목적으로 사용됨 
	public void setId(String id) {
		this.id = id;
	}

	public String getPwd() {//멤버변수에 저장된 비밀번호를 리턴
		return pwd;
	}

	public void setPwd(String pwd) {//새로운 비밀번호를 매개변수로 얻어 위의 멤버변수에 저장(셋팅) 	
		this.pwd = pwd;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getJoindate() {
		return joindate;
	}

	public void setJoindate(Date joindate) {
		this.joindate = joindate;
	}
	
	//getter,setter메소드 추가 단축키 ->alt + shift + s + r
	

}
