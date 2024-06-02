// Amount.java
package model;

public class Amount {
	
	private double value;
	final static String CURRENCY = "€";
	
	public Amount(double value) {
		super();
		this.value = value;
	}
	
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}
	
	public static String getCurrency() {
		return CURRENCY;
	}

	@Override
	public String toString() {
		return value + CURRENCY;
	}
	
	
	
}
