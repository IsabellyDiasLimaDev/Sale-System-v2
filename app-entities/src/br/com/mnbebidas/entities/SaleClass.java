package br.com.mnbebidas.entities;

public class SaleClass {

	private static SaleClass instance;

	private int cdLogin;
	private int cdCashier;
	private int cdTypePay;
	private int noQuantityTotal;
	private double noTotalValue;

	private SaleClass( int cdLogin, int cdCashier,int cdTypePay ,int noQuantityTotal, double noTotalValue) {
		this.cdLogin = cdLogin;
		this.cdCashier = cdCashier;
		this.cdTypePay = cdTypePay;
		this.noQuantityTotal = noQuantityTotal;
		this.noTotalValue = noTotalValue;
	}

	public static SaleClass getInstance( int cdLogin, int cdCashier,int cdTypePay ,int noQuantityTotal,
			double noTotalValue) {
		if(instance == null) {
			instance = new SaleClass(cdLogin, cdCashier, cdTypePay,noQuantityTotal, noTotalValue);
		}
		else {
			instance.setCdCashier(cdCashier);
			instance.setCdLogin(cdLogin);
			
			instance.setNoQuantityTotal(noQuantityTotal);
			instance.setNoTotalValue(noTotalValue);
		}
		return instance;
	}
	
    public static SaleClass getInstance() throws IllegalStateException {
		if (instance == null) {
			//Gera uma sessão se os dados dela forem obtidos antes da sua inicialização
			throw new IllegalStateException("Sessão do venda não foi inicializada");
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
	
	public int getCdTypePay() {
		return cdTypePay;
	}

	public void setCdTypePay(int cdTypePay) {
		this.cdTypePay = cdTypePay;
	}

	public int getNoQuantityTotal() {
		return noQuantityTotal;
	}

	public void setNoQuantityTotal(int noQuantityTotal) {
		this.noQuantityTotal = noQuantityTotal;
	}

	public double getNoTotalValue() {
		return noTotalValue;
	}

	public void setNoTotalValue(double noTotalValue) {
		this.noTotalValue = noTotalValue;
	}

}
