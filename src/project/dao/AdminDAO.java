package project.dao;

import project.Admin;

public interface AdminDAO {
	
	// 관리자 비밀번호 가져오기
	public Admin select(); 
	
	// 관리자 비밀번호 변경하기
	public boolean updateMPw (Admin a); 
	
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
