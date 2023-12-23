package edu.umb.cs681.hw07b;
import java.util.concurrent.locks.ReentrantLock;

public class RunnableCancellablePrimeFactorizer extends RunnablePrimeFactorizer {
    private boolean done = false;
    private final ReentrantLock lock = new ReentrantLock();

    public RunnableCancellablePrimeFactorizer(long dividend, long from, long to) {
        super(dividend, from, to);
    }

    public void setDone() {
        lock.lock();
        try {
            this.done = true;
        } finally {
            lock.unlock();
        }
    }

    public void generatePrimeFactors() {
        lock.lock();
        try {
            if (done) {
                this.factors.clear();
                return; 
            }
            super.generatePrimeFactors();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        long dividend = 111;
        System.out.println("Factorization of " + dividend);
        RunnableCancellablePrimeFactorizer rcp = new RunnableCancellablePrimeFactorizer(dividend, 2, (long) Math.sqrt(dividend));
        
        Thread thread = new Thread(rcp);
        System.out.println("getId: " + thread.getId() +  " from: " + rcp.getFrom() + " to: " + rcp.getTo());
        thread.start();

        try {
            Thread.sleep(1000);
            rcp.setDone();
            thread.join();
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println(rcp.getPrimeFactors());
    }
}
