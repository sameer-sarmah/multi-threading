package consumer.producer.blockingqueue;

public class Consumer implements Runnable{

	private BlockingQueueWrapper blockingQueue;
	public Consumer(BlockingQueueWrapper blockingQueue) {
		this.blockingQueue=blockingQueue;
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
		blockingQueue.get();
	}
	
	
}
