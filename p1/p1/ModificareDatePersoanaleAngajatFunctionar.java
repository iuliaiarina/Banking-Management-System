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
import javax.swing.JTextField;

    public class ModificareDatePersoanaleAngajatFunctionar extends JFrame {
        private final static String USERNAME = "root";
        private final static String PASSWORD = "sssabb";
        private final static String DB_NAME = "Bancar";
        private final static String CONNECTION_LINK = "jdbc:mysql://localhost:3306/";

        private JFrame frame = new JFrame("Modificare date personale");
        private JPanel contentPane;
        private JTextField textField;
        private JTextField textField_1;
        private JTextField textField_2;
        private JTextField textField_3;
        private JTextField textField_4;
        private JTextField textField_5;
        private JTextField textField_6;
        JButton back = new JButton("Back");
        JButton save = new JButton("SAVE");
        JLabel nume = new JLabel("Nume:");
        JLabel prenume = new JLabel("Prenume:");
        JLabel adresa = new JLabel("Adresa:");
        JLabel telefon = new JLabel("Telefon:");
        JLabel email = new JLabel("Email:");
        JLabel contact = new JLabel("Contact:");
        JLabel parola = new JLabel("Parola:");

        private String cnp;

        public ModificareDatePersoanaleAngajatFunctionar(String cnp) {
            this.cnp=cnp;
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setBounds(100, 100, 342, 313);
            contentPane = new JPanel();
            contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
            frame.setContentPane(contentPane);
            contentPane.setLayout(null);

            back.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    //inchid pag actuala
                    frame.dispose();
                    // deschidem pag back
                    PStartAngajatFunctionar viewAngajat =new PStartAngajatFunctionar(cnp);
                    viewAngajat.getFrame().setVisible(true);
                }
            });

            save.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    Connection connection;
                    try {
                        connection = DriverManager.getConnection(CONNECTION_LINK + DB_NAME, USERNAME, PASSWORD);
                        Statement statement = connection.createStatement();
                        if(!(textField.getText()).isEmpty()) {  // daca am introdus cv aici ->mod
                            statement.executeQuery("CALL modifca_persoana('"+textField.getText()+"',NULL,NULL,NULL,NULL,NULL,NULL,'"+cnp+"');");
                        }
                        if(!(textField_1.getText()).isEmpty()) {  // daca am introdus cv aici ->mod
                            statement.executeQuery("CALL modifca_persoana(NULL,'"+textField_1.getText()+"',NULL,NULL,NULL,NULL,NULL,'"+cnp+"');");
                        }
                        if(!(textField_2.getText()).isEmpty()) {  // daca am introdus cv aici ->mod
                            statement.executeQuery("CALL modifca_persoana(NULL,NULL,'"+textField_2.getText()+"',NULL,NULL,NULL,NULL,'"+cnp+"');");
                        }
                        if(!(textField_3.getText()).isEmpty()) {  // daca am introdus cv aici ->mod
                            statement.executeQuery("CALL modifca_persoana(NULL,NULL,NULL,'"+textField_3.getText()+"',NULL,NULL,NULL,'"+cnp+"');");
                        }
                        if(!(textField_4.getText()).isEmpty()) {  // daca am introdus cv aici ->mod
                            statement.executeQuery("CALL modifca_persoana(NULL,NULL,NULL,NULL,'"+textField_4.getText()+"',NULL,NULL,'"+cnp+"');");
                        }
                        if(!(textField_5.getText()).isEmpty()) {  // daca am introdus cv aici ->mod
                            statement.executeQuery("CALL modifca_persoana(NULL,NULL,NULL,NULL,NULL,'"+textField_5.getText()+"',NULL,'"+cnp+"');");
                        }
                        if(!(textField_6.getText()).isEmpty()) {  // daca am introdus cv aici ->mod
                            statement.executeQuery("CALL modifca_persoana(NULL,NULL,NULL,NULL,NULL,NULL,'"+textField_6.getText()+"','"+cnp+"');");
                        }

                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }
            });

            back.setBounds(229, 24, 89, 23);
            contentPane.add(back);
            save.setBounds(229, 238, 89, 23);
            contentPane.add(save);
            nume.setBounds(22, 54, 85, 14);
            contentPane.add(nume);
            prenume.setBounds(22, 79, 85, 14);
            contentPane.add(prenume);
            adresa.setBounds(22, 139, 85, 14);
            contentPane.add(adresa);
            telefon.setBounds(22, 167, 85, 14);
            contentPane.add(telefon);
            email.setBounds(22, 192, 85, 14);
            contentPane.add(email);
            contact.setBounds(22, 217, 85, 14);
            contentPane.add(contact);
            parola.setBounds(22, 104, 85, 14);
            contentPane.add(parola);
            textField = new JTextField();
            textField.setBounds(117, 51, 96, 20);
            contentPane.add(textField);
            textField.setColumns(10);
            textField_1 = new JTextField();
            textField_1.setBounds(117, 76, 96, 20);
            contentPane.add(textField_1);
            textField_1.setColumns(10);
            textField_2 = new JTextField();
            textField_2.setBounds(117, 101, 96, 20);
            contentPane.add(textField_2);
            textField_2.setColumns(10);
            textField_3 = new JTextField();
            textField_3.setBounds(117, 136, 96, 20);
            contentPane.add(textField_3);
            textField_3.setColumns(10);
            textField_4 = new JTextField();
            textField_4.setBounds(117, 164, 96, 20);
            contentPane.add(textField_4);
            textField_4.setColumns(10);
            textField_5 = new JTextField();
            textField_5.setBounds(117, 189, 96, 20);
            contentPane.add(textField_5);
            textField_5.setColumns(10);
            textField_6 = new JTextField();
            textField_6.setBounds(117, 214, 96, 20);
            contentPane.add(textField_6);
            textField_6.setColumns(10);
        }

        public JFrame getFrame() {
            return frame;
        }

        public void setFrame(JFrame frame) {
            this.frame = frame;
        }

    }

