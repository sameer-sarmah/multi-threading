package countdownlatch;

import java.util.concurrent.CountDownLatch;

public class Task implements Runnable {

	private String name;
	protected CountDownLatch latch;
		
	public Task(String name,CountDownLatch latch) {
		super();
		this.name = name;
		this.latch=latch;
	}

	@Override
	public void run() {
		System.out.println("Task "+name+" started");
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Task "+name+" completed");
		latch.countDown();
	}

}
