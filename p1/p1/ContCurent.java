package p1;

import java.sql.Date;

public class ContCurent extends Cont{

    public ContCurent(double suma, String IBAN, String CNP, Date data_creare) {
        super(suma, IBAN, CNP, data_creare);
    }

    @Override
    public String toString() {
        return "ContCurent{}";
    }
}
