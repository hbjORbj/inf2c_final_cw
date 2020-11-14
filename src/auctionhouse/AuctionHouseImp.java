/**
 * 
 */
package auctionhouse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;


/**
 * @author pbj
 *
 */
public class AuctionHouseImp implements AuctionHouse {

    private static Logger logger = Logger.getLogger("auctionhouse");
    private static final String LS = System.lineSeparator();
    
    private String startBanner(String messageName) {
        return  LS 
          + "-------------------------------------------------------------" + LS
          + "MESSAGE IN: " + messageName + LS
          + "-------------------------------------------------------------";
    }
    
    private List<Seller> listOfSellers = new ArrayList<Seller>();
    private TreeMap<Integer, CatalogueEntry> listOfCatalogues = new TreeMap<>();
    private List<Lot> listOfLots = new ArrayList<Lot>();
    private List<Buyer> listOfBuyers = new ArrayList<Buyer>();
    
    private Parameters p;
   
    public AuctionHouseImp(Parameters parameters) {
    		p = parameters;
    }

    public Status registerBuyer(
            String name,
            String address,
            String bankAccount,
            String bankAuthCode) {
        logger.fine(startBanner("registerBuyer " + name));
        
        
        for (Buyer buyer : listOfBuyers) { // this checks whether or not buyer is already registered
        		if (name == buyer.getName()) { // if buyer is already registered, return error
                	logger.warning(startBanner("This buyer has already been registered."));
                	return Status.error("This buyer has already been registered.");
        		}
        }
        
        	listOfBuyers.add(new Buyer(name, address, bankAccount, bankAuthCode)); // if buyer is not registered, add buyer to the list and return OK
        	return Status.OK();
        

        
    }

    public Status registerSeller(
            String name,
            String address,
            String bankAccount) {
        logger.fine(startBanner("registerSeller " + name));
                
        for (Seller seller : listOfSellers) { // this checks whether or not seller is already registered
        		if (name == seller.getName()) { // if seller is already registered, return error
        	        logger.warning(startBanner("This seller has already been registered."));
                	return Status.error("This seller has already been registered.");
        		}
        }
        
        listOfSellers.add(new Seller(name, address, bankAccount)); // if seller is not registered, add seller to the list and return OK
        	return Status.OK();
        
        
 
    }

    public Status addLot(
            String sellerName,
            int number,
            String description,
            Money reservePrice) {
        logger.fine(startBanner("addLot " + sellerName + " " + number));
        

        for (Seller seller : listOfSellers) { // this checks whether or not seller is registered
    		    if (sellerName == seller.getName()) { // if seller is registered, add lot to the list of lots and also to the list of catalogues
    		    	                                      // and return OK
    		        Lot newLot = new Lot(number, sellerName, reservePrice, description, LotStatus.UNSOLD);
    		        CatalogueEntry newCatalogue = new CatalogueEntry(number, description, LotStatus.UNSOLD);
    		        listOfLots.add(newLot);
    		        listOfCatalogues.put(number, newCatalogue);
    		        return Status.OK();
    		    }
        }
    
    		logger.warning(startBanner("This seller has not already been registered.")); // if seller is not registered, return error
    		return Status.error("This seller has not been registered."); 
        
        
        
    }

    public List<CatalogueEntry> viewCatalogue() {
        logger.fine(startBanner("viewCatalog"));
        
        List<CatalogueEntry> catalogue = new ArrayList<CatalogueEntry>(listOfCatalogues.values()); // the objects should be placed 
                                                                                                   // in increasing lot-number order
                                                                                                   // therefore, TreeMap<> is used
        logger.fine("Catalogue: " + catalogue.toString());
        
        return catalogue;
    }

