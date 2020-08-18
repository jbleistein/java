package db_access;

import java.sql.*;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import gui.login_screen;
import orablast.*;

public class db_connect {

	String DB_SERVER;
	String DB_SERVER_PORT;
	String DB_SERVER_SERVICE_NAME;
	String DB_USER_NAME;
	String DB_USER_PWD;
	String ORA_JDBC_CONNECT_STRING;

	public static Connection c1;

	public db_connect() {
		


		DB_USER_NAME = login_screen.getvalue();
		DB_USER_PWD = login_screen.getpwd();
		String DB_SERVER = orablast.DB_SERVER;
		String DB_SERVER_PORT = orablast.DB_SERVER_PORT;
		String DB_SERVER_SERVICE_NAME = orablast.DB_SERVER_SERVICE_NAME;
		
	
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		}

		try {

			c1 = DriverManager.getConnection(
					// The "/" in the connect string below is the way of telling Oracle you want to connect via service rather than SID - :.
					// This will be the requirement going forward with Oracle pluggable databases.
					
					"jdbc:oracle:thin:@" + DB_SERVER + ":" + DB_SERVER_PORT + "/" + DB_SERVER_SERVICE_NAME,
					DB_USER_NAME, DB_USER_PWD);

		}

		catch (SQLException e) {
			

			int db_login_error_code = e.getErrorCode();

			if (db_login_error_code == 1017) {
				
				JOptionPane optionPane = new JOptionPane("Invalid Oracle username or password",
				JOptionPane.ERROR_MESSAGE);
				JDialog dialog = optionPane.createDialog("Login error");
				dialog.setAlwaysOnTop(true);
				dialog.setVisible(true);
		    	System.exit(2);

			}

			else if (db_login_error_code == 28000) {
				
		
				JOptionPane optionPane = new JOptionPane("Oracle user account is locked", JOptionPane.ERROR_MESSAGE);
				JDialog dialog = optionPane.createDialog("Login error");
				dialog.setAlwaysOnTop(true);
				dialog.setVisible(true);
				System.exit(3);

			}

		}



	}


}
