package com.solvd.metro.database;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ConnectionPool {
    private final BlockingQueue<Connection> pool;
    private final int maxSize;

    public ConnectionPool(int maxSize) {
        this.maxSize = maxSize;
        this.pool = new LinkedBlockingQueue<>(maxSize);

        for (int i = 1; i <= maxSize; i++) {
            pool.add(new Connection(i));
        }
    }

    public Connection acquireConnection() throws InterruptedException {
        return pool.take();
    }

    public void releaseConnection(Connection connection) {
        if (connection != null) {
            pool.offer(connection);
        }
    }
}
