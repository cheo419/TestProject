package project.dao;

import java.util.List;

import project.Member;

public interface MemberDAO {
	
	// 회원가입
	public boolean insertMember(Member m);  
	
	// 로그인시 아이디 비밀번호 확인 : 맞으면 true
	public boolean checkLogin(String id, String pw); 
	
	// 입력된 아이디에 해당하는 모든 정보 출력
	public Member select(String id); 
	
	// 진료 예약하기
	public boolean insertRes (Member m); 
	
	// 관리자 예약출력 (모든 회원정보 불러오기위해 리스트 사용)
	public List<Member> selectAdmin(); 
	
	//  예약여부확인 (예약하기 버튼 비활성화,예약내역 삭제시) : 예약내역있으면 true
	public boolean checkRes(String id); 
	
	// <관리자페이지> 테이블뷰에서 선택된 회원 강제탈퇴
	public boolean deleteUser (String id); 
	
	// <관리자페이지,유저페이지> 테이블뷰에서 선택된 예약내역 삭제
	public boolean deleteUserRes (String id); 
	
	// 회원가입시에 같은 아이디가있는지 검색 : 동일한 아이디 있으면 true
	public boolean findSameId (String id); 
	
	// 진료 가능여부 확인. : 예약 가능시 true
	public boolean findSameRes(int value1,String value2,int value3) ;
}


/*

create table firstmember (
    userName varchar2(50) not null,
    phoneNum varchar2(50) not null,
    id varchar2(20) not null,
    pw varchar2(20) not null,
    
    resJinryo char(1),
    resDate date,
    resTime char(1)
    
);

commit;

drop table firstmember;

select * from firstmember;

select count(*) from firstmember where id='nj';

select count(resJinryo) from firstmember where id='nj';

select count(*) from firstmember where resJinryo='3' and resDate='20220824' and resTime='5';




insert into firstmember (userName,phoneNum,id,pw) values ('김나진','010-0000-0000','nj','1026');

update firstmember set resJinryo= '1',resDate='20220819',resTime='1' where id='nj';

delete from firstmember where id='nj4';

update firstmember set resJinryo= '',resDate='',resTime='' where id='nj';


*/