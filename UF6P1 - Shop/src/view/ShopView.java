// ShopView JFrame
package view;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import main.Shop;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JDialog;

public class ShopView extends JFrame implements ActionListener, KeyListener{

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton countCageButton;
	private JButton addNewProductButton;
	private JButton addStockButton;
	private JButton deleteProductButton;
	private Shop shop = new Shop();
	private int option;
	
	/**
	 * Create the frame.
	 */
	public ShopView() {
		shop.readFileInventory();
		setTitle("Shop View");
		addKeyListener(this);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel textLabel = new JLabel("Selecciona una opción de la tienda:");
		textLabel.setBounds(20, 10, 260, 16);
		contentPane.add(textLabel);
		
		countCageButton = new JButton("1. Contar caja");
		countCageButton.setBounds(30, 40, 250, 50);
		contentPane.add(countCageButton);
		countCageButton.addActionListener(this);
		countCageButton.addKeyListener(this);
		countCageButton.setFocusable(false);
		
		addNewProductButton = new JButton("2. Añadir producto");
		addNewProductButton.setBounds(30, 100, 250, 50);
		contentPane.add(addNewProductButton);
		addNewProductButton.addActionListener(this);
		addNewProductButton.addKeyListener(this);
		addNewProductButton.setFocusable(false);
		
		addStockButton = new JButton("3. Añadir stock");
		addStockButton.setBounds(30, 160, 250, 50);
		contentPane.add(addStockButton);
		addStockButton.addActionListener(this);
		addStockButton.addKeyListener(this);
		addStockButton.setFocusable(false);
		
		deleteProductButton = new JButton("9. Eliminar producto");
		deleteProductButton.setBounds(30, 220, 250, 50);
		contentPane.add(deleteProductButton);
		deleteProductButton.addActionListener(this);
		deleteProductButton.addKeyListener(this);
		deleteProductButton.setFocusable(false);
		
	}
	
	// Getters and Setters
	public JButton getCountCageButton() {
		return countCageButton;
	}
	public void setCountCageButton(JButton countCageButton) {
		this.countCageButton = countCageButton;
	}
	public JButton getAddNewProductButton() {
		return addNewProductButton;
	}
	public void setAddNewProductButton(JButton addNewProductButton) {
		this.addNewProductButton = addNewProductButton;
	}
	public JButton getAddStockButton() {
		return addStockButton;
	}
	public void setAddStockButton(JButton addStockButton) {
		this.addStockButton = addStockButton;
	}
	public JButton getDeleteProductButton() {
		return deleteProductButton;
	}
	public void setDeleteProductButton(JButton deleteProductButton) {
		this.deleteProductButton = deleteProductButton;
	}

// KeyActions
	@Override
	public void keyTyped(KeyEvent e) {
		
		
	}
	@Override
	public void keyPressed(KeyEvent ex) {
		int keyCode = ex.getKeyCode();
	    System.out.println("Tecla presionada: " + keyCode);
	    ProductView addproductView = new ProductView();
	    if(keyCode == 49) {
	    	option = 1;
	    }else if(keyCode == 50) {
	    	option = 2;
	    }else if(keyCode == 51) {
	    	option = 3;
	    }else if(keyCode == 57) {
	    	option = 9;
	    }
	    switch (keyCode) {
	        case KeyEvent.VK_1:
	        	openCashView(option, shop);
	            break;
	        case KeyEvent.VK_2:
	        	addproductView.openProductView(option, shop);
	            break;
	        case KeyEvent.VK_3:
	        	addproductView.openProductView(option, shop);
	            break;
	        case KeyEvent.VK_9:
	        	addproductView.openProductView(option, shop);
	            break;
	        default:
	            // Si se presiona una tecla diferente, no hace nada
	            break;
	    }
		
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		int keyCode = e.getKeyCode();
        System.out.println("Tecla liberada: " + keyCode);	
	}

	
	public void actionPerformed(ActionEvent e) {
		ProductView addproductView = new ProductView();
		if(countCageButton == e.getSource()) {
			option = 1;
			openCashView(option, shop);
		}else if(addNewProductButton == e.getSource()) {
			option = 2;
			addproductView.openProductView(option, shop);
		}else if(addStockButton == e.getSource()) {
			option = 3;
			addproductView.openProductView(option, shop);
		}else if(deleteProductButton == e.getSource()) {
			option = 9;
			addproductView.openProductView(option, shop);
		}
		//blockButtons();
	}
	
	public void openCashView(int option, Shop shop) {
		if(option == 1) {
		    CashView cashView = new CashView(shop, this);
		    cashView.setVisible(true);
		}
	}
	
	public void blockButtons() {
		countCageButton.setEnabled(false);
	    addNewProductButton.setEnabled(false);
	    addStockButton.setEnabled(false);
	    deleteProductButton.setEnabled(false); 
	}
	public void unlockButtons() {
		countCageButton.setEnabled(true);
	    addNewProductButton.setEnabled(true);
	    addStockButton.setEnabled(true);
	    deleteProductButton.setEnabled(true); 
	}
}
