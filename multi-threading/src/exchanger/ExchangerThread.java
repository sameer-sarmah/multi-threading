package exchanger;

import java.util.Random;
import java.util.concurrent.Exchanger;
import java.util.function.Supplier;

public class ExchangerThread implements Runnable{

	private Exchanger<Supplier<Integer>> exchanger;
	
	public ExchangerThread(Exchanger<Supplier<Integer>> exchanger) {
		this.exchanger=exchanger;
	}
	
	@Override
	public void run() {
		while(true) {
		Random random=new Random();
		Supplier<Integer> supplier=()->{return random.nextInt(10);};
		try {
			Supplier<Integer> receivedSupplier=this.exchanger.exchange(supplier);
			System.out.println(receivedSupplier.get()+" by "+Thread.currentThread().getName());
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	 }	
	}

}
