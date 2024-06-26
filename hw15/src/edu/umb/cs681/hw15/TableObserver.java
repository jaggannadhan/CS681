package edu.umb.cs681.hw15;


public class TableObserver implements Observer<StockEvent> {
	String ticker;
	double quote;

	public String getTicker() {
		return ticker;
	}
	public double getQuote() {
		return quote;
	}
	public void update(Observable<StockEvent> sender, StockEvent event) {
		System.out.println("Observers: " + sender.getObservers() + "\nStock Quote is; "+ event.quote() + "\n Ticker is: " + event.ticker());
	}

}