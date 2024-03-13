package consumer.producer.condition.lock;

public class Producer implements Runnable{
	private ConditionLockWrapper condtion;
	public Producer(ConditionLockWrapper condtion) {
		this.condtion=condtion;
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
		condtion.add();
	}
}
