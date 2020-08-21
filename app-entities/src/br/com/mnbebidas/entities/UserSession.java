package br.com.mnbebidas.entities;

public final class UserSession {

	private static UserSession instance;

	private int cdLogin;
	private String userName;
	private String privileges;

    private UserSession(int cdLogin, String userName, String privileges) {
        this.cdLogin = cdLogin;
        this.userName = userName;
        this.privileges = privileges;
    }

	public static UserSession getInstace(int cdLogin, String userName, String privileges) {
        if (instance == null) {
            instance = new UserSession(cdLogin, userName, privileges);
        }
		else
		{
			instance.setCdLogin(cdLogin);
			instance.setUserName(userName);
			instance.setPrivileges(privileges);
		}
        return instance;
    }

    public static UserSession getInstace() throws IllegalStateException {
		if (instance == null) {
			//Gera uma sessão se os dados dela forem obtidos antes da sua inicialização
			throw new IllegalStateException("Sessão do usuário não foi inicializada");
		}
        return instance;
    }

	public int getCdLogin() {
		return cdLogin;
	}

	public void setCdLogin(int cdLogin) {
		this.cdLogin = cdLogin;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPrivileges() {
		return privileges;
	}

	public void setPrivileges(String privileges) {
		this.privileges = privileges;
	}

	public void cleanUserSession() {
		userName = "";// or null
		privileges = "";// or null
	}

	@Override
	public String toString() {
		return "UserSession [cdLogin=" + cdLogin + ", userName=" + userName + ", privileges=" + privileges + "]";
	}

}