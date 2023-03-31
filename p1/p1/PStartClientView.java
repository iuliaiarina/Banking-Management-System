package p1;

import java.awt.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;

public class PStartClientView extends JFrame {
	private final static String USERNAME = "root";
	private final static String PASSWORD = "sssabb";
	private final static String DB_NAME = "Bancar";
	private final static String CONNECTION_LINK = "jdbc:mysql://localhost:3306/";
	private String cnp; // totul va primi cnp;

	private JFrame frame = new JFrame("paginaStartCLient");
	private JPanel contentPane = new JPanel();
	private JLabel label = new JLabel("Nume:");
	private JLabel label_1 = new JLabel("Prenume:");
	private JLabel label_2 = new JLabel("Adresa:");
	private JLabel label_3 = new JLabel("Telefon:");
	private JLabel label_4 = new JLabel("Email:");
	private JLabel label_5 = new JLabel("Contact:");
	private JLabel label_6 = new JLabel("Data de nastere:");
	private JLabel label_7 = new JLabel("Sursa de venit:");
	private JLabel label_8 = new JLabel("Tranzactii online:");
	private JLabel label_9 = new JLabel("IBAN:");
	private JLabel label_10 = new JLabel("Numar de conturi:");
	private JButton buttonLogout = new JButton("Logout");
	private JButton button = new JButton("Conturi");
	private JButton button_1 = new JButton("Cerere card");
	private JButton button_2 = new JButton("Modifica date personale");
	private JButton button_3 = new JButton("Lista contacte");
	private JButton exit = new JButton("Lichidare cont");
	private final JScrollPane scrollPane = new JScrollPane();
	private final JPanel panel = new JPanel();
	private final JLabel Label = new JLabel("Notificari:");
	private final JPanel panel_1 = new JPanel();

