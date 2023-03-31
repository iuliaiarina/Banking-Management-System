package p1;

public class Persoana {
    private String nume;
    private String prenume;
    private String parola;
    private String adresa;
    private String telefon;
    private String email;
    private String contact;
    private String CNP;

    public Persoana(String nume, String prenume, String parola, String adresa, String telefon, String email, String contact, String CNP) {
        this.nume = nume;
        this.prenume = prenume;
        this.parola = parola;
        this.adresa = adresa;
        this.telefon = telefon;
        this.email = email;
        this.contact = contact;
        this.CNP = CNP;
    }

    public Persoana() {}

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public String getParola() {
        return parola;
    }

    public void setParola(String parola) {
        this.parola = parola;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getCNP() {
        return CNP;
    }

    public void setCNP(String CNP) {
        this.CNP = CNP;
    }

    @Override
    public String toString() {
        return "Persoana{" +
                "nume='" + nume + '\'' +
                ", prenume='" + prenume + '\'' +
                ", parola='" + parola + '\'' +
                ", adresa='" + adresa + '\'' +
                ", telefon='" + telefon + '\'' +
                ", email='" + email + '\'' +
                ", contact='" + contact + '\'' +
                ", CNP='" + CNP + '\'' +
                '}';
    }
}
