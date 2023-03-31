package p1;

import java.sql.Date;

public class Transfer {
	private String status;
	private String nume_client_destinatie;
	private String nume_client_sursa;
	private Date data_creare;
	private double suma;
	private String IBAN_destinatie;
	private String IBAN_sursa;
	private String CNP_angajat;
	private int transfer_id;

	
	

	public Transfer(String status, String nume_client_destinatie, String nume_client_sursa, Date data_creare,
			double suma, String iBAN_destinatie, String iBAN_sursa, String cNP_angajat, int transfer_id) {
		super();
		this.status = status;
		this.nume_client_destinatie = nume_client_destinatie;
		this.nume_client_sursa = nume_client_sursa;
		this.data_creare = data_creare;
		this.suma = suma;
		IBAN_destinatie = iBAN_destinatie;
		IBAN_sursa = iBAN_sursa;
		CNP_angajat = cNP_angajat;
		this.transfer_id = transfer_id;
	}

	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getNume_client_destinatie() {
		return nume_client_destinatie;
	}
	public void setNume_client_destinatie(String nume_client_destinatie) {
		this.nume_client_destinatie = nume_client_destinatie;
	}
	public String getNume_client_sursa() {
		return nume_client_sursa;
	}
	public void setNume_client_sursa(String nume_client_sursa) {
		this.nume_client_sursa = nume_client_sursa;
	}
	public Date getData_creare() {
		return data_creare;
	}
	public void setData_creare(Date data_creare) {
		this.data_creare = data_creare;
	}
	public double getSuma() {
		return suma;
	}
	public void setSuma(double suma) {
		this.suma = suma;
	}
	public String getIBAN_destinatie() {
		return IBAN_destinatie;
	}
	public void setIBAN_destinatie(String iBAN_destinatie) {
		IBAN_destinatie = iBAN_destinatie;
	}
	public String getIBAN_sursa() {
		return IBAN_sursa;
	}
	public void setIBAN_sursa(String iBAN_sursa) {
		IBAN_sursa = iBAN_sursa;
	}
	public String getCNP_angajat() {
		return CNP_angajat;
	}
	public void setCNP_angajat(String cNP_angajat) {
		CNP_angajat = cNP_angajat;
	}
	public int getTransfer_id() {
		return transfer_id;
	}
	public void setTransfer_id(int transfer_id) {
		this.transfer_id = transfer_id;
	}
	@Override
	public String toString() {
		return "Transfer{" + "status=" + status + ", nume_client_destinatie='" + nume_client_destinatie + '\''
				+ ", nume_client_sursa='" + nume_client_sursa + '\'' + ", data_creare=" + data_creare + ", suma=" + suma
				+ ", IBAN_destinatie='" + IBAN_destinatie + '\'' + ", IBAN_sursa='" + IBAN_sursa + '\''
				+ ", CNP_angajat='" + CNP_angajat + '\'' + ", transfer_id=" + transfer_id + '}';
	}
}
