package executor.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;


public class ExecutorDriver {

	public static void main(String[] args) {
	ExecutorService executor=Executors.newFixedThreadPool(2);
	
	IntStream.rangeClosed(0, 5).forEach((int num)->{
		executor.submit(new Task("Task "+num));
	});
	
	executor.shutdown();
	
	System.out.println("all tasks are submitted");
	
	try {
		executor.awaitTermination(1, TimeUnit.DAYS);
	} catch (InterruptedException e) {
		e.printStackTrace();
	}
	System.out.println("all tasks are completed");

	}

}
