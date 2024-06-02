// Client.java
package model;

public class Client extends Person {

	private int memberld;
	private Amount balance;
	final static int MEMBER_ID = 456;
    final static double BALANCE = 50.00;
	
	public Client(String name) {
		super(name);
		this.balance = new Amount(BALANCE);
	}

	public int getMemberld() {
		return memberld;
	}

	public void setMemberld(int memberld) {
		this.memberld = memberld;
	}

	public Amount getBalance() {
		return balance;
	}

	public void setBalance(Amount balance) {
		this.balance = balance;
	}

	public boolean pay(Amount saleAmount) {

		balance.setValue(balance.getValue() - saleAmount.getValue());
		if(balance.getValue() <= 0) {
			System.out.println("Balance necesary to pay: "+balance);
			return false;
		}else {
			System.out.println("Sale was paid, Cliend balance: "+balance);
			return true;
		}
	}
	
}
