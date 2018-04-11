package consumer.producer;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class QueueWrapper {
private final Queue<String> queue=new ConcurrentLinkedQueue<>();

public  void add(String item) {
	synchronized(this) {
	System.out.println("Lock acquired in add");
	queue.add(item);
	}
	System.out.println("Lock released in add");
}

public synchronized String get( ) {

	return queue.poll();
}

public synchronized int getSize() {

	return queue.size();

}

}
