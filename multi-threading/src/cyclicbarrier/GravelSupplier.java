package cyclicbarrier;

import java.util.concurrent.CyclicBarrier;

public class GravelSupplier  implements Runnable {
	private CyclicBarrier cyclicBarrier;
	public GravelSupplier(CyclicBarrier cyclicBarrier) {
		this.cyclicBarrier=cyclicBarrier;
	}
	
	@Override
	public void run() {
		  try {
			   Thread.sleep(2000);
			   System.out.println("Getting gravels needed for construction");
			   cyclicBarrier.await();
			   System.out.println("All products needed for construction acquired" + Thread.currentThread().getName());
			  } catch (Exception e) {
			   e.printStackTrace();
			  }
		
	}
}
