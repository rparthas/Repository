package com.SMA.mapper;

import org.apache.ibatis.annotations.Insert;

import com.SMA.entity.User;

public interface UserMapper {

	@Insert("INSERT INTO USERDATA(UNAME,PWD,ROLE_ID,EMPID) VALUES(#{username}, #{password}, #{roleId}, #{empId})")
	void insertUser(User user);

}
