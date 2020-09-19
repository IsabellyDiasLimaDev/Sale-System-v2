package br.com.mnbebidas.entities;

public class SaleSession {
	
	private static SaleSession instance;
	
	private int cdSale;
	
	private SaleSession(int cdSale) {
		this.cdSale = cdSale;
	}
	
	public static SaleSession getInstance(int cdSale) {
		if (instance == null) {
            instance = new SaleSession(cdSale);
        }
		else
		{
			instance.setCdSale(cdSale);
		}
        return instance;
	}
	
    public static SaleSession getInstance() throws IllegalStateException {
		if (instance == null) {
			//Gera uma sessão se os dados dela forem obtidos antes da sua inicialização
			throw new IllegalStateException("Sessão da venda não foi inicializada");
		}
        return instance;
    }

	public int getCdSale() {
		return cdSale;
	}

	public void setCdSale(int cdSale) {
		this.cdSale = cdSale;
	}
	
	
}
