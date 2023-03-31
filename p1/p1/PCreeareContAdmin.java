package p1;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class PCreeareContAdmin extends JFrame {
    private final static String USERNAME = "root";
    private final static String PASSWORD = "sssabb";
    private final static String DB_NAME = "Bancar";
    private final static String CONNECTION_LINK = "jdbc:mysql://localhost:3306/";

    private JFrame frame = new JFrame("Creeaza Cont Angajat");
    private JPanel contentpane = new JPanel();
    private JLabel l1 = new JLabel("Nume:");
    private JLabel l2 = new JLabel("Prenume: ");
    private JLabel l3 = new JLabel("Parola: ");
    private JLabel l4 = new JLabel("Adresa: ");
    private JLabel l5 = new JLabel("Telefon: ");
    private JLabel l6 = new JLabel("Email: ");
    private JLabel l7 = new JLabel("Contact: ");
    private JLabel l8 = new JLabel("CNP: ");
    private JLabel l9 = new JLabel("Norma: ");
    private JLabel l10 = new JLabel("Salariu: ");
    private JLabel l11 = new JLabel("Departament: ");
    private JLabel l12 = new JLabel("Sucursala: ");

    private JTextField t1 = new JTextField("Introduceti Nume");
    private JTextField t2 = new JTextField("Introduceti Preume");
    private JTextField t3 = new JTextField("Introduceti Parola");
    private JTextField t4 = new JTextField("Introduceti Adresa");
    private JTextField t5 = new JTextField("Introduceti Telefon");
    private JTextField t6 = new JTextField("Introduceti Email");
    private JTextField t7 = new JTextField("Introduceti Contact");
    private JTextField t8 = new JTextField("Introduceti CNP");
    private JTextField t9 = new JTextField("Introduceti Norma");
    private JTextField t10 = new JTextField("Introduceti Salariu");
    private JTextField t11 = new JTextField("Departament");
    private JTextField t12 = new JTextField("Sucursala");

    private JButton back = new JButton("BACK");
    private JButton creeare = new JButton("Creeaza Cont");

    public PCreeareContAdmin(String CNP) {

        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                p1.PStartAdminView ps = new PStartAdminView(CNP);
                ps.getFrame().setVisible(true);
            }
        });

        creeare.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Connection connection;
                try{
                    connection = DriverManager.getConnection(CONNECTION_LINK + DB_NAME, USERNAME, PASSWORD);
                    Statement statement = connection.createStatement();
                    statement.executeQuery("Call Creare_Admin('"+t1.getText()+"' , '"+ t2.getText() + "' , '"+t3.getText()+"' , '"+t4.getText()+
                            "' , '"+ t5.getText() + "' , '" + t6.getText() + "' , '"+ t7.getText()+"' , '" + t8.getText()+"' , '" +t9.getText() +"' , '"
                            +t10.getText()+"' , '"+t11.getText() +"' , '"+t12.getText()+"');");
                }
                catch(SQLException ex) {
                    ex.printStackTrace();
                }
                frame.dispose();
                p1.PCreeareContAdmin ca = new PCreeareContAdmin(CNP);
                ca.getFrame().setVisible(true);
            }
        });

        contentpane.add(l1);
        l1.setBounds(10, 30, 250, 23);
        contentpane.add(l2);
        l2.setBounds(10,70,250,23);
        contentpane.add(l3);
        l3.setBounds(10,110,250,23);
        contentpane.add(l4);
        l4.setBounds(10,150,250,23);
        contentpane.add(l5);
        l5.setBounds(10,190,250,23);
        contentpane.add(l6);
        l6.setBounds(10,230,250,23);
        contentpane.add(l7);
        l7.setBounds(10,270,250,23);
        contentpane.add(l8);
        l8.setBounds(10,310,250,23);
        contentpane.add(l9);
        l9.setBounds(10,350,250,23);
        contentpane.add(l10);
        l10.setBounds(10,390,250,23);
        contentpane.add(l11);
        l11.setBounds(10,430,250,23);

        contentpane.add(t1);
        t1.setBounds(260, 30, 250, 23);
        contentpane.add(t2);
        t2.setBounds(260, 70, 250, 23);
        contentpane.add(t3);
        t3.setBounds(260, 110, 250, 23);
        contentpane.add(t4);
        t4.setBounds(260, 150, 250, 23);
        contentpane.add(t5);
        t5.setBounds(260, 190, 250, 23);
        contentpane.add(t6);
        t6.setBounds(260, 230, 250, 23);
        contentpane.add(t7);
        t7.setBounds(260, 270, 250, 23);
        contentpane.add(t8);
        t8.setBounds(260, 310, 250, 23);
        contentpane.add(t9);
        t9.setBounds(260, 350, 250, 23);
        contentpane.add(t10);
        t10.setBounds(260, 390, 250, 23);
        contentpane.add(t11);
        t11.setBounds(260, 430, 250, 23);

        contentpane.add(back);
        back.setBounds(10, 500, 250, 23);

        contentpane.add(creeare);
        creeare.setBounds(280,500,250,23);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(100, 100, 617, 601);
        contentpane.setBorder(new EmptyBorder(5, 5, 5, 5));
        frame.setContentPane(contentpane);
        contentpane.setLayout(null);
    }

    public JPanel getContentpane() {
        return contentpane;
    }

    public void setContentpane(JPanel contentpane) {
        this.contentpane = contentpane;
    }

    public JFrame getFrame() {
        return frame;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }
}
