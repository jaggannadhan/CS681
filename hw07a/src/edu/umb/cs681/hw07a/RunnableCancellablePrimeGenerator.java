package edu.umb.cs681.hw07a;
import java.util.concurrent.locks.ReentrantLock;

public class RunnableCancellablePrimeGenerator extends RunnablePrimeGenerator {
	private boolean done = false;
	private ReentrantLock lock;

	public RunnableCancellablePrimeGenerator(long from, long to) {
		super(from, to);
		lock = new ReentrantLock();
	}
	
	public void setDone() {
		lock.lock();
        try {
            this.done = true;
        } finally {
            lock.unlock();
        }
	}

	@Override
    public void generatePrimes() {
        lock.lock();
        try {
            if (done) {
                this.primes.clear();
                return; 
            }
            super.generatePrimes(); 
        } finally {
            lock.unlock();
        }
    }

}