    public Status noteInterest(
            String buyerName,
            int lotNumber) {
        logger.fine(startBanner("noteInterest " + buyerName + " " + lotNumber));
        
        for (Lot lot : listOfLots) { // this checks whether or not lot is registered
    		    if (lot.getNumberOfLot() == lotNumber) { // if lot is registered, check the status of the lot
    			    
            	    if (lot.getStatusOfLot() == LotStatus.IN_AUCTION || lot.getStatusOfLot() == LotStatus.UNSOLD) { // if the lot is in auction or unsold,
            	    	                                                                                                // check if buyer is registered
            	    	    for (Buyer buyer : listOfBuyers) {
            	    	    	    if (buyer.getName() == buyerName) { // if buyer is registered, interest is noted and return OK
            	    	    	    	    buyer.noteInterest(lot);
            	    	    	    	    lot.setInterestedBuyer(buyer);
            	    	    	    	    return Status.OK();
            	    	    	    }
            	    	    }
            	    	    
            	    	    logger.warning(startBanner("This buyer has not been registered.")); // if buyer is not registered, interest cannot be noted
            	    	                                                                        // and return error
                    	return Status.error("This buyer has not been registered."); 

                	}
                        
                else {
            		    logger.warning(startBanner("This lot has already been sold.")); // if the lot is sold or pending payment, interest cannot be noted
                	    return Status.error("This lot has already been sold.");     	 // and return error
                }
    		    }
        }
        
        logger.warning(startBanner("This lot has not been registered.")); // if lot is not registered, interest cannot be noted and return error
	    return Status.error("This lot has not been registered."); 


    }

    public Status openAuction(
            String auctioneerName,
            String auctioneerAddress,
            int lotNumber) {
        logger.fine(startBanner("openAuction " + auctioneerName + " " + lotNumber));
     
        Auctioneer thisAuctioneer = new Auctioneer(auctioneerName, auctioneerAddress);
        
        for (Lot lot : listOfLots) { 
		    if (lot.getNumberOfLot() == lotNumber) { // if lot is registered, check the status of the lot
			    Lot thisLot = lot;
		        
	        	    if (thisLot.getStatusOfLot() == LotStatus.SOLD) { // if lot is sold, auction cannot be opened and return error
	            		logger.warning(startBanner("This lot has already been sold."));
	        	    		return Status.error("This lot has already been sold.");
	        	    }
	        	    
	        	    else if (thisLot.getStatusOfLot() == LotStatus.IN_AUCTION) { // if lot is already in auction, auction cannot be opened and return error
	            		logger.warning(startBanner("This lot has already opened."));
	    	    		    return Status.error("This lot has already opened.");
	        	    }
	        	    
	        	    else if (thisLot.getStatusOfLot() == LotStatus.SOLD_PENDING_PAYMENT) { // if lot is sold and pending payment, auction cannot be opened and return error
	            		logger.warning(startBanner("This lot has already been sold and currently pending payment."));
	    	    		    return Status.error("This lot has already been sold and currently pending payment.");
	        	    }
	        	    
	            else if (thisLot.getStatusOfLot() == LotStatus.UNSOLD) { // if lot is not sold and not in auction, auction can be opened
	            		thisLot.setAuctioneer(thisAuctioneer);              // set the auctioneer to the lot
	            	    List<Buyer> interestedBuyers = thisLot.getAllInterestedBuyers(); // get the list of all interested buyers
	            	    
	            	    for (Seller seller : listOfSellers) { 
	            			if (seller.getName() == thisLot.getNameOfSeller()) { // find the seller of the lot
	            				Seller thisSeller = seller;
	            				String addressOfSeller = seller.getAddress();   // get the address of the seller
	            				thisLot.changeStatusToIn();            // change status of the lot to IN_AUCTION
	            				for (Buyer buyer : interestedBuyers) { // send a message to all interested buyers to notify them that the lot has opened
	    	            			    p.messagingService.auctionOpened(buyer.getAddress(), lotNumber);
	    	            		    }
	    	            		
	    	                    p.messagingService.auctionOpened(addressOfSeller, lotNumber); // send a message to the seller to notify that the lot has opened
	    	                                
	    	            	        return Status.OK(); // return OK
	            			}
	            	    }
	            	        
	            	    

	            		
			    }       
	        	    
	        	    
	        }
		    
        }
        

    		logger.warning(startBanner("This lot has not been registered.")); // if lot is not registered, auction cannot be opened and return error
        return Status.error("This lot has not been registered.");   
        
        
    }

