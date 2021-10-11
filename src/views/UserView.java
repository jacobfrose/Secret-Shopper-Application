package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import common.*;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTabbedPane;
import javax.swing.JScrollPane;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.UIManager;

import java.awt.Font;
import java.awt.Image;

import javax.swing.JTable;
import java.awt.Dimension;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.border.LineBorder;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import java.awt.GridLayout;
import java.awt.Component;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;

public class UserView extends JFrame {
	
	//Initialization of field variables
	private String email = "";
	private JPanel contentPane;
	private JTextField tEmail;
	private JTextField tFName;
	private JTextField tLName;
	private JTextField tShopperType;
	
	Connection con;
	private FormatedTable tIncomplete;
	private FormatedTable tComplete;
	private FormatedTable tAvailable;
	final Color cardinal = new Color(200,16,46);
	final Color dark_cardinal = new Color(124, 37, 41);
	final Color gold = new Color(241, 190, 72);
	private JTextField filterIncomplete;
	private JTextField filterComplete;
	private JTextField filterAvailable;
	private JComboBox cAssignments;
	
	private DefaultComboBoxModel cModel;
	
	private JButton retailSubmit = new JButton();
	private JButton residentialSubmit = new JButton();
	
	private ArrayList<JLabel> retailFormQuestionLabels = new ArrayList<JLabel>();
	private ArrayList<JRadioButton[]> retailFormQuestionOptions = new ArrayList<JRadioButton[]>();
	private ArrayList<ButtonGroup> retailFormButtonGroups = new ArrayList<ButtonGroup>();
	
	private ArrayList<JLabel> residentialFormQuestionLabels = new ArrayList<JLabel>();
	private ArrayList<JRadioButton[]> residentialFormQuestionOptions = new ArrayList<JRadioButton[]>();
	private ArrayList<ButtonGroup> residentialFormButtonGroups = new ArrayList<ButtonGroup>();
	private JPanel profileFront;
	

