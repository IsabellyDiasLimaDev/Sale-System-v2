package br.com.mnbebidas.entities;

public class CashierUserClass {
	private int cdLogin;
	private int cdCashier;
	private boolean opening;
	private boolean closing;

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

	public boolean isOpening() {
		return opening;
	}

	public void setOpening(boolean opening) {
		this.opening = opening;
	}

	public boolean isClosing() {
		return closing;
	}

	public void setClosing(boolean closing) {
		this.closing = closing;
	}

}
