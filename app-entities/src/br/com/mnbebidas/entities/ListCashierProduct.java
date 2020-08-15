package br.com.mnbebidas.entities;

public class ListCashierProduct {

	private int position;
	private long noBarCode;
	private String nmDescription;
	private int noQuantity;
	private double noValue;
	private double totalValue;

	
	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public long getNoBarCode() {
		return noBarCode;
	}

	public void setNoBarCode(long noBarCode) {
		this.noBarCode = noBarCode;
	}

	public String getNmDescription() {
		return nmDescription;
	}

	public void setNmDescription(String nmDescription) {
		this.nmDescription = nmDescription;
	}

	public int getNoQuantity() {
		return noQuantity;
	}

	public void setNoQuantity(int noQuantity) {
		this.noQuantity = noQuantity;
	}

	public double getNoValue() {
		return noValue;
	}

	public void setNoValue(double noValue) {
		this.noValue = noValue;
	}

	public double getTotalValue() {
		return totalValue;
	}

	public void setTotalValue(double totalValue) {
		this.totalValue = totalValue;
	}

}
