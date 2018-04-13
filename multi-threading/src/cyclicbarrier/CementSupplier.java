package cyclicbarrier;

import java.util.concurrent.CyclicBarrier;

public class CementSupplier implements Runnable{
	private CyclicBarrier cyclicBarrier;
	public CementSupplier(CyclicBarrier cyclicBarrier) {
		this.cyclicBarrier=cyclicBarrier;
	}
	
	@Override
	public void run() {
		  try {
			   Thread.sleep(3000);
			   System.out.println("Getting cement needed for construction");
			   cyclicBarrier.await();
			   System.out.println("All products needed for construction acquired" + Thread.currentThread().getName());
			  } catch (Exception e) {
			   e.printStackTrace();
			  }
		
	}

}
