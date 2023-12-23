package edu.umb.cs681.hw17;

import java.util.concurrent.ConcurrentLinkedQueue;
public abstract class Observable<StockEvent> {
	
	ConcurrentLinkedQueue<Observer<StockEvent>> observers = new ConcurrentLinkedQueue<Observer<StockEvent>>();
	
	public void addObserver(Observer<StockEvent> o) {
		observers.add(o);
	}

	public void removeObserver(Observer<StockEvent> o) {
		observers.remove(o);
	}

	public int countObservers() {
		return observers.size();
		
	}

	public ConcurrentLinkedQueue<Observer<StockEvent>> getObservers(){
		return observers;
	}

	public void clearObservers() {
		observers.clear();
	}

	public void notifyObservers(StockEvent event) {
		observers.forEach( (observer) -> { 
			observer.update( this, event); 
		});
	}	
}
