package cs2420FinalProject;

import java.util.ArrayList;
import java.util.Arrays;
import java.math.*;

public class Account {
	
	private static ArrayList<String> allSymbols = new ArrayList<>();
	private static ArrayList<Account> allAccounts = new ArrayList<>();

	public static ArrayList<Account> genAccounts() {
		
		// Temporary, Eventually load accounts from a CSV
		ArrayList<Account> accounts = new ArrayList<>();

		String[] sym = new String[]{"TSLA", "GOOG", "AMZN", "AAPL"};
		int[] sm = new int[]{12, 16, 20, 24, 32};
		int[] bg = new int[]{40, 64, 80, 120, 180};;
		for (String symbol : sym) {
			for (int big : bg) {
				for (int small : sm) {
//					accounts.add(new Account( "Patrick_"+symbol+"_"+small+"_"+big, 10000, symbol, small, big));
//					accounts.add(new Account(  "Andrew_"+symbol+"_"+small+"_"+big, 10000, symbol, small, big));
					accounts.add(new Account("Benjamin_"+symbol+"_"+small+"_"+big, 10000, symbol, small, big));
//					accounts.add(new Account( "Matthew_"+symbol+"_"+small+"_"+big, 10000, symbol, small, big));
				}
			}
		}

		// TODO: for testing
//		accounts.clear();
//		accounts.add(new Account("A", 10000, "TSLA", 12, 40));

		allAccounts = accounts;
		return accounts;
	}

	public static ArrayList<Account> getAccounts() {
		return allAccounts;
	}

	public static void saveAccounts(Account[] accounts) {
		 // Save accounts list to a CSV
 	}
	
	// makes a list of symbols from all of the possible accounts
	public static ArrayList<String> getSymbols() {
		return allSymbols;
	}

	public static void printSummary() {
		System.out.println("\n\nAccounts Summary:");
		for (Account a : Account.getAccounts()) { // TODO: getting accounts creates all new accounts. so make a static allAccounts variable
			System.out.println("\t"+a);
		}
	}

	public static void queueFinish() {
		Account.printSummary();
	}
	
	public String id;
	public int balance;
	public String symbol;
	public int mvaSmall = 0;
	public int mvaBig = 0;
	
	private boolean inTrade = false;
	private int lastShares = 0;
	private int lastBalance = 0;
	private int profit = 0;

	public Account(String id, int balance, String symbol, int mvaSmall, int mvaBig) {
		this.id = id;
		this.balance = balance;
		this.symbol = symbol;
		this.mvaSmall = mvaSmall;
		this.mvaBig = mvaBig;
		
		Account.allSymbols.add(symbol);
	}

	public void onAddedToQueue(String side) {
		this.setInTrade(side.toUpperCase() == "BUY");
	}
	
	// + buy()
	public void buy(double price) {
		lastBalance = balance;
		
		// 1100 / 200
		int sharesToBuy = (int) Math.floor(balance / price);

		// 100 = 1100 - 5 * 200
		balance -= sharesToBuy * price;
		
		lastShares = sharesToBuy;

		System.out.println("buy{" +
				"profit=" + profit +
				", balance=" + balance +
				", lastBalance=" + lastBalance +
				", lastShares=" + lastShares +
				"}");
	}

	// + sell()
	public void sell(double price) {
		
		//  1200  = 100 + 5 * 220
		balance += lastShares * price;
		profit += balance - lastBalance;
		
		lastShares = 0;
		lastBalance = 0;

		System.out.println("sell{" +
				"profit=" + profit +
				", balance=" + balance +
				", lastBalance=" + lastBalance +
				", lastShares=" + lastShares +
				"}");
	}

	public boolean isInTrade() {
		return inTrade;
	}

	public void setInTrade(boolean inTrade) {
		this.inTrade = inTrade;
	}
	
	public String getName() {
		return this.id;
	}

	@Override
	public String toString() {
		return "Account{" +
				"id='" + id + '\'' +
				", balance=" + balance +
				", profit=" + profit +
				", symbol='" + symbol + '\'' +
				", mvaSmall=" + mvaSmall +
				", mvaBig=" + mvaBig +
//				", inTrade=" + inTrade +
//				", lastShares=" + lastShares +
//				", lastBalance=" + lastBalance +
				'}';
	}
}