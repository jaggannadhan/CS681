package edu.umb.cs681.hw15;

import java.util.concurrent.locks.ReentrantLock;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class StockQuoteObservable extends Observable<StockEvent> {
	Map<String, Double> mapTQ = new HashMap<String, Double>();
	private ReentrantLock lockTQ = new ReentrantLock();

	public void changeQuote(String t, double q) {
		
		lockTQ.lock();
		try {
			mapTQ.put(t, q);
		} finally {
			lockTQ.unlock();
			notifyObservers(new StockEvent(t, q));
		} 
	}

	public static void main(String[] args) {
		Thread[] threads = new Thread[12];
		TableObserver TObv = new TableObserver();
		StockQuoteObservable SQObv = new StockQuoteObservable();

		SQObv.addObserver(TObv);

		for(int i = 0; i < threads.length ; i++ ) {
			int j = i;
			Thread thread = new Thread(
				() -> {
					SQObv.changeQuote("ticker" + j, (double)j);
				}
			);
			System.out.println("Running thread#" + j);
			threads[i] = thread;
			thread.start();
		}


        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
