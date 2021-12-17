package cs2420FinalProject;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class Main {

	public static void main(String[] args) {
		
		// Get accounts
		ArrayList<Account> accounts = Account.genAccounts();
		ArrayList<String> stocks = Account.getSymbols();
		StockData.generateData(stocks);
		
		// Initialize Queue
		ExecutionQueue queue = new ExecutionQueue();
		queue.run();

		String startDay = "2021-01-01";
		String endDay = "2021-12-01";

		// Make Prediction Everyday
		for (LocalDate date : getDateRange(startDay, endDay)) {
		
			// Run PredictionAlgorithm
			for (Account account : accounts) {

				PredictionAlgorithm algo = new PredictionAlgorithm(queue, account);

				if (date.compareTo(LocalDate.parse(endDay).minusDays(1)) != 1) {

					algo.run(date);

				} else {

					algo.finalDay(date);

				}
			}

			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	private static ArrayList<LocalDate> getDateRange(String startDate, String endDate) {
		LocalDate start = LocalDate.parse(startDate);

        LocalDate end = LocalDate.parse(endDate);
		System.out.println("Start: " + start + ", End: " + end);
        
        ArrayList<LocalDate> ret = new ArrayList<LocalDate>();
        LocalDate tmp = start;
        while(tmp.isBefore(end) || tmp.equals(end)) {
            ret.add(tmp);
            tmp = tmp.plusDays(1);
        }
        return ret;
	}
}
