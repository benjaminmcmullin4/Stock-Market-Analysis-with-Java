package cs2420FinalProject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;


public class StockData {
	
	public static HashMap<String, ArrayList<StockData>> stockData = new HashMap();

	public static void generateData(ArrayList<String> stocks) {

		// Get Data
		for (String stock : stocks)
			try {
				stockData.put(stock, genData(stock));
			} catch (IOException e) {
				e.printStackTrace();
			}
		
	}
	

	/**
	 * Gets Data from Internet(API) or CSVs
	 * 
	 * @param stock
	 * @return
	 * @throws IOException 
	 */
	public static ArrayList<StockData> genData(String stock) throws IOException {
		
		ArrayList<StockData> data = new ArrayList<StockData>();
		
		
		String dataFile = stock.toUpperCase() + ".csv";
        BufferedReader reader = new BufferedReader(new FileReader(dataFile));
        
        String line = reader.readLine();
        while ((line = reader.readLine()) != null) {
        	
        	String[] lineData = line.split(",");
        	LocalDate date = LocalDate.parse(lineData[0]);
        	double close = Double.parseDouble(lineData[4]);
        	
        	data.add(new StockData(date, close));	
        }
        reader.close();		
		return data;
	}
	
	public static ArrayList<StockData> getStockData(String stock) {
		return stockData.get(stock);
	}

	private LocalDate date;
	private double close;

	public StockData(LocalDate date, double close) {
		this.setDate(date);
		this.setClose(close);
	}


	public LocalDate getDate() {
		return date;
	}


	public void setDate(LocalDate date) {
		this.date = date;
	}


	public double getClose() {
		return close;
	}


	public void setClose(double close) {
		this.close = close;
	}
	
	

}
