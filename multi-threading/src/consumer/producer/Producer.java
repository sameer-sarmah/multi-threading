package consumer.producer;

public class Producer implements Runnable{
	private QueueWrapper queue;
	private final int MAX_SIZE=10;
	private  int COUNTER=1;
	public Producer(QueueWrapper queue) {
		this.queue=queue;
	}
	@Override
	public void run() {
		
		try {
			while(true) {
			produce();
			Thread.sleep(300);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private void produce() throws InterruptedException {
		synchronized (queue) {
			System.out.println("Lock acquired in producer");
			while(queue.getSize()==MAX_SIZE) {
				queue.wait(0);
			}
			String item = "Item "+COUNTER;
			COUNTER++;
			System.out.println("Added item "+item);
			queue.add(item);
			queue.notifyAll();
		}
		System.out.println("Lock released in producer");
	}
}
