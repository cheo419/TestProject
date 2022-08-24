package project.dao;

import java.util.List;

import project.Member;

public interface MemberDAO {
	public boolean insertMember(Member m); // 회원가입
	
	public boolean checkLogin(String id, String pw); // 로그인
	
	public Member select(String id); // 유저의 예약내용 출력
	
	public boolean insertRes (Member m); // 진료 예약하기 (DB에 진료예약정보 저장되면 true)
	
	public List<Member> selectAdmin(); // 관리자 예약출력
	
	public boolean checkRes(String id); //  예약여부확인 (예약하기 버튼 비활성화,예약내역 삭제시): 예약내역있음true
	
	public boolean deleteUser (String id); // 관리자페이지에서 테이블뷰에서 선택된 회원 강제탈퇴
	
	public boolean deleteUserRes (String id); // (관리자페이지,유저페이지)테이블뷰에서 선택된 예약내역 삭제
	
	public boolean findSameId (String id); // 회원가입시에 같은 아이디가있는지 검색 (동일한 아이디 있으면 true)
	

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