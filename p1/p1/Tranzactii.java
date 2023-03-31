package p1;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JComboBox;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JScrollPane;

public class Tranzactii extends JFrame {
	private final static String USERNAME = "root";
	private final static String PASSWORD = "0000";
	private final static String DB_NAME = "Bancar";
	private final static String CONNECTION_LINK = "jdbc:mysql://localhost:3306/";
	private JFrame frame = new JFrame("Transferuri");
	private JPanel contentPane;
	private JTextField iban;
	private JTextField nume;
	private JTextField suma;
	private JTextField suma2;
	JPanel panel = new JPanel();
	JButton done = new JButton("DONE");
	JButton back = new JButton("BACK");
	JLabel mesaj2 = new JLabel("");
	JLabel mesaj = new JLabel("");
	JButton done2 = new JButton("DONE");
	JComboBox comboBox = new JComboBox();
	JScrollPane scrollPane = new JScrollPane();
	JPanel panel_2 = new JPanel();
	
	public Tranzactii(String cnp) {
		// JComboBox<String> comboBox = new JComboBox<String>(new String[] { "none" });
		Connection connection;
		try {
			connection = DriverManager.getConnection(CONNECTION_LINK + DB_NAME, USERNAME, PASSWORD);
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(
					"SELECT * FROM CONT_CURENT JOIN Cont on  CONT_CURENT.IBAN=CONT.IBAN join CONT_CLIENT ON CONT_CURENT.IBAN=CONT_CLIENT.IBAN WHERE CNP = "
							+ cnp);
			ContCurent contCurent = null;
			while (resultSet.next()) {
				contCurent = new ContCurent(resultSet.getDouble("suma"), resultSet.getString("IBAN"),
						resultSet.getString("CNP"), resultSet.getDate("data_creare"));

				JLabel label1 = new JLabel("IBAN:        " + contCurent.getIBAN());
				JLabel label2 = new JLabel("Suma:        " + contCurent.getSuma());
				JLabel label3 = new JLabel("Data creare: " + contCurent.getDataCreare());
				panel.add(label1);
				panel.add(label2);
				panel.add(label3);
			}
			///////// lista de contacte:
			resultSet = statement.executeQuery("select* from lista_contacte;");
			while (resultSet.next()) {
				ListaContacte listaContacte = new ListaContacte(resultSet.getString("comentariu"),
						resultSet.getString("nume_client_destinatie"), resultSet.getString("IBAN_destinatie"),
						resultSet.getString("CNP"), resultSet.getInt("lista_id"));
				JLabel label1 = new JLabel(listaContacte.getNume_client_destinatie());
				JLabel label2 = new JLabel(listaContacte.getIBAN_destinatie());
				JLabel label3 = new JLabel(listaContacte.getComentariu());

				JLabel supreme = new JLabel("<html>" + label1.getText() + "<br/>" + label2.getText() + "<br/>"
						+ label3.getText() + "</html>");
				comboBox.addItem(supreme.getText());
			}
			///////////// transferuri:
			resultSet = statement.executeQuery(
					"select*  from transfer where (transfer.IBAN_sursa in(select iban from cont_client where cnp='"+cnp+"')) or (IBAN_destinatie in(select iban from cont_client where cnp='"+cnp+"')) order by data_creare;");
			while (resultSet.next()) {
				Transfer tran = new Transfer(resultSet.getString("status"), resultSet.getString("nume_client_destinatie"),
						resultSet.getString("nume_client_sursa"),resultSet.getDate("data_creare"),
						resultSet.getDouble("suma"),resultSet.getString("IBAN_destinatie"),
						resultSet.getString("IBAN_sursa"),resultSet.getString("CNP_angajat"),
						resultSet.getInt("transfer_id"));

				JLabel label1 = new JLabel("Status:  " + tran.getStatus());
				JLabel label2 = new JLabel("Nume destinatar:    " + tran.getNume_client_destinatie());
				JLabel label3 = new JLabel("IBAN destinatie:   " + tran.getIBAN_destinatie());
				JLabel label4 = new JLabel("Nume sursa:     " + tran.getNume_client_sursa());
				JLabel label5 = new JLabel("IBAN sursa:   " + tran.getIBAN_sursa());
				JLabel label6 = new JLabel("Suma:     " + tran.getSuma());
				JLabel label7 = new JLabel("Din data:    " + tran.getData_creare());
				JLabel space =new JLabel("---------------------------------------------------");
				panel_2.add(label1);
				panel_2.add(label2);
				panel_2.add(label3);
				panel_2.add(label4);
				panel_2.add(label5);
				panel_2.add(label6);
				panel_2.add(label7);
				panel_2.add(space);
			}
			
			ContCurent asd = contCurent;
			done.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Connection connection;
					try {
						String IBAN_sursa = asd.getIBAN();
						String nume_client_destinatie = nume.getText();
						String IBAN_destinatie = iban.getText();

						// verificam daca este corecta suma:
						String sir = suma.getText();
						if (nume_client_destinatie.isEmpty() || IBAN_destinatie.isEmpty() || sir.isEmpty())
							mesaj.setText("Camp gol");
						else {
							boolean ok = true;
							for (int i = 0; i < sir.length(); i++) {
								if (!(('0' <= sir.charAt(i) && sir.charAt(i) <= '9') || sir.charAt(i) == '.')) {
									ok = false;
								}
							}
							if (!ok) {
								mesaj.setText("Suma invalida");
							} else {
								double sumaa = Double.parseDouble(sir);
								// System.out.println(IBAN_sursa+"\n"+IBAN_destinatie+sumaa);
								connection = DriverManager.getConnection(CONNECTION_LINK + DB_NAME, USERNAME, PASSWORD);
								Statement statement = connection.createStatement();
								ResultSet resultSet = statement
										.executeQuery("CALL cerere_transfer ('" + nume_client_destinatie + "','"
												+ IBAN_destinatie + "','" + sumaa + "','" + IBAN_sursa + "');");
								frame.dispose();
								Tranzactii t = new Tranzactii(cnp);
								t.getFrame().setVisible(true);
							}
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			});
			done2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Connection connection;
					try {
						String IBAN_sursa = asd.getIBAN();

						// ver box-ul:
						String itemBox = new String((String) comboBox.getSelectedItem());

						String nume_client_destinatie = new String();
						String IBAN_destinatie = new String();

						itemBox = itemBox.substring(6, itemBox.length() - 7);
						int index = 0;
						String[] vec = new String[100];
						for (String val : itemBox.split("<br/>", 3)) {
							vec[index] = val;
							index++;
						}
						nume_client_destinatie = vec[0];
						IBAN_destinatie = vec[1];
						// ver suma:
						String sir = suma2.getText();
						if (sir.isEmpty())
							mesaj2.setText("Camp gol");
						else {
							boolean ok = true;
							for (int i = 0; i < sir.length(); i++) {
								if (!(('0' <= sir.charAt(i) && sir.charAt(i) <= '9') || sir.charAt(i) == '.')) {
									ok = false;
								}
							}

							if (!ok) {
								mesaj2.setText("Suma invalida");
							} else {
								double sumaa = Double.parseDouble(sir);
								connection = DriverManager.getConnection(CONNECTION_LINK + DB_NAME, USERNAME, PASSWORD);
								Statement statement = connection.createStatement();
								ResultSet resultSet = statement
										.executeQuery("CALL cerere_transfer ('" + nume_client_destinatie + "','"
												+ IBAN_destinatie + "','" + sumaa + "','" + IBAN_sursa + "');");
								frame.dispose();
								Tranzactii t = new Tranzactii(cnp);
								t.getFrame().setVisible(true);
							}
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			});

		} catch (SQLException e) {
			e.printStackTrace();
		}

		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				PConturi pc = new PConturi(cnp);
				pc.getFrame().setVisible(true);
			}
		});

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 531, 558);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		frame.setContentPane(contentPane);
		contentPane.setLayout(null);
		JLabel Label = new JLabel("Efectuati o tranzactie:");
		Label.setBounds(10, 136, 183, 20);
		contentPane.add(Label);
		JLabel Label_1 = new JLabel("IBAN destinatar:");
		Label_1.setBounds(10, 192, 102, 14);
		contentPane.add(Label_1);
		JLabel Label_2 = new JLabel("Nume destinatar:");
		Label_2.setBounds(10, 167, 102, 14);
		contentPane.add(Label_2);
		JLabel Label_3 = new JLabel("Suma:");
		Label_3.setBounds(10, 223, 85, 14);
		contentPane.add(Label_3);
		iban = new JTextField();
		iban.setBounds(122, 189, 96, 20);
		contentPane.add(iban);
		iban.setColumns(10);
		nume = new JTextField();
		nume.setBounds(120, 164, 96, 20);
		contentPane.add(nume);
		nume.setColumns(10);
		suma = new JTextField();
		suma.setBounds(122, 220, 96, 20);
		contentPane.add(suma);
		suma.setColumns(10);
		done.setBounds(107, 266, 89, 23);
		contentPane.add(done);
		back.setBounds(337, 10, 89, 23);
		contentPane.add(back);
		JLabel Label_4 = new JLabel("Cont curent:");
		Label_4.setBounds(10, 14, 128, 14);
		contentPane.add(Label_4);
		JLabel Label_8 = new JLabel("Efectuati o tranzactie din lista de contacte:");
		Label_8.setBounds(235, 139, 282, 14);
		contentPane.add(Label_8);
		comboBox.setBounds(251, 161, 226, 48);
		contentPane.add(comboBox);
		JLabel Label_9 = new JLabel("suma:");
		Label_9.setBounds(251, 223, 49, 14);
		contentPane.add(Label_9);
		suma2 = new JTextField();
		suma2.setBounds(294, 220, 96, 20);
		contentPane.add(suma2);
		suma2.setColumns(10);
		panel.setBounds(20, 43, 215, 82);
		contentPane.add(panel);
		panel.setLayout(new GridLayout(0, 1, 0, 0));
		done2.setBounds(380, 266, 89, 23);
		contentPane.add(done2);
		mesaj.setBounds(10, 270, 85, 28);
		contentPane.add(mesaj);
		mesaj2.setBounds(248, 270, 122, 28);
		contentPane.add(mesaj2);
		
		
		scrollPane.setBounds(10, 309, 497, 201);
		contentPane.add(scrollPane);
		
		JPanel panel_1 = new JPanel();
		scrollPane.setColumnHeaderView(panel_1);
		
		JLabel Label_5 = new JLabel("Transferurile tale:");
		panel_1.add(Label_5);
		
		
		scrollPane.setViewportView(panel_2);
		panel_2.setLayout(new GridLayout(0, 1, 0, 0));
	}

	public JFrame getFrame() {
		return frame;
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}
}
