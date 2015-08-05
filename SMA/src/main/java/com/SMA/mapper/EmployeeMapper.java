package com.SMA.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import com.SMA.entity.Department;
import com.SMA.entity.Employee;
import com.SMA.entity.Permissionarios;

public interface EmployeeMapper {

	@Select("SELECT RFC AS rfc,PATERNO AS name FROM PERMISIONARIOS")
	public List<Permissionarios> getPermissionarios();

	@Select("SELECT ID AS id,DEPARTMENT AS name FROM DEPARTMENTS")
	public List<Department> getDepartments();

	@Insert("INSERT INTO EMPLOYEES(ID,NAME,SURNAME1,SURNAME2,ALIAS,CLAVE,CURP,DOB,BIRTHPLACE,STREET,COLONY,MUNICIPAL,STATE,TELEPHONE,TARJETON_NUMERO,TARJETON_TIPO,TARJETO_EXPEDICION,TARJETON_VENICIMIENTO,"
			+ "DEPARTMENT,RFC,IMSS,LICENCIA_NUMERO,LICENCIA_TIPO,TARJETO_EXPEDICION,LICENCIA_VENICIMIENTO) "
			+ "VALUES(#id,#name,#surname1,#surname2,#alias,#clave,#curp,#birthDateAsDate,#birthPlace,#street,#colony,#municpal,#state,#telephone,#tarjetonNumero,#tarjetonTipo,#tarjetonVenicimientoAsDate,"
			+ "#tarjetonExpedicionAsDate,#department,#rfc,#imss,#tarjetonNumero,#tarjetonTipo,#tarjetonVenicimientoAsDate,#tarjetonExpedicionAsDate)")
	public void addEmployee(Employee employee);

}
