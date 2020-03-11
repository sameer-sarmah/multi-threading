package consumer.producer.blockingqueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;

public class BlockingQueueWrapper {

	private BlockingQueue<String> queue;
	private int COUNTER = 1;
    private boolean isSynchronousQueue=false;
    private int capacity;

	public BlockingQueueWrapper(boolean isSynchronousQueue,int capacity) {
		super();
		this.isSynchronousQueue = isSynchronousQueue;
		this.capacity=capacity;
		if(isSynchronousQueue) {
			queue=new SynchronousQueue<>();
		}
		else {
			queue = new ArrayBlockingQueue<String>(capacity);
		}
	}

	public void add() {
		String item = null;
		item = "Item " + COUNTER;
		COUNTER++;
		try {
			queue.put(item);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
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
