package countdownlatch;

import java.util.concurrent.CountDownLatch;

public  class TriggerByLatch extends Task{

	public TriggerByLatch(String name, CountDownLatch latch) {
		super(name, latch);
	}

	@Override
	public void run() {
		
		try {
			this.latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if(latch.getCount()==0) {
		System.out.println("Notified when latch reached value 0");
		}
	}

}
