package p1;

public class Dobanda {
    private int perioada;
    private int dobanda;
    private int dobandaId;

    public int getPerioada() {
        return perioada;
    }

    public void setPerioada(int perioada) {
        this.perioada = perioada;
    }

    public int getDobanda() {
        return dobanda;
    }

    public void setDobanda(int dobanda) {
        this.dobanda = dobanda;
    }

    public int getDobandaId() {
        return dobandaId;
    }

    public void setDobandaId(int dobandaId) {
        this.dobandaId = dobandaId;
    }

    public Dobanda(int perioada, int dobanda, int dobandaId) {
        this.perioada = perioada;
        this.dobanda = dobanda;
        this.dobandaId = dobandaId;
    }

    public Dobanda() {
    }

    @Override
    public String toString() {
        return "Dobanda{" +
                "perioada=" + perioada +
                ", dobanda=" + dobanda +
                ", dobandaId=" + dobandaId +
                '}';
    }
}
