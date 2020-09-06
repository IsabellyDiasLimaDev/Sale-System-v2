package br.com.mnbebidas.entities;

public class CashierUserClass {
	
	private static CashierUserClass instance;
	
	private int cdLogin;
	private int cdCashier;
	


	private CashierUserClass(int cdLogin, int cdCashier) {
		this.cdLogin = cdLogin;
		this.cdCashier = cdCashier;
	}

	public static CashierUserClass getInstace(int cdLogin, int cdCashier) {
        if (instance == null) {
            instance = new CashierUserClass(cdLogin, cdCashier);
        }
		else
		{
			instance.setCdLogin(cdLogin);
			instance.setCdCashier(cdCashier);
		}
        return instance;
    }

    public static CashierUserClass getInstace() throws IllegalStateException {
		if (instance == null) {
			//Gera uma sessão se os dados dela forem obtidos antes da sua inicialização
			throw new IllegalStateException("Sessão do usuário com o caixa caixa não foi inicializada");
		}
        return instance;
    }

	public int getCdLogin() {
		return cdLogin;
	}

	public void setCdLogin(int cdLogin) {
		this.cdLogin = cdLogin;
	}

	public int getCdCashier() {
		return cdCashier;
	}

	public void setCdCashier(int cdCashier) {
		this.cdCashier = cdCashier;
	}
	
	

}
