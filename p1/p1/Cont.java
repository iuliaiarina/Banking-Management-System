package p1;

import java.sql.Date;

public class Cont {
	private double suma;
	private String IBAN;
	private String CNP;
	private Date data_creare;

	public double getSuma() {
		return suma;
	}

	public String getIBAN() {
		return IBAN;
	}

	public String getCNP() {
		return CNP;
	}

	public Date getDataCreare() {
		return data_creare;
	}

	public void setSuma(int suma) {
		this.suma = suma;
	}

	public void setIBAN(String IBAN) {
		this.IBAN = IBAN;
	}

	public void setCNP(String CNP) {
		this.CNP = CNP;
	}

	public void setDataCreeare(Date dataCreeare) {
		this.data_creare = dataCreeare;
	}

	public Cont(double suma, String IBAN, String CNP, Date data_creare) {
		this.suma = suma;
		this.IBAN = IBAN;
		this.CNP = CNP;
		this.data_creare = data_creare;
	}

	public Cont() {
	}

	@Override
	public String toString() {
		return "Cont{" + "suma=" + suma + ", IBAN='" + IBAN + '\'' + ", CNP='" + CNP + '\'' + ", dataCreeare="
				+ data_creare + '}';
	}

}
