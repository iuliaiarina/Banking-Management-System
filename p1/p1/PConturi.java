package p1;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JLabel;
import java.awt.CardLayout;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import java.awt.GridLayout;
import javax.swing.SwingConstants;

public class PConturi extends JFrame {
	private final static String USERNAME = "root";
	private final static String PASSWORD = "sssabb";
	private final static String DB_NAME = "Bancar";
	private final static String CONNECTION_LINK = "jdbc:mysql://localhost:3306/";

	private JFrame frame = new JFrame("Conturi");
	private JPanel contentPane;
	private final JPanel rightDepo = new JPanel();
	JScrollPane scrollPane = new JScrollPane();
	JPanel panel = new JPanel();
	JLabel lblNewLabel_1 = new JLabel("Conturi de economii:");
	JButton back = new JButton("BACK");
	JButton tranzactie = new JButton("Tranzactie");
	JScrollPane scrollPane_1 = new JScrollPane();
	JPanel panel_2 = new JPanel();
	JLabel lblNewLabel = new JLabel("Depozite:");
	JLabel lblNewLabel_2 = new JLabel("Cont curent:");
	private final JButton btnNewButton = new JButton("Adauga cont de economii");
	private final JButton btnNewButton_2 = new JButton("Adaugare depozit");
	private final JPanel panel_1 = new JPanel();
	private final JPanel rightEcon = new JPanel();
	private final JLabel lblNewLabel_3 = new JLabel("Tipul de dobanda:");
	private final JButton retragere = new JButton("Retragere fonduri");

