package p1;

import p1.PStartController;
import p1.PStartModel;
import p1.PStartView;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class PPlataSalarii extends JFrame {
    private final static String USERNAME = "root";
    private final static String PASSWORD = "sssabb";
    private final static String DB_NAME = "Bancar";
    private final static String CONNECTION_LINK = "jdbc:mysql://localhost:3306/";
    private String cnp; // totul va primi cnp;

    JFrame frame = new JFrame("Pagina de plata a salariilor");
    JPanel panel = new JPanel();
    JLabel l1 = new JLabel("IBAN Cont Angajat: ");
    JLabel l2 = new JLabel("Valoare Salariu: ");
    JTextField t1 = new JTextField("Introduceti IBAN-ul Contului Angajatului");
    JTextField t2 = new JTextField("Introduceti Valoarea Salariului");

    JButton plateste = new JButton("Plateste");
    JButton back = new JButton("Back");


    public PPlataSalarii (String CNP)
    {
        this.cnp = CNP;
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                p1.PStartAdminView ad = new PStartAdminView(CNP);
                ad.getFrame().setVisible(true);
            }
        });

        plateste.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Connection connection;
                try{
                    connection = DriverManager.getConnection(CONNECTION_LINK + DB_NAME, USERNAME, PASSWORD);
                    Statement statement = connection.createStatement();
                    statement.executeQuery("Call adauga_fonduri('"+t1.getText()+"' , '"+t2.getText()+"');");

                }catch(SQLException ex)
                {
                    ex.printStackTrace();
                }
                frame.dispose();
                p1.PPlataSalarii ps = new PPlataSalarii(CNP);
                ps.getFrame().setVisible(true);
            }

        });

        frame.setBounds(100, 100, 617, 601);
        panel.setBorder(new EmptyBorder(5, 5, 5, 5));
        frame.setContentPane(panel);
        panel.setLayout(null);
        panel.add(l1);
        l1.setBounds(50,150,250,30);
        panel.add(l2);
        l2.setBounds(320,150,250,30);
        panel.add(t1);
        t1.setBounds(50,200,250,30);
        panel.add(t2);
        t2.setBounds(320,200,250,30);
        panel.add(plateste);
        plateste.setBounds(50,500,250,30);
        panel.add(back);
        back.setBounds(320,500,250,30);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(false);
    }

    public JFrame getFrame() {
        return frame;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }
}
