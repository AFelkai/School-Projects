//Aaron Felkai
//Project 5
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;

public class Bank {

	Deque<Customer> customers = new ArrayDeque<Customer>();
	ArrayList<Transaction> transactions = new ArrayList<Transaction>();
	final String customerData = "D:" + File.separator + "Documents" + File.separator + "Customers.csv";
	final String transactionData = "D:" + File.separator + "Documents" + File.separator + "Transactions.csv";

	public ArrayList<String> readData(String csvLocation){

		ArrayList<String> data = new ArrayList<String>();
		String csvFile = csvLocation;
		BufferedReader br = null;
		String line = "";
		String csvSplitBy = ",";
		int row = 0;

		try {

			br = new BufferedReader(new FileReader(csvFile));
			while((line = br.readLine()) != null) {

				String[] column = line.split(csvSplitBy);

				if(row > 0) {

					for(int i = 0; i < column.length; i++) {
						data.add(column[i]);
					}
				}
				row++;
			}
		}
		catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		finally {

			if(br != null) {
				try {
					br.close();
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return data;
	}

	public Deque<Customer> makeCustomerObjects(ArrayList<String> data) {
		Deque<Customer> customerList = new ArrayDeque<Customer>();

		for(int i = 0; i < data.size(); i += 4) {

			if(!customerList.isEmpty() && Integer.parseInt(data.get(i+1)) == customerList.getLast().getID()) {
				for(int j = 0; j < transactions.size(); j++) {
					if(data.get(i+3).contains(transactions.get(j).getType())) customerList.getLast().addTransaction(transactions.get(j));
				}
			}
			else {
				customerList.add(new Customer(Integer.parseInt(data.get(i+1)), Integer.parseInt(data.get(i+2))));
				for(int j = 0; j < transactions.size(); j++) {
					if(data.get(i+3).contains(transactions.get(j).getType())) customerList.getLast().addTransaction(transactions.get(j));
				}
			}
		}
		return customerList;
	}

	public ArrayList<Transaction> makeTransactionObjects(ArrayList<String> data) {
		ArrayList<Transaction> transactionList = new ArrayList<Transaction>();

		for(int i = 0; i < data.size(); i += 3) {
			transactionList.add(new Transaction(Integer.parseInt(data.get(i)), data.get(i+1), Integer.parseInt(data.get(i+2))));
		}

		return transactionList;
	}

	public String serveCustomers(ArrayList<Transaction> transactions, Deque<Customer> customers) {
		String output = "Customer|Arrived|Waiting Time|At Teller|Transaction Time|Departed\n";
		Deque<Customer> customers2 = new ArrayDeque<Customer>();
		int time = 1;
		int numCustomers = 0;
		int averageWait = 0;
		while(!customers.isEmpty()) {
			numCustomers++;
			int customerNum = customers.getFirst().getID();
			int arrived = customers.getFirst().getArrivalTime();
			int transactionTime = 0;
			for(int j = 0; j < customers.getFirst().getTransactions().size(); j++)
				transactionTime += customers.getFirst().getTransactions().get(j).getDuration();
			int waitingTime = time - arrived;
			if(waitingTime < 0) waitingTime = 0;
			int atTeller = arrived + waitingTime;
			int departed = arrived + transactionTime + waitingTime;
			customers.getFirst().setDepartureTime(departed);
			customers2.add(customers.getFirst());

			averageWait += waitingTime;
			time = departed;

			output += customerNum + " " + arrived + " " + waitingTime + " " + atTeller + " " + transactionTime + " " + departed + "\n";

			customers.remove();
		}

		output += "\nArrival/Departure Events:";
		time = 1;
		output += "\nCustomer " + customers2.getFirst().getID() + " Arrived at " + customers2.getFirst().getArrivalTime();
		customers.add(customers2.remove());
		while(!customers2.isEmpty()) {
			if(customers2.getFirst().getArrivalTime() < customers.getFirst().getDepartureTime()) {
				output += "\nCustomer " + customers2.getFirst().getID() + " Arrived at " + customers2.getFirst().getArrivalTime();
				customers.add(customers2.remove());
			}
			else {
				output += "\nCustomer " + customers.getFirst().getID() + " Departed at " + customers.getFirst().getDepartureTime();
				customers.remove();
			}
		}
		while(!customers.isEmpty()) {
			if(customers2.isEmpty()) {
				output += "\nCustomer " + customers.getFirst().getID() + " Departed at " + customers.getFirst().getDepartureTime();
				customers.remove();
			}
		}

		averageWait /= numCustomers;
		output += "\n\nNumber of Customers: " + numCustomers + "\nAverage Wait Time: " + averageWait;
		return output;
	}

	public static void main(String[] args) {
		Bank chase = new Bank();

		ArrayList<String> customerD = chase.readData(chase.customerData);
		ArrayList<String> transactionD = chase.readData(chase.transactionData);

		chase.transactions = chase.makeTransactionObjects(transactionD);
		chase.customers = chase.makeCustomerObjects(customerD);


		System.out.println(chase.serveCustomers(chase.transactions, chase.customers));


	}

}