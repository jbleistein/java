
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

import dnl.utils.text.table.TextTable;

	public class test2 {
		
		static Object[][] sql2;
		static ArrayList<Object> newObj;
		static int a;
	 	
	 	static Connection c1;
	 	
	 	public static void main(String[] args) {
	 		System.out.println("App - version 1\n\n");
	 		
	 		try {
	 			System.out.println("Loading Oracle driver.");
	 			Class.forName("oracle.jdbc.driver.OracleDriver");
	 		} catch (ClassNotFoundException e) {
	 			e.printStackTrace();
	 		}
	 		
	 		try {
	 			System.out.println("Connecting to Oracle database\n\n");
	 			c1 = DriverManager.getConnection("jdbc:oracle:thin:@192.168.240.134:1521:pdb1.ats.local","app","app123");
	 		} catch (SQLException e) {
	 			e.printStackTrace();
	 		}
	 		
	 		try {
	 			PreparedStatement stmt1 = c1.prepareStatement("select * from names",
	 					ResultSet.TYPE_SCROLL_INSENSITIVE,
	 				    ResultSet.CONCUR_READ_ONLY
	 					);
	 			
	 			String sql = "select * from names";

	 			
	 			stmt1.execute(sql);

	 	        
	 			ResultSet rs3=stmt1.getResultSet();

	 	      ResultSetMetaData metadata = rs3.getMetaData();
	 	      int numberOfColumns = metadata.getColumnCount();
	 	      
	 	     if(!rs3.next()) {
	 				
	 				return;
	 				
	 			}
	 	        
	 			rs3.last();
		 	    int num_of_rows = rs3.getRow();
	 			
	 		rs3.beforeFirst(); //resets resultset after looping through it each time.
	 	      
	 	     
	 	        String[] col_names = new String[] {"fname"};
	 	        Object[][] resultSet = new Object[num_of_rows][numberOfColumns];
	 	        
	 	       
	 	      int i=0;
	 	      while (rs3.next()) {

	 	          for (int j = 0; j < numberOfColumns; j++) {
	 	              resultSet[i][j] = rs3.getString(j+1);
	 	          }
	 	          i++;
	 	      }

	 			TextTable tt = new TextTable(col_names, resultSet);

	 			
				// this adds the numbering on the left 
				tt.setAddRowNumbering(true); 
				// sort by the first column 
				tt.setSort(0); 
				tt.printTable(); 
	 			
	 		} catch (SQLException e) {
	 			
	 			e.printStackTrace();
	 		}
	 	}
	}


