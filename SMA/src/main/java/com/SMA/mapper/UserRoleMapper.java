package com.SMA.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import com.SMA.entity.Role;
import com.SMA.entity.User;

public interface UserRoleMapper {

	@Insert("INSERT INTO USERDATA(UNAME,PWD,ROLE_ID,EMPID) VALUES(#{username}, #{password}, #{roleId}, #{empId})")
	void insertUser(User user);

	@Select("SELECT UNAME as username,PWD as password,ROLE_ID as roleId,EMPID as empId FROM USERDATA WHERE UNAME = #{username}")
	User findUser(String username);
	
	@Select("Select ROLE_ID as roleId,ROLE_NAME as roleName FROM ROLES")
	List<Role> getAllRoles();

}
