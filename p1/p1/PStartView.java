package p1;

import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class PStartView extends JFrame{

	private PStartModel model;

	 private JFrame frame = new JFrame("Pagina login");
     private JPanel panel = new JPanel();
     private JLabel labelCNP = new JLabel("Introduceti CNP-ul dumneavoastra: ");
     private JTextField textCNP = new JTextField();
     private JLabel labelPassword = new JLabel("Introduceti parola dumneavoastra: ");
     private JTextField textPassword = new JTextField();
     private JButton buttonLogin = new JButton("Login");
     private JComboBox boxTip = new JComboBox<String>(new String[]{"Client", "Administrator", "Angajat"});
	 private JLabel labelMesaj = new JLabel("mesaje de eroare");
	
     public PStartView() {
		super();
		 frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	     frame.setSize(600,200);
	     panel.setLayout(new GridLayout(4,2));
	     panel.add(labelCNP);
	     panel.add(textCNP);
	     panel.add(labelPassword);
	     panel.add(textPassword);
	     panel.add(buttonLogin);
		 panel.add(boxTip);
		 panel.add(labelMesaj);
		 frame.setContentPane(panel);
	     frame.setVisible(true);
	}
     
     void login(ActionListener listenForButton){
    	 buttonLogin.addActionListener(listenForButton);
     }
     
     
	public JFrame getFrame() {
		return frame;
	}
	public void setFrame(JFrame frame) {
		this.frame = frame;
	}
	public JPanel getPanel() {
		return panel;
	}
	public void setPanel(JPanel panel) {
		this.panel = panel;
	}
	public JLabel getLabelCNP() {
		return labelCNP;
	}
	public void setLabelCNP(JLabel labelCNP) {
		this.labelCNP = labelCNP;
	}
	public JTextField getTextCNP() {
		return textCNP;
	}
	public void setTextCNP(JTextField textCNP) {
		this.textCNP = textCNP;
	}
	public JLabel getLabelPassword() {
		return labelPassword;
	}
	public void setLabelPassword(JLabel labelPassword) {
		this.labelPassword = labelPassword;
	}
	public JTextField getTextPassword() {
		return textPassword;
	}
	public void setTextPassword(JTextField textPassword) {
		this.textPassword = textPassword;
	}
	public JButton getButtonLogin() {
		return buttonLogin;
	}
	public void setButtonLogin(JButton buttonLogin) {
		this.buttonLogin = buttonLogin;
	}
	public JComboBox getBoxTip() {
		return boxTip;
	}
	public void setBoxTip(JComboBox boxTip) {
		this.boxTip = boxTip;
	}

	public JLabel getLabelMesaj() {
		return labelMesaj;
	}
	public void setLabelMesaj(String labelMesaj) {
		this.labelMesaj.setText(labelMesaj);
	}

}
