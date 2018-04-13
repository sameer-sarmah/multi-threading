package cyclicbarrier;

import java.util.concurrent.CyclicBarrier;

public class RailingSupplier implements Runnable {
	private CyclicBarrier cyclicBarrier;
	public RailingSupplier(CyclicBarrier cyclicBarrier) {
		this.cyclicBarrier=cyclicBarrier;
	}
	
	@Override
	public void run() {
		  try {
			   Thread.sleep(4000);
			   System.out.println("Getting railing needed for construction");
			   cyclicBarrier.await();
			   System.out.println("All products needed for construction acquired" + Thread.currentThread().getName());
			  } catch (Exception e) {
			   e.printStackTrace();
			  }
		
	}
}
