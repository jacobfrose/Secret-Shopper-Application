package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.GridBagLayout;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.mysql.cj.jdbc.exceptions.CommunicationsException;
import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Dimension;
import javax.swing.JButton;
import java.awt.Component;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.MouseMotionAdapter;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.UIManager;
import java.awt.Toolkit;
import javax.swing.JPasswordField;

public class Login extends JFrame {

	private JPanel contentPane;
	private JTextField tEmail;
	private JButton bLogin;
	private JLabel lRegister;
	Connection con;
	private JComboBox cShopperType;
	private JPasswordField tPassword;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Login() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Login.class.getResource("/resources/Smol.png")));
		
		initialize();
		events();
	}
	public void initialize()
	{
		try {
			createConnection();
		}
		catch(SQLException | ClassNotFoundException e)
		{
			
		}
		setResizable(false);
		setMaximumSize(new Dimension(300, 200));
		setMinimumSize(new Dimension(300, 200));
		setTitle("Login");
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setSize(270, 212);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		contentPane = new JPanel();
		
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Email: ");
		lblNewLabel.setBounds(53, 50, 48, 14);
		contentPane.add(lblNewLabel);
		
		tEmail = new JTextField();
		tEmail.setText("jfrose@iastate.edu");
		tEmail.setBounds(100, 47, 164, 20);
		contentPane.add(tEmail);
		tEmail.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password: ");
		lblPassword.setBounds(31, 81, 70, 14);
		contentPane.add(lblPassword);
		
		bLogin = new JButton("Login");
		
		bLogin.setBounds(165, 105, 89, 23);
		contentPane.add(bLogin);
		
		lRegister = new JLabel("Register");
		
		
		lRegister.setAlignmentX(Component.RIGHT_ALIGNMENT);
		lRegister.setBounds(196, 139, 58, 14);
		contentPane.add(lRegister);
		
		cShopperType = new JComboBox();
		cShopperType.setModel(new DefaultComboBoxModel(new String[] {"Student", "Employee"}));
		cShopperType.setBounds(100, 14, 164, 22);
		contentPane.add(cShopperType);
		
		JLabel lblNewLabel_1 = new JLabel("Shopper Type: ");
		lblNewLabel_1.setBounds(10, 14, 81, 14);
		contentPane.add(lblNewLabel_1);
		
		tPassword = new JPasswordField();
		tPassword.setText("betsy1998");
		tPassword.setBounds(100, 75, 164, 20);
		contentPane.add(tPassword);
	}
	public void events()
	{
		bLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					ResultSet result = con.createStatement().executeQuery("SELECT shopper_email, password FROM users");
					while(result.next())
					{
						if(result.getString(1).equals(tEmail.getText()) && result.getString(2).equals(tPassword.getText()))
						{
							if(cShopperType.getSelectedItem().toString().equals("Student"))
							{
								JOptionPane.showMessageDialog(null, "Account found");
								StudentView app = new StudentView(con, tEmail.getText());
								app.setVisible(true);
								dispose();
							}
							else
							{
								JOptionPane.showMessageDialog(getParent(), "Full Time Not Yet Supported.");
							}
						}
						else
						{
							JOptionPane.showMessageDialog(getParent(), "Account doesn't exist. Please register.");
						}
					}
				} catch (SQLException | NullPointerException e1) {
						JOptionPane.showMessageDialog(null, "Oh Yeah Baby.");
					e1.printStackTrace();
				}
			}
		});
		lRegister.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					reg register = new reg(con);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		lRegister.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				lRegister.setForeground(Color.blue);
			}
		});
		contentPane.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				lRegister.setForeground(Color.black);
			}
		});
		
	}
	public void createConnection() throws SQLException, ClassNotFoundException
	{
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/secretshopper", "newuser", "betsy1998");
			//con = DriverManager.getConnection("jdbc:mysql://192.168.1.19:3306/secretshopper", "newuser", "betsy1998");
		}catch(CommunicationsException c)
		{
			JOptionPane.showMessageDialog(getParent(), "Could not conenct to the server.");
			dispose();
		}
	}
}