	/**
	 * Create the frame.
	 * @throws SQLException 
	 */
	public UserView(Connection con, String email) throws SQLException {
		setMinimumSize(new Dimension(852, 480));
		this.con = con;
		this.email = email;
		initialize();
		events();
	}
	/**
	 * Initialize components
	 * @throws SQLException
	 */
	public void initialize() throws SQLException
	{
		//Initialization of main frame
		setTitle("ISU Dining Secret Shopper");
		setIconImage(Toolkit.getDefaultToolkit().getImage(UserView.class.getResource("/resources/Smol.png")));
		ResultSet info = con.createStatement().executeQuery("SELECT shopper_email, first_name, last_name, user_type FROM users WHERE shopper_email = '" + email + "'");
		info.next();
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setSize(1088, 534);
		setSize(Integer.parseInt(String.format("%.0f",dim.getWidth()*.8)), Integer.parseInt(String.format("%.0f",dim.getHeight()*.8)));
		setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Initialization of MenuBar and ContentPane
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		contentPane = new JPanel();
		contentPane.setBorder(new LineBorder(new Color(0, 0, 0), 0));
		setContentPane(contentPane);
		
		//Initialization of ScrollPanes
		JScrollPane scrollTab = new JScrollPane();
		scrollTab.setBorder(new LineBorder(new Color(130, 135, 144), 0));
		JScrollPane scrollProfile = new JScrollPane();
		scrollProfile.setBorder(new LineBorder(new Color(130, 135, 144), 0));
		JScrollPane scrollIncomplete = new JScrollPane();
		JScrollPane scrollComplete = new JScrollPane();
		JScrollPane scrollAvailable = new JScrollPane();
		
		//Initialization of TabbedPane
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setPreferredSize(new Dimension(0, 0));
		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		tabbedPane.setBorder(new LineBorder(new Color(0, 0, 0), 0));
		
		
		//Initialization of JPanels
		JPanel profile = new JPanel();
		profile.setPreferredSize(new Dimension(0, 0));
		profile.setBorder(new LineBorder(new Color(0, 0, 0), 0));
		JPanel retail = new JPanel();
		retail.setBorder(new LineBorder(new Color(0, 0, 0), 0));
		JPanel residential = new JPanel();
		residential.setBorder(new LineBorder(new Color(0, 0, 0), 0));
		profileFront = new JPanel();
		
		profileFront.setBorder(new LineBorder(new Color(0, 0, 0), 0));
		profileFront.setPreferredSize(new Dimension(650, 350));
		
		//Creation of tabs in TabbedPane
		tabbedPane.addTab(null,profile);
		tabbedPane.setTabComponentAt(0, new HeaderLabel(new Dimension(70, 20), "Home", new Font("Arial", Font.BOLD, 16), cardinal, new ImageIcon(new ImageIcon(UserView.class.getResource("/resources/home.png")).getImage().getScaledInstance(16, 16, Image.SCALE_DEFAULT))));
		tabbedPane.addTab(null, retail);
		tabbedPane.setTabComponentAt(1, new HeaderLabel(new Dimension(70, 20), "Retail", new Font("Arial", Font.BOLD, 16), cardinal, new ImageIcon(new ImageIcon(UserView.class.getResource("/resources/form.png")).getImage().getScaledInstance(16, 16, Image.SCALE_DEFAULT))));
		tabbedPane.addTab(null, residential);
		tabbedPane.setTabComponentAt(2, new HeaderLabel(new Dimension(110, 20), "Residential", new Font("Arial", Font.BOLD, 16), cardinal, new ImageIcon(new ImageIcon(UserView.class.getResource("/resources/form.png")).getImage().getScaledInstance(16, 16, Image.SCALE_DEFAULT))));
		
		//Table creation
		tIncomplete = new FormatedTable();
		tComplete = new FormatedTable();
		tAvailable = new FormatedTable();
		
		//Table filter creation
		filterComplete = new JTextField();
		filterComplete.setColumns(10);
		filterIncomplete = new JTextField();
		filterIncomplete.setColumns(10);
		filterAvailable = new JTextField();
		filterAvailable.setColumns(10);
		
		//Initialize tables
		updateTable("student_assignments", filterAvailable, (DefaultTableModel) tAvailable.getModel(), "location, location_type, date, time, notes", "available = 1");
		updateTable("student_assignments", filterIncomplete, (DefaultTableModel) tComplete.getModel(), "location, location_type, date, time, notes", "shopper_email = '" + email + "' AND complete = 0");
		updateTable("student_assignments", filterComplete, (DefaultTableModel) tIncomplete.getModel(), "location, location_type, date, time, notes", "shopper_email = '" + email + "' AND complete = 1");
		
		//Initialization of HeaderLabels
		HeaderLabel lCompleteAssignments = new HeaderLabel("Complete Assignments", new Font("Arial", Font.BOLD, 16), cardinal);
		HeaderLabel lIncompleteAssignments = new HeaderLabel("Incomplete Assignments", new Font("Arial", Font.BOLD, 16), cardinal);
		HeaderLabel lAvailableAssignments = new HeaderLabel("Available Assignments", new Font("Arial", Font.BOLD, 16), cardinal);
		HeaderLabel lUserProfile = new HeaderLabel("User Profile", new Font("Arial", Font.BOLD, 16), cardinal);
		HeaderLabel lFilterComplete = new HeaderLabel("Filter", new Font("Arial", Font.BOLD, 12), dark_cardinal);
		HeaderLabel lFilterIncomplete = new HeaderLabel("Filter", new Font("Arial", Font.BOLD, 12), dark_cardinal);
		HeaderLabel lFilterAvailable = new HeaderLabel("Filter", new Font("Arial", Font.BOLD, 12), dark_cardinal);
		HeaderLabel lEmail = new HeaderLabel("Email:", new Font("Arial", Font.BOLD, 12), dark_cardinal);
		HeaderLabel lFirstName = new HeaderLabel("First Name:", new Font("Arial", Font.BOLD, 12), dark_cardinal);
		HeaderLabel lLastName = new HeaderLabel("Last Name:", new Font("Arial", Font.BOLD, 12), dark_cardinal);
		HeaderLabel lShopperType = new HeaderLabel("Shopper Type:", new Font("Arial", Font.BOLD, 12), dark_cardinal);
		HeaderLabel lPickUp = new HeaderLabel("Pick Up:", new Font("Arial", Font.BOLD, 12), dark_cardinal);
		HeaderLabel retailHeaderLabel = new HeaderLabel(new Dimension(300,100), "Retail Secret Shopper Form", new Font("Arial", Font.BOLD, 28), cardinal, new ImageIcon(new ImageIcon(UserView.class.getResource("/resources/dininglogo.png")).getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT)));
		retailHeaderLabel.setHorizontalAlignment(SwingConstants.CENTER);
		retailHeaderLabel.setAlignmentY(Component.CENTER_ALIGNMENT);
		retailHeaderLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		HeaderLabel residentialHeaderLabel = new HeaderLabel(new Dimension(400,100), "Residential Secret Shopper Form", new Font("Arial", Font.BOLD, 28), cardinal, new ImageIcon(new ImageIcon(UserView.class.getResource("/resources/dininglogo.png")).getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT)));
		residentialHeaderLabel.setHorizontalAlignment(SwingConstants.CENTER);
		residentialHeaderLabel.setAlignmentY(Component.CENTER_ALIGNMENT);
		residentialHeaderLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		//Sets panes and tables to ScrollPanes
		scrollTab.setViewportView(tabbedPane);
		scrollProfile.setViewportView(profileFront);
		scrollIncomplete.setViewportView(tIncomplete);
		scrollComplete.setViewportView(tComplete);
		scrollAvailable.setViewportView(tAvailable);
		
		//Initializes and sets profile information
		tShopperType = new JTextField();
		tShopperType.setFont(new Font("Arial", Font.PLAIN, 11));
		tShopperType.setText("<dynamic>");
		tShopperType.setEditable(false);
		tShopperType.setColumns(10);
		tShopperType.setText(info.getString(4));
		tLName = new JTextField();
		tLName.setFont(new Font("Arial", Font.PLAIN, 11));
		tLName.setText("<dynamic>");
		tLName.setEditable(false);
		tLName.setColumns(10);
		tLName.setText(info.getString(3));
		tFName = new JTextField();
		tFName.setFont(new Font("Arial", Font.PLAIN, 11));
		tFName.setText("<dynamic>");
		tFName.setEditable(false);
		tFName.setColumns(10);
		tFName.setText(info.getString(2));
		tEmail = new JTextField();
		tEmail.setFont(new Font("Arial", Font.PLAIN, 11));
		tEmail.setEditable(false);
		tEmail.setColumns(10);
		tEmail.setText(email);
		
		//Creates assignment pick-up JComboBox
		cAssignments = new JComboBox();
		cAssignments.setToolTipText("Assignments");
		
		//Initializes assignment ComboBox
		DynamicCombobox.updateComboBox((DefaultComboBoxModel) cAssignments.getModel(), cAssignments, "assignment_id, location, date, time", "student_assignments", "available = 1", con);
		
