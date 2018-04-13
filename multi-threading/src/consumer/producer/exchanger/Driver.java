package consumer.producer.exchanger;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Driver {
	public static void main(String[] args) {
		ExecutorService executor=Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
		Exchanger<String> exchanger = new Exchanger<>();
		executor.submit(new Producer(exchanger));
		executor.submit(new Consumer(exchanger));
		executor.shutdown();
		
		System.out.println("all tasks are submitted");
		try {
			executor.awaitTermination(10, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
