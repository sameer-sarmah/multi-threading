package exchanger;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import java.util.stream.IntStream;

public class Driver {

	public static void main(String[] args) {
		ExecutorService executor=Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
		Exchanger<Supplier<Integer>> exchanger = new Exchanger<>();
		IntStream.range(0, 2)
        .forEach((val)->executor.submit(new ExchangerThread(exchanger)));
		executor.shutdown();
		
		System.out.println("all tasks are submitted");
		try {
			executor.awaitTermination(10, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
