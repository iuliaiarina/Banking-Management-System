package p1;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class PListaTransferuri extends JFrame {
    private final static String USERNAME = "root";
    private final static String PASSWORD = "sssabb";
    private final static String DB_NAME = "Bancar";
    private final static String CONNECTION_LINK = "jdbc:mysql://localhost:3306/";
    private JFrame frame = new JFrame("Lista tuturor tranzactiilor");
    JButton back = new JButton("BACK");
    JLabel Label_1 = new JLabel("Tranzactii");
    private final JPanel rightEcon = new JPanel();
    JScrollPane scrollPane = new JScrollPane();
    JPanel panel = new JPanel();
    private JPanel contentPane;

    private JButton aproba = new JButton("Aproba Transfer");
    private JLabel l1 = new JLabel("Introduceti ID-ul transferului");
    private JTextField t1 = new JTextField("ID");

    public PListaTransferuri (String CNP) {

        //BACK
        back.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //inchid pagina asta
                frame.dispose();
                PStartAdminView ad = new PStartAdminView(CNP);
                ad.getFrame().setVisible(true);

            }
        });
        aproba.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Connection connection1;
                try{
                    connection1 = DriverManager.getConnection(CONNECTION_LINK + DB_NAME, USERNAME, PASSWORD);
                    Statement statement1 = connection1.createStatement();
                    statement1.executeQuery("Call aprobare_transfer('"+t1.getText()+"' , '"+CNP+"');");
                }catch (SQLException exc) {
                    exc.printStackTrace();
                }
                frame.dispose();
                p1.PListaTransferuri lt = new PListaTransferuri(CNP);
                lt.getFrame().setVisible(true);



            }
        });

        Connection connection;
        try {
            connection = DriverManager.getConnection(CONNECTION_LINK + DB_NAME, USERNAME, PASSWORD);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT * FROM TRANSFER");

            Transfer transfer = null;
            while (resultSet.next()) {
                //pentru fiecare tupla din selectul de mai sus
                // Transfer tr = transfer;
                Transfer tr = new Transfer(resultSet.getString("STATUS"), resultSet.getString("nume_client_destinatie"),
                        resultSet.getString("nume_client_sursa"), resultSet.getDate("data_creare"),
                        resultSet.getDouble("suma"), resultSet.getString("Iban_destinatie"),
                        resultSet.getString("IBAN_sursa"), resultSet.getString("CNP_angajat"),
                        resultSet.getInt("transfer_id"));

                JLabel label1 = new JLabel("Status :        " + tr.getStatus());
                JLabel label2 = new JLabel("Nume client destinatie:        " + tr.getNume_client_destinatie());
                JLabel label3 = new JLabel("Nume client sursa: " + tr.getNume_client_sursa());
                JLabel label4 = new JLabel("Data transferului:     " + tr.getData_creare());
                JLabel label5 = new JLabel("Suma transferata: " + tr.getSuma());
                JLabel label6 = new JLabel("IBAN destinatie: " + tr.getIBAN_destinatie());
                JLabel label7 = new JLabel("IBAN sursa: " + tr.getIBAN_sursa());
                JLabel label8 = new JLabel("CNP angajat: " + tr.getCNP_angajat());
                JLabel label9 = new JLabel("ID-ul transferului: " + tr.getTransfer_id());
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
        scrollPane.setBounds(10, 60, 350, 550);
        contentPane.add(scrollPane);
        scrollPane.setColumnHeaderView(panel);
        panel.add(Label_1);

        back.setBounds(411, 11, 148, 23);
        contentPane.add(back);
        contentPane.add(l1);
        l1.setBounds(411, 100, 148, 23);
        contentPane.add(t1);
        t1.setBounds(411, 158, 148, 23);
        contentPane.add(aproba);
        aproba.setBounds(411, 191, 148, 23);
    }
    public JFrame getFrame() {
        return frame;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }
}
