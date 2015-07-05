package com.SMA.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.SMA.entity.Role;
import com.SMA.entity.User;
import com.SMA.entity.UserRoleMap;

public interface UserRoleMapper {

	@Insert("INSERT INTO USERDATA(UNAME,PWD,EMPID) VALUES(#{username}, #{password}, #{empId})")
	void insertUser(User user);

	@Select("SELECT UNAME as username,PWD as password,EMPID as empId FROM USERDATA WHERE UNAME = #{username}")
	User findUser(String username);

	@Select("Select ROLE_ID as roleId,ROLE_NAME as roleName FROM ROLES")
	List<Role> getAllRoles();

	@Insert("INSERT INTO USERROLEMAP(UNAME,ROLE_ID) VALUES(#{username}, #{roleId})")
	void insertRole(UserRoleMap userRoleMap);

	@Update("UPDATE USERROLEMAP SET ROLE_ID = #{roleId} WHERE UNAME = #{username}")
	void updateRole(UserRoleMap userRoleMap);

	@Update("UPDATE USERDATA SET EMPID = #{empId} ,PWD = #{password}  WHERE UNAME = #{username}")
	void updateUser(User user);

	@Select("SELECT UNAME as username,ROLE_ID as roleId FROM USERROLEMAP WHERE UNAME = #{0} AND ROLE_ID = #{1}")
	UserRoleMap getUserRole(String username, Integer roleId);

	@Select("SELECT UNAME as username,PWD as password,EMPID as empId FROM USERDATA")
	List<User> getAllUsers();

	@Select("Select RL.ROLE_ID as roleId,RL.ROLE_NAME as roleName FROM ROLES RL,USERROLEMAP UL WHERE UL.UNAME =#{0} AND UL.ROLE_ID = RL.ROLE_ID")
	List<Role> getRolesForUser(String username);
	
	
	@Delete("DELETE FROM USERDATA WHERE UNAME=#{0}")
	void deleteUser(String username);
	
	@Delete("DELETE FROM USERROLEMAP WHERE UNAME = #{0}")
	void deleteUserRoles(String username);

}
