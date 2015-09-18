package com.SMA.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.SMA.entity.Department;
import com.SMA.entity.Employee;
import com.SMA.entity.Permissionarios;

public interface EmployeeMapper {

	@Select("SELECT RFC AS rfc,PATERNO AS name FROM PERMISIONARIOS")
	public List<Permissionarios> getPermissionarios();

	@Select("SELECT ID AS id,DEPARTMENT AS name FROM DEPARTMENTS")
	public List<Department> getDepartments();

	@Insert("INSERT INTO EMPLOYEES(ID,NAME,SURNAME1,SURNAME2,ALIAS,CLAVE,CURP,DOB,BIRTHPLACE,STREET,COLONY,MUNICIPAL,STATE,TELEPHONE,TARJETON_NUMERO,TARJETON_TIPO,TARJETON_EXPEDICION,TARJETON_VENICIMIENTO,"
			+ "DEPARTMENT,RFC,IMSS,LICENCIA_NUMERO,LICENCIA_TIPO,LICENCIA_EXPEDICION,LICENCIA_VENICIMIENTO) "
			+ "VALUES(#{id},#{name},#{surname1},#{surname2},#{alias},#{clave},#{curp},#{birthDateAsDate},#{birthPlace},#{street},#{colony},#{municipal},#{state},#{telephone},#{tarjetonNumero},#{tarjetonTipo},#{tarjetonVenicimientoAsDate},"
			+ "#{tarjetonExpedicionAsDate},#{department},#{rfc},#{imss},#{licenciaNumero},#{licenciaTipo},#{licenciaVenicimientoAsDate},#{licenciaExpedicionAsDate})")
	public void addEmployee(Employee employee);
	
	@Update("UPDATE EMPLOYEES SET ID = #{id},NAME = #{name},SURNAME1 = #{surname1},SURNAME2 = #{surname2},ALIAS=#{alias},CLAVE = #{clave},CURP = #{curp},DOB = #{birthDateAsDate},BIRTHPLACE = #{birthplace},"
			+ "STREET = #{street},COLONY = #{colony},MUNICIPAL = #{municipal},STATE = #{state},TELEPHONE = #{telephone},TARJETON_NUMERO = #{tarjetonNumero},TARJETON_TIPO = #{tarjetonTipo},TARJETON_EXPEDICION = #{tarjetonExpedicionAsDate},"
			+ "TARJETON_VENICIMIENTO = #{tarjetonVenicimientoAsDate},DEPARTMENT = #{department},RFC = #{rfc},IMSS = #{imss},LICENCIA_NUMERO = #{licenciaNumero},LICENCIA_TIPO = #{licenciaTipo},"
			+ "LICENCIA_EXPEDICION = #{licenciaExpedicionAsDate},LICENCIA_VENICIMIENTO = #{licenciaVenicimientoAsDate}  WHERE ID=#{id}")
	public void updateEmployee(Employee employee);
	
	
	@Select("SELECT ID AS id,NAME AS name,SURNAME1 AS surname1,SURNAME2 AS surname2,ALIAS AS alias,CLAVE AS clave,CURP AS curp,DOB AS birthDate,BIRTHPLACE AS birthplace,STREET AS street,"
			+ "COLONY as colony,MUNICIPAL as municipal,STATE as state,TELEPHONE AS telephone,TARJETON_NUMERO AS tarjetonNumero,TARJETON_TIPO As tarjetonTipo,TARJETON_EXPEDICION AS tarjetonExpedicion,"
			+ "TARJETON_VENICIMIENTO AS tarjetonVenicimiento,DEPARTMENT AS department,RFC AS rfc,IMSS AS imss,LICENCIA_NUMERO AS licenciaNumero,LICENCIA_TIPO AS licenciaTipo,"
			+ "LICENCIA_EXPEDICION AS licenciaExpedicion,LICENCIA_VENICIMIENTO AS licenciaVenicimiento  from EMPLOYEES")
	public List<Employee> getAllEmployees();
	
	@Delete("DELETE FROM EMPLOYEES where id=#{1}")
	public void deleteEmployee(String employeeId);

}
