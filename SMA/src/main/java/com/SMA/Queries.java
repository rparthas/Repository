package com.SMA;

public class Queries {

	public static final String SELECT_USER = "SELECT USERNAME,PASSWORD,'TRUE'  FROM USERS WHERE USERNAME= ?";
	public static final String SELECT_ROLE = "SELECT US.USERNAME,RL.ROLE_NAME FROM USERS US,ROLES RL WHERE US.USERNAME=? AND RL.ROLE_ID = US.ROLE_ID";
}
