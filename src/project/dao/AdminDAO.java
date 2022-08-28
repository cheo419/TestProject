package project.dao;

import project.Admin;

public interface AdminDAO {
	
	// 관리자 비밀번호 가져오기
	public Admin select(); 
	
	// 관리자 비밀번호 변경하기
	public boolean updateMPw (Admin a); 
	
}

/*
 *  1. firstmember create 하기
 *  2. firstAdmin create 하기
 *  3. insert fisrtAdmin 하기 : 초기 비밀번호 '1111'설정. (꼭 해야함)
 *  4. commit 하기

create table firstmember (
    userName varchar2(50) not null,
    phoneNum varchar2(50) not null,
    id varchar2(20) not null,
    pw varchar2(20) not null,
    
    resJinryo char(1),
    resDate date,
    resTime char(1)
    
);

create table firstAdmin (
    mPw varchar2(20) not null
);
insert firstAdmin set mPw= '1111';



commit;



select * from firstmember;
select * from firstAdmin;

drop table firstmember;
drop table firstAdmin;





*/