    public Status makeBid(
            String buyerName,
            int lotNumber,
            Money bid) {
        logger.fine(startBanner("makeBid " + buyerName + " " + lotNumber + " " + bid));
                
        for (Lot lot : listOfLots) { 
		    if (lot.getNumberOfLot() == lotNumber) { // if lot is registered, check the status of lot
			    Lot thisLot = lot;
		        Money bidPrice = thisLot.getLastBid(); // get the current bid price
			    List<Buyer> interestedBuyers = thisLot.getAllInterestedBuyers(); // get all buyers who noted interest in lot
			    
			    if (thisLot.getStatusOfLot() == LotStatus.IN_AUCTION) { // if auction of lot has opened, check if buyer has noted interest in lot
			        for (Buyer buyer : listOfBuyers) { 
					    if (buyer.getName() == buyerName) { 
						    Buyer thisBuyer = buyer;
						    if (!interestedBuyers.contains(thisBuyer)) { // if buyer has not noted interest in lot, return error
			            		    logger.warning(startBanner("The buyer did not note interest in this lot."));
			    	    	            return Status.error("The buyer did not note interest in this lot.");
			        	        }
					    }
			        }
			                                       // if buyer has noted interest in lot, following codes operate
			        
	        	        if (bid.lessEqual(bidPrice)) { // if bid is less than current bid, return error
	            		    logger.warning(startBanner("This bid is less than the current bid price so this bid cannot be made."));
	        	    	        return Status.error("This bid is less than the current bid price so this bid cannot be made.");
	        	        }
	        	        
	            	    else { // if bid is higher than current bid, set the new bid price and new bidder
	            	    	    for (Buyer buyer : listOfBuyers) {
	            	    	    	    if (buyer.getName() == buyerName) {
	            	    	    	    	    Buyer thisBuyer = buyer;
	            	    	    	    	    thisLot.setLastBid(bid); // set the new bid
	            	    	    	    	    thisLot.setLastBidderOfLot(thisBuyer); // set buyer to new bidder
	            	    	    	    }
	            	    	    }
	        	    	        
	            	    	    for (Seller seller : listOfSellers) { 
	            	    			if (seller.getName() == thisLot.getNameOfSeller()) {
	            	    				Seller thisSeller = seller;
	            	    			    String addressOfSeller = thisSeller.getAddress(); // get the seller's address
	    	        	    	            p.messagingService.bidAccepted(addressOfSeller, lotNumber, bid); // notify seller that a new bid is made
	    	        	    	            p.messagingService.bidAccepted(thisLot.getAuctioneer().getAddress(), lotNumber, bid); // notify auctioneer that a new bid is made
	            	    			}
	            	    	    }

	        	    	        for (Buyer buyer : interestedBuyers) {
	        	    	    	        if (buyer.getName() != buyerName) {
	        	    	    	            p.messagingService.bidAccepted(buyer.getAddress(), lotNumber, bid); // notify all interested buyers except for the buyer who just made bid
	        	    	    	                                                                                // that a new bid is made
	        	    	    	        }
	        	    	        }
	        	    	    
	        	    	        return Status.OK(); // return OK
	        	        }
			        
			        
			    }
			    
		        else if (thisLot.getStatusOfLot() == LotStatus.UNSOLD) { // if auction of lot has not opened, return error
	    		        logger.warning(startBanner("The auction of this lot has not opened yet."));
	        	        return Status.error("The auction of this lot has not opened yet.");
	            }
	        
	            else if (thisLot.getStatusOfLot() == LotStatus.SOLD) { // if lot is already sold, return error
			        logger.warning(startBanner("This lot has already been sold."));
	                return Status.error("This lot has already been sold.");
	            }
	        
	            else if (thisLot.getStatusOfLot() == LotStatus.SOLD_PENDING_PAYMENT) { // if lot is already sold and pending payment, return error
			        logger.warning(startBanner("This lot has already been sold and pending payment."));
	        	        return Status.error("This lot has already been sold and pending payment.");
	            }


		    }
        }
        
        logger.warning(startBanner("This lot has never been registered.")); // if lot has never been registered, return error
        return Status.error("This lot has never been registered.");
        


    }

