package thread;

import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import gui.main_dashboard1;

public class monitor_db_thread1 extends Thread {
	
	
	
	public void run() {
		
		/* May have to change logic check from isalive method to a state other than RUNNABLE test with the getstate method if 
		the current isalive logic starts to fail again. It appears to be working - 08/16/20
		
		*/
		
		//State thread1_state = main_dashboard1.bt1.getState();
		//State thread2_state = main_dashboard1.bt2.getState();
		
		//System.out.println("object 1 thread state: " +thread1_state);
		//System.out.println("object 2 thread state: " +thread2_state);
	
		

		while (main_dashboard1.bt1.isAlive() || main_dashboard1.bt2.isAlive()) {
			
			//Wake up every 3 seconds to check if DB worker threads are still running.
			
			try {
				Thread.sleep(3000);
				
				//System.out.println("in while loop of monitor class");
			} catch (InterruptedException e) {
				e.printStackTrace();
			} 
			
		}
		
		//System.out.println("From thread seen from monitor: " +main_dashboard1.bt1.get_error_count());
		//System.out.println("From thread seen from monitor: " +main_dashboard1.bt2.get_error_count());
		

	      if ( main_dashboard1.bt1.get_error_count() > 0 || main_dashboard1.bt2.get_error_count() > 0) {
	  		

	          EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							JOptionPane optionPane2 = new JOptionPane("Blast was unsuccessful in one or more selected databases", JOptionPane.ERROR_MESSAGE);
					  		JDialog dialog = optionPane2.createDialog("Error");
					  		dialog.setAlwaysOnTop(true);
					  		dialog.setVisible(true);
							
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});		
	  		
	      	  
	        } 
	      
	      else if ( main_dashboard1.bt1.get_error_count() == 0 || main_dashboard1.bt2.get_error_count() == 0) {
	        	

		     EventQueue.invokeLater(new Runnable() {
	        	public void run() {
					try {
						
						JOptionPane optionPane1 = new JOptionPane("Blast was successful for all selected databases", JOptionPane.PLAIN_MESSAGE);
			  			JDialog dialog = optionPane1.createDialog("Success.");
			  			dialog.setAlwaysOnTop(true);
			  			dialog.setVisible(true);
						
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});		 	        
	      
	    }
	      
	}

	
}
