package p1;

public class ContClient {
    private String IBAN;
    private String CNP;

    public String getIBAN() {
        return IBAN;
    }

    public void setIBAN(String IBAN) {
        this.IBAN = IBAN;
    }

    public String getCNP() {
        return CNP;
    }

    public void setCNP(String CNP) {
        this.CNP = CNP;
    }

    public ContClient(String IBAN, String CNP) {
        this.IBAN = IBAN;
        this.CNP = CNP;
    }

    @Override
    public String toString() {
        return "ContClient{" +
                "IBAN='" + IBAN + '\'' +
                ", CNP='" + CNP + '\'' +
                '}';
    }
}
