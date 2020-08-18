package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JTextField;
import db_access.db_connect;
import thread.test1;

import javax.swing.JComboBox;
import javax.swing.JPasswordField;
import javax.swing.JButton;

public class login_screen implements ActionListener {

	public static JFrame frmOrablastLogin;
	public static JTextField textField;
	public JPasswordField passwordField;
	public static String pass;
	private JButton btnLogin;
	private JButton btnExit;
	
	public static char[] password;
	public static String name;

		public login_screen() {
		initialize();
	}

	private void initialize() {
		frmOrablastLogin = new JFrame();
		frmOrablastLogin.setTitle("ORABlast - Login");
		frmOrablastLogin.setBounds(100, 100, 952, 664);
		//frmOrablastLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmOrablastLogin.getContentPane().setLayout(null);
		
		
		JLabel lblNewLabel = new JLabel("ORABlast - version 1.0");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblNewLabel.setBounds(12, 13, 582, 92);
		frmOrablastLogin.getContentPane().add(lblNewLabel);
		
		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setBounds(12, 189, 114, 33);
		frmOrablastLogin.getContentPane().add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(12, 247, 68, 33);
		frmOrablastLogin.getContentPane().add(lblPassword);
		
		textField = new JTextField();
		textField.setBounds(82, 194, 317, 22);
		frmOrablastLogin.getContentPane().add(textField);
		textField.setColumns(10);
       
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(53, 313, 135, 22);
		frmOrablastLogin.getContentPane().add(comboBox);
		comboBox.addItem("SYSDBA");
		comboBox.addItem("SYSOPER");
		comboBox.addItem("SYSBACKUP");
		comboBox.addItem("SYSASM");
		comboBox.addItem("SYSDG");
		comboBox.addItem("SYSKM");
		
		
		
		JLabel lblPriv = new JLabel("Priv:");
		lblPriv.setBounds(12, 316, 56, 16);
		frmOrablastLogin.getContentPane().add(lblPriv);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(84, 252, 315, 22);
		frmOrablastLogin.getContentPane().add(passwordField);
			
		btnExit = new JButton("Exit");
		btnExit.setBounds(825, 581, 97, 25);
		frmOrablastLogin.getContentPane().add(btnExit);
		btnExit.addActionListener(this);
		
		btnLogin = new JButton("Login");
		btnLogin.setBounds(716, 581, 97, 25);
		frmOrablastLogin.getContentPane().add(btnLogin);
		frmOrablastLogin.setLocationRelativeTo(null);
		frmOrablastLogin.setVisible(true);
		frmOrablastLogin.setResizable(false);
		btnLogin.addActionListener(this);
		
					 
	}

	@Override
	public void actionPerformed(ActionEvent e) {
	
		if (e.getSource() == btnLogin) {
			
			frmOrablastLogin.dispose();
			
			password = passwordField.getPassword();
			
			name = textField.getText();
			
			pass = new String(password);

			db_connect db1 = new db_connect();

			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						main_dashboard1 md = new main_dashboard1();
						main_dashboard1.frmOracleRrtDashboard.setVisible(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});			
		}
	
		
		 if (e.getSource() == btnExit) {
				
				int reply = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "Confirm", JOptionPane.YES_NO_OPTION);
				if (reply == JOptionPane.YES_OPTION) {
				    
					System.exit(0);
					
				} else {

				    
				}
			}
			
		  }
			
	
	public static String getvalue() {
	return name;
	
	}
	
	public static String getpwd() {
		
		return pass;	
	
		}
	}
		
				
		
	

