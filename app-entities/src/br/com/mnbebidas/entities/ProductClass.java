package br.com.mnbebidas.entities;

public class ProductClass {

	private int cdProduct;
	private String noBarCode;
	private String nmDescription;
	private String dtExpirationDate;
	private double noAmountPaid;
	private double noSaleValue;
	private double noProfit;
	private int noQuantity;

	public int getCdProduct() {
		return cdProduct;
	}

	public void setCdProduct(int cdProduct) {
		this.cdProduct = cdProduct;
	}

	public String getNoBarCode() {
		return noBarCode;
	}

	public void setNoBarCode(String noBarCode) {
		this.noBarCode = noBarCode;
	}

	public String getNmDescription() {
		return nmDescription;
	}

	public void setNmDescription(String nmDescription) {
		this.nmDescription = nmDescription;
	}

	public String getDtExpirationDate() {
		return dtExpirationDate;
	}

	public void setDtExpirationDate(String dtExpirationDate) {
		this.dtExpirationDate = dtExpirationDate;
	}

	public double getNoAmountPaid() {
		return noAmountPaid;
	}

	public void setNoAmountPaid(double noAmountPaid) {
		this.noAmountPaid = noAmountPaid;
	}

	public double getNoSaleValue() {
		return noSaleValue;
	}

	public void setNoSaleValue(double noSaleValue) {
		this.noSaleValue = noSaleValue;
	}

	public double getNoProfit() {
		return noProfit;
	}

	public void setNoProfit(double noProfit) {
		this.noProfit = noProfit;
	}

	public int getNoQuantity() {
		return noQuantity;
	}

	public void setNoQuantity(int noQuantity) {
		this.noQuantity = noQuantity;
	}

}
