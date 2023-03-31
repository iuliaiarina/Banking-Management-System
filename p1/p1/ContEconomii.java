package p1;

import java.sql.Date;

public class ContEconomii extends Cont {
	private int dobanda;

	public ContEconomii(Double suma, String IBAN, String CNP, Date data_creare, int dobanda) {
		super(suma, IBAN, CNP, data_creare);
		this.dobanda = dobanda;
	}

	public ContEconomii(int dobanda) {
		this.dobanda = dobanda;
	}
	
	
	
	public int getDobanda() {
		return dobanda;
	}

	public void setDobanda(int dobanda) {
		this.dobanda = dobanda;
	}

	@Override
	public String toString() {
		return "ContEconomii{" + "dobanda=" + dobanda + '}';
	}

}
