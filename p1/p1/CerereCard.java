package p1;

public class CerereCard {
    private String CNP;
    private String IBAN;
    private String CNP_admin;
    private String CNP_angajat;
    private int cerere_id;
    
    
    public CerereCard(String cNP, String iBAN, String cNP_admin, String cNP_angajat, int cerere_id) {
		super();
		CNP = cNP;
		IBAN = iBAN;
		CNP_admin = cNP_admin;
		CNP_angajat = cNP_angajat;
		this.cerere_id = cerere_id;
	}

	public String getIBAN() {
		return IBAN;
	}

	public void setIBAN(String iBAN) {
		IBAN = iBAN;
	}

	public int getCerere_id() {
		return cerere_id;
	}

	public void setCerere_id(int cerere_id) {
		this.cerere_id = cerere_id;
	}

	public String getCNP() {
        return CNP;
    }

    public void setCNP(String CNP) {
        this.CNP = CNP;
    }

    public String getCNP_admin() {
        return CNP_admin;
    }

    public void setCNP_admin(String CNP_admin) {
        this.CNP_admin = CNP_admin;
    }

    public String getCNP_angajat() {
        return CNP_angajat;
    }

    public void setCNP_angajat(String CNP_angajat) {
        this.CNP_angajat = CNP_angajat;
    }

    @Override
    public String toString() {
        return "CerereCard{" +
                "CNP='" + CNP + '\'' +
                ", CNP_admin='" + CNP_admin + '\'' +
                ", CNP_angajat='" + CNP_angajat + '\'' +
                '}';
    }
}
