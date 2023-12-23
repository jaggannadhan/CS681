package edu.umb.cs681.hw14;

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
		lockObs.lock();
		try {
			observers.forEach( (observer) -> { 
				observer.update( this, event); 
			});
		} finally {
			lockObs.unlock();
		}
	}	
}
