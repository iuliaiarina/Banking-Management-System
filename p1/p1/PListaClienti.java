package p1;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class PListaClienti extends JFrame {
    private final static String USERNAME = "root";
    private final static String PASSWORD = "sssabb";
    private final static String DB_NAME = "Bancar";
    private final static String CONNECTION_LINK = "jdbc:mysql://localhost:3306/";

    private JFrame frame = new JFrame("Lista tuturor clientilor");
    JButton back = new JButton("BACK");
    private JPanel contentPane;
    private final JPanel rightDepo = new JPanel();
    JScrollPane scrollPane = new JScrollPane();
    JPanel panel = new JPanel();
    JLabel Label_1 = new JLabel("Lista de clienti");
    private final JPanel rightEcon = new JPanel();

    public PListaClienti (String cnp) {

        //Populam lista de clienti
        Connection connection;
        try {
            connection = DriverManager.getConnection(CONNECTION_LINK + DB_NAME, USERNAME, PASSWORD);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT * FROM CLIENT JOIN PERSOANA ON CLIENT.CNP = PERSOANA.CNP ");

            Transfer transfer = null;
            while (resultSet.next()) {
                //pentru fiecare tupla din selectul de mai sus

                Client c = new Client(resultSet.getString("nume"), resultSet.getString("prenume"),
                        resultSet.getString("parola"), resultSet.getString("adresa"), resultSet.getString("telefon"),
                        resultSet.getString("email"), resultSet.getString("contact"),
                        resultSet.getString("cnp"), resultSet.getDate("data_nastere"),
                        resultSet.getString("sursa_venit"), resultSet.getInt("nr_conturi"),
                        resultSet.getBoolean("tranzactii_online"));

                JLabel label1 = new JLabel("Nume :        " + c.getNume());
                JLabel label2 = new JLabel("Prenume:        " + c.getPrenume());
                JLabel label3 = new JLabel("Data nasterii: " + c.getDateNastere());
                JLabel label4 = new JLabel("Telefon:     " + c.getTelefon());
                JLabel label5 = new JLabel("Adresa: " + c.getAdresa());
                JLabel label6 = new JLabel("Sursa de venit: " + c.getSursaVenit());
                JLabel label7 = new JLabel("CNP: " + c.getCNP());
                JLabel label8 = new JLabel("Numarul conturilor: " + c.getNrConturi());
                JLabel label9 = new JLabel("Email: " + c.getEmail());
                JLabel space = new JLabel("------------------------------------------------------------------------------------------------------");
                rightEcon.add(label1);
                rightEcon.add(label2);
                rightEcon.add(label3);
                rightEcon.add(label4);
                rightEcon.add(label5);
                rightEcon.add(label6);
                rightEcon.add(label7);
                rightEcon.add(label8);
                rightEcon.add(label9);
                rightEcon.add(space);
            }
        } catch (SQLException exc) {
            exc.printStackTrace();
        }
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                p1.PStartAdminView adm = new PStartAdminView(cnp);
                adm.getFrame().setVisible(true);
            }
        });

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(100, 100, 584, 671);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        frame.setContentPane(contentPane);
        contentPane.setLayout(null);
        scrollPane.setViewportView(rightEcon);
        rightEcon.setLayout(new GridLayout(0, 1, 0, 0));
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBounds(10, 10, 350, 550);
        contentPane.add(scrollPane);
        scrollPane.setColumnHeaderView(panel);
        panel.add(Label_1);


        back.setBounds(411, 11, 148, 23);
        contentPane.add(back);


        rightDepo.setLayout(new GridLayout(0, 1, 0, 0));
    }

    public JFrame getFrame() {
        return frame;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }
}
