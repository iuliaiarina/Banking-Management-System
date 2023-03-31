package p1;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.GridLayout;
import javax.swing.ScrollPaneConstants;
import javax.swing.JTextField;
import java.awt.GridBagLayout;

public class PCerereCard extends JFrame {
	private final static String USERNAME = "root";
	private final static String PASSWORD = "sssabb";
	private final static String DB_NAME = "Bancar";
	private final static String CONNECTION_LINK = "jdbc:mysql://localhost:3306/";

	private JFrame frame = new JFrame("Carduri");
	private JPanel contentPane;
	JButton back = new JButton("BACK");
	JButton cere = new JButton("Cere card nou");
	JLabel titlu = new JLabel("Carduri bancare:");
	JScrollPane scrollPane = new JScrollPane();
	private String cnp;
	private final JPanel panel = new JPanel();
	JPanel panel_1 = new JPanel();
	private final JPanel panel_2 = new JPanel();
	private final JScrollPane scrollPane_1 = new JScrollPane();
	private final JPanel panel_3 = new JPanel();
	private final JPanel panel_4 = new JPanel();
	private final JLabel Label = new JLabel("Cereri de carduri bancare");

	public PCerereCard(String cnp) {
		this.cnp = cnp;
		panel.add(titlu);
		Connection connection;
		try {
			connection = DriverManager.getConnection(CONNECTION_LINK + DB_NAME, USERNAME, PASSWORD);
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT* FROM CERERE_CARD where cnp=" + this.cnp);

			while (resultSet.next()) {
				CerereCard cerereCard = new CerereCard(resultSet.getString("CNP"), resultSet.getString("IBAN"),
						resultSet.getString("CNP_ADMIN"), resultSet.getString("CNP_ANGAJAT"),
						resultSet.getInt("cerere_id"));
				if (cerereCard.getCNP_admin() != null && cerereCard.getCNP_angajat() != null) {
					JLabel label = new JLabel("Cardul cu id-ul " + cerereCard.getCerere_id() + "\n");
					panel_1.add(label);
				} else {
					JLabel label = new JLabel("Cardul cu id-ul " + cerereCard.getCerere_id() + "\n");
					panel_4.add(label);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 354, 422);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		frame.setContentPane(contentPane);
		contentPane.setLayout(null);

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

		cere.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Connection connection;
				try {
					connection = DriverManager.getConnection(CONNECTION_LINK + DB_NAME, USERNAME, PASSWORD);
					Statement statement = connection.createStatement();
					statement.executeQuery("CAll cerere_card ('" + cnp + "');");
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				frame.dispose();
				// deschidem pag back:
				PCerereCard c= new PCerereCard(cnp);
				c.getFrame().setVisible(true);
			}
		});

		back.setBounds(237, 11, 89, 23);
		contentPane.add(back);
		cere.setBounds(29, 57, 158, 23);
		contentPane.add(cere);
		scrollPane.setBounds(29, 110, 290, 115);
		contentPane.add(scrollPane);

		scrollPane.setColumnHeaderView(panel);
		scrollPane.setViewportView(panel_1);
		panel_1.setLayout(new GridLayout(0, 1, 0, 0)); // pt a fi unu sub altu;
		panel_1.add(panel_2);
		scrollPane_1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane_1.setBounds(29, 242, 290, 132);

		contentPane.add(scrollPane_1);

		scrollPane_1.setColumnHeaderView(panel_3);

		panel_3.add(Label);

		scrollPane_1.setViewportView(panel_4);
		panel_4.setLayout(new GridLayout(0, 1, 0, 0));
	}

	public JFrame getFrame() {
		return frame;
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}
}
