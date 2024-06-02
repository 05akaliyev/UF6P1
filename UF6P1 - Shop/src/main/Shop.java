// Shop.java
package main;

import model.Amount;
import model.Client;
import model.Employee;
import model.Product;
import model.Sale;

import java.awt.Button;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Shop {
	private Amount cash = new Amount(100.00);
	private ArrayList<Product> inventory;
	private int numberProducts;
	private ArrayList<Sale> sales;
	private boolean errorMethot = true;
	final static double TAX_RATE = 1.04;
	//create a new variable to count the sales of the shop.
	private int countSales;
	
	public Shop() {
		// cash = 0.0; initial cash = 100.00 [CORRECTION]
	// Change array inventory to ArrayList inventory
		inventory = new ArrayList<Product>();		
		//create a new sales array to inset the sales [CORRECTION]
		sales = new ArrayList<Sale>();
		countSales = 0;
	}

	public static void main(String[] args) {
		Shop shop = new Shop();

			//shop.loadInventory();
			shop.readFileInventory();
		
		Scanner scanner = new Scanner(System.in);
		int opcion = 0;
		boolean exit = false;
		
// UF4 create initSession using Employee obj
	shop.initSession();

		do {
			System.out.println("\n");
			System.out.println("===========================");
			System.out.println("Menu principal miTienda.com");
			System.out.println("===========================");
			System.out.println("1) Contar caja");
			System.out.println("2) Añadir producto");
			System.out.println("3) Añadir stock");
			System.out.println("4) Marcar producto proxima caducidad");
			System.out.println("5) Ver inventario");
			System.out.println("6) Venta");
			System.out.println("7) Ver ventas");
			System.out.println("8) Ver Precio total de ventas");
			System.out.println("9) Eliminar producto");
			System.out.println("10) Salir programa");
			System.out.print("Seleccione una opción: ");
			opcion = scanner.nextInt();

			switch (opcion) {
			case 1:
				shop.showCash();
				break;

			case 2:
				shop.addNewProduct(null, opcion, opcion);
				break;

			case 3:
				shop.addStock(null, opcion);
				break;

			case 4:
				shop.setExpired();
				break;

			case 5:
				shop.showInventory();
				break;

			case 6:
				shop.sale();
				break;

			case 7:
				shop.showSales();
				break;
			// Create new case to calculate total amount in ventas
			case 8:
				shop.showAmountVentas();
				break;
			case 9:
				shop.deleteProduct(null);
				break;
			// Change case 8 for case 10. [CORRECTION]
			case 10:
				exit = true;
				break;
			// Add text if the option is not correct [CORRECTION]
			default:System.out.println("This option not exist");
			}
			
		} while (!exit);
		
	}

	/**
	 * load initial inventory to shop
	 */
	public void loadInventory() {
		
		addProduct(new Product("Manzana", 10.00, true, 10));
		addProduct(new Product("Pera", 20.00, true, 20));
		addProduct(new Product("Hamburguesa", 30.00, true, 30));
		addProduct(new Product("Fresa", 5.00, true, 20));
		
	}
	// Read inventory file or create a new one if not exist
	public void readFileInventory() {
		int count = 0;
		boolean exit = false;
		String x = null; String y = null; String z = null;
		
		try {
        	//create file
			File fileInventory = new File("inputInventory.txt");
			if(fileInventory.exists()) {
				FileReader fr = new FileReader("inputInventory.txt");
				BufferedReader br = new BufferedReader(fr);
				while(exit == false) {
            		String myLine = br.readLine();
            		if(myLine != null) {
		            	String[] result1 = myLine.split(";");
		            	while(count<3) {
			            	String[] result2 = result1[count].split(":");
			            	if(count == 0) {
			            		 x = result2[1];
			            	}else if(count == 1) {
			            		 y = result2[1];
			            	}else if(count == 2) {
			            		 z = result2[1];
			            	}
			            	count++;
		            	}
		            	double price = Double.parseDouble(y);
		            	int stock = Integer.parseInt(z);
		            	addProduct(new Product(x, price, true, stock));
		            	count = 0;
            		}else {
            			exit = true;
            		}
				}
	            	fr.close();
	        		br.close();
			}else if(fileInventory.createNewFile()) {
            	System.out.println("File created: " + fileInventory.getName());
            	//load inventory products to add to inputInventory.txt
            	loadInventory();
            	FileWriter myWriter = new FileWriter("inputInventory.txt"); 
        					for (Product product : inventory) {
        		    			if (product != null) {
        		    				myWriter.write("Product:"+product.getName()+";Wholesaler Price:"
        		    			+product.getWholesalerPrice()+";Stock:"+product.getStock()+";\n");  
        		    			}
        					}        			        		
                System.out.println("File inventory finished");
                myWriter.close();
                readFileInventory();
            }
 
            
        } catch (IOException e) {
            System.out.println("Error: Archivo no encontrado");
            e.printStackTrace();
        }
		
	}
	// Write new products to file inventory.txt
	private void writeNewInventory() {
		try {
			File fileInventory = new File("inputInventory.txt");
			FileWriter myWriter = new FileWriter("inputInventory.txt"); 
			if(fileInventory.exists()) {		
				for (Product product : inventory) {
	    			if (product != null) {
	    				myWriter.write("Product:"+product.getName()+";Wholesaler Price:"
	    			+product.getWholesalerPrice()+";Stock:"+product.getStock()+";\n");  
	    			}
				}        			        		
    System.out.println("File inventory finished");
    myWriter.close();
			}
		} catch (IOException e) {
            System.out.println("Error: Archivo no encontrado");
            e.printStackTrace();
        }
	}
	// write the new sales in the fileSales
	private void updateFileSales() {
		int numberSale = 1;
		
		try {
        	//create file
			LocalDate dateSaleFile = LocalDate.now();
            File fileSales = new File("sales"+dateSaleFile+".txt");
            if(fileSales.createNewFile()) {
            	System.out.println("File created: " + fileSales.getName());
            }else{
            	System.out.println("uploading file");
            	fileSales.delete();	
            }
            
            //write on the file the information
            FileWriter myWriter = new FileWriter("sales"+dateSaleFile+".txt");
            for (Sale sale : sales) {
    			if (sale != null) {
    					myWriter.write(numberSale+"; Cliente="+sale.getClient()+
    							";Date="+sale.getDate()+";\n");  
    					for (Product product : inventory) {
    		    			if (product != null) {
    		    				myWriter.write(numberSale+";Products="+product.getName()+","
    		    			+product.getPublicPrice()+";");  
    		    			}
    		    		}	
    					myWriter.write("\n"+numberSale+";Amount="+sale.getAmount()+";\n");
    				numberSale++;
    			}
    		}
            System.out.println("File finished");
            myWriter.close();
            
        } catch (IOException e) {
            System.out.println("Error: Archivo no encontrado");
            e.printStackTrace();
        }
		
	}
	
	/**
	 * show current total cash 
	 */
	
	// Show total cash [CORRECTION] Change to return String
	public String showCash() {
		String cashValue = cash.toString();
		return cashValue;
	}

	/**
	 * add a new product to inventory getting data from console
	 * @return 
	 */
	
	// changed the name of methot [CORRECTION]
	public boolean addNewProduct(String name, int stock, double price) {
		/*
		* Scanner scanner = new Scanner(System.in);
		* System.out.print("Nombre: ");
		* String name = scanner.nextLine();
		*/
		// check if name exist if not return nothing [CORRECTION]
		Product product = findProduct(name);
			if(product != null){
				//System.out.println("The product alredy exists");
				errorMethot = false;
			}else {
				errorMethot = true;
				addProduct(new Product(name, price, true, stock));
				
				writeNewInventory();
			}
		/*
		*	System.out.print("Precio mayorista: ");
		*	double wholesalerPrice = scanner.nextDouble();
		*	System.out.print("Stock: ");
		*	int stock = scanner.nextInt();
		 */
		
		return errorMethot;
	}

	/**
	 * add stock for a specific product
	 */
	public boolean addStock(String name, int stock) {
		/*
		* Scanner scanner = new Scanner(System.in);
		* System.out.print("Seleccione un nombre de producto: ");
		* String name = scanner.next();
		*/
		Product product = findProduct(name);

		if (product != null) {
		//make available if stock = 0 [CORRECTION]
			if(product.getStock() == 0) {
				product.setAvailable(true);
			}
		// ask for stock
		//System.out.println(product.getStock()); Stock after change
			/*
			* System.out.print("Seleccione la cantidad a añadir: ");
			* int stock = scanner.nextInt();
			*/
		// update stock product
		//plus the stock that you write [CORRECTION]
			product.setStock(product.getStock() + stock);
			System.out.println("El stock del producto " + name + " ha sido actualizado a " + product.getStock());
			writeNewInventory();
			errorMethot = true;
			//System.out.println(product.getStock()); Stock before change
		} else {
			//System.out.println("No se ha encontrado el producto con nombre " + name);
			errorMethot = false;
		}
		return errorMethot;
	}

	/**
	 * set a product as expired
	 */
	private void setExpired() {
		Scanner scanner = new Scanner(System.in);
		System.out.print("Seleccione un nombre de producto: ");
		String name = scanner.next();

		Product product = findProduct(name);

		if (product != null) {
			//use methot expire() [CORRECTION]
			product.expire();
			System.out.println("El stock del producto " + name + " ha sido actualizado a " + product.getPublicPrice());
			writeNewInventory();
		}
	}

	/**
	 * show all inventory
	 */
	public void showInventory() {
		System.out.println("Contenido actual de la tienda:");
		try {
			File fileInventory = new File("inputInventory.txt");
		    Scanner myReader = new Scanner(fileInventory);
		      while (myReader.hasNextLine()) {
		        String data = myReader.nextLine();
		        System.out.println(data);
		      }
		      myReader.close();
		    } catch (FileNotFoundException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
	}

	/**
	 * make a sale of products to a client
	 */
	public void sale() {
		// ask for client name
	//Create account Products
		ArrayList<Product> products = new ArrayList<Product>();
		
		Scanner sc = new Scanner(System.in);
		System.out.println("Realizar venta, escribir nombre cliente");
		String nameClient = sc.nextLine();
	//Create object Client 
		Client client = new Client(nameClient);
		// sale product until input name is not 0
		Amount totalAmount = new Amount(0);
		String name = "";
		while (!name.equals("0")) {
// show the list of products [CORRECTION]
			for (int i=0;i<inventory.size();i++) {
			      
			      System.out.println(inventory.get(i));
			    }
			System.out.println("Introduce el nombre del producto, escribir 0 para terminar:");
			name = sc.nextLine();
			
			if (name.equals("0")) {
				break;
			}
			Product product = findProduct(name);
			boolean productAvailable = false;

			if (product != null && product.isAvailable()) {
				productAvailable = true;
				double sum = totalAmount.getValue() + product.getPublicPrice();
				totalAmount.setValue(sum); 
				product.setStock(product.getStock() - 1);
				// if no more stock, set as not available to sale
				if (product.getStock() == 0) {
					product.setAvailable(false);
				}
				//add product to products
				System.out.println("Producto añadido con éxito");
				products.add(product);
			}

			if (!productAvailable) {
				System.out.println("Producto no encontrado o sin stock");
			}

		}
		
		// show cost total
		double sum = totalAmount.getValue() * TAX_RATE;
		totalAmount.setValue(sum);
		cash.setValue(totalAmount.getValue() + cash.getValue());
		System.out.println("Venta realizada con éxito, total " + totalAmount +"\n");
		client.pay(totalAmount);

		
		LocalDateTime date = LocalDateTime.now();
		
		// Create a new sale and add to array sales [CORRECTION]	
		Sale sale = new Sale(client, products, totalAmount, date);
		sales.add(sale);
		
		writeNewInventory();
	}
	
	/**
	 * show all sales
	 */
	private void showSales() {
		Scanner sc = new Scanner(System.in);
		//View the sales [CORRECTION]
		boolean check;
		if(sales != null) {
		System.out.println("Lista de ventas:");
			for (Sale sale : sales) {
				if (sale != null) {
					System.out.println(sale.toString());
				}
			}
			do {
				check = false;
			System.out.println("Do you what to safe this file sales (Y/N)");
			String button = sc.next();
			if(button.equalsIgnoreCase("Y")) {
				updateFileSales();
				check = true;
			}else if(button.equalsIgnoreCase("N")) {
				check = true;
			}else {
				System.out.println("Error, please insert Y or N");
			}
			}while(check == false);
		}else {
			System.out.println("There are not sales");
		}		
	}

	public void showAmountVentas() {
		double total = 0;
		if(sales != null) {
			for (Sale sale : sales) {
				if (sale != null) {
					total += sale.getAmount().getValue();
				}
			}
		}
		
		System.out.println("Amount sales = "+total+Amount.getCurrency());
		
	}
// method delete product.	
	public boolean deleteProduct(String name) {
		/*
		* Scanner scanner = new Scanner(System.in);
		* System.out.print("Seleccione un nombre de producto: ");
		* String name = scanner.next();
		*/
		Product product = findProduct(name);
		
		if(product != null) {
			for (int i=0;i<inventory.size();i++) {
				if (product != null) {
					inventory.remove(product);
					System.out.println(product.getName()+" was deleted");
					//Rewrite the inventory without the product
					writeNewInventory();
					errorMethot = true;
				}
			}
		}else {
			System.out.println("This product not exists");
			errorMethot = false;
		}
		return errorMethot;
	}
	
	
	/**
	 * add a product to inventory
	 * 
	 * @param product
	 */
	public void addProduct(Product product) {
		inventory.add(product);
	}
	
	
	/**
	 * check if inventory is full or not
	 */
	
	/*
	 * public boolean isInventoryFull() { 
	 * 		if (numberProducts == 10) {
	 *  		return true; 
	 * 		}else { 
	 * 			return false; 
	 * 		} 
	 * }
	 */

	/**
	 * find product by name
	 * 
	 * @param product name
	 */
	public Product findProduct(String name) {
		for (int i = 0; i < inventory.size(); i++) {
			// add IgnoreCase to not key sensitive [CORRECTION]
			if (inventory.get(i) != null && inventory.get(i).getName().equalsIgnoreCase(name)) {
				return inventory.get(i);
			}
		}
		return null;

	}

	public void initSession(){
		Scanner sc = new Scanner(System.in);
		boolean logged;
		System.out.println("Name User: ");
		String user = sc.next();
		do {
			
			System.out.println("ID User: ");
			int iduser = sc.nextInt();
			System.out.println("Password: ");
			String password = sc.next();
			
			Employee employee = new Employee(user, iduser);
			
			logged = employee.login(iduser, password);
			
		}while(logged == false);
	}
	
}