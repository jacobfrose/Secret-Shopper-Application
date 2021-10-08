package views;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;


import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.Dimension;

public class reg extends JFrame {

	private JPanel contentPane;
	private JTextField tFirstName;
	private JTextField tLastName;
	private JTextField tEmail;
	private JTextField tPassword;
	private JButton bRegister;
	Connection con;
	Statement stat;
	private JComboBox cShopperType;

	/**
	 * Create the frame.
	 * @throws SQLException 
	 */
	public reg(Connection con) throws SQLException {
		this.con = con;
		setBounds(100, 100, 269, 278);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		setTitle("Register");
		setSize(new Dimension(300, 300));
		setResizable(false);
		setMinimumSize(new Dimension(300, 300));
		setMaximumSize(new Dimension(300, 300));
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel lFirstName = new JLabel("First Name: ");
		
		JLabel lblNewLabel_1 = new JLabel("Last Name: ");
		
		JLabel lblNewLabel_3 = new JLabel("Email");
		
		JLabel lblNewLabel_5 = new JLabel("Shopper Type: ");
		
		JLabel lblNewLabel_7 = new JLabel("Password");
		
		tFirstName = new JTextField();
		tFirstName.setColumns(10);
		tFirstName.setText("");
		
		tLastName = new JTextField();
		tLastName.setColumns(10);

		tLastName.setText("");
		
		tEmail = new JTextField();
		tEmail.setColumns(10);

		tEmail.setText("");
		
		tPassword = new JTextField();
		tPassword.setColumns(10);

		tPassword.setText("");
		
		cShopperType = new JComboBox();
		cShopperType.setModel(new DefaultComboBoxModel(new String[] {"ISU Dining Student Employee", "ISU Employee (Faculty/Staff)"}));
		
		bRegister = new JButton("Register");
		
		JLabel lblNewLabel = new JLabel("Register");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
					.addGap(35)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblNewLabel_3)
							.addGap(14))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addComponent(lblNewLabel_5)
								.addComponent(lblNewLabel_1)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(lFirstName)
									.addGap(11)))
							.addPreferredGap(ComponentPlacement.RELATED))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblNewLabel_7)
							.addPreferredGap(ComponentPlacement.UNRELATED)))
					.addGap(4)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblNewLabel)
							.addContainerGap())
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(tFirstName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
									.addComponent(tEmail, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addComponent(tLastName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addComponent(tPassword, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addComponent(cShopperType, 0, 105, Short.MAX_VALUE)
									.addComponent(bRegister)))
							.addGap(589))))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNewLabel)
					.addGap(23)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(tFirstName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lFirstName))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_1)
						.addComponent(tLastName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(tEmail, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_3))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(tPassword, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_7))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(cShopperType, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_5))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(bRegister)
					.addContainerGap(316, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
		stat = con.createStatement();
		events();
	}
	public void events()
	{
		bRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ResultSet result = null;
				boolean emailAvailable = true;
				
				try {
					result = stat.executeQuery("SELECT shopper_email FROM users");
					while(result.next())
					{
						if(tEmail.getText().equals(result.getString(1))) {
							emailAvailable = false;
						}
					}
					if(tFirstName.getText().equals(""))
					{
						JOptionPane.showMessageDialog(getParent(), "Please enter a valid first name.");
					}
					else if(tLastName.getText().equals(""))
					{
						JOptionPane.showMessageDialog(getParent(), "Please enter a valid last name.");
					}
					else if(tEmail.getText().equals("") || tEmail.getText().contains("@") == false || tEmail.getText().substring(tEmail.getText().indexOf('@')).contains(".") == false)
					{
						JOptionPane.showMessageDialog(getParent(), "Please enter a valid email address.");
					}
					else if(tPassword.getText().length() < 8)
					{
						JOptionPane.showMessageDialog(getParent(), "Please enter a password of at least 8 characters.");
					}
					else if(emailAvailable == false)
					{
						JOptionPane.showMessageDialog(getParent(), "Account already exists with selected email. Please use a different email address.");
					}
					else
					{
						if(cShopperType.getSelectedItem().toString().equals("ISU Dining Student Employee"))
						{
							stat.execute("INSERT INTO users VALUES('" + tEmail.getText() + "','" + tPassword.getText() + "','" + tFirstName.getText() + "','" + tLastName.getText() + "','" +  cShopperType.getSelectedItem().toString() + "')");
							
							dispose();
							Login l = new Login();
						}
						else
						{
							JOptionPane.showMessageDialog(getParent(), "Not supported yet.");
						}
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
	}
	

}
