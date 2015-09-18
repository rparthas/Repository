package com.SMA.controller;

import java.text.SimpleDateFormat;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.SMA.Constants;
import com.SMA.entity.Department;
import com.SMA.entity.Employee;
import com.SMA.entity.Permissionarios;
import com.SMA.entity.Response;
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
	public Response addEmployee(@RequestBody Employee employee)
			throws Exception {
		SimpleDateFormat format = new SimpleDateFormat("DD/MM/YYYY");
		if (employee.getBirthDate() != null) {
			employee.setBirthDateAsDate(format.parse(employee.getBirthDate()));
		}
		if (employee.getTarjetonExpedicion() != null) {
			employee.setTarjetonExpedicionAsDate(format.parse(employee
					.getTarjetonExpedicion()));
		}
		if (employee.getTarjetonVenicimiento() != null) {
			employee.setTarjetonVenicimientoAsDate(format.parse(employee
					.getTarjetonVenicimiento()));
		}
		if (employee.getLicenciaVenicimiento() != null) {
			employee.setLicenciaVenicimientoAsDate(format.parse(employee
					.getLicenciaVenicimiento()));
		}
		if (employee.getLicenciaExpedicion() != null) {
			employee.setLicenciaExpedicionAsDate(format.parse(employee
					.getLicenciaExpedicion()));
		}
		if (employee.getId() != null) {
			employeeMapper.updateEmployee(employee);
		} else {
			employeeMapper.addEmployee(employee);
		}
		return new Response(Constants.EMP_ADDED);
	}

	@RequestMapping("/getAllEmployees")
	@ResponseBody
	public List<Employee> getAllEmployees() {
		return employeeMapper.getAllEmployees();
	}

	@RequestMapping("/deleteEmployee")
	@ResponseBody
	public Response deleteEmployee(@RequestParam String id) {
		employeeMapper.deleteEmployee(id);
		return new Response(Constants.EMP_DELETED);
	}

}
