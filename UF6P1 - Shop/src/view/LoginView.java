// LoginView JFrame
package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import exception.LimitLoginException;
import main.Shop;
import model.Employee;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginView extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private int loginAttempts = 0;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginView frame = new LoginView();
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
	public LoginView() {
		setTitle("Login View");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel userLabel = new JLabel("User:");
		userLabel.setBounds(49, 70, 61, 16);
		contentPane.add(userLabel);
		
		textField = new JTextField();
		textField.setBounds(120, 65, 130, 26);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel passLabel = new JLabel("Password:");
		passLabel.setBounds(49, 160, 61, 16);
		contentPane.add(passLabel);
		
		textField_1 = new JTextField();
		textField_1.setBounds(120, 155, 130, 26);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		JButton logedButton = new JButton("Login");
		logedButton.setBounds(300, 200, 117, 29);
		contentPane.add(logedButton);
		logedButton.addActionListener(this);
		
	}

	public void actionPerformed(ActionEvent e) {
		try {	
			int userId = Integer.parseInt(textField.getText());
			String userPass = textField_1.getText();
			Employee employee = new Employee("employeeA", userId);
			if(employee.login(userId, userPass) == true) {
				ShopView shopView = new ShopView();
		        shopView.setVisible(true);
		        this.dispose();
			} else {
				JOptionPane.showMessageDialog(this,
						"ERROR: data is wrong",
						"Error de Insercion", JOptionPane.ERROR_MESSAGE);
				textField.setText("");
				textField_1.setText("");
				loginAttempts += 1;
				if (loginAttempts > 2) {
                    throw new LimitLoginException();
                }
			}
		} catch (LimitLoginException ex) {
			
	        // Exception management
	        ex.printStackTrace();
	        JOptionPane.showMessageDialog(this,
                    "Se han excedido el limite de intentos de inicio de sesion.",
                    "ERROR", JOptionPane.ERROR_MESSAGE);
	        System.exit(0);
	    } catch (NumberFormatException ex) {

	        ex.printStackTrace();
	        JOptionPane.showMessageDialog(this,
					"ERROR: data is wrong",
					"Error de Inserción", JOptionPane.ERROR_MESSAGE);
	        System.exit(0);
	    }
	}
}
