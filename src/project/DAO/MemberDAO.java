package project.DAO;

import java.util.List;

import project.Member;

public interface MemberDAO {
	public boolean insertMember(Member m); // 회원가입
	
	public boolean checkLogin(String id, String pw); // 로그인
	
	public Member select(String id); // 로그인 내용 저장
	
	public boolean insertRes (Member m); // 예약
	
	public List<Member> selectAdmin(); // 관리자 예약출력

//	public List<Member> selectUser();	// 유저의 예약출력
}


/*

create table firstmember (
    userName varchar2(10) not null,
    phoneNum varchar2(20) not null,
    id varchar2(20) not null,
    pw varchar2(20) not null,
    
    resJinryo char(1),
    resDate date,
    resTime char(1)
    
);

drop table firstmember;

select * from firstmember;

commit;

insert into firstmember (userName,phoneNum,id,pw) values ('김나진','010-0000-0000','nj','1026');

update firstmember set resJinryo= '1',resDate='20220819',resTime='1' where id='nj';


*/