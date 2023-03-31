package p1;

public class ListaContacte {
    private String comentariu;
    private String nume_client_destinatie;
    private String IBAN_destinatie;
    private String CNP;
    private int lista_id;


	public String getComentariu() {
        return comentariu;
    }

    public void setComentariu(String comentariu) {
        this.comentariu = comentariu;
    }

    public String getNume_client_destinatie() {
        return nume_client_destinatie;
    }

    public void setNume_client_destinatie(String nume_client_destinatie) {
        this.nume_client_destinatie = nume_client_destinatie;
    }

    public String getIBAN_destinatie() {
        return IBAN_destinatie;
    }

    public void setIBAN_destinatie(String IBAN_destinatie) {
        this.IBAN_destinatie = IBAN_destinatie;
    }

    public String getCNP() {
        return CNP;
    }

    public void setCNP(String CNP) {
        this.CNP = CNP;
    }

    public int getLista_id() {
        return lista_id;
    }

    public void setLista_id(int lista_id) {
        this.lista_id = lista_id;
    }

    public ListaContacte(String comentariu, String nume_client_destinatie, String IBAN_destinatie, String CNP, int lista_id) {
        this.comentariu = comentariu;
        this.nume_client_destinatie = nume_client_destinatie;
        this.IBAN_destinatie = IBAN_destinatie;
        this.CNP = CNP;
        this.lista_id = lista_id;
    }

    @Override
    public String toString() {
        return "ListaContacte{" +
                "comentariu='" + comentariu + '\'' +
                ", nume_client_destinatie='" + nume_client_destinatie + '\'' +
                ", IBAN_destinatie='" + IBAN_destinatie + '\'' +
                ", CNP='" + CNP + '\'' +
                ", lista_id=" + lista_id +
                '}';
    }
}
