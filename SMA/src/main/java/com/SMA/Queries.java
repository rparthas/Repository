package com.SMA;

public class Queries {

	public static final String SELECT_USER = "SELECT UNAME,PWD,'TRUE'  FROM USERDATA WHERE UNAME= ?";
	public static final String SELECT_ROLE = "SELECT US.UNAME,RL.ROLE_NAME FROM USERDATA US,ROLES RL WHERE US.UNAME=? AND RL.ROLE_ID = US.ROLE_ID";
}
