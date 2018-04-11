package consumer.producer.condition.lock;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionLockWrapper {
	private final Lock lock = new ReentrantLock();
	private final Condition readyForReadCond = lock.newCondition(); 
	private final Condition readyToAddCond = lock.newCondition();
	private final Queue<String> queue=new ConcurrentLinkedQueue<>();
	private final int MAX_SIZE=10;
	private  int COUNTER=1;
	
	public  void add() throws InterruptedException {
		String item =null;
		lock.lock();
		try
		{
			System.out.println("Lock acquired in producer");
			while(this.getSize()==MAX_SIZE) {
				readyToAddCond.await();
			}
			 item = "Item "+COUNTER;
			COUNTER++;
			System.out.println("Added item "+item);
			queue.add(item);
			readyForReadCond.signalAll();
		}
		finally {
			System.out.println("Lock released in producer");
			lock.unlock();
		}

		}

	public  String get( ) throws InterruptedException {
        String value=null;
		lock.lock();
		try{
			System.out.println("Lock acquired in consumer");
			while(this.getSize()==0) {
				readyForReadCond.await();
			}
			value=queue.poll();
			System.out.println("Consuming "+value);
			readyToAddCond.signalAll();
		}
		finally {
			System.out.println("Lock released in consumer");
			lock.unlock();
		}
		return value;
		
	}

	public synchronized int getSize() {

		return queue.size();

	}
}
