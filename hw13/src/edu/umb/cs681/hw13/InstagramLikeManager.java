package edu.umb.cs681.hw13;
 
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class InstagramLikeManager {
    private int likeCount;
    private final Lock lock = new ReentrantLock();

    public void likePost() {
        lock.lock();
        try {
            likeCount++; // Increment the likeCount within the critical section
        } finally {
            lock.unlock();
        }
    }

    public int getLikeCount() {
        return likeCount;
    }

    public static void main(String[] args) {
        final int NUM_THREADS = 10;
        final int LIKES_PER_THREAD = 100;

        InstagramLikeManager likeManager = new InstagramLikeManager();
        ExecutorService executor = Executors.newFixedThreadPool(NUM_THREADS);

        for (int i = 0; i < NUM_THREADS; i++) {
            executor.execute(() -> {
                for (int j = 0; j < LIKES_PER_THREAD; j++) {
                    likeManager.likePost();
                }
            });
        }

        executor.shutdown();
        while (!executor.isTerminated()) {
            // Waiting for all threads to complete
        }

        // Get total likes after all threads complete
        int totalLikes = likeManager.getLikeCount();
        System.out.println("Total Likes: " + totalLikes);
    }
}
