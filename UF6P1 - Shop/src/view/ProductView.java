// ProductView JDialog
package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import main.Shop;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class ProductView extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JTextField nameProduct;
	private JTextField stockProduct;
	private JTextField priceProduct;
	private JButton okButton;
	private JButton cancelButton;
	private JLabel nameProductLabel;
	private JLabel stockProductLabel;
	private JLabel priceProductLabel;
	private Shop shop;
	private int option;
	/**
	 * Create the dialog.
	 */
	public ProductView() {
		setTitle("Product View");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);
		
		nameProductLabel = new JLabel("Nombre de producto: ");
		nameProductLabel.setBounds(31, 50, 130, 15);
		getContentPane().add(nameProductLabel);
		
		stockProductLabel = new JLabel("Stock de producto: ");
		stockProductLabel.setBounds(31, 100, 130, 15);
		getContentPane().add(stockProductLabel);
		
		priceProductLabel = new JLabel("Precio de producto: ");
		priceProductLabel.setBounds(31, 150, 130, 15);
		getContentPane().add(priceProductLabel);
		
		nameProduct = new JTextField();
		nameProduct.setBounds(190, 45, 130, 26);
		getContentPane().add(nameProduct);
		nameProduct.setColumns(10);
		
		stockProduct = new JTextField();
		stockProduct.setBounds(190, 95, 130, 26);
		getContentPane().add(stockProduct);
		stockProduct.setColumns(10);
		
		priceProduct = new JTextField();
		priceProduct.setBounds(190, 145, 130, 26);
		getContentPane().add(priceProduct);
		priceProduct.setColumns(10);
		
		okButton = new JButton("OK");
		okButton.setBounds(240, 230, 80, 30);
		getContentPane().add(okButton);
		okButton.addActionListener(this);
		
		cancelButton = new JButton("Cancel");
		cancelButton.setBounds(330, 230, 100, 30);
		getContentPane().add(cancelButton);
		cancelButton.addActionListener(this);
		
	}
	
	public void openProductView(int option, Shop shop) {
		this.shop = shop;
		this.option = option;
		
		switch (option) {
        case 2:
            
            setVisible(true);
            break;
        case 3:
            
            setTitle("Add stock view");
            priceProductLabel.setVisible(false);
            priceProduct.setVisible(false);
            setVisible(true);
            break;
        case 9:
            
            setTitle("Delete product view");
            stockProductLabel.setVisible(false);
            stockProduct.setVisible(false);
            priceProductLabel.setVisible(false);
            priceProduct.setVisible(false);
            setVisible(true);
            break;
        default:        
            setVisible(false);
            break;
    }
	}
	
	public void actionPerformed(ActionEvent e) {
		
		if(okButton == e.getSource()) {
			String name = nameProduct.getText();
			int stock = Integer.parseInt(stockProduct.getText());
			double price = Double.parseDouble(priceProduct.getText());
			if(option == 2) {
				if(shop.addNewProduct(name, stock, price) == false) {
					JOptionPane.showMessageDialog(this,
							"ERROR: product alredy exist, wrong data",
							"Error add new product", JOptionPane.ERROR_MESSAGE);
				}else {
					this.dispose();
				}
			}else if(option == 3) {
				if(shop.addStock(name, stock) == false) {
					JOptionPane.showMessageDialog(this,
							"ERROR: product not exist, wrong data",
							"Error to add stock", JOptionPane.ERROR_MESSAGE);
				}else {
					this.dispose();
				}
			}else if(option == 9) {				
				if(shop.deleteProduct(name) == false) {
					JOptionPane.showMessageDialog(this,
							"ERROR: product not exist",
							"Error to delete", JOptionPane.ERROR_MESSAGE);
				}else {
					this.dispose();
				}
			}
		}else if(cancelButton == e.getSource()) {
			this.dispose();
		}
		
	}
	
	
}
