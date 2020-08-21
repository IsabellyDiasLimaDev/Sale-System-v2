package br.com.mnbebidas.entities;

public class ListCashierClass {
	private int cdCashier;

	public int getCdCashier() {
		return cdCashier;
	}

	public void setCdCashier(int cdCashier) {
		this.cdCashier = cdCashier;
	}

	@Override
	public String toString() {
		return "Caixa " + cdCashier;
	}
	
	
}
