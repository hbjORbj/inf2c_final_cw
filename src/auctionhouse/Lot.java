package auctionhouse;

import java.util.List;
import java.util.ArrayList;

public class Lot extends CatalogueEntry {
    
	private String nameOfSeller;
	private Buyer lastBidderOfLot;
	public Auctioneer auctioneerOfLot;
	private Money reservePrice;
	public Money lastBid;
	private List<Buyer> listOfInterestedBuyers;
	
	public Lot(int numberOfLot, String nameOfSeller, Money reservePrice, String descriptionOfLot, LotStatus statusOfLot) {
	    super(numberOfLot, descriptionOfLot, statusOfLot);
		listOfInterestedBuyers = new ArrayList<Buyer>();
	    this.nameOfSeller = nameOfSeller;
	    this.reservePrice = reservePrice;
	    lastBid = new Money("0");
	}
	
	public void setLastBidderOfLot(Buyer bidder) {
		lastBidderOfLot = bidder;
	}
	
	public void setLastBid(Money bid) {
		lastBid = bid;
	}
	
	public Buyer getLastBidderOfLot() {
	    return lastBidderOfLot;	
	}
	
	public Money getReservePrice() {
		return reservePrice;
	}
	
	public String getNameOfSeller() {
		return nameOfSeller;
	}
	
	public LotStatus getStatusOfLot() {
		return status;
	}
	
	public int getNumberOfLot() {
		return lotNumber;
	}
	
	public void setInterestedBuyer(Buyer b) {
		listOfInterestedBuyers.add(b);
	}
	
	public void setAuctioneer(Auctioneer a) {
		auctioneerOfLot = a;
	}
	
	public Auctioneer getAuctioneer() {
		return auctioneerOfLot;
	}
	
	public List<Buyer> getAllInterestedBuyers() {
		return listOfInterestedBuyers;
	}
	
	public void changeStatusToIn() {
		status = LotStatus.IN_AUCTION;
	}
	
	public void changeStatusToUn() {
		status = LotStatus.UNSOLD;
	}
	
	public void changeStatusToPending() {
		status = LotStatus.SOLD_PENDING_PAYMENT;
	}
	
	public void changeStatusToSo() {
		status = LotStatus.SOLD;
	}
	
	public Money getLastBid() {
		return lastBid;
	}
	
	
}
