package auctionhouse;

import java.util.List;
import java.util.ArrayList;

public class Seller {
	
	public String nameOfSeller;
	public String addressOfSeller;
	public String bankAccountNumber;
	public List<Lot> listOfSellingLots;
	
	public Seller(String name, String address, String account) {
		listOfSellingLots = new ArrayList<Lot>();
	    nameOfSeller = name;
	    addressOfSeller = address;
	    bankAccountNumber = account;
	}
	
	public String getName() {
		return nameOfSeller;
	}
	
	public String getAddress() {
		return addressOfSeller;
	}
	
	public String getBankAccountNumber() {
		return bankAccountNumber;
	}
	
	public void sellLot(int lotNumber, String description, Money reservePrice) {
	    	listOfSellingLots.add(new Lot(lotNumber, nameOfSeller, reservePrice, description, LotStatus.UNSOLD));	    	
	}
	
}
