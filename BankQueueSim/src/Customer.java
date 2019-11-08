import java.util.ArrayList;

public class Customer {

	int id;
	int arrivalTime;
	int departureTime;
	ArrayList<Transaction> transactions = new ArrayList<Transaction>();
	int transactionTime;
	
	Customer(int custID, int arrTime) {
		id = custID;
		arrivalTime = arrTime;
	}
	
	public void addTransaction(Transaction t) {
		transactions.add(t);
	}
	
	public int getID() {
		return id;
	}
	
	public int getArrivalTime() {
		return arrivalTime;
	}
	
	public ArrayList<Transaction> getTransactions() {
		return transactions;
	}
	
	public int getTransactionTime() {
		return transactionTime;
	}
	
	public int getDepartureTime() {
		return departureTime;
	}
	
	public void setDepartureTime(int departure) {
		departureTime = departure;
	}
}
