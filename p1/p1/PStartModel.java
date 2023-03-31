package p1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PStartModel {
	private final static String USERNAME = "root";
	private final static String PASSWORD = "sssabb";
	private final static String DB_NAME = "Bancar";
	private final static String CONNECTION_LINK = "jdbc:mysql://localhost:3306/";

	// functie care  verifica datele introduse (timise dint controller):
	String checkLogin(String CNP, String password, String item) {
		String aux = null;
		Connection connection;
		try {
			connection = DriverManager.getConnection(CONNECTION_LINK + DB_NAME, USERNAME, PASSWORD);
			Statement statement = connection.createStatement();
			ResultSet resultSetCNP = statement.executeQuery("SELECT CNP FROM " + item + " WHERE CNP = " + "'"+CNP+"'");
			
			boolean x = true;
			while (resultSetCNP.next()) {
				if (resultSetCNP.getString("CNP").equals(CNP)) 
					x = false;
			}
			
			if (x == false) {
				ResultSet resultSetParola = statement.executeQuery("SELECT parola FROM Persoana where cnp =" + "'"+CNP+"'");
				while (resultSetParola.next()) {
					
					if (resultSetParola.getString("parola").equals(password)) 
						aux= new String("exista");// VOM DA PAGINA
					else 
						aux= new String("Paroloa INCORECTA");
				}
			}
			else 
				aux= new String("CNP nu exista in "+item);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return aux;
	}
}
