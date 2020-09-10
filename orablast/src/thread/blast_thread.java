package thread;

import java.awt.EventQueue;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

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
	 DefaultTableModel model = main_dashboard1.model;
	 int i;
	 Statement stmt2;
	 String[] stmts;
	 ResultSetMetaData rsmd;
	 boolean rs2;
	 public static File fname;
	 
	 int r;
	 int db_ind;
	 int a;
	 int b;
	
	ResultSet rs3;
	static Connection c1;
	static Connection c2;
	public static PrintStream pw1;


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
			         
			         String[] stmts = sql2.split(";"); //Array to break up each sql statement deliminted by ;.
			
			         
			         for (int n=0;n < stmts.length;n++) {
			       
				 	PreparedStatement stmt2 = c2.prepareStatement(stmts[n],
				 	ResultSet.TYPE_SCROLL_INSENSITIVE,
				 	ResultSet.CONCUR_READ_ONLY
				 	);
				 	
				 	try {
			        	 
			        	
			         rs2 = stmt2.execute(stmts[n]); //Where we actually run the code in the database.
			         rs3 = stmt2.getResultSet();
			         
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
			          
			        	
				 	} catch (SQLException e) {
			        	 
			        	 int db_ind = main_dashboard1.hm.get_pdb_ind_hm(myArray[i]);
							
							table_1.setValueAt("ERROR", db_ind , 2);	
							
						e.printStackTrace();
							
							System.out.println(e.getErrorCode());

			        	 any_thread_errors=1;
			        	 
			         }
				 	
				 	
		             
				 	if (rs2 == false) { //True if select/query and false if DDL or any other DML
		            
				 			System.out.println("DDL or non-SELECT output: " +stmt2.getUpdateCount());   
			          
					 } else {
				 	
				 	
				 	  ResultSetMetaData metadata = rs3.getMetaData();
				 	  
				 	  
				 	  int numberOfColumns = metadata.getColumnCount();
					 	
					 	     if(!rs3.next()) {
					 				
					 				return;
					 			}
					 	        
					 			rs3.last();
						 	    int num_of_rows = rs3.getRow();
						 	    
				 			
				 		rs3.beforeFirst(); //resets resultset after looping through it each time.
				 		
				 		
				 	        Object[][] resultSet = new Object[num_of_rows][numberOfColumns];
				 	        String col_names[] = new String[metadata.getColumnCount()];

				 	     a=0;
 
				 	     while (rs3.next()) {

				 	          for (int j = 0; j < numberOfColumns; j++) {
				 	              resultSet[a][j] = rs3.getString(j+1);
				 	              
				 	          }
				 	          a++;
				 	      
				 	     }
				 	      b=0;
				 	      
				 	      for (int j = 1; j <= numberOfColumns; j++) {
				 	    	      col_names[b] = metadata.getColumnName(j);
				 	    	      b++;
				 	      }
			              
				 	      
				 			TextTable tt = new TextTable(col_names, resultSet);


							// this adds the numbering on the left 
							tt.setAddRowNumbering(true); 
							// sort by the first column 
							tt.setSort(0); 
						
							
							synchronized(this) {
							

							String file_ts = new SimpleDateFormat("yyyyMMdd").format(new Date());
							//String file_name = ".."+File.separator+"output"+File.separator+"orablast_"+myArray[i]+"_"+file_ts+".out";
							
							fname = new File(".."+File.separator+"output"+File.separator+"orablast_"+myArray[i]+"_"+file_ts+".out");
							
							
					        PrintStream stream = new PrintStream(new FileOutputStream(fname, true));
					        
					        System.setOut(stream);
							
							
							tt.printTable();
		
							stream.close();
							
			         
							    }
					
							}
				 	
			         }
			         
					}

				
					} catch (Exception e) { 
					 
					
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
	