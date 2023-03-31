
package p1;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class apCereCardAng extends JFrame {
    private final static String USERNAME = "root";
    private final static String PASSWORD = "sssabb";
    private final static String DB_NAME = "Bancar";
    private final static String CONNECTION_LINK = "jdbc:mysql://localhost:3306/";

    private JFrame frame = new JFrame("Pagina Cererilor de Card");
    private JPanel contentPane = new JPanel();
    private JButton back = new JButton("BACK");
    JLabel Label_1 = new JLabel("Cereri: ");
    private final JPanel rightEcon = new JPanel();
    JScrollPane scrollPane = new JScrollPane();
    JPanel panel = new JPanel();

    JLabel l1 = new JLabel("Introduceti ID-ul cererii:");
    JTextField t1 = new JTextField("ID");
    JButton aproba = new JButton("Aproba Cerere");

    public apCereCardAng(String CNP) {
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                p1.PStartAngajatFunctionar pad = new PStartAngajatFunctionar(CNP);
                pad.getFrame().setVisible(true);
            }
        });
        Connection connection;
        try {
            connection = DriverManager.getConnection(CONNECTION_LINK + DB_NAME, USERNAME, PASSWORD);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT * FROM Cerere_card");
            while(resultSet.next())
            {
                JLabel label1 = new JLabel("CNP Client:          "+resultSet.getString("CNP"));
                JLabel label2 = new JLabel("IBAN Client:          "+resultSet.getString("IBAN"));
                JLabel label3 = new JLabel("CNP Administrator:          "+resultSet.getString("CNP_admin"));
                JLabel label4 = new JLabel("CNP Angajat:          "+resultSet.getString("CNP_angajat"));
                JLabel label5 = new JLabel("ID Cerere:          "+resultSet.getString("cerere_id"));
                JLabel label6 = new JLabel("------------------------------------------------------------------------------------------------------");
                rightEcon.add(label1);
                rightEcon.add(label2);
                rightEcon.add(label3);
                rightEcon.add(label4);
                rightEcon.add(label5);
                rightEcon.add(label6);
            }
        } catch (SQLException exc) {
            exc.printStackTrace();
        }
        aproba.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Connection connection;
                try {
                    connection = DriverManager.getConnection(CONNECTION_LINK + DB_NAME, USERNAME, PASSWORD);
                    Statement statement = connection.createStatement();
                    //System.out.println("Call aprobare_cerere_card_admin( "+t1.getText()+" ,'"+CNP+"');");
                    statement.executeQuery("Call aprobare_cerere_card_angajat( '"+CNP+"' ,"+t1.getText()+");");
                }catch (SQLException exc) {
                    exc.printStackTrace();
                }
                frame.dispose();
                p1.apCereCardAng pc = new apCereCardAng(CNP);
                pc.getFrame().setVisible(true);

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
