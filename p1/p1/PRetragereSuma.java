package p1;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class PRetragereSuma {
	private final static String USERNAME = "root";
	private final static String PASSWORD = "sssabb";
	private final static String DB_NAME = "Bancar";
	private final static String CONNECTION_LINK = "jdbc:mysql://localhost:3306/";

	private JFrame frame = new JFrame("Retragere suma");
	private JPanel contentPane;
	private JTextField suma;
	JButton back = new JButton("BACK");
	JButton btnNewButton = new JButton("RETRAGE SUMA");
	private final JLabel mesaj = new JLabel("");

	public  PRetragereSuma(String iban, String cnp) {

		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// check if number: 
				String sir = suma.getText();
				boolean ok=true;
				for(int i=0;i<sir.length();i++) {
					if( !(('0'<=sir.charAt(i)  && sir.charAt(i)<='9')||sir.charAt(i)=='.')) {
						ok=false;
					}
				}
				if(!ok) {
					mesaj.setText("Introduceti un numar valid");
				}
				else {
					mesaj.setText("");
					double suma2 = Double.parseDouble(sir);
					Connection connection;
					try {
						connection = DriverManager.getConnection(CONNECTION_LINK + DB_NAME, USERNAME, PASSWORD);
						Statement statement = connection.createStatement();
						ResultSet resultSet = statement.executeQuery("CALL retrage_fonduri ('"+iban+"','+"+ suma2 +"');");
					} catch (SQLException e1) { 
						e1.printStackTrace();
					}
					frame.dispose();
					PConturi pc = new PConturi(cnp);
					pc.getFrame().setVisible(true);
				}
			}
		});

		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				PConturi pc = new PConturi(cnp);
				pc.getFrame().setVisible(true);
			}
		});

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 256, 187);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		frame.setContentPane(contentPane);
		contentPane.setLayout(null);
		back.setBounds(143, 11, 89, 23);
		contentPane.add(back);
		btnNewButton.setBounds(41, 76, 165, 23);
		contentPane.add(btnNewButton);
		suma = new JTextField();
		suma.setBounds(41, 45, 165, 20);
		contentPane.add(suma);
		suma.setColumns(10);

		mesaj.setBounds(30, 110, 196, 29);
		
		contentPane.add(mesaj);
	}

	public JFrame getFrame() {
		return frame;
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}
}
