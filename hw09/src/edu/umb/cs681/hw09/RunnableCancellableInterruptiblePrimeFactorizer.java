package edu.umb.cs681.hw09;

import java.util.concurrent.locks.ReentrantLock;

public class RunnableCancellableInterruptiblePrimeFactorizer extends RunnableCancellablePrimeFactorizer {
	
	private boolean done = false;
	private boolean shouldEndThread = false;
	private final ReentrantLock lock = new ReentrantLock();
	
	public RunnableCancellableInterruptiblePrimeFactorizer(long dividend, long from, long to) {
		super(dividend, from, to);
	}

	public void endThread() {
        shouldEndThread = true;
    }

	public void generatePrimeFactors(){
		lock.lock();
		try {
			while(!shouldEndThread) {
				if (done) {
					this.factors.clear();
					return; 
				}
				super.generatePrimeFactors();
			} 
		} finally {
			lock.unlock();
		}
	}

	public static void main(String[] args) {
		long dividend = 123;
		RunnableCancellableInterruptiblePrimeFactorizer rcipf = new RunnableCancellableInterruptiblePrimeFactorizer(
			dividend, 2, (long) Math.sqrt(dividend)
		);
		Thread thread = new Thread(rcipf);
		thread.start();
		
		try {
			Thread.sleep(1500); 
            rcipf.endThread();
            thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		rcipf.getPrimeFactors().forEach( (Long prime) -> System.out.print(prime + ", ") );
		System.out.println("\n" + rcipf.getPrimeFactors().size() + " prime numbers are found.");
	}
}
