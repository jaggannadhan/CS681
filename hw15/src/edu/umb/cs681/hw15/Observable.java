package edu.umb.cs681.hw15;

import java.util.concurrent.locks.ReentrantLock;
import java.util.LinkedList;
import java.util.List;

public abstract class Observable<StockEvent> {
	
	private ReentrantLock lockObs = new ReentrantLock();
	LinkedList<Observer<StockEvent>> observers = new LinkedList<Observer<StockEvent>>();
	
	public void addObserver(Observer<StockEvent> o) {
		lockObs.lock();
		try {
			observers.add(o);
		} finally {
			lockObs.unlock();
		}
	}

	public void removeObserver(Observer<StockEvent> o) {
		lockObs.lock();
		try {
			observers.remove(o);
		} finally {
			lockObs.unlock();
		}
	}

	public int countObservers() {
		return observers.size();
		
	}

	public List<Observer<StockEvent>> getObservers(){
		return observers;
	}

	public void clearObservers() {
		lockObs.lock();
		try {
			observers.clear();
		} finally {
			lockObs.unlock();
		}
		
	}

	public void notifyObservers(StockEvent event) {
		LinkedList<Observer<StockEvent>> observersLocal = new LinkedList<Observer<StockEvent>>();
		lockObs.lock();
		for (Observer<StockEvent> observer : observers) {
			observersLocal.add(observer);
		}
		lockObs.unlock();
		observersLocal.forEach( (observer) -> { 
			observer.update( this, event); 
		});
	}	
}
