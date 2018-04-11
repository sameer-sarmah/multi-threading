package countdownlatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;


public class ExecutorDriver {

	public static void main(String[] args) {
	ExecutorService executor=Executors.newFixedThreadPool(2);
	CountDownLatch latch=new CountDownLatch(5);
	IntStream.rangeClosed(0, 5).forEach((int num)->{
		executor.submit(new Task("Task "+num,latch));
	});
	
	executor.shutdown();
	
	System.out.println("all tasks are submitted");
	
	try {
		latch.await();
	} catch (InterruptedException e) {
		e.printStackTrace();
	}
	System.out.println("all tasks are completed");

	}

}
