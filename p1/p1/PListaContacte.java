package p1;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.GridBagLayout;
import javax.swing.ScrollPaneConstants;

public class PListaContacte extends JFrame {
	private final static String USERNAME = "root";
	private final static String PASSWORD = "sssabb";
	private final static String DB_NAME = "Bancar";
	private final static String CONNECTION_LINK = "jdbc:mysql://localhost:3306/";

	private JFrame frame = new JFrame("Lista de contacte");
	private JPanel contentPane;
	private JTextField numePrenume;
	private JTextField textField_1;
	private JTextField textField;
	JPanel panel_1 = new JPanel();
	JLabel Label = new JLabel("Nume Prenume:");
	JLabel Label_1 = new JLabel("IBAN");
	JScrollPane scrollPane = new JScrollPane();
	JPanel panel = new JPanel();
	JLabel Label_2 = new JLabel("Lista de contacte");
	JButton adauga = new JButton("ADAUGA");
	private final JLabel mesaj = new JLabel("");

	public PListaContacte(String cnp) {
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 450, 362);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		frame.setContentPane(contentPane);
		contentPane.setLayout(null);
		JLabel Label_3 = new JLabel("Informatii");
		Connection connection;
		try {
			connection = DriverManager.getConnection(CONNECTION_LINK + DB_NAME, USERNAME, PASSWORD);
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("select* from lista_contacte;");
			while (resultSet.next()) {
				ListaContacte listaContacte = new ListaContacte(resultSet.getString("comentariu"),
						resultSet.getString("nume_client_destinatie"), resultSet.getString("IBAN_destinatie"),
						resultSet.getString("CNP"), resultSet.getInt("lista_id"));
				JLabel label1 = new JLabel(listaContacte.getNume_client_destinatie());
				JLabel label2 = new JLabel(listaContacte.getIBAN_destinatie());
				JLabel label3 = new JLabel(listaContacte.getComentariu());
				JLabel spatiu = new JLabel("---------------------------------");
				
				panel_1.add(label1);
				panel_1.add(label2);
				panel_1.add(label3);
				panel_1.add(spatiu);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		JButton back = new JButton("BACK");
		back.setBounds(337, 11, 89, 23);
		contentPane.add(back);

		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// inchid pag actuala
				frame.dispose();
				// deschidem pag back:
				PStartClientView viewClient = new PStartClientView(cnp);
				PStartClientController controllerClient = new PStartClientController(viewClient);
				viewClient.getFrame().setVisible(true);
			}
		});

		numePrenume = new JTextField();
		numePrenume.setBounds(136, 198, 96, 20);
		contentPane.add(numePrenume);
		numePrenume.setColumns(10);
		textField_1 = new JTextField();
		textField_1.setBounds(136, 232, 96, 20);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		Label.setBounds(10, 201, 106, 14);
		contentPane.add(Label);
		Label_1.setBounds(20, 235, 79, 14);
		contentPane.add(Label_1);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(10, 11, 308, 162);
		contentPane.add(scrollPane);
		scrollPane.setColumnHeaderView(panel);
		panel.add(Label_2);
		scrollPane.setViewportView(panel_1);
		panel_1.setLayout(new GridLayout(0, 1, 0, 0));
		textField = new JTextField();
		textField.setBounds(113, 263, 194, 51);
		contentPane.add(textField);
		textField.setColumns(10);
		Label_3.setBounds(10, 267, 79, 14);
		contentPane.add(Label_3);
		adauga.setBounds(337, 245, 89, 23);
		contentPane.add(adauga);
		mesaj.setBounds(347, 280, 79, 17);

		contentPane.add(mesaj);

		adauga.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nume = numePrenume.getText();
				if(nume.isEmpty())nume =new String("empty");
				String iban = textField_1.getText();
				if(iban.isEmpty())iban =new String("empty");
				String informatii = textField.getText();
				if(informatii.isEmpty())informatii =new String("empty");
				ResultSet resultSet = null;
				Connection connection;
				try {
					connection = DriverManager.getConnection(CONNECTION_LINK + DB_NAME, USERNAME, PASSWORD);
					Statement statement = connection.createStatement();
					resultSet = statement.executeQuery("call adauga_in_lista ('" + informatii + "', '" + nume + "','"
							+ iban + "', '" + cnp + "');");
					
					} 
				 catch (SQLException e1) {
					e1.printStackTrace();
					
				}
				//if(!mesaj.getText().equals("Date introduse gresit!")) {
				frame.dispose();
				PListaContacte plc = new PListaContacte(cnp);
				plc.getFrame().setVisible(true);
				//}else mesaj.setText("Date introduse gresit!");
			}
		});
	}

	public JFrame getFrame() {
		return frame;
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}

}