	public PStartClientView(String cnp) {
		this.cnp = cnp;
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Connection connection;
				try {
					connection = DriverManager.getConnection(CONNECTION_LINK + DB_NAME, USERNAME, PASSWORD);
					Statement statement = connection.createStatement();
					ResultSet resultSet = statement.executeQuery("CALL lichidare_cont_client('" + cnp + "');");
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				frame.dispose();
				PStartView view = new PStartView();
				PStartModel model = new PStartModel();
				PStartController controller = new PStartController(model, view);
				view.getFrame().setVisible(true);
			}
		});

		Connection connection;
		Client client = null;
		try {
			connection = DriverManager.getConnection(CONNECTION_LINK + DB_NAME, USERNAME, PASSWORD);
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(
					"SELECT * from persoana join client on persoana.cnp=client.cnp where client.cnp=" + this.cnp);

			while (resultSet.next()) {
				client = new Client(resultSet.getString("NUME"), resultSet.getString("PRENUME"),
						resultSet.getString("PAROLA"), resultSet.getString("ADRESA"), resultSet.getString("TELEFON"),
						resultSet.getString("EMAIL"), resultSet.getString("CONTACT"), resultSet.getString("CNP"),
						resultSet.getDate("DATA_NASTERE"), resultSet.getString("SURSA_VENIT"),
						resultSet.getInt("NR_CONTURI"), resultSet.getBoolean("TRANZACTII_ONLINE"));
			}

			ResultSet tran = statement.executeQuery(
					"select*  from transfer where transfer.status='SUCCESSFUL' and ((transfer.IBAN_sursa in(select iban from cont_client where cnp='"
							+ cnp + "')) or (IBAN_destinatie in(select iban from cont_client where cnp='" + cnp
							+ "'))) order by data_creare;");
			while (tran.next()) {
				JLabel space = new JLabel("TRANSFER:    ");
				JLabel label1 = new JLabel("Status:  " + tran.getString("status"));
				JLabel label2 = new JLabel("Nume destinatar:    " + tran.getString("nume_client_destinatie"));
				JLabel label3 = new JLabel("IBAN destinatie:   " + tran.getString("IBAN_destinatie"));
				JLabel label4 = new JLabel("Nume sursa:     " + tran.getString("Nume_client_sursa"));
				JLabel label5 = new JLabel("IBAN sursa:   " + tran.getString("IBAN_sursa"));
				JLabel label6 = new JLabel("Suma:     " + tran.getDouble("Suma"));
				JLabel label7 = new JLabel("Din data:    " + tran.getDate("Data_creare"));
				JLabel space1 = new JLabel("----------------------------");
				panel_1.add(space);
				panel_1.add(label1);
				panel_1.add(label2);
				panel_1.add(label3);
				panel_1.add(label4);
				panel_1.add(label5);
				panel_1.add(label6);
				panel_1.add(label7);
				panel_1.add(space1);

			}

			ResultSet asd = statement.executeQuery(
					"select* from cerere_card where (cerere_card.cnp_admin is not null and cerere_card.cnp_admin is not null) and cerere_card.cnp='"
							+ cnp + "';");
			while (asd.next()) {
				JLabel space = new JLabel("CERERE DE CARD:  ");
				JLabel label1 = new JLabel("id_card:  " + asd.getInt("cerere_id"));
				JLabel label2 = new JLabel("Status:   APROBAT");
				JLabel space1 = new JLabel("----------------------------");
				panel_1.add(space);
				panel_1.add(label1);
				panel_1.add(label2);
				panel_1.add(space);
				panel_1.add(space1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (client == null)
			System.out.println("eroare");
		else {
			label.setText("Nume: " + client.getNume());
			label_1.setText("Prenume: " + client.getPrenume());
			label_2.setText("Adresa: " + client.getAdresa());
			label_3.setText("Telefon: " + client.getTelefon());
			label_4.setText("Email: " + client.getEmail());
			label_5.setText("Contact: " + client.getContact());
			label_6.setText("Data de nastere: " + client.getDateNastere());
			label_7.setText("Sursa de venit: " + client.getSursaVenit());
			label_8.setText("Tranzactii online: " + client.isTranzactiiOnline());
			label_9.setText("IBAN: " + client.getCNP());
			label_10.setText("Numar de conturi: " + client.getNrConturi());
		}
		contentPane.add(exit);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		exit.setBounds(324, 33, 119, 23);
		frame.setBounds(100, 100, 617, 601);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		frame.setContentPane(contentPane);
		contentPane.setLayout(null);
		label.setBounds(34, 31, 247, 26);
		contentPane.add(label);
		label_1.setBounds(34, 68, 247, 26);
		contentPane.add(label_1);
		label_2.setBounds(34, 105, 247, 26);
		contentPane.add(label_2);
		label_3.setBounds(34, 142, 247, 26);
		contentPane.add(label_3);
		label_4.setBounds(34, 179, 247, 26);
		contentPane.add(label_4);
		label_5.setBounds(34, 219, 247, 26);
		contentPane.add(label_5);
		label_6.setBounds(34, 256, 247, 26);
		contentPane.add(label_6);
		label_7.setBounds(34, 293, 247, 26);
		contentPane.add(label_7);
		label_8.setBounds(34, 329, 247, 26);
		contentPane.add(label_8);
		label_9.setBounds(34, 363, 247, 26);
		contentPane.add(label_9);
		label_10.setBounds(34, 398, 247, 26);
		contentPane.add(label_10);
		buttonLogout.setBounds(464, 33, 89, 23);
		contentPane.add(buttonLogout);
		button.setBounds(450, 365, 117, 23);
		contentPane.add(button);
		button_1.setBounds(450, 400, 117, 23);
		contentPane.add(button_1);
		button_2.setBounds(24, 448, 180, 26);
		contentPane.add(button_2);
		button_3.setBounds(450, 434, 117, 23);
		contentPane.add(button_3);
		scrollPane.setBounds(291, 68, 302, 277);

		contentPane.add(scrollPane);

		scrollPane.setColumnHeaderView(panel);

		panel.add(Label);

		scrollPane.setViewportView(panel_1);
		panel_1.setLayout(new GridLayout(0, 1, 0, 0));

		frame.setVisible(false);
	}

	void logout(ActionListener listenForButton) {
		buttonLogout.addActionListener(listenForButton);
	}

	void listaContacte(ActionListener listenForButton) {
		button_3.addActionListener(listenForButton);
	}

	void conturi(ActionListener listenForButton) {
		button.addActionListener(listenForButton);
	}

	void cerereCard(ActionListener listenForButton) {
		button_1.addActionListener(listenForButton);
	}

	void modificaDate(ActionListener listenForButton) {
		button_2.addActionListener(listenForButton);
	}

	public String getCnp() {
		return cnp;
	}

	public void setCnp(String cnp) {
		this.cnp = cnp;
	}

	public JFrame getFrame() {
		return frame;
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}
}
