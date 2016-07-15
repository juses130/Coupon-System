package GUI;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import com.dbdao.CompanyDBDAO;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JSplitPane;
import javax.swing.SwingConstants;

public class TestDBApp {

	private JFrame frmAddcompay;
	private JTextField companyIDTextField;
	private JTextField companyNameTextField;
	private JTextField companypasswordTextField;
	private JTextField compEmailTextField;
	private JTextArea textAreaDB;
	private JTextField customerIDtextField;
	private JTextField customerNametextField_1;
	private JTextField customerPasstextField_2;
	
	private int companyID;
	private String companyName;
	private String companyPassword;
	private String companyEmail;
	private JTextField idCompanyDeleteTextField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TestDBApp window = new TestDBApp();
					window.frmAddcompay.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TestDBApp() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmAddcompay = new JFrame();
		frmAddcompay.setTitle("Coupon Man");
		frmAddcompay.setResizable(false);
		frmAddcompay.setBounds(100, 100, 948, 551);
		frmAddcompay.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmAddcompay.getContentPane().setLayout(null);
		final String connectionUrl = "jdbc:mysql://localhost:3306/coupon";

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Display Area", TitledBorder.LEFT,
				TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(0, 0, 472, 296);
		frmAddcompay.getContentPane().add(panel);
		panel.setLayout(null);

		textAreaDB = new JTextArea();
		textAreaDB.setBounds(10, 21, 452, 266);
		
		//JScrollPane scroll = new JScrollPane(textAreaDB);
		//scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		panel.add(textAreaDB);
		//panel.add(scroll);

		JPanel panelAddCompany = new JPanel();
		panelAddCompany.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Add Company",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelAddCompany.setBounds(10, 307, 462, 204);
		frmAddcompay.getContentPane().add(panelAddCompany);
		panelAddCompany.setLayout(null);

		JLabel lblCompanyId = new JLabel("Company ID");
		lblCompanyId.setBounds(10, 22, 98, 14);
		panelAddCompany.add(lblCompanyId);

		companyIDTextField = new JTextField();

		
		companyIDTextField.setBounds(118, 19, 110, 20);
		panelAddCompany.add(companyIDTextField);
		companyIDTextField.setColumns(10);

		JLabel lblCompanyName = new JLabel("Company name");
		lblCompanyName.setBounds(10, 52, 98, 14);
		panelAddCompany.add(lblCompanyName);

		companyNameTextField = new JTextField();
		companyNameTextField.setBounds(118, 49, 110, 20);
		panelAddCompany.add(companyNameTextField);
		companyNameTextField.setColumns(10);

		JLabel lblNewLabel = new JLabel("Password");
		lblNewLabel.setBounds(10, 80, 98, 14);
		panelAddCompany.add(lblNewLabel);

		companypasswordTextField = new JTextField();
		companypasswordTextField.setBounds(118, 77, 110, 20);
		panelAddCompany.add(companypasswordTextField);
		companypasswordTextField.setColumns(10);

		JLabel lblEmail = new JLabel("Email");
		lblEmail.setBounds(10, 108, 98, 14);
		panelAddCompany.add(lblEmail);

		compEmailTextField = new JTextField();
		compEmailTextField.setBounds(118, 105, 110, 20);
		panelAddCompany.add(compEmailTextField);
		compEmailTextField.setColumns(10);

		JPanel panelAddCustomer = new JPanel();
		panelAddCustomer.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Add Customer",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelAddCustomer.setBounds(482, 11, 229, 500);
		frmAddcompay.getContentPane().add(panelAddCustomer);
		panelAddCustomer.setLayout(null);
		
		
		//JButton Print All Customers
		JButton btnNewButton = new JButton("Print All Customers");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				// 2. get a connection
				String connectionUrl = "jdbc:mysql://localhost:3306/coupon";
						
