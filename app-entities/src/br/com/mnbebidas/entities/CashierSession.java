package br.com.mnbebidas.entities;

public class CashierSession {
	
	private static CashierSession instance;
	
	private int cdCashier;
	private boolean opening;
	private boolean closing;
	

	
	private CashierSession(int cdCashier, boolean opening, boolean closing) {
		this.cdCashier = cdCashier;
		this.opening = opening;
		this.closing = closing;
	}

	public static CashierSession getInstance(int cdCashier,boolean opening,boolean closing) {
		if (instance == null) {
            instance = new CashierSession(cdCashier,opening,closing);
        }
		else
		{
			instance.setCdCashier(cdCashier);
			instance.setOpening(opening);
			instance.setClosing(closing);
		}
        return instance;
	}
	
    public static CashierSession getInstace() throws IllegalStateException {
		if (instance == null) {
			//Gera uma sessão se os dados dela forem obtidos antes da sua inicialização
			throw new IllegalStateException("Sessão do caixa não foi inicializada");
		}
        return instance;
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

	@Override
	public String toString() {
		return "Caixa " + cdCashier;
	}
	
	
	
	
}
