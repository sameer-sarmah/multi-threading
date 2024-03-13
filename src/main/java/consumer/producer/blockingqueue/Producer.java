package consumer.producer.blockingqueue;

public class Producer implements Runnable{
	private BlockingQueueWrapper blockingQueue;
	public Producer(BlockingQueueWrapper blockingQueue) {
		this.blockingQueue=blockingQueue;
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
		blockingQueue.add();
	}
}
