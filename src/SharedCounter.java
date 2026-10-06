import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * A thread-safe counter backed by a {@link ReentrantReadWriteLock}.
 * <p>
 * Allows multiple reader threads to read the counter's value concurrently
 * while ensuring that writer threads have exclusive access during updates.
 */
public class SharedCounter {
    /**
     * The current counter's value.
     */
    private int value;

    /**
     * The read-write lock used to control concurrent access.
     */
    private final ReentrantReadWriteLock rwLock;

    /**
     * Constructs a new {@code SharedCounter} initialized with a default value of 0.
     */
    public SharedCounter() {
        value = 0;
        rwLock = new ReentrantReadWriteLock();
    }

    /**
     * Reads the current counter's value under a shared read lock.<br>
     * Multiple threads can execute this method simultaneously.
     */
    public void read() {
        rwLock.readLock().lock();
        try {
            System.out.printf("%s reading (value = %d)\n",
                    Thread.currentThread().getName(), value);
            Thread.sleep(500);      // simulate work, long enough to make overlap visible
            System.out.printf("%s finished reading\n", Thread.currentThread().getName());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            rwLock.readLock().unlock();
        }
    }

    /**
     * Updates the counter's value under an exclusive write lock.<br>
     * Only one thread can execute this method at a time, and no readers
     * can access the counter while a writing is in progress.
     *
     * @param newValue the new value to assign to the counter
     */
    public void write(int newValue) {
        rwLock.writeLock().lock();
        try {
            System.out.printf("%s writing (value -> %d)\n",
                    Thread.currentThread().getName(), newValue);
            Thread.sleep(500); // simulate work
            value = newValue;
            System.out.printf("%s finished writing\n", Thread.currentThread().getName());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            rwLock.writeLock().unlock();
        }
    }
}
