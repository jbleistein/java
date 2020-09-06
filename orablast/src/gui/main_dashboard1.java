package gui;

import java.awt.EventQueue;
import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.BoxLayout;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JProgressBar;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.RowSorter;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import db_access.create_db_connections;
import db_access.db_connect;
import mem_structs.dbname_hash_map;
import mem_structs.hash_map;
import thread.blast_thread;
import thread.build_jtable;
import thread.monitor_db_thread1;
import thread.test1;

import javax.swing.JScrollPane;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;

import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JCheckBox;
import javax.swing.table.*;


public class main_dashboard1 extends Thread implements ActionListener {
	

	public static JFrame frmOracleRrtDashboard;
	private JTextField textField;
	public static JTable table_1;
	public static DefaultTableModel model;
	JButton btnNewButton_2;
	JButton btnNewButton;
	JButton btnNewButton_1;
	JButton button;
	public static JTextArea textArea;
	public static blast_thread bt1;
	public static blast_thread bt2;

	String DB_SERVER="192.168.240.134";
	String DB_SERVER_PORT="1521";
	String DB_SERVER_SERVICE_NAME="pdb1.ats.local";
	String DB_USER_NAME="app";
	String DB_USER_PWD="app123";
	String ORA_JDBC_CONNECT_STRING;
	String [] array;
	monitor_db_thread1 mt1;
	public static hash_map hm;
	JPanel panel;
	public static JFrame pbjf1;
	public static JFrame pbjf2;
    
		
	static Connection c2=null;
	private JTextField textField_1;
	JPopupMenu popupMenu;
    JMenuItem menuItemAdd;

	
	public main_dashboard1() {
	
		frmOracleRrtDashboard = new JFrame();
		frmOracleRrtDashboard.setTitle("Oracle RRT Dashboard");
		frmOracleRrtDashboard.setBounds(100, 100, 891, 591);
		frmOracleRrtDashboard.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmOracleRrtDashboard.getContentPane().setLayout(new BoxLayout(frmOracleRrtDashboard.getContentPane(), BoxLayout.X_AXIS));
		frmOracleRrtDashboard.setLocationRelativeTo(null);
		frmOracleRrtDashboard.setVisible(true);
		frmOracleRrtDashboard.setResizable(false);
	
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		frmOracleRrtDashboard.getContentPane().add(tabbedPane);
		
	    panel = new JPanel();
		tabbedPane.addTab("Blast", null, panel, null);
		panel.setLayout(null);
		
		JFileChooser filechooser1 = new JFileChooser();
		filechooser1.setBounds(211,370,407,135);
		panel.add(filechooser1);
		
		
		btnNewButton = new JButton("Blast");
		btnNewButton.setBounds(747, 353, 117, 29);
		panel.add(btnNewButton);
		
		btnNewButton_1 = new JButton("Clear");
		btnNewButton_1.setBounds(630, 353, 117, 29);
		panel.add(btnNewButton_1);
		
		textArea = new JTextArea();
		textArea.setBounds(274, 22, 568, 319);
		panel.add(textArea);
		
 model = new DefaultTableModel();
        
		model.addColumn("Host");
        model.addColumn("DB");   
        model.addColumn("Status");
	 
		
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("DOD", null, panel_1, null);
		

		table_1 = new JTable(model);
		table_1.setBounds(6, 22, 133, 483);
		//table_1.setGridColor(Color.gray);
		panel.add(table_1);
		

		textField_1 = new JTextField();
		textField_1.setBounds(20, 458, 130, 26);
		panel.add(textField_1);
		textField_1.setColumns(10);

		RowSorter<? extends TableModel> rs = table_1.getRowSorter();
        if (rs == null) {
            table_1.setAutoCreateRowSorter(true);
            rs = table_1.getRowSorter();
        }

        TableRowSorter<? extends TableModel> rowSorter =
                (rs instanceof TableRowSorter) ? (TableRowSorter<? extends TableModel>) rs : null;

        if (rowSorter == null) {
            throw new RuntimeException("Cannot find appropriate rowSorter: " + rs);
        }

        textField_1.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                update(e);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                update(e);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                update(e);
            }