				try (Connection con = DriverManager.getConnection(connectionUrl, "root", "12345")) {

					Statement stat = con.createStatement();
					String sql = "SELECT * FROM Customer";
					ResultSet rs = stat.executeQuery(sql);
					textAreaDB.setText("ID\tCUST_NAME\tPASSWORD\t\n");
					while (rs.next()) {

						textAreaDB.append(rs.getString(1) + "\t");
						textAreaDB.append(rs.getString(2) + "\t");
						textAreaDB.append(rs.getString(3) + "\t\n");

					}

				} catch (SQLException e2) {
					// textAreaDB.setText("Exception is: ");
					textAreaDB.setText(e2.getMessage());
					// textAreaDB.setText("Error connection");

				}

			}

		});
		btnNewButton.setBounds(10, 139, 165, 23);
		panelAddCustomer.add(btnNewButton);

		JLabel lblCustomerId = new JLabel("Customer ID");
		lblCustomerId.setBounds(10, 24, 97, 14);
		panelAddCustomer.add(lblCustomerId);

		JLabel lblNewLabel_1 = new JLabel("Customer Name");
		lblNewLabel_1.setBounds(10, 50, 97, 14);
		panelAddCustomer.add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("Customer Password");
		lblNewLabel_2.setToolTipText("");
		lblNewLabel_2.setBounds(10, 75, 139, 14);
		panelAddCustomer.add(lblNewLabel_2);

		customerIDtextField = new JTextField();
		customerIDtextField.setBounds(137, 21, 86, 20);
		panelAddCustomer.add(customerIDtextField);
		customerIDtextField.setColumns(10);

		customerNametextField_1 = new JTextField();
		customerNametextField_1.setBounds(137, 47, 86, 20);
		panelAddCustomer.add(customerNametextField_1);
		customerNametextField_1.setColumns(10);

		customerPasstextField_2 = new JTextField();
		customerPasstextField_2.setBounds(137, 72, 86, 20);
		panelAddCustomer.add(customerPasstextField_2);
		customerPasstextField_2.setColumns(10);

		JButton btnNewButton_1 = new JButton("Add Customer");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String customerName = customerNametextField_1.getText();
				int customerID = Integer.parseInt(customerIDtextField.getText());
				String customerPass = customerPasstextField_2.getText();
				
				try (Connection con = DriverManager.getConnection(connectionUrl, "root", "1234")) {
					
					String insertCustomer = "INSERT INTO Customer(customerID,CUST_NAME,PASSWORD) VALUES(?,?,?)";
					PreparedStatement prep = con.prepareStatement(insertCustomer);
					prep.setInt(1, customerID);
					prep.setString(2, customerName);
					prep.setString(3, customerPass);
					prep.execute();
					prep.close();
					textAreaDB.setText("Customer added");
					customerNametextField_1.setText("");
					customerIDtextField.setText("");
					customerPasstextField_2.setText("");
					
				} catch (SQLException e2) {
					// textAreaDB.setText("Exception is: ");
					textAreaDB.setText(e2.getMessage() + "Err");
					customerNametextField_1.setText("");
					customerIDtextField.setText("");
					customerPasstextField_2.setText("");
					// textAreaDB.setText("Error connection");

				}
				
				
			}
		});
		btnNewButton_1.setBounds(49, 100, 132, 23);
		panelAddCustomer.add(btnNewButton_1);

		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new TitledBorder(null, "Coupon", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_3.setBounds(721, 11, 211, 500);
		frmAddcompay.getContentPane().add(panel_3);
		panel_3.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JButton btnAddCompany = new JButton("Add Company");
		btnAddCompany.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				companyName = companyNameTextField.getText();
				companyPassword = companypasswordTextField.getText();
				companyID = Integer.parseInt(companyIDTextField.getText());
				companyEmail = compEmailTextField.getText();
				
				
				
				// 2. get a connection
				
				try (Connection con = DriverManager.getConnection(connectionUrl,"root","1234")) {
				
						//Statement stat = con.createStatement();
						// the sql insert statement
						String insertCompany = "INSERT INTO Company(companyID,COMP_NAME,PASSWORD,EMAIL) VALUES(?,?,?,?)";
						//
						// create the sql insert preparedstatement
						PreparedStatement prep = con.prepareStatement(insertCompany);
						//prep.setInt(0, companyID);
						prep.setInt(1, companyID);
						prep.setString(2, companyName);
						prep.setString(3, companyPassword);
						prep.setString(4,companyEmail);
						// execute the preparedstatement
						prep.execute();
						textAreaDB.setText("Added");
						companyNameTextField.setText("");
						companypasswordTextField.setText("");
						companyIDTextField.setText("");
						compEmailTextField.setText("");
				
				} catch (SQLException e2) {
					// textAreaDB.setText("Exception is: ");
					textAreaDB.setText(e2.getMessage());
					// textAreaDB.setText("Error connection");

				}
				
				

			}
		});
		btnAddCompany.setBounds(55, 133, 118, 23);
		panelAddCompany.add(btnAddCompany);

		JButton btnPrintAllCompanies = new JButton("Print All Companies");
		btnPrintAllCompanies.setBounds(238, 104, 153, 23);
		panelAddCompany.add(btnPrintAllCompanies);
		
		JButton btnNewButton_2 = new JButton("Delete Company ");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int removeID = Integer.parseInt(idCompanyDeleteTextField.getText());
				
				String connectionUrl = "jdbc:mysql://localhost:3306/coupon";
				try (Connection con = DriverManager.getConnection(connectionUrl, "root", "1234")) {
					
					String sql = "DELETE FROM coupons_management_system.company WHERE companyID = ?";
					Statement stat = con.createStatement();
					PreparedStatement stmt = con.prepareStatement(sql);
					stmt.setInt(1, removeID);
					stmt.executeUpdate();
					stmt.close();
					idCompanyDeleteTextField.setText("ID");
					textAreaDB.setText("Deleted");
				
				} catch (SQLException e2) {
					// textAreaDB.setText("Exception is: ");
					textAreaDB.setText(e2.getMessage() + "Err");
					
					// textAreaDB.setText("Error connection");

				}

			}
		});
		btnNewButton_2.setBounds(238, 18, 153, 23);
		panelAddCompany.add(btnNewButton_2);
		
		idCompanyDeleteTextField = new JTextField();
		idCompanyDeleteTextField.setText("ID");
		idCompanyDeleteTextField.setBounds(401, 19, 51, 20);
		panelAddCompany.add(idCompanyDeleteTextField);
		idCompanyDeleteTextField.setColumns(10);
		btnPrintAllCompanies.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				// 1. load the jdbc driver
//				try {
//					Class.forName("com.mysql.jdbc.Driver");
//				} catch (ClassNotFoundException e1) {
//					textAreaDB.setText(e1.getMessage());
//					e1.printStackTrace();
//				}

				// 2. get a connection
				//String connectionUrl = "jdbc:mysql://localhost:3306/coupon";
				try (Connection con = DriverManager.getConnection(connectionUrl, "root", "1234")) {

					Statement stat = con.createStatement();
					String sql = "SELECT * FROM coupons_management_system.company";
					ResultSet rs = stat.executeQuery(sql);
					textAreaDB.setText("ID\tCOMANY\tPASSWORD\tEMAIL\n");
					while (rs.next()) {

						textAreaDB.append(rs.getString(1) + "\t");
						textAreaDB.append(rs.getString(2) + "\t");
						textAreaDB.append(rs.getString(3) + "\t");
						textAreaDB.append(rs.getString(4) + "\t\n");

					}

				} catch (SQLException e2) {
					// textAreaDB.setText("Exception is: ");
					textAreaDB.setText(e2.getMessage() + "Err");
					
					// textAreaDB.setText("Error connection");

				}

			}
		});
	}
}
