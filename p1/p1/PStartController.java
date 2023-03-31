package p1;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;



public class PStartController {
	 private PStartModel model;
	 private PStartView  view;

	 public PStartController(PStartModel model, PStartView view) {
		super();
		this.model = model;
		this.view = view;
		this.view.login(new Login());  // aici unim buttonul Login din view cu clasa de tip ACtionEvent declaratamai jos si facem asta cu ajutorul unei functii declarate in view
	}

	class Login implements ActionListener{    //pt butonul de login

        private final static String USERNAME = "root";
        private final static String PASSWORD = "sssabb";
        private final static String DB_NAME = "Bancar";
        private final static String CONNECTION_LINK = "jdbc:mysql://localhost:3306/";

        public void actionPerformed(ActionEvent e) {
        	JTextField textCNP = view.getTextCNP();
            String CNP;
            CNP = textCNP.getText();
            
            String password;
            JTextField textPassword = view.getTextPassword();
            password = textPassword.getText();
                    
            JComboBox<String> boxTip =  view.getBoxTip();
            String item=new String((String)boxTip.getSelectedItem());
            
            String aux= model.checkLogin(CNP,password,item);
            view.setLabelMesaj(aux);
            
            if(aux.equals("exista")){
                view.getFrame().dispose();
                JComboBox boxAux = view.getBoxTip();
                String Tip = (String) boxAux.getSelectedItem();
                if(Tip.equals("Client")) {
                    // deschidem prima pagina de client:
                    PStartClientView viewClient = new PStartClientView(CNP);
                    PStartClientController controllerClient = new PStartClientController(viewClient);
                    viewClient.getFrame().setVisible(true);
                }else if(Tip.equals("Angajat")){
                    Angajat a = new Angajat();
                    Connection connection;
                    try{
                        connection = DriverManager.getConnection(CONNECTION_LINK + DB_NAME, USERNAME, PASSWORD);
                        Statement statement = connection.createStatement();
                        ResultSet resultSet = statement.executeQuery(
                                "SELECT DEPARTAMENT from ANGAJAT where ANGAJAT.cnp=" + CNP);

                        Angajat b = new Angajat();
                        while(resultSet.next()){
                            b.setDepartament(resultSet.getString("DEPARTAMENT"));
                        };

                        if (a == null)
                            System.out.println("eroare");
                        a.setDepartament(b.getDepartament());
                    }catch (SQLException err) {
                        err.printStackTrace();
                    }


                    if(a.getDepartament().equals("HR")){
                        //deschidem pagina de HR
                        PStartAngajatHR AngajatHR = new PStartAngajatHR(CNP);
                        AngajatHR.getFrame().setVisible(true);
                    }else if (a.getDepartament().equals("FUNCTIONAR")){
                        PStartAngajatFunctionar AngajatFunctionar = new PStartAngajatFunctionar(CNP);
                        AngajatFunctionar.getFrame().setVisible(true);
                    }


                }else if (Tip.equals("Administrator")) {
                    //aici ii codul lui Petrica
                    view.getFrame().dispose();
                    PStartAdminView Admin = new PStartAdminView(CNP);
                    Admin.getFrame().setVisible(true);
                }
            }
        }
    };
}
