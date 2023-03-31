package p1;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.JButton;

public class PStartAngajatHR extends JFrame {
        private final static String USERNAME = "root";
        private final static String PASSWORD = "sssabb";
        private final static String DB_NAME = "Bancar";
        private final static String CONNECTION_LINK = "jdbc:mysql://localhost:3306/";
         private String cnp;

         private JFrame frame = new JFrame("Pagina de start client HR");
        private JPanel contentPane = new JPanel();
        private JLabel Label = new JLabel("CNP :");
        private JLabel Label_1 = new JLabel("Nume :");
        private JLabel Label_2 = new JLabel("Prenume :");
        private JLabel Label_3 = new JLabel("Salariu :");
        private JLabel Label_4 = new JLabel("Norma :");
        private  JLabel Label_5 = new JLabel("Departament :");
        private  JLabel Label_6 = new JLabel("Sucursala : ");
        private  JLabel Label_7 = new JLabel("Telefon : ");
        private  JLabel Label_8 = new JLabel("E-mail : ");
        private  JLabel Label_9 = new JLabel("Adresa : ");
        private  JButton Button = new JButton("Lista clientilor");
        private  JButton ListaAngajatilor = new JButton("Lista angajatilor");
        private  JButton ListaAdministratorilor = new JButton("Lista administratorilor");
        private  JButton ButtonLogout = new JButton("LOGOUT");
        private  JButton ButtonModificareDate = new JButton("Modifica datele personale");

        public PStartAngajatHR(String cnp) {
            this.cnp = cnp;

            //BUTON DELISTA ADMINISTRATORI
            ListaAdministratorilor.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    //inchid pagina actuala
                    frame.dispose();
                    //deschid pagina care trebuie
                    PListAdmini pla = new PListAdmini(cnp);
                    pla.getFrame().setVisible(true);
                }
            });

            //BUTON DE LISTA ANGAJATI
            ListaAngajatilor.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    //stergem pagina actuala
                    frame.dispose();
                    //deschidem pagina de care avem nevoie
                    PListAngajati pla = new PListAngajati(cnp);
                    pla.getFrame().setVisible(true);
                }
            });

            //BUTON DE LISTA CLIENTI
            Button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    //stergem pagina actuala
                    frame.dispose();
                    //o deschidem pe cea noua
                    PListClienti plc = new PListClienti(cnp);
                    plc.getFrame().setVisible(true);
                }
            });

            Connection connection;
            try{
                connection = DriverManager.getConnection(CONNECTION_LINK + DB_NAME, USERNAME, PASSWORD);
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(
                        "SELECT * from persoana join angajat on persoana.cnp=angajat.cnp where persoana.cnp=" + this.cnp);
                Angajat a = null;
                while(resultSet.next()){
                    a = new Angajat(resultSet.getString("NUME"), resultSet.getString("PRENUME"), resultSet.getString("PAROLA"),
                            resultSet.getString("ADRESA"), resultSet.getString("TELEFON"), resultSet.getString("EMAIL"),
                            resultSet.getString("CONTACT"), resultSet.getString("CNP"), resultSet.getInt("NORMA"),
                            resultSet.getInt("SALARIU"), resultSet.getString("DEPARTAMENT"), resultSet.getString("SUCURSALA"));
                }

                if (a == null)
                    System.out.println("eroare");
                else {
                    Label.setText("CNP : " + a.getCNP());
                    Label_1.setText("Nume: " + a.getNume());
                    Label_2.setText("Prenume : " + a.getPrenume());
                    Label_3.setText("Salariu : " + a.getSalariu());
                    Label_4.setText("Norma : " + a.getNorma());
                    Label_5.setText("Departament : " + a.getDepartament());
                    Label_6.setText("Sucursala : " + a.getSucursala());
                    Label_7.setText("Telefon : " + a.getTelefon());
                    Label_8.setText("E-mail : " + a.getEmail());
                    Label_9.setText("Adresa : " + a.getAdresa());
                                   }

            }catch (SQLException e){
                e.printStackTrace();
            }
            ButtonLogout.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    // inchid pag actuala
                    frame.dispose();
                    // deschidem start:
                    PStartView view = new PStartView();
                    PStartModel model = new PStartModel();
                    PStartController controller = new PStartController(model, view);
                    view.getFrame().setVisible(true);
                }
            });

            ButtonModificareDate.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    //inchid pagina actuala
                    frame.dispose();
                    //deschidem pagina de modificare date
                    ModificareDatePersonaleAngajatHR mdp = new ModificareDatePersonaleAngajatHR(cnp);
                    mdp.getFrame().setVisible(true);

                }
            });

            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setBounds(100, 100, 617, 513);

            contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
            frame.setContentPane(contentPane);
            contentPane.setLayout(null);

            Label.setFont(new Font("Tahoma", Font.PLAIN, 15));
            Label.setBounds(23, 35, 205, 14);
            contentPane.add(Label);

            Label_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
            Label_1.setBounds(23, 71, 205, 14);
            contentPane.add(Label_1);

            Label_2.setFont(new Font("Tahoma", Font.PLAIN, 15));
            Label_2.setBounds(23, 113, 205, 14);
            contentPane.add(Label_2);

            Label_3.setFont(new Font("Tahoma", Font.PLAIN, 15));
            Label_3.setBounds(23, 154, 205, 14);
            contentPane.add(Label_3);

            Label_4.setFont(new Font("Tahoma", Font.PLAIN, 15));
            Label_4.setBounds(23, 195, 205, 14);
            contentPane.add(Label_4);

            Label_5.setFont(new Font("Tahoma", Font.PLAIN, 15));
            Label_5.setBounds(23, 236, 205, 14);
            contentPane.add(Label_5);

            Label_6.setFont(new Font("Tahoma", Font.PLAIN, 15));
            Label_6.setBounds(23, 280, 205, 14);
            contentPane.add(Label_6);

            Label_7.setFont(new Font("Tahoma", Font.PLAIN, 15));
            Label_7.setBounds(23, 322, 205, 14);
            contentPane.add(Label_7);

            Label_8.setFont(new Font("Tahoma", Font.PLAIN, 15));
            Label_8.setBounds(23, 367, 205, 14);
            contentPane.add(Label_8);

            Label_9.setFont(new Font("Tahoma", Font.PLAIN, 15));
            Label_9.setBounds(23, 409, 205, 14);
            contentPane.add(Label_9);

            Button.setFont(new Font("Tahoma", Font.PLAIN, 15));
            Button.setBounds(387, 48, 167, 23);
            contentPane.add(Button);

            ListaAngajatilor.setFont(new Font("Tahoma", Font.PLAIN, 15));
            ListaAngajatilor.setBounds(387, 111, 167, 23);
            contentPane.add(ListaAngajatilor);

            ListaAdministratorilor.setFont(new Font("Tahoma", Font.PLAIN, 15));
            ListaAdministratorilor.setBounds(387, 170, 167, 23);
            contentPane.add(ListaAdministratorilor);

            ButtonLogout.setFont(new Font("Tahoma", Font.PLAIN, 15));
            ButtonLogout.setBounds(387, 400, 167, 23);
            contentPane.add(ButtonLogout);

            ButtonModificareDate.setFont(new Font("Tahoma", Font.PLAIN, 15));
            ButtonModificareDate.setBounds(387, 230, 167, 23);
            contentPane.add(ButtonModificareDate);
        }

    public JFrame getFrame() {
        return frame;
    }
    public void setFrame(JFrame frame) {
        this.frame = frame;
    }
}


