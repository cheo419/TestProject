package project.dao;

import java.util.List;

import project.Member;

public interface MemberDAO {
	
	// 회원가입
//	 반드시 firstmemberRes에도 아이디를 insert 해줘야함.
	public boolean insertMember(Member m);  
	
	// 로그인시 아이디 비밀번호 확인 : 맞으면 true
	public boolean checkLogin(String id, String pw); 
	
	// 입력된 아이디에 해당하는 이름, 전화번호, 비밀번호 가져옴.
	public Member select(String id); 
	
	// 입력된 아이디에 해당하는 모든 예약정보 출력 (모든정보불러오기위해 리스트사용)
	// 예약된 내역이없으면 아무것도 출력안됨.
	public List<Member> selectIdRes(String id); 
	
	// 진료 예약하기
	public boolean insertRes (Member m); 
	
	// 관리자 모든회원정보 출력 (모든 회원정보 불러오기위해 리스트 사용)
	public List<Member> selectAdmin(); 
	
	//  예약여부확인 (예약하기 버튼 비활성화,예약내역 삭제시) : 예약내역있으면 true
	public boolean checkRes(String id); 
	
	// <관리자페이지>테이블뷰에서 선택된 회원 강제탈퇴 , <회원정보수정 페이지> 정보수정페이지에서 탈퇴
	// 반드시 예약내역 삭제가 선행되어야함.(primary key 때문에)
	public boolean deleteUser (String id); 
	
	// <관리자페이지> 테이블뷰에서 선택된 회원의 모든 예약내역 삭제
	// 회원강제탈퇴시에만 사용함
	public boolean deleteUserResAll (String id); 
	
	// <관리자페이지,유저페이지> 테이블뷰에서 선택된 예약내역만 삭제
	public boolean deleteResSelect (String id,int resJinryo); 
	
	// 회원가입시에 같은 아이디가있는지 검색 : 동일한 아이디 있으면 true
	public boolean findSameId (String id); 
	
	// 진료 가능여부 확인. : 예약 가능시 true
	public boolean findSameRes(int value1,String value2,int value3) ;
	
	// 회원정보 수정
	public boolean updateInfo(Member m, String id);
	
	// 해당진료과로 예약내역이 있는지 확인 (추가진료예약시에 사용) 없으면 true
	public boolean noJinryo(String id, int resJinryo);
	
	
	
	
	
}

/*
 * 	0. (초기화 필요시)firstmember, firstmemberRes, firstAdmin 모두 drop하기.
 *  1. firstmember create 하기
 *  2. firstmemberRes create 하기
 *  3. firstAdmin create 하기
 *  4. insert fisrtAdmin 하기 : 초기 비밀번호 '1111'설정. (꼭 해야함)
 *  5. commit 하기

create table firstmember (
    userName varchar2(50) not null,
    phoneNum varchar2(50) not null,
    id varchar2(20) not null primary key,
    pw varchar2(20) not null
);
create table firstmemberRes (
    resJinryo char(1),
    resDate date,
    resTime char(1),
    id varchar2(20) not null,
    foreign key(id) references firstmember(id)
);

create table firstAdmin (
    mPw varchar2(20) not null
);
insert into firstAdmin values ('1111');

commit;


select * from firstmember;
select * from firstmemberRes;

select * from firstAdmin;


drop table firstmember;
drop table firstmemberRes;

drop table firstAdmin;





*/