package consumer.producer.condition.lock;

public class Consumer implements Runnable{

	private ConditionLockWrapper condtion;
	public Consumer(ConditionLockWrapper condtion) {
		this.condtion=condtion;
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
		condtion.get();
	}
	
	
}
