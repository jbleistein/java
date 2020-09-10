package thread;

import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.table.DefaultTableModel;
import gui.main_dashboard1;

public class build_jtable extends Thread {
	
	public static JFrame pbjf2;
	
	DefaultTableModel model = main_dashboard1.model;
	

	public void run() {
		
		 

try {

    Class.forName("oracle.jdbc.driver.OracleDriver"); 
    
    
    Connection con = DriverManager.getConnection("jdbc:oracle:thin:@192.168.240.134:1521/nonpdb1.ats.local", "app", "app123");
   
    PreparedStatement pstm = con.prepareStatement("select * from dbs");
    ResultSet Rs = pstm.executeQuery();
    
    
    EventQueue.invokeLater(new Runnable() {
			public void run() {

				    try {
						while(Rs.next()) {
						     model.addRow(new Object[]{Rs.getString(1),Rs.getString(2)});
						}
					} catch (SQLException e) {
						
						e.printStackTrace();
					}
			}
		});		
      
    
} catch (Exception e) {
    System.out.println(e.getMessage());
    
			}




try {
	Thread.sleep(5000);
} catch (InterruptedException e) {

	e.printStackTrace();
}

//Turn off progress bar because table is built.

	main_dashboard1.pbjf2.setVisible(false);

	}
	
}

