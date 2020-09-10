package br.com.mnbebidas.entities;

public class SaleProductViewClass {
	private static SaleProductViewClass instance;
	private String noBarCode;
	private String nmDescription;
	private double noValue;
	private int noQuantity;
	
	private SaleProductViewClass(String noBarCode, String nmDescription, double noValue, int noQuantity) {
		this.noBarCode = noBarCode;
		this.nmDescription = nmDescription;
		this.noValue = noValue;
		this.noQuantity = noQuantity;
	}

	public static SaleProductViewClass getInstance(String noBarCode, String nmDescription, double noValue, int noQuantity) {
		if(instance == null) {
			instance = new SaleProductViewClass(noBarCode, nmDescription, noValue, noQuantity);
					
		}
		else {
			instance.setNmDescription(nmDescription);
			instance.setNoBarCode(noBarCode);
			instance.setNoQuantity(noQuantity);
			instance.setNoValue(noValue);
		}
		return instance;
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

	public double getNoValue() {
		return noValue;
	}

	public void setNoValue(double noValue) {
		this.noValue = noValue;
	}

	public int getNoQuantity() {
		return noQuantity;
	}

	public void setNoQuantity(int noQuantity) {
		this.noQuantity = noQuantity;
	}
	
	
	
	
}
