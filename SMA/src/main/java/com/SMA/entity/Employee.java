package com.SMA.entity;

import java.util.Date;

public class Employee {

	public Date getTarjetonVenicimientoAsDate() {
		return tarjetonVenicimientoAsDate;
	}

	public void setTarjetonVenicimientoAsDate(Date tarjetonVenicimientoAsDate) {
		this.tarjetonVenicimientoAsDate = tarjetonVenicimientoAsDate;
	}

	public Date getTarjetonExpedicionAsDate() {
		return tarjetonExpedicionAsDate;
	}

	public void setTarjetonExpedicionAsDate(Date tarjetonExpedicionAsDate) {
		this.tarjetonExpedicionAsDate = tarjetonExpedicionAsDate;
	}

	public String getLicenciaNumero() {
		return licenciaNumero;
	}

	public void setLicenciaNumero(String licenciaNumero) {
		this.licenciaNumero = licenciaNumero;
	}

	public String getLicenciaTipo() {
		return licenciaTipo;
	}

	public void setLicenciaTipo(String licenciaTipo) {
		this.licenciaTipo = licenciaTipo;
	}

	public String getLicenciaVenicimiento() {
		return licenciaVenicimiento;
	}

	public void setLicenciaVenicimiento(String licenciaVenicimiento) {
		this.licenciaVenicimiento = licenciaVenicimiento;
	}

	public String getLicenciaExpedicion() {
		return licenciaExpedicion;
	}

	public void setLicenciaExpedicion(String licenciaExpedicion) {
		this.licenciaExpedicion = licenciaExpedicion;
	}

	public Date getLicenciaVenicimientoAsDate() {
		return licenciaVenicimientoAsDate;
	}

	public void setLicenciaVenicimientoAsDate(Date licenciaVenicimientoAsDate) {
		this.licenciaVenicimientoAsDate = licenciaVenicimientoAsDate;
	}

	public Date getLicenciaExpedicionAsDate() {
		return licenciaExpedicionAsDate;
	}

	public void setLicenciaExpedicionAsDate(Date licenciaExpedicionAsDate) {
		this.licenciaExpedicionAsDate = licenciaExpedicionAsDate;
	}

	private String id;
	private String name;
	private String surname1;
	private String surname2;
	private String alias;
	private String clave;
	private String curp;
	private Date birthDateAsDate;
	private String birthDate;
	private String birthPlace;
	private String street;
	private String colony;
	private String municipal;
	private String state;
	private String telephone;
	private String tarjetonNumero;
	private String tarjetonTipo;
	private String tarjetonVenicimiento;
	private String tarjetonExpedicion;
	private Date tarjetonVenicimientoAsDate;
	private Date tarjetonExpedicionAsDate;
	private String department;
	private String rfc;
	private String imss;
	private String licenciaNumero;
	private String licenciaTipo;
	private String licenciaVenicimiento;
	private String licenciaExpedicion;
	private Date licenciaVenicimientoAsDate;
	private Date licenciaExpedicionAsDate;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname1() {
		return surname1;
	}

	public void setSurname1(String surname1) {
		this.surname1 = surname1;
	}

	public String getSurname2() {
		return surname2;
	}

	public void setSurname2(String surname2) {
		this.surname2 = surname2;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public String getCurp() {
		return curp;
	}

	public void setCurp(String curp) {
		this.curp = curp;
	}

	public Date getBirthDateAsDate() {
		return birthDateAsDate;
	}

	public void setBirthDateAsDate(Date birthDateAsDate) {
		this.birthDateAsDate = birthDateAsDate;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public String getBirthPlace() {
		return birthPlace;
	}

	public void setBirthPlace(String birthPlace) {
		this.birthPlace = birthPlace;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getColony() {
		return colony;
	}

	public void setColony(String colony) {
		this.colony = colony;
	}

	public String getMunicipal() {
		return municipal;
	}

	public void setMunicipal(String municipal) {
		this.municipal = municipal;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getTarjetonNumero() {
		return tarjetonNumero;
	}

	public void setTarjetonNumero(String tarjetonNumero) {
		this.tarjetonNumero = tarjetonNumero;
	}

	public String getTarjetonVenicimiento() {
		return tarjetonVenicimiento;
	}

	public void setTarjetonVenicimiento(String tarjetonVenicimiento) {
		this.tarjetonVenicimiento = tarjetonVenicimiento;
	}

	public String getTarjetonExpedicion() {
		return tarjetonExpedicion;
	}

	public void setTarjetonExpedicion(String tarjetonExpedicion) {
		this.tarjetonExpedicion = tarjetonExpedicion;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getRfc() {
		return rfc;
	}

	public void setRfc(String rfc) {
		this.rfc = rfc;
	}

	public String getImss() {
		return imss;
	}

	public void setImss(String imss) {
		this.imss = imss;
	}

	public String getTarjetonTipo() {
		return tarjetonTipo;
	}

	public void setTarjetonTipo(String tarjetonTipo) {
		this.tarjetonTipo = tarjetonTipo;
	}

}
