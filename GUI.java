
/*
 * 14-4347	Ali Asgher
 * 14-4027	Usman Nazir
 * 14-4225	Sara Tanzeel
 * 14-4048	Muhammad Fahad Zafar
 */

import java.awt.EventQueue;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.*;
import java.awt.event.*;
import java.rmi.Naming;
import java.rmi.RemoteException;

public class GUI extends JFrame {

	JPanel contentPane;
	JTextField textField;
	JTextField textField_1;
	JTextArea textArea = null;
	private JButton btnUnregister;
	private JButton btnSendMessage;
	private static ClientInterface c;
	private static ServerInterface si;
	private static GUI frame;	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new GUI();
					frame.setVisible(true);
					frame.addWindowListener(new WindowAdapter() {
						
						// extra check to properly close connection if client closes dialog box without registering
						public void windowClosing(WindowEvent e) {
							try {
								si.disConnect(c);
							} catch (RemoteException e1) {
								e1.printStackTrace();
							}
						}
					});
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textArea = new JTextArea();
		textArea.setBounds(10, 127, 414, 58);
		JScrollPane sp = new JScrollPane(textArea); 
		sp.setBounds(10, 114, 414, 73);
		sp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		sp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		sp.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {  
			public void adjustmentValueChanged(AdjustmentEvent e) {  				// adding a scroll bar
				e.getAdjustable().setValue(e.getAdjustable().getMaximum());  
			}
		});
		textArea.setEditable(false);
		contentPane.add(sp);
		
		JLabel label = new JLabel("");
		label.setFont(new Font("Calibri", Font.BOLD, 16));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(10, 11, 414, 26);
		contentPane.add(label);
		
		JLabel label_1 = new JLabel("");
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setFont(new Font("Calibri", Font.PLAIN, 14));
		label_1.setBounds(10, 92, 290, 19);
		contentPane.add(label_1);
		
		JLabel lblEnterName = new JLabel("Enter Name:");
		lblEnterName.setBounds(10, 58, 70, 14);
		contentPane.add(lblEnterName);
		
		textField = new JTextField();
		textField.setBounds(82, 55, 218, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JButton btnRegister = new JButton("Register");
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					c = new Client (textField.getText());							// name of client
					c.setGUI(frame);												// setting up GUI
					si = (ServerInterface) Naming.lookup("rmi://localhost/obj");	// looking up for server's object
					label_1.setText("You're Connected to the Server");
					label.setText("Welcome " + textField.getText());
					textField.setEditable(false);
					btnRegister.setEnabled(false);
					btnUnregister.setEnabled(true);
					btnSendMessage.setEnabled(true);
					si.connect(c, textField.getText());								// connecting with server
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		btnRegister.setBounds(310, 54, 114, 23);
		contentPane.add(btnRegister);
		
		btnUnregister = new JButton("Unregister");
		btnUnregister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnRegister.setEnabled(true);
				btnUnregister.setEnabled(false);
				btnSendMessage.setEnabled(false);
				label_1.setText("You've lost connection to Server");
				try {
					si.disConnect(c);
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnUnregister.setBounds(310, 88, 114, 23);
		contentPane.add(btnUnregister);
		
		btnSendMessage = new JButton("Send Message");
		btnSendMessage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String msg = textField_1.getText();
				textField_1.setText("");
				try {
					si.sendMessage(c, msg);
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnSendMessage.setBounds(155, 227, 123, 23);
		contentPane.add(btnSendMessage);
		
		textField_1 = new JTextField();
		textField_1.setBounds(10, 196, 414, 20);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		btnUnregister.setEnabled(false);
		btnSendMessage.setEnabled(false);
	}
}
