package consumer.producer.blockingqueue;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BlockingQueueWrapper {

	private final BlockingQueue<String> queue = new ArrayBlockingQueue<String>(10);
	private int COUNTER = 1;

	public void add() {
		String item = null;
		item = "Item " + COUNTER;
		COUNTER++;
		queue.offer(item);
		System.out.println("Added item " + item);

	}

	public String get() throws InterruptedException {
		String value = null;
		value = queue.take();
		System.out.println("Consuming " + value);
		return value;

	}

	public int getSize() {

		return queue.size();

	}
}
