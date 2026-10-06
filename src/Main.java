/**
 * Demonstrates the behavior of {@link SharedCounter} under concurrent access.<br>
 * Illustrates how multiple reader threads can execute concurrently without blocking
 * each other, while writer threads require exclusive access, blocking other readers
 * and writers.
 */
public class Main {
    /**
     * Program entry point.<br>
     * First launches multiple reader threads concurrently to demonstrate overlapping execution.
     * Then launches a mix of writer and reader threads to demonstrate exclusive write locking.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        SharedCounter counter = new SharedCounter();

        // Three readers started together: expect their "started"/"finished"
        // messages to overlap, since multiple readers may hold the read lock
        // simultaneously.
        Thread reader1 = new Thread(() -> counter.read(), "Reader-1");
        Thread reader2 = new Thread(() -> counter.read(), "Reader-2");
        Thread reader3 = new Thread(() -> counter.read(), "Reader-3");

        reader1.start();
        reader2.start();
        reader3.start();

        try {
            reader1.join();
            reader2.join();
            reader3.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println();

        // Two writers and two more readers started together: expect the writers
        // "started"/"finished" messages to never overlap with any reader's,
        // since the write lock is exclusive.
        Thread writer1 = new Thread(() -> counter.write(42), "Writer-1");
        Thread writer2 = new Thread(() -> counter.write(14), "Writer-2");
        Thread reader4 = new Thread(counter::read, "Reader-4");
        Thread reader5 = new Thread(counter::read, "Reader-5");

        writer1.start();
        writer2.start();
        reader4.start();
        reader5.start();

        try {
            writer1.join();
            writer2.join();
            reader4.join();
            reader5.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}