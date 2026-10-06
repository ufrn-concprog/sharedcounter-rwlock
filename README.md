# Shared Counter with Read-Write Lock in Java

This code demonstrates concurrent access control to a shared resource using Java's [`ReentrantReadWriteLock` class](https://docs.oracle.com/en/java/javase/26/docs/api/java.base/java/util/concurrent/locks/ReentrantReadWriteLock.html), allowing concurrent read operations while ensuring exclusive write operations.

This project is part of the Concurrent Programming module at the [Federal University of Rio Grande do Norte (UFRN)](https://www.ufrn.br/), Natal, Brazil.

## 📃 Description

This project demonstrates the classic Readers-Writers synchronization problem in Java. It implements a thread-safe shared counter (`SharedCounter`) whose access is managed using a `ReentrantReadWriteLock`:

- **Shared read lock**: Multiple reader threads can hold the read lock simultaneously, allowing concurrent read operations without blocking each other.
- **Exclusive write lock**: When a writer thread updates the counter, it acquires an exclusive write lock, blocking all other readers and writers until the update completes.

The main program simulates concurrent execution in two phases: first launching multiple reader threads to demonstrate simultaneous reading, followed by a mix of reader and writer threads to demonstrate mutual exclusion during writes.

## 📂 Repository Structure

```text
.
└── doc                     # Javadoc documentation
├── README.md
└── src
    ├── Main.java           # Entry point demonstrating concurrent reading and exclusive writing behavior
    └── SharedCounter.java  # Implements the thread-safe counter backed by ReentrantReadWriteLock
```

## 🚀 Getting Started

### Requirements

- Java Development Kit (JDK) 8 or newer
- A terminal or IDE

The program uses Java's standard library, so it requires no additional dependencies.

## ▶️ Running

When running the application, multiple readers execute concurrently (overlapping "reading" and "finished reading" messages), whereas writers execute exclusively:

```text
Reader-1 reading (value = 0)
Reader-3 reading (value = 0)
Reader-2 reading (value = 0)
Reader-2 finished reading
Reader-3 finished reading
Reader-1 finished reading

Reader-4 reading (value = 0)
Reader-5 reading (value = 0)
Reader-4 finished reading
Reader-5 finished reading
Writer-2 writing (value -> 14)
Writer-2 finished writing
Writer-1 writing (value -> 42)
Writer-1 finished writing
```

⚠️ *Note:* The exact interleaving and order of threads may vary across executions due to thread scheduling.
