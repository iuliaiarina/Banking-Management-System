package p1;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import java.awt.GridLayout;
import javax.swing.JTextField;

public class PPlataFacturi extends JFrame {

	private final static String USERNAME = "root";
	private final static String PASSWORD = "sssabb";
	private final static String DB_NAME = "Bancar";
	private final static String CONNECTION_LINK = "jdbc:mysql://localhost:3306/";
	private JFrame frame =new JFrame("Platire facturi");
	private JPanel contentPane;
	JButton back = new JButton("BACK");
	JScrollPane scrollPane = new JScrollPane();
	JPanel panel = new JPanel();
	private final JPanel panel_1 = new JPanel();
	JPanel panel_2 = new JPanel();
	public PPlataFacturi(String cnp) {
		
		Connection connection;
		try {
			connection = DriverManager.getConnection(CONNECTION_LINK + DB_NAME, USERNAME, PASSWORD);
			Statement statement = connection.createStatement();
			//cont curent
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
			//cont curent
			ResultSet rs = statement.executeQuery(
					"SELECT* FROM facturi WHERE CNP="+cnp);
			while (rs.next()) {
				JLabel label1 = new JLabel( rs.getString("nume"));
				JLabel label2 = new JLabel("Suma:" + rs.getDouble("suma"));
				JLabel label3 = new JLabel("ID: " + rs.getInt("factura_id"));
				JLabel space = new JLabel("-------------------------------------------------------------------------");
				int id= rs.getInt("factura_id");
				panel_2.add(label1);
				panel_2.add(label2);
				panel_2.add(label3);
				JButton plat = new JButton("PLATESTE");
				panel_2.add(plat);
				plat.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Connection connection;
						try {
							connection = DriverManager.getConnection(CONNECTION_LINK + DB_NAME, USERNAME, PASSWORD);
							Statement statement = connection.createStatement();;
							statement.executeQuery("CALL PLATA_FACTURI('"+cnp+"','"+id+"');");
						}catch (SQLException e1) {
							e1.printStackTrace();
						}
						frame.dispose();
						PPlataFacturi pfd=new PPlataFacturi(cnp);
						pfd.getFrame().setVisible(true);
					}
				});
				panel_2.add(space);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				PConturi pc =new PConturi(cnp);
				pc.getFrame().setVisible(true);
			}
		});
		
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 450, 415);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		frame.setContentPane(contentPane);
		contentPane.setLayout(null);
		back.setBounds(337, 11, 89, 23);
		contentPane.add(back);
		scrollPane.setBounds(26, 126, 294, 241);
		contentPane.add(scrollPane);
		
		scrollPane.setColumnHeaderView(panel_1);
		
		JLabel lblNewLabel = new JLabel("FACTURI NEPLATITE");
		panel_1.add(lblNewLabel);
		scrollPane.setViewportView(panel_2);
		panel_2.setLayout(new GridLayout(0, 1, 0, 0));
		panel.setBounds(26, 11, 294, 97);
		contentPane.add(panel);
		panel.setLayout(new GridLayout(0, 1, 0, 0));
	}
	public JFrame getFrame() {
		return frame;
	}
	public void setFrame(JFrame frame) {
		this.frame = frame;
	}
}
