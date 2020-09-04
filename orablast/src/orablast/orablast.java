package orablast;

import java.awt.EventQueue;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.util.Properties;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import gui.login_screen;
import gui.main_dashboard1;
import test.test;

/*OraBlast - Version 1.0
 * OraBlast is a JAVA application used to connect to multiple Oracle databases in the environment to run a SQL statement of some sort.
 * The SQL statement could be a DML, DDL, etc. This program uses the JDBC driver and the JAVA Swing library for it's GUI components.
 * This program pushes statements to the database using multiple threads for performance. This program was developed and tested to
 * run on Microsoft Windows workstations but can run on any platform where the JAVA JVM is available.
 * 
 * - Initial creation: Justin Bleistein - (April 2020 ATS)
 * 
 * Main entry class of OraBlast JAVA program.
 * 
 * Application return code legend:
 * 
 * 1 - Config file not found in default location
 * 2 - Invalid username or password for Oracle authentication and schema database.
 * 3 - User account locked for Oracle authentication in schema database.
 * 
 * 
 */

public class orablast {

	public static String DB_SERVER;
	public static String DB_SERVER_PORT;
	public static String DB_SERVER_SERVICE_NAME;
	public static JFrame frmOrablastLogin;

	public static void main(String[] args) {
		
		try{
			  Thread.sleep(5000); // Delay start of application for 5 seconds to display the splash screen implemented by jar manifest file.
			} catch(Exception e) {}
		
		System.out.println("ORABlast version - 1.0 JRB\n\n");

		// Startup routines
		// Process properties file for application config, if environmental
		// variables were not set.

		try (InputStream cfg_file = new FileInputStream(".."+File.separator+"cfg"+File.separator+"orablast.cfg")) {

			Properties prop = new Properties();
			prop.load(cfg_file);

			 DB_SERVER = prop.getProperty("DB_SERVER");
			 DB_SERVER_PORT = prop.getProperty("DB_SERVER_PORT");
			 DB_SERVER_SERVICE_NAME = prop.getProperty("DB_SERVER_SERVICE_NAME");

		}

		catch (java.io.FileNotFoundException e) {

			//e.printStackTrace();

			JOptionPane optionPane = new JOptionPane("Cannot find config file: orablast.cfg in the default location.",
					JOptionPane.ERROR_MESSAGE);
			JDialog dialog = optionPane.createDialog("Application error");
			dialog.setAlwaysOnTop(true);
			dialog.setVisible(true);

			System.exit(1);

		} catch (IOException e1) {

			e1.printStackTrace();
		}
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					login_screen ls1 = new login_screen(); // Launch GUI login window.
					login_screen.frmOrablastLogin.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

		

	}
