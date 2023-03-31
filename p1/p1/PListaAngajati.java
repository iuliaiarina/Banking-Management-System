package p1;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class PListaAngajati extends JFrame {
    private final static String USERNAME = "root";
    private final static String PASSWORD = "sssabb";
    private final static String DB_NAME = "Bancar";
    private final static String CONNECTION_LINK = "jdbc:mysql://localhost:3306/";

    private JFrame frame = new JFrame("Lista tuturor angajatilor");
    JButton back = new JButton("BACK");
    private JPanel contentPane;
    private final JPanel rightDepo = new JPanel();
    JScrollPane scrollPane = new JScrollPane();
    JPanel panel = new JPanel();
    JLabel Label_1 = new JLabel("Lista angajatilor");
    private final JPanel rightEcon = new JPanel();

    public PListaAngajati(String cnp){
        //Populam lista de angajati
        Connection connection;
        try{
            connection = DriverManager.getConnection(CONNECTION_LINK + DB_NAME, USERNAME, PASSWORD);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT * FROM ANGAJAT JOIN PERSOANA ON Angajat.CNP = PERSOANA.CNP ");

            Transfer transfer = null;
            while(resultSet.next()){
                //pentru fiecare tupla din selectul de mai sus

                Angajat c = new Angajat(resultSet.getString("nume"), resultSet.getString("prenume"),
                        resultSet.getString("parola"), resultSet.getString("adresa"), resultSet.getString("telefon"),
                        resultSet.getString("email"), resultSet.getString("contact"),
                        resultSet.getString("cnp"), resultSet.getInt("norma"),
                        resultSet.getInt("salariu"), resultSet.getString("departament"),
                        resultSet.getString("sucursala"));

                JLabel label1 = new JLabel("Nume :        " + c.getNume());
                JLabel label2 = new JLabel("Prenume:        " + c.getPrenume());
                JLabel label3 = new JLabel("norma: " + c.getNorma());
                JLabel label4 = new JLabel("Telefon:     " + c.getTelefon());
                JLabel label5 = new JLabel("Adresa: " + c.getAdresa());
                JLabel label6 = new JLabel("Salar: " + c.getSalariu());
                JLabel label7 = new JLabel("CNP: " + c.getCNP());
                JLabel label8 = new JLabel("Departament: " + c.getDepartament());
                JLabel label9 = new JLabel("Email: " + c.getEmail());
                JLabel label10 = new JLabel("Sucursala: "+ c.getSucursala());
                JLabel space = new JLabel("------------------------------------------------------------------------------------------------------");
                rightEcon.add(label1);
                rightEcon.add(label2);
                rightEcon.add(label3);
                rightEcon.add(label4);
                rightEcon.add(label5);
                rightEcon.add(label6);
                rightEcon.add(label7);
                rightEcon.add(label8);
                rightEcon.add(label10);
                rightEcon.add(label9);
                rightEcon.add(space);
            }
        }catch(SQLException exc){
            exc.printStackTrace();
        }

        //BACK_BUTTON
        back.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //inchid pagina asta
                frame.dispose();
                PStartAdminView ad = new PStartAdminView(cnp);
                ad.getFrame().setVisible(true);
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
