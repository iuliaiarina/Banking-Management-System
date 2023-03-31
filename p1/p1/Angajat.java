package p1;

public class Angajat extends Persoana{
    private int norma;
    private int salariu;
    private String departament;
    private String sucursala;

    public Angajat(String nume, String prenume, String parola, String adresa, String telefon, String email, String contact, String CNP, int norma, int salariu, String departament, String sucursala) {
        super(nume, prenume, parola, adresa, telefon, email, contact, CNP);
        this.norma = norma;
        this.salariu = salariu;
        this.departament = departament;
        this.sucursala = sucursala;

    }

    public Angajat() {
    }

    public int getNorma() {
        return norma;
    }

    public void setNorma(int norma) {
        this.norma = norma;
    }

    public int getSalariu() {
        return salariu;
    }

    public void setSalariu(int salariu) {
        this.salariu = salariu;
    }

    public String getDepartament() {
        return departament;
    }

    public void setDepartament(String departament) {
        this.departament = departament;
    }

    public String getSucursala() {
        return sucursala;
    }

    public void setSucursala(String sucursala) {
        this.sucursala = sucursala;
    }


    @Override
    public String toString() {
        return "Angajat{" +
                "norma=" + norma +
                ", salariu=" + salariu +
                ", departament=" + departament +
                ", sucursala='" + sucursala + '\'' + '}';
    }
}
