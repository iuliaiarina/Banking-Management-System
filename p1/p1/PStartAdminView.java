package p1;

import p1.Administrator;
import p1.PStartController;
import p1.PStartModel;
import p1.PStartView;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class PStartAdminView extends JFrame {
    private final static String USERNAME = "root";
    private final static String PASSWORD = "sssabb";
    private final static String DB_NAME = "Bancar";
    private final static String CONNECTION_LINK = "jdbc:mysql://localhost:3306/";
    private String cnp; // totul va primi cnp;

    private JFrame frame = new JFrame("paginaStartAdmin");
    private JPanel contentPane = new JPanel();
    private JLabel label_0 = new JLabel("CNP:");
    private JLabel label_1 = new JLabel("Nume:");
    private JLabel label_2 = new JLabel("Prenume:");
    private JLabel label_3 = new JLabel("Adresa:");
    private JLabel label_4 = new JLabel("Telefon:");
    private JLabel label_5 = new JLabel("Email:");
    private JLabel label_6 = new JLabel("Salariu:");
    private JLabel label_7 = new JLabel("Norma:");
    private JLabel label_8 = new JLabel("Sucursala:");
    private JLabel label_9 = new JLabel("Departament:");
    private JButton exit = new JButton("Exit");
    private JButton lista_clienti = new JButton("Lista Clienti");
    private JButton lista_angajati = new JButton("Lista Angajati");
    private JButton lista_admini = new JButton("Lista Administratori");
    private JButton lista_transferuri = new JButton("Lista Transferuri");
    private JButton creeareContClient = new JButton("Creeare cont Client");
    private JButton creeareContAngajat = new JButton("Creeare cont Angajat");
    private JButton creeareContAdministrator = new JButton("Creeare cont Administrator");
    private JButton plateste_salarii = new JButton("Plata Salarii");
    private JButton aproba_card = new JButton("Aprobare Cerere Card");




    public PStartAdminView(String cnp) {
        this.cnp = cnp;
        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                p1.PStartView view = new PStartView();
                p1.PStartModel model = new PStartModel();
                p1.PStartController controller = new PStartController(model, view);
                view.getFrame().setVisible(true);
            }

        });

        lista_clienti.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                p1.PListaClienti lc = new PListaClienti(cnp);
                lc.getFrame().setVisible(true);
            }
        });
        lista_angajati.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                p1.PListaAngajati lan = new PListaAngajati(cnp);
                lan.getFrame().setVisible(true);
            }
        });
        lista_admini.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                p1.PListaAdmini la = new PListaAdmini(cnp);
                la.getFrame().setVisible(true);
            }
        });
        lista_transferuri.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                p1 .PListaTransferuri lt = new PListaTransferuri(cnp);
                lt.getFrame().setVisible(true);

            }
        });
        creeareContClient.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                p1.PCreeareContClient cc = new PCreeareContClient(cnp);
                cc.getFrame().setVisible(true);
            }
        });
        creeareContAngajat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                p1.PCreeareContAngajat ca = new PCreeareContAngajat(cnp);
                ca.getFrame().setVisible(true);
            }
        });
        creeareContAdministrator.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                p1.PCreeareContAdmin ca = new PCreeareContAdmin(cnp);
                ca.getFrame().setVisible(true);
            }
        });
        plateste_salarii.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                p1.PPlataSalarii ps = new PPlataSalarii(cnp);
                ps.getFrame().setVisible(true);
            }
        });
        aproba_card.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                p1.PAprobareCerereCard pa = new PAprobareCerereCard(cnp);
                pa.getFrame().setVisible(true);
            }
        });

        Connection connection;
        p1.Administrator admin = null;
        try{
            connection = DriverManager.getConnection(CONNECTION_LINK + DB_NAME, USERNAME, PASSWORD);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from persoana join angajat on persoana.cnp = angajat.cnp where angajat.cnp in (Select * from administrator) and angajat.cnp = "+this.cnp);
            while (resultSet.next()) {
                admin = new Administrator(resultSet.getString("NUME"),
                        resultSet.getString("PRENUME"),
                        resultSet.getString("PAROLA"),
                        resultSet.getString("ADRESA"),
                        resultSet.getString("TELEFON"),
                        resultSet.getString("EMAIL"),
                        resultSet.getString("CONTACT"),
                        resultSet.getString("CNP"),
                        resultSet.getInt("NORMA"),
                        resultSet.getInt("SALARIU"),
                        resultSet.getString("DEPARTAMENT"),
                        resultSet.getString("SUCURSALA"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(admin == null)
        {
            System.out.println("eroare");
        }
        else{
            label_0.setText("CNP: "+admin.getCNP());
            label_1.setText("Nume: "+admin.getNume());
            label_2.setText("Prenume: "+admin.getPrenume());
            label_3.setText("Adresa: "+admin.getAdresa());
            label_4.setText("Telefon: "+admin.getTelefon());
            label_5.setText("Email: "+admin.getEmail());
            label_6.setText("Salariu: "+admin.getSalariu());
            label_7.setText("Norma: "+admin.getNorma());
            label_8.setText("Sucursala: "+admin.getSucursala());
            label_9.setText("Departament: "+admin.getDepartament());
        }

        contentPane.add(exit);
        contentPane.add(lista_clienti);
        contentPane.add(lista_angajati);
        contentPane.add(lista_admini);
        contentPane.add(lista_transferuri);
        contentPane.add(creeareContClient);
        contentPane.add(creeareContAngajat);
        contentPane.add(creeareContAdministrator);
        contentPane.add(plateste_salarii);
        contentPane.add(aproba_card);

        exit.setBounds(324, 33, 250, 23);
        lista_clienti.setBounds(324,65,250,23);
        lista_angajati.setBounds(324,97,250,23);
        lista_admini.setBounds(324,129,250,23);
        lista_transferuri.setBounds(324,164,250,23);
        creeareContClient.setBounds(324,199,250,23);
        creeareContAngajat.setBounds(324,234,250,23);
        creeareContAdministrator.setBounds(324,269,250,23);
        plateste_salarii.setBounds(324,304,250,23);
        aproba_card.setBounds(324,339,250,23);


        frame.setBounds(100, 100, 617, 601);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        frame.setContentPane(contentPane);
        contentPane.setLayout(null);

        label_0.setBounds(34, 31, 247, 26);
        contentPane.add(label_0);
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
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(false);
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





