package com.SMA.controller;

import java.text.SimpleDateFormat;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.SMA.Constants;
import com.SMA.entity.Department;
import com.SMA.entity.Employee;
import com.SMA.entity.Permissionarios;
import com.SMA.mapper.EmployeeMapper;

@RestController
public class EmployeeController {

	final static Logger logger = LoggerFactory
			.getLogger(EmployeeController.class);

	@Autowired
	EmployeeMapper employeeMapper;

	@RequestMapping("/getDepartments")
	@ResponseBody
	public List<Department> getAllDepartments() {
		return employeeMapper.getDepartments();
	}

	@RequestMapping("/getPermisionarios")
	@ResponseBody
	public List<Permissionarios> getAllPermissionarios() {
		return employeeMapper.getPermissionarios();
	}

	@RequestMapping("/addEmployee")
	@ResponseBody
	public String addEmployee(@RequestBody Employee employee) throws Exception {
		SimpleDateFormat format = new SimpleDateFormat("DD/MM/YYYY");
		employee.setBirthDateAsDate(format.parse(employee
				.getBirthDate()));
		employee.setTarjetonExpedicionAsDate(format.parse(employee
				.getTarjetonExpedicion()));
		employee.setTarjetonVenicimientoAsDate(format.parse(employee
				.getTarjetonVenicimiento()));
		employee.setLicenciaVenicimientoAsDate(
				format.parse(employee.getLicenciaVenicimiento()));
		employee.setLicenciaExpedicionAsDate(
				format.parse(employee.getLicenciaExpedicion()));
		
		employeeMapper.addEmployee(employee);
		return Constants.EMP_ADDED;
	}

}
