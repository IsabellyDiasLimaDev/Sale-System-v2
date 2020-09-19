package br.com.mnbebidas.entities;

public class UserClass {

	private int cdLogin;
	private String nmEmail;
	private String nmUser;
	private String nmPassword;
	private String dsType;
	private String dsStatus;

	public int getCdLogin() {
		return cdLogin;
	}

	public void setCdLogin(int cdLogin) {
		this.cdLogin = cdLogin;
	}

	public String getNmEmail() {
		return nmEmail;
	}

	public void setNmEmail(String nmEmail) {
		this.nmEmail = nmEmail;
	}

	public String getNmUser() {
		return nmUser;
	}

	public void setNmUser(String nmUser) {
		this.nmUser = nmUser;
	}

	public String getNmPassword() {
		return nmPassword;
	}

	public void setNmPassword(String nmPassword) {
		this.nmPassword = nmPassword;
	}

	public String getDsType() {
		return dsType;
	}

	public void setDsType(String dsType) {
		this.dsType = dsType;
	}

	public String getDsStatus() {
		return dsStatus;
	}

	public void setDsStatus(String dsStatus) {
		this.dsStatus = dsStatus;
	}

}
