package p1;

import java.sql.Date;

public class Depozit extends Cont{

    private int dobandaId;
    private Date data_ultim_mod;
	public Depozit(double suma, String IBAN, String CNP, Date data_creare, int dobandaId, Date data_ultim_mod) {
		super(suma, IBAN, CNP, data_creare);
		this.dobandaId = dobandaId;
		this.data_ultim_mod = data_ultim_mod;
	}
	public int getDobandaId() {
		return dobandaId;
	}
	public void setDobandaId(int dobandaId) {
		this.dobandaId = dobandaId;
	}
	public Date getData_ultim_mod() {
		return data_ultim_mod;
	}
	public void setData_ultim_mod(Date data_ultim_mod) {
		this.data_ultim_mod = data_ultim_mod;
	}
	@Override
	public String toString() {
		return "Depozit [dobandaId=" + dobandaId + ", data_ultim_mod=" + data_ultim_mod + "]";
	}
    

}
