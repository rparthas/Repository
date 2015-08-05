package com.SMA.controller.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.SMA.Application;
import com.SMA.controller.EmployeeController;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class EmployeeTest {

	final static Logger logger = LoggerFactory.getLogger(EmployeeTest.class);

	@Autowired
	private EmployeeController employeeController;

	@Test
	public void getDepartments() {
		logger.info(employeeController.getAllDepartments() + "");
	}

	@Test
	public void getAllUsers() {
		logger.info(employeeController.getAllPermissionarios() + "");
	}

}
