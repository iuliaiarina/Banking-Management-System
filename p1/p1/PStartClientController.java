package p1;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

public class PStartClientController {
	private PStartClientView view;

	public PStartClientController(PStartClientView view) {
		super();
		this.view = view;
		this.view.logout(new Logout());
		this.view.listaContacte(new ListaContacte());
		this.view.conturi(new Conturi());
		this.view.cerereCard(new CerereCard());
		this.view.modificaDate(new ModificaDate());
	}
	
	class Logout implements ActionListener { // pt butonul de logout
		public void actionPerformed(ActionEvent e) {
			// inchid pag actuala
			view.getFrame().dispose();
			// deschidem start:
			PStartView view = new PStartView();
			PStartModel model = new PStartModel();
			PStartController controller = new PStartController(model, view);
			view.getFrame().setVisible(true);
		}
	};

	class ListaContacte implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			view.getFrame().dispose();
			PListaContacte plc = new PListaContacte(view.getCnp());
			plc.getFrame().setVisible(true);
		}
	};

	class Conturi implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			view.getFrame().dispose();
			PConturi pc = new PConturi(view.getCnp());
			pc.getFrame().setVisible(true);
		}
	};

	class CerereCard implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			view.getFrame().dispose();
			PCerereCard pcc = new PCerereCard(view.getCnp());
			pcc.getFrame().setVisible(true);
		}
	};

	class ModificaDate implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// inchid pag actuala
			view.getFrame().dispose();
			// deschidem pag respectiva:
			ModificaDatePersonale mdp = new ModificaDatePersonale(view.getCnp());
			mdp.getFrame().setVisible(true);
		}
	};

}
