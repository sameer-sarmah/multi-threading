package consumer.producer.exchanger;

import java.util.concurrent.Exchanger;


public class Consumer implements Runnable {
	private Exchanger<String> exchanger;
	private String message;
	
	public Consumer(Exchanger<String> exchanger) {
		this.exchanger=exchanger;
	}
	
	@Override
	public void run() {
		while(true) {
		try {
			String receivedMessage=this.exchanger.exchange(this.message);
			this.message=receivedMessage;
			System.out.println("Consumed ,Hello from "+receivedMessage+" in "+Thread.currentThread().getName());
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	 }	
	}
}
