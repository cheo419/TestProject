package project.DAO;

import java.util.List;

import project.Member;

public interface DAO {
	
	public boolean insertMember (Member m); // 회원가입

	public boolean insertRes (Member m); // 예약하기
	
	public boolean checkLogin (String id, String pw); // 로그인시 아이디비번 체크
	
	public Member selectUser (String id); // 내역보기 (유저)
	
	
	public List<Member> selectAdmin (); // 내역보기 (관리자)
}




/*


create table firstmember (
    userName varchar2(10) not null,
    phoneNum varchar2(20) not null,
    id varchar2(20) not null,
    pw varchar2(20) not null,
    
    resDepart char(1),
    resDate date,
    resTime char(1)
    
);

drop table firstmember;
select * from firstmember;

insert into firstmember (userName,phoneNum,id,pw) values ('김나진','010-0000-0000','nj','1026');

update firstmember set resDepart= '1',resDate='20220819',resTime='1' where id='nj';




*/