////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////Content Pane Layout//////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addComponent(scrollTab, GroupLayout.DEFAULT_SIZE, 1254, Short.MAX_VALUE)
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addComponent(scrollTab, GroupLayout.DEFAULT_SIZE, 669, Short.MAX_VALUE)
		);
		
////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////Profile Pane Layout//////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////
		GroupLayout gl_profileFront = new GroupLayout(profileFront);
		gl_profileFront.setHorizontalGroup(
			gl_profileFront.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_profileFront.createSequentialGroup()
					.addGap(10)
					.addComponent(scrollComplete, GroupLayout.DEFAULT_SIZE, 339, Short.MAX_VALUE)
					.addGap(10)
					.addComponent(scrollIncomplete, GroupLayout.DEFAULT_SIZE, 339, Short.MAX_VALUE)
					.addGap(25))
				.addGroup(gl_profileFront.createSequentialGroup()
					.addGroup(gl_profileFront.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_profileFront.createSequentialGroup()
							.addGap(10)
							.addGroup(gl_profileFront.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_profileFront.createParallelGroup(Alignment.LEADING)
									.addGroup(gl_profileFront.createSequentialGroup()
										.addComponent(lIncompleteAssignments, GroupLayout.DEFAULT_SIZE, 193, Short.MAX_VALUE)
										.addGap(22)
										.addComponent(lFilterIncomplete, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addGap(10)
										.addComponent(filterIncomplete, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addGap(10))
									.addGroup(gl_profileFront.createSequentialGroup()
										.addGroup(gl_profileFront.createParallelGroup(Alignment.LEADING)
											.addComponent(lFirstName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
											.addComponent(lLastName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
											.addComponent(lShopperType, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
										.addGap(10)
										.addGroup(gl_profileFront.createParallelGroup(Alignment.LEADING)
											.addComponent(tFName, GroupLayout.PREFERRED_SIZE, 199, GroupLayout.PREFERRED_SIZE)
											.addComponent(tLName, GroupLayout.PREFERRED_SIZE, 199, GroupLayout.PREFERRED_SIZE)
											.addComponent(tShopperType, GroupLayout.PREFERRED_SIZE, 199, GroupLayout.PREFERRED_SIZE))
										.addGap(59)))
								.addGroup(gl_profileFront.createSequentialGroup()
									.addComponent(lEmail, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addGap(57)
									.addComponent(tEmail, GroupLayout.PREFERRED_SIZE, 199, GroupLayout.PREFERRED_SIZE)
									.addGap(59))))
						.addGroup(gl_profileFront.createSequentialGroup()
							.addContainerGap()
							.addComponent(lUserProfile, GroupLayout.PREFERRED_SIZE, 164, GroupLayout.PREFERRED_SIZE)
							.addGap(185)))
					.addGroup(gl_profileFront.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_profileFront.createSequentialGroup()
							.addComponent(lPickUp, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(cAssignments, GroupLayout.PREFERRED_SIZE, 213, GroupLayout.PREFERRED_SIZE)
							.addContainerGap())
						.addGroup(Alignment.TRAILING, gl_profileFront.createSequentialGroup()
							.addComponent(lAvailableAssignments, GroupLayout.DEFAULT_SIZE, 193, Short.MAX_VALUE)
							.addGap(22)
							.addComponent(lFilterAvailable, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(8)
							.addComponent(filterAvailable, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(27))
						.addGroup(Alignment.TRAILING, gl_profileFront.createSequentialGroup()
							.addGroup(gl_profileFront.createParallelGroup(Alignment.TRAILING)
								.addComponent(scrollAvailable, GroupLayout.DEFAULT_SIZE, 339, Short.MAX_VALUE)
								.addGroup(gl_profileFront.createSequentialGroup()
									.addComponent(lCompleteAssignments, GroupLayout.DEFAULT_SIZE, 193, Short.MAX_VALUE)
									.addGap(22)
									.addComponent(lFilterComplete, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addGap(6)
									.addComponent(filterComplete, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)))
							.addGap(25))))
		);
		gl_profileFront.setVerticalGroup(
			gl_profileFront.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_profileFront.createSequentialGroup()
					.addGroup(gl_profileFront.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_profileFront.createSequentialGroup()
							.addGap(15)
							.addComponent(lUserProfile, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_profileFront.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_profileFront.createParallelGroup(Alignment.BASELINE)
								.addComponent(lPickUp, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(cAssignments, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
					.addGap(8)
					.addGroup(gl_profileFront.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_profileFront.createSequentialGroup()
							.addGap(3)
							.addComponent(lEmail, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addComponent(tEmail, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_profileFront.createSequentialGroup()
							.addGap(3)
							.addGroup(gl_profileFront.createParallelGroup(Alignment.BASELINE)
								.addComponent(lFilterAvailable, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lAvailableAssignments, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
						.addComponent(filterAvailable, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGroup(gl_profileFront.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_profileFront.createSequentialGroup()
							.addGap(5)
							.addGroup(gl_profileFront.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_profileFront.createSequentialGroup()
									.addGap(4)
									.addComponent(lFirstName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addGap(12)
									.addComponent(lLastName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addGap(12)
									.addComponent(lShopperType, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_profileFront.createSequentialGroup()
									.addComponent(tFName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addGap(6)
									.addComponent(tLName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addGap(6)
									.addComponent(tShopperType, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
						.addGroup(gl_profileFront.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(scrollAvailable, GroupLayout.PREFERRED_SIZE, 137, GroupLayout.PREFERRED_SIZE)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_profileFront.createParallelGroup(Alignment.LEADING)
						.addComponent(lIncompleteAssignments, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_profileFront.createSequentialGroup()
							.addGap(5)
							.addComponent(lFilterIncomplete, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_profileFront.createSequentialGroup()
							.addGap(2)
							.addComponent(filterIncomplete, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addComponent(lCompleteAssignments, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_profileFront.createSequentialGroup()
							.addGap(5)
							.addComponent(lFilterComplete, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_profileFront.createSequentialGroup()
							.addGap(2)
							.addComponent(filterComplete, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addGap(5)
					.addGroup(gl_profileFront.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollComplete, GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
						.addComponent(scrollIncomplete, GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE))
					.addGap(24))
		);
		profileFront.setLayout(gl_profileFront);
		GroupLayout gl_profile = new GroupLayout(profile);
		gl_profile.setHorizontalGroup(
			gl_profile.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_profile.createSequentialGroup()
					.addGap(10)
					.addComponent(scrollProfile, GroupLayout.DEFAULT_SIZE, 2010, Short.MAX_VALUE)
					.addGap(7))
		);
		gl_profile.setVerticalGroup(
			gl_profile.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_profile.createSequentialGroup()
					.addGap(11)
					.addComponent(scrollProfile, GroupLayout.DEFAULT_SIZE, 1048, Short.MAX_VALUE)
					.addGap(4))
		);
		profile.setLayout(gl_profile);
		
		JPanel retailHeaderPanel = new JPanel();
		
		JScrollPane scrollPane = new JScrollPane();
////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////Retail Pane Layout//////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////
		GroupLayout gl_retail = new GroupLayout(retail);
		gl_retail.setHorizontalGroup(
			gl_retail.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_retail.createSequentialGroup()
					.addContainerGap()
					.addComponent(retailHeaderPanel, GroupLayout.PREFERRED_SIZE, 1000, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 1000, GroupLayout.PREFERRED_SIZE)
					.addGap(19))
		);
		gl_retail.setVerticalGroup(
			gl_retail.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_retail.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_retail.createParallelGroup(Alignment.LEADING)
						.addComponent(retailHeaderPanel, GroupLayout.PREFERRED_SIZE, 1050, GroupLayout.PREFERRED_SIZE)
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 1050, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		retailHeaderPanel.setLayout(new GridLayout(1, 0, 0, 0));
		
		
		retailHeaderPanel.add(retailHeaderLabel);
		
		JPanel retailFormPanel = new JPanel();
		scrollPane.setViewportView(retailFormPanel);
		
		
		FormLayout retailFormLayout = new FormLayout(new ColumnSpec[] {
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,},
			new RowSpec[] {});
		retailFormPanel.setLayout(retailFormLayout);
		
		//Builds the retail form from database
		ResultSet retailQuestionsResult = con.createStatement().executeQuery("SELECT * FROM retail_question_registry");
		buildForm(retailQuestionsResult, retailFormLayout, retailFormPanel, retailSubmit, retailFormQuestionLabels, retailFormQuestionOptions, retailFormButtonGroups);
		
		retail.setLayout(gl_retail);
		
		contentPane.setLayout(gl_contentPane);
		
////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////Residential Pane Layout//////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////
		JPanel residentialFormPanel = new JPanel();
		JPanel residentialHeaderPanel = new JPanel();
		JScrollPane resScrollPane = new JScrollPane();
		
		GroupLayout gl_residential = new GroupLayout(residential);
		gl_residential.setHorizontalGroup(
			gl_residential.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_residential.createSequentialGroup()
					.addContainerGap()
					.addComponent(residentialHeaderPanel, GroupLayout.PREFERRED_SIZE, 1000, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(resScrollPane, GroupLayout.PREFERRED_SIZE, 1000, GroupLayout.PREFERRED_SIZE)
					.addGap(19))
		);
		gl_residential.setVerticalGroup(
			gl_residential.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_residential.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_residential.createParallelGroup(Alignment.LEADING)
						.addComponent(resScrollPane, GroupLayout.PREFERRED_SIZE, 1050, GroupLayout.PREFERRED_SIZE)
						.addComponent(residentialHeaderPanel, GroupLayout.PREFERRED_SIZE, 1050, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		residential.setLayout(gl_residential);
		
		resScrollPane.setViewportView(residentialFormPanel);
		residentialHeaderPanel.setLayout(new GridLayout(1, 0, 0, 0));
		
		
		residentialHeaderPanel.add(residentialHeaderLabel);
		
		FormLayout residentialFormLayout = new FormLayout(new ColumnSpec[] {
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,},
			new RowSpec[] {});
		residentialFormPanel.setLayout(residentialFormLayout);
		
		//Builds the residential form from database
		ResultSet residentialQuestionsResult = con.createStatement().executeQuery("SELECT * FROM residential_question_registry");
		buildForm(residentialQuestionsResult, residentialFormLayout, residentialFormPanel, residentialSubmit, residentialFormQuestionLabels, residentialFormQuestionOptions, residentialFormButtonGroups);
		
		retailQuestionsResult.close();
		info.close();
	}
	/**
	 * Handle events
	 */
	public void events()
	{
		documentListener("student_assignments", filterIncomplete, (DefaultTableModel) tComplete.getModel(), "location, location_type, date, time, notes", "shopper_email = '" + email + "' AND complete = 1");
		documentListener("student_assignments", filterComplete, (DefaultTableModel) tIncomplete.getModel(), "location, location_type, date, time, notes", "shopper_email = '" + email + "' AND complete = 0");
		documentListener("student_assignments", filterAvailable, (DefaultTableModel) tAvailable.getModel(), "location, location_type, date, time, notes", "available = 1");

		cAssignments.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int result = JOptionPane.showConfirmDialog(getParent(), "Are you sure you want to pick up this assignment?", "Assignment Pick-Up Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, new ImageIcon(new ImageIcon(UserView.class.getResource("/resources/Dining Logo.png")).getImage().getScaledInstance(64, 64, Image.SCALE_DEFAULT)));
				if(result == JOptionPane.YES_OPTION)
				{
					Scanner delim = new Scanner(cAssignments.getSelectedItem().toString());
					delim.useDelimiter(" | ");
					String id = "'"+delim.next()+"'";
					try 
					{
						con.createStatement().execute("UPDATE student_assignments SET shopper_email = '" + email + "', available = 0 WHERE assignment_id = " + id);
						Thread.sleep(200);
						updateTable("student_assignments", filterAvailable, (DefaultTableModel) tAvailable.getModel(), "location, location_type, date, time, notes", "available = 1");
						updateTable("student_assignments", filterIncomplete, (DefaultTableModel) tComplete.getModel(), "location, location_type, date, time, notes", "shopper_email = '" + email + "' AND complete = 0");
						updateTable("student_assignments", filterComplete, (DefaultTableModel) tIncomplete.getModel(), "location, location_type, date, time, notes", "shopper_email = '" + email + "' AND complete = 1");
						DynamicCombobox.updateComboBox((DefaultComboBoxModel) cAssignments.getModel(), cAssignments,"assignment_id, location, date, time", "student_assignments", "available = 1", con);
					} 
					catch (SQLException | InterruptedException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		retailSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO Add auto number generation for id
				String query = "INSERT INTO retail_responses VALUES (";
				query += "'020202', '" + email + "', ";
				for(int i =0; i < retailFormQuestionOptions.size(); i++)
				{
					for(int j = 0; j < retailFormQuestionOptions.get(i).length; j++)
					{
						if(retailFormQuestionOptions.get(i)[j].isSelected())
						{
							query += "'" + retailFormQuestionOptions.get(i)[j].getText() + "'";
						}
					}
					if(i < retailFormQuestionOptions.size()-1)
					{
						query += ", ";
					}
				}
				query+=")";
				JOptionPane.showMessageDialog(getParent(), query);
				try {
					con.createStatement().execute(query);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		residentialSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO Add auto number generation for id
				String query = "INSERT INTO residential_responses VALUES (";
				query += "'020202', '" + email + "', ";
				for(int i =0; i < residentialFormQuestionOptions.size(); i++)
				{
					for(int j = 0; j < residentialFormQuestionOptions.get(i).length; j++)
					{
						if(residentialFormQuestionOptions.get(i)[j].isSelected())
						{
							query += "'" + residentialFormQuestionOptions.get(i)[j].getText() + "'";
						}
					}
					if(i < residentialFormQuestionOptions.size()-1)
					{
						query += ", ";
					}
				}
				query+=")";
				JOptionPane.showMessageDialog(getParent(), query);
				try {
					con.createStatement().execute(query);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		profileFront.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				tComplete.clearSelection();
				tIncomplete.clearSelection();
				tAvailable.clearSelection();
			}
		});
		profileFront.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(SwingUtilities.isRightMouseButton(e))
				{
					JPopupMenu m = new JPopupMenu();
					JMenuItem item = new JMenuItem("Hi");
					m.add(item);
					m.show(profileFront,e.getX(), e.getY());
				}
			}
		});
	}
	/**
	 * Update specified table model, "model", with the query derived from "tableName", "columns", and "conditions". Finally, searches for "filterText" in the results and adds rows where it is present.
	 * @param tableName
	 * @param filterText
	 * @param model
	 * @param columns
	 * @param conditions
	 * @throws SQLException
	 */
	public void updateTable(String tableName, JTextField filterText, DefaultTableModel model, String columns, String conditions) throws SQLException
	{
		//Boolean variable to determine whether to add or not while filtering
		boolean add = false;
		//Resets the table rows and columns
		model.setRowCount(0);
		model.setColumnCount(0);
		//Retrieves table information and metadata
		ResultSet result = con.createStatement().executeQuery("SELECT "+columns+" FROM " + tableName + " WHERE " + conditions);
		ResultSetMetaData meta = result.getMetaData();
		//Adds table columns using the ResultSetMetaData
		for(int i = 1; i <= meta.getColumnCount();i++)
		{
			//Capitalizes each word
			Scanner delim = new Scanner(meta.getColumnName(i));
			delim.useDelimiter("_");
			String columnName = "";
			while(delim.hasNext())
			{
				String next = delim.next();
				columnName += next.substring(0,1).toUpperCase() + next.substring(1) + " ";
			}
			//Adds column
			model.addColumn(columnName);
		}
		//Determines if there if filter data
		if(filterText.getText().equals(""))//Adds complete ResultSet to table
		{
			while(result.next())
			{
				String[] row = new String[meta.getColumnCount()];
				for(int i = 1; i <= meta.getColumnCount(); i++)
				{
					row[i-1] = result.getString(i);
				}
				model.addRow(row);
			}
		}
		else//Adds table rows that include the filter text
		{
			while(result.next())
			{
				//Searches for filter text in each row
				for(int j = 1; j <= meta.getColumnCount(); j++)
				{
					try
					{
						String str = result.getString(j);
						if(result.getString(j).toUpperCase().contains(filterText.getText().toUpperCase()))
							add = true;
					}
					catch(NullPointerException n)
					{
					}
				}
				//Adds row if filter text was found
				if(add==true)
				{
					String[] row = new String[meta.getColumnCount()];
					for(int i = 1; i <= meta.getColumnCount(); i++)
					{
						row[i-1] = result.getString(i);
					}
					model.addRow(row);
				}
				//Resets filter boolean	
				add = false;
			}
		}
	}
	/**
	 * Create document listener to determine when filter text is changed and updates the specified table model with the query arguments.
	 * Also updates the assignment pick-up combobox
	 * @param tableName
	 * @param filterText
	 * @param model
	 * @param columns
	 * @param conditions
	 */
	public void documentListener(String tableName, JTextField filterText, DefaultTableModel model, String columns, String conditions)
	{
		filterText.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void insertUpdate(DocumentEvent e) {
				try {
					updateTable(tableName, filterText, model, columns, conditions);//Updates table
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				try {
					updateTable(tableName, filterText, model, columns, conditions);//Updates table
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				try {
					updateTable(tableName, filterText, model, columns, conditions);//Updates table
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
	}
	public void addFormItem(JPanel panel, int i, String[] question, ArrayList<JLabel> formQuestionLabels, ArrayList<JRadioButton[]> formQuestionOptions, ArrayList<ButtonGroup> formButtonGroups)
	{
		
//		JLabel l = new JLabel(question[0]);
//		panel.add(l, "6, "+(2 + 6*(i-1))+", 1, 1");
//		
		formQuestionLabels.add(new JLabel(question[1]));
		panel.add(formQuestionLabels.get(i-1), "8, "+(2 + 6*(i-1))+", 8, 1");
		
		formQuestionOptions.add(new JRadioButton[] {
				new JRadioButton(question[2]),
				new JRadioButton(question[3]),
				new JRadioButton(question[4]),
				new JRadioButton(question[5]),
				new JRadioButton(question[6])});
		
		formButtonGroups.add(new ButtonGroup());
		for(int j = 0; j < formQuestionOptions.get(i-1).length; j++)
		{
			formButtonGroups.get(i-1).add(formQuestionOptions.get(i-1)[j]);
			panel.add(formQuestionOptions.get(i-1)[j], (8+j*2) + ", "+(4+6*(i-1)));
		}
		
//		String str = buttonGroup.getSelection().getActionCommand().toString();
		
		panel.add(new JLabel("                       "), "8, "+(6+6*(i-1))+", 8, 1");
	}
	public void buildForm(ResultSet questionsResult, FormLayout formLayout, JPanel formPanel, JButton submit, ArrayList<JLabel> formQuestionLabels, ArrayList<JRadioButton[]> formQuestionOptions, ArrayList<ButtonGroup> formButtonGroups ) throws SQLException
	{
		int rowCounter = 2;
		ArrayList<String[]> questions = new ArrayList<String[]>();
		while(questionsResult.next())
		{
			questions.add(new String[] {
					questionsResult.getString(1), 
					questionsResult.getString(2), 
					questionsResult.getString(3), 
					questionsResult.getString(4), 
					questionsResult.getString(5), 
					questionsResult.getString(6), 
					questionsResult.getString(7)});
		}
		
		for(int i = 1; i <= questions.size(); i++)
		{
			for(int j = 0; j < 3; j++)
			{
				formLayout.appendRow(FormSpecs.RELATED_GAP_ROWSPEC);
				formLayout.appendRow(FormSpecs.DEFAULT_ROWSPEC);
			}
			addFormItem(formPanel, i, questions.get(i-1), formQuestionLabels, formQuestionOptions, formButtonGroups);
			rowCounter += 6;
		}
		formLayout.appendRow(FormSpecs.RELATED_GAP_ROWSPEC);
		formLayout.appendRow(FormSpecs.DEFAULT_ROWSPEC);
		formLayout.appendRow(FormSpecs.RELATED_GAP_ROWSPEC);
		formLayout.appendRow(FormSpecs.DEFAULT_ROWSPEC);
		
		submit.setText("Submit Form");
		if(1 == 1)
		{
			formPanel.add(submit, 8+","+ (rowCounter));
			formPanel.add(new JLabel("                       "), "8, "+(rowCounter+2));
		}
	}
}

