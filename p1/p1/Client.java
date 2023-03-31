package p1;

import java.sql.Date;

public class Client extends Persoana{
    private Date DateNastere;
    private String sursaVenit;
    private int nrConturi;
    private boolean tranzactiiOnline;

    public Client(String nume, String prenume, String parola, String adresa, String telefon, String email, String contact, String CNP, Date DateNastere, String sursaVenit, int nrConturi, boolean tranzactiiOnline) {
        super(nume, prenume, parola, adresa, telefon, email, contact, CNP);
        this.DateNastere = DateNastere;
        this.sursaVenit = sursaVenit;
        this.nrConturi = nrConturi;
        this.tranzactiiOnline = tranzactiiOnline;
    }

    public Client() {};

    public Date getDateNastere() {
        return DateNastere;
    }

    public void setDateNastere(Date DateNastere) {
        this.DateNastere = DateNastere;
    }

    public String getSursaVenit() {
        return sursaVenit;
    }

    public void setSursaVenit(String sursaVenit) {
        this.sursaVenit = sursaVenit;
    }

    public int getNrConturi() {
        return nrConturi;
    }

    public void setNrConturi(int nrConturi) {
        this.nrConturi = nrConturi;
    }

    public boolean isTranzactiiOnline() {
        return tranzactiiOnline;
    }

    public void setTranzactiiOnline(boolean tranzactiiOnline) {
        this.tranzactiiOnline = tranzactiiOnline;
    }

    @Override
    public String toString() {
        return "Client{" +
                "DateNastere=" + DateNastere +
                ", sursaVenit='" + sursaVenit + '\'' +
                ", nrConturi=" + nrConturi +
                ", tranzactiiOnline=" + tranzactiiOnline +
                '}';
    }
}
