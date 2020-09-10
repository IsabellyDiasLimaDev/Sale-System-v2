package br.com.mnbebidas.entities;

public class SaleViewClass {

	private static SaleViewClass instance;
	private int cdSale;
	private int noQuantityTotal;
	private double noTotalValue;
	private String nmTypePay;
	private String nmUser;
	private int cdCashier;

	private SaleViewClass(int cdSale, int noQuantityTotal, double noTotalValue, String nmTypePay, String nmUser,
			int cdCashier) {
		this.cdSale = cdSale;
		this.noQuantityTotal = noQuantityTotal;
		this.noTotalValue = noTotalValue;
		this.nmTypePay = nmTypePay;
		this.nmUser = nmUser;
		this.cdCashier = cdCashier;
	}

	public static SaleViewClass getInstance(int cdSale, int noQuantityTotal, double noTotalValue, String nmTypePay, String nmUser,
			int cdCashier) {
		
		if(instance == null) {
			instance = new SaleViewClass(cdSale, noQuantityTotal, noTotalValue, nmTypePay, nmUser, cdCashier);
		}
		else {
			instance.setCdCashier(cdCashier);
			instance.setCdSale(cdSale);
			instance.setNmTypePay(nmTypePay);
			instance.setNmUser(nmUser);
			instance.setNoQuantityTotal(noQuantityTotal);
			instance.setNoTotalValue(noTotalValue);
		}
		
		return instance;
	}


	public int getCdSale() {
		return cdSale;
	}

	public void setCdSale(int cdSale) {
		this.cdSale = cdSale;
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

	public String getNmTypePay() {
		return nmTypePay;
	}

	public void setNmTypePay(String nmTypePay) {
		this.nmTypePay = nmTypePay;
	}

	public String getNmUser() {
		return nmUser;
	}

	public void setNmUser(String nmUser) {
		this.nmUser = nmUser;
	}

	public int getCdCashier() {
		return cdCashier;
	}

	public void setCdCashier(int cdCashier) {
		this.cdCashier = cdCashier;
	}
	
	

}
