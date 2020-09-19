package br.com.mnbebidas.entities;

public class CashierClass {

	private static CashierClass instance;

	private int cdProduct;
	private String nmDescription;
	private double noSaleValue;
	private int noQuantity;

	private CashierClass(int cdProduct, String nmDescription, double noSaleValue, int noQuantity) {

		this.cdProduct = cdProduct;
		this.nmDescription = nmDescription;
		this.noSaleValue = noSaleValue;
		this.noQuantity = noQuantity;
	}

	public static CashierClass getInstance(int cdProduct, String nmDescription, double noSaleValue, int noQuantity) {
		if (instance == null) {
			instance = new CashierClass(cdProduct, nmDescription, noSaleValue, noQuantity);
		} else {
			instance.setCdProduct(cdProduct);
			instance.setNmDescription(nmDescription);
			instance.setNoSaleValue(noSaleValue);
			instance.setNoQuantity(noQuantity);
			
		}
		return instance;
	}
	
    public static CashierClass getInstace() throws IllegalStateException {
		if (instance == null) {
			//Gera uma sessão se os dados dela forem obtidos antes da sua inicialização
			throw new IllegalStateException("Classe do caixa não definida");
		}
        return instance;
    }

	public int getCdProduct() {
		return cdProduct;
	}

	public void setCdProduct(int cdProduct) {
		this.cdProduct = cdProduct;
	}

	public String getNmDescription() {
		return nmDescription;
	}

	public void setNmDescription(String nmDescription) {
		this.nmDescription = nmDescription;
	}

	public double getNoSaleValue() {
		return noSaleValue;
	}

	public void setNoSaleValue(double noSaleValue) {
		this.noSaleValue = noSaleValue;
	}

	public int getNoQuantity() {
		return noQuantity;
	}

	public void setNoQuantity(int noQuantity) {
		this.noQuantity = noQuantity;
	}
	
	

}
