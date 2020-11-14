package auctionhouse;

import java.util.List;
import java.util.ArrayList;

public class Buyer {
	
	public String nameOfBuyer;
	public String addressOfBuyer;
	public String bankAuthorisationNumber;
	public String bankAccountNumber;
	private List<Lot> listOfInterestedLots;
	
	public Buyer(String name, String address, String account, String code) {
		listOfInterestedLots = new ArrayList<Lot>();
		nameOfBuyer = name;
		addressOfBuyer = address;
		bankAccountNumber = account;
		bankAuthorisationNumber = code;
	}
	
	public String getName() {
		return nameOfBuyer;
	}
	
	public String getAddress() {
		return addressOfBuyer;
	}
	
	public String getBankAccountNumber() {
		return bankAccountNumber;
	}
	
	public String getBankAuthorisationNumber() {
		return bankAuthorisationNumber;
	}
	
	public void noteInterest(Lot lot) {
		listOfInterestedLots.add(lot);
	}

	
}