	public PConturi(String cnp) {

		Connection connection;
		try {
			connection = DriverManager.getConnection(CONNECTION_LINK + DB_NAME, USERNAME, PASSWORD);
			Statement statement = connection.createStatement();

			// cont curent
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
				panel_1.add(label1);
				panel_1.add(label2);
				panel_1.add(label3);
			}
			ContCurent cc2 = contCurent;
			retragere.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

					Connection connection;
					try {
						connection = DriverManager.getConnection(CONNECTION_LINK + DB_NAME, USERNAME, PASSWORD);
						Statement statement = connection.createStatement();
						ResultSet resultSet = statement
								.executeQuery("CALL retrage_fonduri('" + cc2.getIBAN() + "','" + cc2.getSuma() + "');");
					} catch (SQLException e1) {
						e1.printStackTrace();
					}

					frame.dispose();
					PConturi pc = new PConturi(cnp);
					pc.getFrame().setVisible(true);
				}
			});
			// cont de economii
			resultSet = statement.executeQuery(
					" SELECT * FROM CONT_economii JOIN Cont on  CONT_economii.IBAN=CONT.IBAN join CONT_CLIENT ON CONT_economii.IBAN=CONT_CLIENT.IBAN WHERE CNP ="
							+ cnp);

			while (resultSet.next()) {
				ContCurent cc = contCurent;
				ContEconomii contEconomii = new ContEconomii(resultSet.getDouble("suma"), resultSet.getString("IBAN"),
						resultSet.getString("CNP"), resultSet.getDate("data_creare"), resultSet.getInt("dobanda"));

				JLabel label1 = new JLabel("IBAN:        " + contEconomii.getIBAN());
				JLabel label2 = new JLabel("Suma:        " + contEconomii.getSuma());
				JLabel label3 = new JLabel("Data creare: " + contEconomii.getDataCreare());
				JLabel label4 = new JLabel("Dobanda:     " + contEconomii.getDobanda());
				JLabel space = new JLabel("-----------------------------------------------------------------");
				rightEcon.add(label1);
				rightEcon.add(label2);
				rightEcon.add(label3);
				rightEcon.add(label4);
				// adaugare suma:
				JButton suma = new JButton("Adaugare suma");
				rightEcon.add(suma);
				suma.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						frame.dispose();
						PAdaugareSuma pas = new PAdaugareSuma(contEconomii.getIBAN(), cnp);
						pas.getFrame().setVisible(true);
					}
				});
				// retrage suma:
				JButton ret = new JButton("Retreagere suma");
				rightEcon.add(ret);
				ret.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						frame.dispose();
						PRetragereSuma pas = new PRetragereSuma(contEconomii.getIBAN(), cnp);
						pas.getFrame().setVisible(true);
					}
				});
				// stergere:
				JButton delete = new JButton("Sterge cont");
				rightEcon.add(delete);
				delete.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Connection connection;
						try {
							connection = DriverManager.getConnection(CONNECTION_LINK + DB_NAME, USERNAME, PASSWORD);
							Statement statement = connection.createStatement();
							ResultSet resultSet = statement.executeQuery("CALL lichidare_cont_bancar ('"
									+ contEconomii.getIBAN() + "','" + cc.getIBAN() + "');");
						} catch (SQLException e1) {
							e1.printStackTrace();
						}

						// refresh;
						frame.dispose();
						PConturi pc = new PConturi(cnp);
						pc.getFrame().setVisible(true);
					}
				});
				rightEcon.add(space);
			}
			/// depozit:
			resultSet = statement.executeQuery(
					"SELECT * FROM depozit JOIN cont on depozit.iban= cont.iban join cont_client ON(depozit.IBAN = cont_client.IBAN) join dobanda on dobanda.dobanda_id=depozit.dobanda_id WHERE cont_client.CNP = "
							+ cnp);
			while (resultSet.next()) {
				ContCurent cc = contCurent;
				Depozit depozit = new Depozit(resultSet.getDouble("suma"), resultSet.getString("IBAN"),
						resultSet.getString("CNP"), resultSet.getDate("data_creare"), resultSet.getInt("dobanda_id"),
						resultSet.getDate("data_ultim_mod"));
				Dobanda dobanda = new Dobanda(resultSet.getInt("perioada"), resultSet.getInt("dobanda"),
						resultSet.getInt("dobanda_id"));
				JLabel label1 = new JLabel("IBAN:        " + depozit.getIBAN());
				JLabel label2 = new JLabel("Suma:        " + depozit.getSuma());
				JLabel label3 = new JLabel("Data creare: " + depozit.getDataCreare());
				JLabel label4 = new JLabel(
						"Dobanda de " + dobanda.getDobanda() + "% pe perioada de " + dobanda.getPerioada() + " luni");
				JLabel label5 = new JLabel("ultima modificare: " + depozit.getData_ultim_mod());
				JLabel space = new JLabel("---------------------------------------------------------------");
				rightDepo.add(label1);
				rightDepo.add(label2);
				rightDepo.add(label3);
				rightDepo.add(label4);
				rightDepo.add(label5);

				// adaugare suma:
				JButton suma = new JButton("Adaugare suma");
				rightDepo.add(suma);
				suma.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						frame.dispose();
						PAdaugareSuma pas = new PAdaugareSuma(depozit.getIBAN(), cnp);
						pas.getFrame().setVisible(true);
					}
				});
				// retrage suma:
				JButton ret = new JButton("Retreagere suma");
				rightDepo.add(ret);
				ret.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						frame.dispose();
						PRetragereSuma pas = new PRetragereSuma(depozit.getIBAN(), cnp);
						pas.getFrame().setVisible(true);
					}
				});
				JButton delete = new JButton("Sterge depozit");
				rightDepo.add(delete);
				delete.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Connection connection;
						try {
							connection = DriverManager.getConnection(CONNECTION_LINK + DB_NAME, USERNAME, PASSWORD);
							Statement statement = connection.createStatement();
							ResultSet resultSet = statement.executeQuery(
									"CALL lichidare_cont_bancar ('" + depozit.getIBAN() + "','" + cc.getIBAN() + "');");
						} catch (SQLException e1) {
							e1.printStackTrace();
						}

						// refresh;
						frame.dispose();
						PConturi pc = new PConturi(cnp);
						pc.getFrame().setVisible(true);
					}
				});
				rightDepo.add(space);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				PStartClientView viewClient = new PStartClientView(cnp);
				PStartClientController controllerClient = new PStartClientController(viewClient);
				viewClient.getFrame().setVisible(true);
			}
		});

		tranzactie.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				Tranzactii t = new Tranzactii(cnp);
				t.getFrame().setVisible(true);
			}
		});

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 584, 671);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		frame.setContentPane(contentPane);
		contentPane.setLayout(null);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(10, 144, 273, 219);
		contentPane.add(scrollPane);
		scrollPane.setColumnHeaderView(panel);
		panel.add(lblNewLabel_1);

		scrollPane.setViewportView(rightEcon);
		rightEcon.setLayout(new GridLayout(0, 1, 0, 0));
		back.setBounds(411, 11, 148, 23);
		contentPane.add(back);
		tranzactie.setBounds(411, 45, 148, 23);
		contentPane.add(tranzactie);
		scrollPane_1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane_1.setBounds(10, 374, 273, 260);
		contentPane.add(scrollPane_1);
		scrollPane_1.setColumnHeaderView(panel_2);
		panel_2.add(lblNewLabel);
		scrollPane_1.setViewportView(rightDepo);
		rightDepo.setLayout(new GridLayout(0, 1, 0, 0));
		lblNewLabel_2.setBounds(42, 30, 180, 14);
		contentPane.add(lblNewLabel_2);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton.setBounds(308, 312, 211, 23);

		contentPane.add(btnNewButton);

		btnNewButton_2.setBounds(309, 578, 210, 23);

		contentPane.add(btnNewButton_2);
		panel_1.setBounds(42, 52, 190, 81);

		contentPane.add(panel_1);
		panel_1.setLayout(new GridLayout(0, 1, 0, 0));
		panel_1.add(retragere);

		retragere.setVerticalAlignment(SwingConstants.BOTTOM);

		lblNewLabel_3.setBounds(432, 374, 128, 14);
		contentPane.add(lblNewLabel_3);

		JButton factura = new JButton("Plata facturi");
		factura.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				PPlataFacturi pf = new PPlataFacturi(cnp);
				pf.getFrame().setVisible(true);
			}
		});
		factura.setBounds(411, 79, 148, 23);
		contentPane.add(factura);

		JComboBox<String> comboBox = new JComboBox<String>(new String[] { "BRD", "ING", "RZBR", "BTRL" });
		comboBox.setBounds(308, 159, 128, 22);
		contentPane.add(comboBox);
		JComboBox<String> comboBox_1 = new JComboBox<String>(new String[] { "BRD", "ING", "RZBR", "BTRL" });
		comboBox_1.setBounds(308, 408, 128, 22);
		contentPane.add(comboBox_1);

		@SuppressWarnings("unchecked")
		JComboBox<Integer> comboBox_2 = new JComboBox(new Integer[] { 1, 2, 3 });
		comboBox_2.setBounds(452, 408, 70, 22);
		contentPane.add(comboBox_2);

		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String itemEcon = new String((String) comboBox.getSelectedItem());
				Connection connection;
				try {
					connection = DriverManager.getConnection(CONNECTION_LINK + DB_NAME, USERNAME, PASSWORD);
					Statement statement = connection.createStatement();
					ResultSet resultSet = statement
							.executeQuery("CALL creare_cont_economii('" + itemEcon + "','" + cnp + "');");
				} catch (SQLException e1) {
					e1.printStackTrace();
				}

				frame.dispose();
				PConturi pc = new PConturi(cnp);
				pc.getFrame().setVisible(true);
			}
		});

		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String itemDepo = new String((String) comboBox_1.getSelectedItem());
				int itemTip = new Integer((Integer) comboBox_2.getSelectedItem());
				Connection connection;
				try {
					connection = DriverManager.getConnection(CONNECTION_LINK + DB_NAME, USERNAME, PASSWORD);
					Statement statement = connection.createStatement();
					ResultSet resultSet = statement.executeQuery(
							"CALL creare_cont_depozit('" + itemDepo + "', '" + cnp + "','" + itemTip + "');");
				} catch (SQLException e1) {
					e1.printStackTrace();
				}

				frame.dispose();
				PConturi pc = new PConturi(cnp);
				pc.getFrame().setVisible(true);
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
