package cs2420FinalProject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;


public class PredictionAlgorithm {
	

	private ExecutionQueue queue;
	private Account account;
	
	public PredictionAlgorithm(ExecutionQueue queue, Account account) {
		this.queue = queue;
		this.account = account;
	}
	
	private void onExecute(String side, StockData todayStock) {
		
		QueueNode node = new QueueNode(side, this.account, todayStock);
		this.account.onAddedToQueue(side);
		queue.add(node);
		
	}
	
	public void run(LocalDate date) {

		ArrayList<StockData> mvaSmallData = new ArrayList<>();
		ArrayList<StockData> mvaBigData = new ArrayList<>();
		StockData todayStock = null;
		
		for (StockData day : StockData.getStockData(this.account.symbol)) {
			mvaSmallData.add(day);
			mvaBigData.add(day);
			
			if (mvaSmallData.size() > this.account.mvaSmall) mvaSmallData.remove(0);
			if (mvaBigData.size() > this.account.mvaBig) mvaBigData.remove(0);
			
			if (day.getDate().compareTo(date) >= 1) {
				todayStock = day;
				break;
			}
		}
		
		double aveSmall = average(mvaSmallData);
		double aveBig = average(mvaBigData);
		
		// Core Above and Below methodology
		if (aveSmall > aveBig && !this.account.isInTrade()) {
			onExecute("BUY", todayStock);
			
		} else if (aveSmall < aveBig && this.account.isInTrade()) {
			onExecute("SELL", todayStock);
		}
	}

	public void finalDay(LocalDate finalDay) {
		System.out.println("Selling At End");

		if (!this.account.isInTrade()) {
			return;
		}

		for (StockData day : StockData.getStockData(this.account.symbol)) {
			if (day.getDate().compareTo(finalDay) == 1) {
				onExecute("SELL", day);
			}
		}
	}
	
	private double average(ArrayList<StockData> data) {
		double sum = 0.0;
		for (StockData day : data) {
			sum += day.getClose();
		}
		return sum / data.size();
		
	}
}