    public Status closeAuction(
            String auctioneerName,
            int lotNumber) {
        logger.fine(startBanner("closeAuction " + auctioneerName + " " + lotNumber));
        
        Status sale = new Status(Status.Kind.SALE);
        Status noSale = new Status(Status.Kind.NO_SALE);
        Status pending = new Status(Status.Kind.SALE_PENDING_PAYMENT);
                
        for (Lot lot : listOfLots) { 
		    if (lot.getNumberOfLot() == lotNumber) { // if lot is registered, check the status of lot
			    Lot thisLot = lot;
			    if (thisLot.getStatusOfLot() == LotStatus.IN_AUCTION) { // if lot is in auction, check if name of auctioneer trying to close auction matches
			    	                                                        // name of auctioneer who opened auction
			    	
			    	    if (thisLot.getAuctioneer().getName() == auctioneerName) { // if name of auctioneer trying to close auction matches
			    	    	                                                           // name of auctioneer who opened auction, close auction
		                Money hammerPrice = thisLot.getLastBid();              // get hammer price
	    	                List<Buyer> interestedBuyers = thisLot.getAllInterestedBuyers(); // get all buyers who noted interest in lot
	        	            for (Seller seller : listOfSellers) { 
	        			        if (seller.getName() == thisLot.getNameOfSeller()) {
	        				        Seller thisSeller = seller;
	        	    	                String addressOfSeller = thisSeller.getAddress(); // get address of seller
	        		            
	        	    	                if (hammerPrice.lessEqual(thisLot.getReservePrice())) { // if hammer price is less than reserve price,
	        	                	        thisLot.changeStatusToUn();                        // change status of lot from IN_AUCTION to UNSOLD
	        	                	    
	        	                    for (Buyer buyer : interestedBuyers) { // send a message to all interested buyers to notify them that the lot has not been sold.
	        	                		    p.messagingService.lotUnsold(buyer.getAddress(), lotNumber);
	        	                	    }
	        	                		
	        	                    p.messagingService.lotUnsold(addressOfSeller, lotNumber); // send a message to the seller to notify that the lot has not been sold.
	        	                    
	        	                    return noSale;
	        	                
	        	                    }
	        	    	            
	        	    	                else if (thisLot.getReservePrice().lessEqual(hammerPrice)) { // if hammer price is higher than reserve price
	        	                	        String bankAccountNumberOfBuyer = thisLot.getLastBidderOfLot().getBankAccountNumber();
	        	                	        String bankAccountNumberOfSeller = thisSeller.getBankAccountNumber();
	        	                	        Money moneyForBuyer = thisLot.getLastBid().addPercent(p.buyerPremium);
	        	                	        Money moneyForSeller = thisLot.getLastBid().addPercent(-p.commission);
	        	                	        String bankAuthNumberOfBuyer = thisLot.getLastBidderOfLot().getBankAuthorisationNumber();
	        	                	        String houseAccount = p.houseBankAccount;
	        	                	        String houseAuth = p.houseBankAuthCode;
	        	                	        Status transactionFromB = p.bankingService.transfer(bankAccountNumberOfBuyer, bankAuthNumberOfBuyer, houseAccount, moneyForBuyer);
	        	                	        Status transactionFromH = p.bankingService.transfer(houseAccount, houseAuth, bankAccountNumberOfSeller, moneyForSeller);
	        	                	    
	        	                	        if (transactionFromB.kind == Status.Kind.OK && transactionFromH.kind == Status.Kind.OK) { // if both transactions go well,
	        	                	    	        thisLot.changeStatusToSo();                                                           // change status of lot from IN_AUCTION to SOLD
	        	                	    	    
	        	                	    	        for (Buyer buyer : interestedBuyers) { // send a message to all interested buyers to notify them that the lot has been sold.
	        	                        			p.messagingService.lotSold(buyer.getAddress(), lotNumber);
	        	                        		}
	        	                        		
	        	                             p.messagingService.lotSold(addressOfSeller, lotNumber); // send a message to the seller to notify that the lot has been sold.
	        	                            
	        	                	    	        return sale;
	        	                	        }
	        	                	    
	        	                	        else { // if one or both of transactions do not go well, change status of lot from IN_AUCTION to SOLD_PENDING_PAYMENT
	        	                	    	        thisLot.changeStatusToPending();
	        	                	    	        return pending;
	        	                	        }
	        	                	    

	        	                    }
	        			        }
	        	            }
	        	        

	        	        
	        	        
	        	        
			    } 
			    }
			    
			    else if (thisLot.getStatusOfLot() == LotStatus.SOLD) { // if lot is already sold, return error
			    	    logger.warning(startBanner("The lot has already been sold")); 
			    	    return Status.error("The lot has already been sold");
			    }
			    
			    else if (thisLot.getStatusOfLot() == LotStatus.UNSOLD) { // if auction of lot has not opened, return error
			    	    logger.warning(startBanner("The auction of lot has never opened"));
		    	        return Status.error("The auction of lot has never opened");
			    }
			    
			    else if (thisLot.getStatusOfLot() == LotStatus.SOLD_PENDING_PAYMENT) {
			        logger.warning(startBanner("The lot has already been sold and pending payment")); // if lot is already sold and pending payment, return error
		    	        return Status.error("The lot has already been sold and pending payment");
			    }
			    
			    
		    }
        }
        
        logger.warning(startBanner("This lot has never been registered.")); // if lot has never been registered, return error
        	return Status.error("This lot has never been registered.");
        
        
    }
}
