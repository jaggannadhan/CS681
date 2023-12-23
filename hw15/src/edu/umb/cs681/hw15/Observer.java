package edu.umb.cs681.hw15;

public interface Observer<StockEvent> {
	public void update(Observable<StockEvent> observable, StockEvent event);
}
