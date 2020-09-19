package br.com.mnbebidas.entities;

public class PaymentClass {
	
	private static PaymentClass instance;
	private int cdTypePay;
	private int cdSale;
	private double parcialValue;
	
	

	private PaymentClass(int cdTypePay, int cdSale, double parcialValue) {
		this.cdTypePay = cdTypePay;
		this.cdSale = cdSale;
		this.parcialValue = parcialValue;
	}
	
	public static PaymentClass getInstance(int cdTypePay, int cdSale, double parcialValue) {
		if(instance == null) {
			instance = new PaymentClass(cdTypePay, cdSale, parcialValue);
		}
		else {
			instance.setCdSale(cdSale);
			instance.setCdTypePay(cdTypePay);
			instance.setParcialValue(parcialValue);
		}
		
		return instance;
	}
	
    public static PaymentClass getInstance() throws IllegalStateException {
		if (instance == null) {
			//Gera uma sessão se os dados dela forem obtidos antes da sua inicialização
			throw new IllegalStateException("Sessão do pagamento não foi inicializada");
		}
        return instance;
    }

	public int getCdTypePay() {
		return cdTypePay;
	}

	public void setCdTypePay(int cdTypePay) {
		this.cdTypePay = cdTypePay;
	}

	public int getCdSale() {
		return cdSale;
	}

	public void setCdSale(int cdSale) {
		this.cdSale = cdSale;
	}

	public double getParcialValue() {
		return parcialValue;
	}

	public void setParcialValue(double parcialValue) {
		this.parcialValue = parcialValue;
	}

	@Override
	public String toString() {
		return "PaymentClass [cdTypePay=" + cdTypePay + ", cdSale=" + cdSale + ", parcialValue=" + parcialValue + "]";
	}
	
	

}
