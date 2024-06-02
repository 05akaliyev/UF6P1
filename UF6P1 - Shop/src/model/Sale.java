// Sale.java
package model;

import java.awt.event.MouseAdapter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Formatter;

public class Sale {
	//client be Client type
	private Client client;
	private ArrayList<Product> products;
	private Amount amount;
	private LocalDateTime date;
	
	public Sale(Client client, ArrayList<Product> products, Amount amount, LocalDateTime date) {
		super();
		this.client = client;
		this.products = products;
		this.amount = amount;
		this.date = date;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public ArrayList<Product> getProducts() {
		return products;
	}

	public void setProducts(ArrayList<Product> products) {
		this.products = products;
	}

	public Amount getAmount() {
		return amount;
	}

	public void setAmount(Amount amount) {
		this.amount = amount;
	}
	
	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	@Override
	public String toString() {	
		DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyy HH:mm:ss");
		String formattedDate = date.format(myFormatObj);
		
		return "Sale [client=" + client + ", products="+ products.toString() 
		+", amount=" + amount + Amount.getCurrency() 
		+ " Date:"+ formattedDate +"]";
	}

}
