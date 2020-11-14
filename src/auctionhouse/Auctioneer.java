package auctionhouse;

public class Auctioneer {
	
	public String nameOfAuctioneer;
	public String addressOfAuctioneer;
	
	public String getName() {
		return nameOfAuctioneer;
	}
	
	public String getAddress() {
		return addressOfAuctioneer;
	}
	
	public Auctioneer(String name, String address) {
		nameOfAuctioneer = name;
		addressOfAuctioneer = address;
	}
}
