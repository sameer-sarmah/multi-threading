package consumer.producer;

public class Consumer implements Runnable{

	private QueueWrapper queue;
	public Consumer(QueueWrapper queue) {
		this.queue=queue;
	}
	@Override
	public void run() {
		
		try {
			while(true) {
			consume();
			Thread.sleep(600);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private  void consume() throws InterruptedException {
		synchronized (queue) {
			System.out.println("Lock acquired in consumer");
			while(queue.getSize()==0) {
				queue.wait(0);
			}
			System.out.println("Consuming "+queue.get());
			queue.notifyAll();
		}
		System.out.println("Lock released in consumer");
	}
	
	
}
