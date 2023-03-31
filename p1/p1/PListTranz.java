package p1;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class PListTranz extends JFrame {
    private final static String USERNAME = "root";
    private final static String PASSWORD = "sssabb";
    private final static String DB_NAME = "Bancar";
    private final static String CONNECTION_LINK = "jdbc:mysql://localhost:3306/";

    private JFrame frame = new JFrame("Lista tuturor tranzactiilor");
    JButton back = new JButton("BACK");
    private JPanel contentPane;
    private final JPanel rightDepo = new JPanel();
    JScrollPane scrollPane = new JScrollPane();
    JPanel panel = new JPanel();
    JLabel Label_1 = new JLabel("Tranzactii");
    private final JPanel rightEcon = new JPanel();
    JButton tranzactie = new JButton("xxxxxxxx");
    JButton ordStatus = new JButton("Status /\\ \\/");
    JButton ordClient = new JButton("C. Sursa /\\ \\/");



    public PListTranz (String CNP){

            //BACK
            back.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    //inchid pagina asta
                    frame.dispose();
                    PStartAngajatFunctionar af = new PStartAngajatFunctionar(CNP);
                    af.getFrame().setVisible(true);

                }
            });

            //LISTA TRANZ + APROBARE TRANZ
            Connection connection;
            try{
                connection = DriverManager.getConnection(CONNECTION_LINK + DB_NAME, USERNAME, PASSWORD);
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(
                                "SELECT * FROM TRANSFER;");

                Transfer transfer = null;
                while(resultSet.next()){
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
                    if(tr.getStatus().equals("CREATED")){
                        JButton aprobare = new JButton("Aprobare transfer");
                        rightEcon.add(aprobare);

                        //punem aici action listener pe buton, deoarece daca nu se creeaza nu putem pune action listener pe el
                        aprobare.addActionListener(new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                   Connection connection;
                                    try {
                                        connection = DriverManager.getConnection(CONNECTION_LINK + DB_NAME, USERNAME, PASSWORD);
                                        Statement statement = connection.createStatement();
                                        ResultSet resultSet = statement.executeQuery("CALL aprobare_transfer ('"
                                                + tr.getTransfer_id() + "','" + CNP + "');");
                                    } catch (SQLException e1) {
                                        e1.printStackTrace();
                                    }

                                    //refresh la pagina
                                    frame.dispose();
                                    PListTranz pl = new PListTranz(CNP);
                                    pl.getFrame().setVisible(true);
                                }
                        });
                    }

                    rightEcon.add(space);
                }

            }catch(SQLException exc){
                exc.printStackTrace();
            }


            //BUTONUL DE ORDER BY STATUS
            ordStatus.addActionListener(new ActionListener() {


                public void actionPerformed(ActionEvent e) {
                    String text = ordStatus.getText();
                    String ascDesc = text.substring(7, 12);
                    Connection connection;
                    PListTranz pl = new PListTranz(CNP);
                    System.out.println(ordStatus.getText());
                    if (ascDesc.equals("/\\ \\/")){//daca inca nu a fost setata vreo modalitate de filtrare
                        // Connection connection; //Facem ca lista de tranzactii sa fie usa in ordine ascendenta dupa status
                        try {
                            connection = DriverManager.getConnection(CONNECTION_LINK + DB_NAME, USERNAME, PASSWORD);
                            Statement statement = connection.createStatement();
                            ResultSet resultSet = statement.executeQuery(
                                    "SELECT * FROM TRANSFER ORDER BY STATUS ASC;");

                            Transfer transfer = null;
                            pl.rightEcon.removeAll();
                            while(resultSet.next()){
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
                                pl.rightEcon.add(label1);
                                pl.rightEcon.add(label2);
                                pl.rightEcon.add(label3);
                                pl.rightEcon.add(label4);
                                pl. rightEcon.add(label5);
                                pl.rightEcon.add(label6);
                                pl.rightEcon.add(label7);
                                pl. rightEcon.add(label8);
                                pl. rightEcon.add(label9);
                                if(tr.getStatus().equals("CREATED")){
                                    JButton aprobare = new JButton("Aprobare transfer");
                                    pl.rightEcon.add(aprobare);

                                    //punem aici action listener pe buton, deoarece daca nu se creeaza nu putem pune action listener pe el
                                    aprobare.addActionListener(new ActionListener() {
                                        public void actionPerformed(ActionEvent e) {
                                            Connection connection;
                                            try {
                                                connection = DriverManager.getConnection(CONNECTION_LINK + DB_NAME, USERNAME, PASSWORD);
                                                Statement statement = connection.createStatement();
                                                ResultSet resultSet = statement.executeQuery("CALL aprobare_transfer ('"
                                                        + tr.getTransfer_id() + "','" + CNP + "');");
                                            } catch (SQLException e1) {
                                                e1.printStackTrace();
                                            }
                                        }
                                    });
                                }
                                pl.rightEcon.add(space);
                            }
                        } catch (SQLException exc) {
                            exc.printStackTrace();
                        }
                        //refresh la pagina
                        frame.dispose();

                        pl.getFrame().setVisible(true);
                        pl.ordStatus.setText("Status /\\   ");

                    }else if(ascDesc.equals("/\\   ")){//acum ii ascendenta, si o face sa fie descendenta
                        //dam refresh la pagina
                        try {
                            connection = DriverManager.getConnection(CONNECTION_LINK + DB_NAME, USERNAME, PASSWORD);
                            Statement statement = connection.createStatement();
                            ResultSet resultSet = statement.executeQuery(
                                    "SELECT * FROM TRANSFER ORDER BY STATUS DESC;");

                            Transfer transfer = null;
                            pl.rightEcon.removeAll();
                            while(resultSet.next()){
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
                                pl.rightEcon.add(label1);
                                pl.rightEcon.add(label2);
                                pl.rightEcon.add(label3);
                                pl.rightEcon.add(label4);
                                pl. rightEcon.add(label5);
                                pl.rightEcon.add(label6);
                                pl.rightEcon.add(label7);
                                pl. rightEcon.add(label8);
                                pl. rightEcon.add(label9);
                                if(tr.getStatus().equals("CREATED")){
                                    JButton aprobare = new JButton("Aprobare transfer");
                                    pl.rightEcon.add(aprobare);

                                    //punem aici action listener pe buton, deoarece daca nu se creeaza nu putem pune action listener pe el
                                    aprobare.addActionListener(new ActionListener() {
                                        public void actionPerformed(ActionEvent e) {
                                            Connection connection;
                                            try {
                                                connection = DriverManager.getConnection(CONNECTION_LINK + DB_NAME, USERNAME, PASSWORD);
                                                Statement statement = connection.createStatement();
                                                ResultSet resultSet = statement.executeQuery("CALL aprobare_transfer ('"
                                                        + tr.getTransfer_id() + "','" + CNP + "');");
                                            } catch (SQLException e1) {
                                                e1.printStackTrace();
                                            }
                                        }
                                    });
                                }
                                pl.rightEcon.add(space);
                            }
                        } catch (SQLException exc) {
                            exc.printStackTrace();
                        }
                        frame.dispose();

                        pl.getFrame().setVisible(true);
                        pl.ordStatus.setText("Status \\/    ");
                    }else { //ii descendenta si o facem inapoi STANDARD
                        //dam refresh la pagina cu nou nostru
                        frame.dispose();

                        pl.getFrame().setVisible(true);
                        pl.ordStatus.setText("Status /\\ \\/");
                    }

                }
            });

            // BUTONUL DE ORDER BY NUME DE FAMILIE CLIENT
            ordClient.addActionListener(new ActionListener() {


                public void actionPerformed(ActionEvent e) {
                    String text = ordClient.getText();
                    String ascDesc = text.substring(9, 14);
                    Connection connection;
                    PListTranz pl = new PListTranz(CNP);
                    System.out.println(text);
                    if (ascDesc.equals("/\\ \\/")){//daca inca nu a fost setata vreo modalitate de filtrare
                        // Connection connection; //Facem ca lista de tranzactii sa fie usa in ordine ascendenta dupa client
                        try {
                            connection = DriverManager.getConnection(CONNECTION_LINK + DB_NAME, USERNAME, PASSWORD);
                            Statement statement = connection.createStatement();
                            ResultSet resultSet = statement.executeQuery(
                                    "SELECT * FROM TRANSFER ORDER BY NUME_CLIENT_SURSA ASC;");

                            Transfer transfer = null;
                            pl.rightEcon.removeAll();
                            while(resultSet.next()){
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
                                pl.rightEcon.add(label1);
                                pl.rightEcon.add(label2);
                                pl.rightEcon.add(label3);
                                pl.rightEcon.add(label4);
                                pl. rightEcon.add(label5);
                                pl.rightEcon.add(label6);
                                pl.rightEcon.add(label7);
                                pl. rightEcon.add(label8);
                                pl. rightEcon.add(label9);
                                if(tr.getStatus().equals("CREATED")){
                                    JButton aprobare = new JButton("Aprobare transfer");
                                    pl.rightEcon.add(aprobare);

                                    //punem aici action listener pe buton, deoarece daca nu se creeaza nu putem pune action listener pe el
                                    aprobare.addActionListener(new ActionListener() {
                                        public void actionPerformed(ActionEvent e) {
                                            Connection connection;
                                            try {
                                                connection = DriverManager.getConnection(CONNECTION_LINK + DB_NAME, USERNAME, PASSWORD);
                                                Statement statement = connection.createStatement();
                                                ResultSet resultSet = statement.executeQuery("CALL aprobare_transfer ('"
                                                        + tr.getTransfer_id() + "','" + CNP + "');");
                                            } catch (SQLException e1) {
                                                e1.printStackTrace();
                                            }
                                        }
                                    });
                                }
                                pl.rightEcon.add(space);
                            }
                        } catch (SQLException exc) {
                            exc.printStackTrace();
                        }
                        //refresh la pagina
                        frame.dispose();

                        pl.getFrame().setVisible(true);
                        pl.ordClient.setText("C. Sursa /\\   ");

                    }else if(ascDesc.equals("/\\   ")){//acum ii ascendenta, si o face sa fie descendenta
                        //dam refresh la pagina
                        try {
                            connection = DriverManager.getConnection(CONNECTION_LINK + DB_NAME, USERNAME, PASSWORD);
                            Statement statement = connection.createStatement();
                            ResultSet resultSet = statement.executeQuery(
                                    "SELECT * FROM TRANSFER ORDER BY NUME_CLIENT_SURSA DESC;");

                            Transfer transfer = null;
                            pl.rightEcon.removeAll();
                            while(resultSet.next()){
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
                                pl.rightEcon.add(label1);
                                pl.rightEcon.add(label2);
                                pl.rightEcon.add(label3);
                                pl.rightEcon.add(label4);
                                pl. rightEcon.add(label5);
                                pl.rightEcon.add(label6);
                                pl.rightEcon.add(label7);
                                pl. rightEcon.add(label8);
                                pl. rightEcon.add(label9);
                                if(tr.getStatus().equals("CREATED")){
                                    JButton aprobare = new JButton("Aprobare transfer");
                                    pl.rightEcon.add(aprobare);

                                    //punem aici action listener pe buton, deoarece daca nu se creeaza nu putem pune action listener pe el
                                    aprobare.addActionListener(new ActionListener() {
                                        public void actionPerformed(ActionEvent e) {
                                            Connection connection;
                                            try {
                                                connection = DriverManager.getConnection(CONNECTION_LINK + DB_NAME, USERNAME, PASSWORD);
                                                Statement statement = connection.createStatement();
                                                ResultSet resultSet = statement.executeQuery("CALL aprobare_transfer ('"
                                                        + tr.getTransfer_id() + "','" + CNP + "');");
                                            } catch (SQLException e1) {
                                                e1.printStackTrace();
                                            }
                                        }
                                    });
                                }
                                pl.rightEcon.add(space);
                            }
                        } catch (SQLException exc) {
                            exc.printStackTrace();
                        }
                        frame.dispose();

                        pl.getFrame().setVisible(true);
                        pl.ordClient.setText("C. Sursa \\/    ");
                    }else { //ii descendenta si o facem inapoi STANDARD
                        //dam refresh la pagina cu nou nostru
                        frame.dispose();

                        pl.getFrame().setVisible(true);
                        pl.ordClient.setText("C. Sursa /\\ \\/");
                    }

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
            tranzactie.setBounds(411, 45, 148, 23);
            contentPane.add(tranzactie);

            ordStatus.setFont(new Font("Tahoma", Font.PLAIN, 15));
            ordStatus.setBounds(10, 10, 170, 25);
            contentPane.add(ordStatus);

            ordClient.setFont(new Font("Tahoma", Font.PLAIN, 15));
            ordClient.setBounds(180, 10, 170, 25);
            contentPane.add(ordClient);

            rightDepo.setLayout(new GridLayout(0, 1, 0, 0));
    }

    public JFrame getFrame() {
        return frame;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }
}
