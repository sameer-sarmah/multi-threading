package cyclicbarrier;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ConstructionDriver {

	public static void main(String[] args) {
		Runnable startConstruction=()-> System.out.println("Start Construction");
		CyclicBarrier cyclicBarrier = new CyclicBarrier(3,startConstruction);
		ExecutorService executor=Executors.newFixedThreadPool(3);
		executor.submit(new CementSupplier(cyclicBarrier));
		executor.submit(new GravelSupplier(cyclicBarrier));
		executor.submit(new RailingSupplier(cyclicBarrier));
		
		executor.shutdown();
		
		System.out.println("all tasks are submitted");
		try {
			executor.awaitTermination(1, TimeUnit.DAYS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

}
