package mem_structs;

import java.util.HashMap;

import javax.swing.JTable;

import gui.main_dashboard1;

public class dbname_hash_map {
	
	 static JTable table_1 = main_dashboard1.table_1;
	 HashMap<String, Integer> pdb_ind;
	 int a;
	 String db;
	
	public void create_pdb_ind_hm() {
		
		int row_count = table_1.getRowCount();
		
		pdb_ind = new HashMap<String, Integer>();
		 
		  for (a=0;a < row_count;a++) {
			  
			  db = (String) table_1.getValueAt(a, 1);

			  // Add keys and values to hash table data structure
			 pdb_ind.put(db, a);
			 
		}
		  
	}
	
	public int get_pdb_ind_hm(String a) {
		
		int b = pdb_ind.get(a);
		return b;
		
	}

	public static void put_value() {
	
		
	}


}
