package cs2420FinalProject;

import java.util.Arrays;
import java.util.List;
import java.lang.IllegalArgumentException;

/**
 * Basically a Wrapper around account
 * to connect the Queue to the Account class
 * 
 * @author bmcmullin
 *
 */
public class QueueNode {
	
	QueueNode next;
	private List<String> allowedSides = Arrays.asList("BUY", "SELL");
	private String side;
	private Account account;
	private StockData todayStock;
	
	public QueueNode(String side, Account account, StockData todayStock) {
		this.side = side.toUpperCase();
		this.account = account;
		this.todayStock = todayStock;
		
		if (!this.allowedSides.contains(this.side)) {
			throw new IllegalArgumentException("Side: " + this.side + ", Not Supported. AllowedSides: ['BUY', 'SELL]");
		}
	}
	
	public String getSide() { return this.side; }
	public Account getAccount() { return this.account; }
	public StockData getStockData() { return this.todayStock; }

	public void execute() {

		if (this.side == "BUY") {
			this.account.buy(todayStock.getClose());
		} else {
			this.account.sell(todayStock.getClose());
		}
	}

	@Override
	public String toString() {
		return "QueueNode{" +
				"side='" + side + '\'' +
				", account=" + account.getName() +
				", todayStock=" + todayStock.getDate() +
//				", next=" + next +
				'}';
	}
}