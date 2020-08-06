package br.com.mnbebidas.entities;

public final class UserSession {

	private static UserSession instance;

	private String userName;
	private String privileges;

	public UserSession(String userName, String privileges) {
		this.userName = userName;
		this.privileges = privileges;
	}
	public UserSession() {
		
	}

	public static UserSession getInstace(String userName, String privileges) {
		if (instance == null) {
			instance = new UserSession(userName, privileges);
		}
		return instance;
	}

	public String getUserName() {
		return userName;
	}

	public String getPrivileges() {
		return privileges;
	}

	public void cleanUserSession() {
		userName = "";// or null
		privileges = "";// or null
	}

	@Override
	public String toString() {
		return "UserSession{" + "userName='" + userName + '\'' + ", privileges=" + privileges + '}';
	}
}