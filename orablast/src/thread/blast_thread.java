package thread;

import java.awt.EventQueue;
import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import db_access.db_connect;
import dnl.utils.text.table.TextTable;
import gui.main_dashboard1;
import mem_structs.hash_map;

public class blast_thread extends Thread {
	
	private int any_thread_errors;
	Connection base_db_connection = db_connect.c1; //App config database established in the earlier db connect class.
	 static JTable table_1 = main_dashboard1.table_1;
	 public String[] myArray;
	 boolean rs2;
	 DefaultTableModel model = main_dashboard1.model;
	 int i;
	 Statement stmt2;
	 String[] stmts;
	 ResultSetMetaData rsmd;
	 int r;
	 ResultSet rs3;
	 int db_ind;
	
	static Connection c1;
	static Connection c2;


	public blast_thread(String[] myArray) {
		
		this.myArray=myArray;
	}
	
	
	public void run() {
		
		
		 	for (i=0; i < myArray.length; i++) {
		 		
		 		
		        try {
      
		            		            
		            Statement stmt = base_db_connection.createStatement();
					String sql = "select * from dbs where dbname = '" +myArray[i] +"'";
					

					ResultSet rs = stmt.executeQuery(sql);
					
					while (rs.next()) {
					
					
					Class.forName("oracle.jdbc.driver.OracleDriver");
					String connect_string_from_db_table=rs.getString("connect_string");
			        String full_connect_string="jdbc:oracle:thin:app/app123@"+connect_string_from_db_table;
			        
			        System.out.println("connect string: "+full_connect_string);
			        
			        
			          c2 = DriverManager.getConnection(full_connect_string);
			         
			         
			         String sql2 =  main_dashboard1.textArea.getText(); // Get user inputed SQL statement from text area in Blast dashboard GUI
			         stmts = sql2.split(";");
			         
			        
			         PreparedStatement stmt2 = c2.prepareStatement(sql2,
			        		    ResultSet.TYPE_SCROLL_INSENSITIVE,
			 				    ResultSet.CONCUR_READ_ONLY);
			         
						
			         try {
			        	 
			        	for (int n=0;n < stmts.length;n++) {
			        		
			        
			         rs2 = stmt2.execute(stmts[n]); //Where we actually run the code in the database.
			         
			         db_ind = main_dashboard1.hm.get_pdb_ind_hm(myArray[i]);
			         
			         any_thread_errors=0;
			          
			         EventQueue.invokeLater(new Runnable() {
							public void run() {
								try {
							
									table_1.setValueAt("SUCCESS", db_ind , 2);	
									
							}

								
								catch (Exception e) {
									
									e.printStackTrace();
									
									any_thread_errors=1;
									
								}
							}
						});			
			          
			          
			        }
			        	
			        	} catch (SQLException e) {
			        	 
			        	 int db_ind = main_dashboard1.hm.get_pdb_ind_hm(myArray[i]);
							
							table_1.setValueAt("ERROR", db_ind , 2);	
							
						e.printStackTrace();
							
							System.out.println(e.getErrorCode());

			        	 any_thread_errors=1;
			        	 
			         }
			          
		             // if (rs2 == true) { // true if select and false if DDL or any other DML
		            
			          rs3=stmt2.getResultSet();
			          
			          rsmd = rs3.getMetaData();
			          int rs_col_count = rsmd.getColumnCount();
			          
			          System.out.println("\n\n!!!column count: " +rs_col_count);
			          
			          if(!rs3.next()) {
			 				
			 				return;
			 				
			 			}
			 	        
			 			rs3.last();
			 			
				 	    int num_of_rows = rs3.getRow();
				 	    
				 	   rs3.beforeFirst(); //resets resultset after looping through it each time.
				 		
				 		
			 	        Object[][] resultSet = new Object[num_of_rows][rs_col_count];
			 	        String col_names[] = new String[rsmd.getColumnCount()];
			 	        
				 	      i=0;
				 	      			 	     
				 	      while (rs3.next()) {

				 	          for (int j = 0; j < rs_col_count; j++) {
				 	              resultSet[i][j] = rs3.getString(j+1);
				 	              
				 	          }
				 	          i++;
				 	      }
				 	      
				 	      i=0;
				 	      
				 	      for (int j = 1; j <= rs_col_count; j++) {
				 	    	      col_names[i] = rsmd.getColumnName(j);
				 	    	      i++;
				 	      }
				 	      
				 	     TextTable tt = new TextTable(col_names, resultSet);

				 			
							// this adds the numbering on the left 
							tt.setAddRowNumbering(true); 
							// sort by the first column 
							tt.setSort(0); 
							tt.printTable(); 
			          
			          /*
			          while (rs3.next()) {
			        	 
			          
			          for (r=1;r <= rs_col_count;r++) { //The column index starts at 1 not 0.
			          		
			          		System.out.println("SQL query output: " +rs3.getString(r));	
			          		
			          
					}
			          	
			          }*/
			          
					}
					
					/*else {
	                	
                        System.out.println("Output: " +stmt2.getUpdateCount());   
 		                
					*/}
					
		        
		        	        
		        catch (Exception e) { 
					 
					 
		        	int db_ind = main_dashboard1.hm.get_pdb_ind_hm(myArray[i]);
					
					table_1.setValueAt("ERROR", db_ind , 2);	

	        	    any_thread_errors=1;
	        	    
	        	    e.printStackTrace();
	        	    
					 }
					 
					 
					} 

		        
		 	}
	

	public int get_error_count() {
		
		return any_thread_errors;
		
	    }
	}
	
	
	

			
		 	  

		 	

	
		 
	 
	 


		         
		 	
		 
	 