            private void update(DocumentEvent e) {
                String text = textField_1.getText();
                if (text.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }
        });

		
		JScrollPane scrollPane = new JScrollPane(table_1);
		scrollPane.setBounds(10, -2, 197, 448);
		panel.add(scrollPane);
		
		
		btnNewButton_2 = new JButton("Exit");
		btnNewButton_2.setBounds(747, 488, 117, 29);
		panel.add(btnNewButton_2);
		
		JScrollPane scrollPane_1 = new JScrollPane(textArea);
		scrollPane_1.setBounds(274, 22, 568, 319);
		panel.add(scrollPane_1);
		
		button = new JButton("Clear");
		button.setBounds(6, 494, 117, 29);
		panel.add(button);
		
		btnNewButton_2.addActionListener(this);
		btnNewButton.addActionListener(this);
		button.addActionListener(this);
		btnNewButton_1.addActionListener(this);
		
		//Build list of databases in table.
        
	       build_jtable bjt1 = new build_jtable();
	       bjt1.start();
	       
	     //Progress bar while building list of databases in table
		    
		    pbjf2 = new JFrame();
		    pbjf2.setSize(200,100);
		    pbjf2.setLocationRelativeTo(null);
		  
		    
		    JPanel jp2 = new JPanel();
		    
		    JProgressBar pb2 = new JProgressBar();
		    pb2.setIndeterminate(true);
		    
		    jp2.add(pb2);
		    pbjf2.add(jp2);
		    pbjf2.setVisible(true);
		    pbjf2.setAlwaysOnTop(true);
		    JLabel jl1 = new JLabel("Building list of databases");
		    jp2.add(jl1);
		    pbjf2.pack();
		    	

			    JPopupMenu popupMenu = new JPopupMenu();
			    JMenuItem menuItemAdd = new JMenuItem("View output");
			    table_1.setComponentPopupMenu(popupMenu);
			    
			    ActionListener actionListener = new PopupActionListener();
		    
		        popupMenu.add(menuItemAdd);
		        menuItemAdd.addActionListener(actionListener);
		        menuItemAdd.setEnabled(true);
		        
		        
		        
		        
	}
	
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == popupMenu) {
			
			System.out.println("Selected item from popup window.");
			
		}
		
		if(e.getSource() == btnNewButton_2) {
			
			int reply = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "Confirm", JOptionPane.YES_NO_OPTION);
			if (reply == JOptionPane.YES_OPTION) {
			    
				System.exit(0);
				
			} else {

			    
			}
		}
		
		if (e.getSource() == button) {
			
			table_1.clearSelection();
			
			
			for (int a=0;a < table_1.getRowCount();a++) {
				
				table_1.setValueAt("", a, 2);
				
				
			}
			
			
		}
		
		if (e.getSource() == btnNewButton_1) {
			
			textArea.setText(null);
			
		
		}
			
			if (e.getSource() == btnNewButton) {
				//blast
				
				
				
				int [] SelectedRow = table_1.getSelectedRows();
				
				for (int i = 0;i < SelectedRow.length;i++) {
					
					String data = (String) table_1.getValueAt(SelectedRow[i], 1);	
					
				}
				
				
				String [] array = new String[SelectedRow.length];
				
				for (int i = 0; i < array.length;i++ )
				
				{
					
					array[i] = (String) table_1.getValueAt(SelectedRow[i], 1);
					
				}
				
				
					  String [] myArray1 = Arrays.copyOfRange(array, 0, array.length/2);
				      String [] myArray2 = Arrays.copyOfRange(array, array.length/2, array.length);
				      
				    //Build hash table structure in memory so that the blast threads can look up the index number of the GUI table row by db name to mark each run 
				    //as SUCCESS or ERROR.
				      
				      hm = new hash_map();
					  hm.create_pdb_ind_hm();
					  
					//Test to see if main SQL text area in the main dashboard is null.
					  
					  if (textArea.getText().equals("")) {

						     EventQueue.invokeLater(new Runnable() {
					        	public void run() {
									try {
										
										JOptionPane optionPane1 = new JOptionPane("SQL text area is blank. Please fill", JOptionPane.ERROR_MESSAGE);
							  			JDialog dialog = optionPane1.createDialog("Error");
							  			dialog.setAlwaysOnTop(true);
							  			dialog.setVisible(true);
							  			
										
									} catch (Exception e) {
										e.printStackTrace();
									}
								}
							});		 	        

					  }
					  

					  else if (SelectedRow.length == 0) {
							
							EventQueue.invokeLater(new Runnable() {
					        	public void run() {
									try {
										
										JOptionPane optionPane1 = new JOptionPane("No selected databases for blast. Please select one or more databases", JOptionPane.ERROR_MESSAGE);
							  			JDialog dialog = optionPane1.createDialog("Error");
							  			dialog.setAlwaysOnTop(true);
							  			dialog.setVisible(true);
							  			
										
									} catch (Exception e) {
										e.printStackTrace();
									}
								}
							});		 	        
							
							
						}
					  
					  else {
				      
					  
						  
						  //debugging stuff
				      
				      for (int i=0;i < myArray1.length;i++) {
				    	  
				    	  System.out.println("Myarray1 elements: " +myArray1[i]);
				    	  
				      }
				      
				      System.out.println("\n\n");
				      

				      for (int i=0;i < myArray2.length;i++) {
				    	  
				    	  System.out.println("Myarray2 elements: " +myArray2[i]);
				    	  
				      
				      }

				      
					   //Start DB worker threads.   
						  
					      bt1 = new blast_thread(myArray1);
					      bt2 = new blast_thread(myArray2);
				      

						    bt1.start();
						    bt2.start();
						    
						    //Progress bar while running blast
						    
						    pbjf1 = new JFrame();
						    pbjf1.setSize(200,100);
						    pbjf1.setLocationRelativeTo(null);
						  
						    
						    JPanel jp1 = new JPanel();
						    
						    JProgressBar pb1 = new JProgressBar();
						    pb1.setIndeterminate(true);
						    
						    jp1.add(pb1);
						    pbjf1.add(jp1);
						    pbjf1.pack();
						    pbjf1.setVisible(true);
						    JLabel jl2 = new JLabel("Blasting SQL out to databases");
						    jp1.add(jl2);
						    pbjf1.pack();
						 
				    
				  //Thread to monitor other threads. Nothing fancy from a concurrency perspective just know when the DB worker threads are done running.
				    
				    mt1 = new monitor_db_thread1();
				    mt1.start();
				    
					  }
				  }
	
	
			} 


		}

class PopupActionListener implements ActionListener {
	  public void actionPerformed(ActionEvent actionEvent) {
		  
		  
		  String file_ts = new SimpleDateFormat("yyyyMMdd").format(new Date());
		  
		  String file_name = ".."+File.separator+"output"+File.separator+"orablast_pdb1_"+file_ts
					+".out";
					
		  File f = new File(file_name);
			
		  
		  try {
			Desktop.getDesktop().edit(f);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		  
	    System.out.println("Selected: " + actionEvent.getActionCommand());
	  
	  }
	}