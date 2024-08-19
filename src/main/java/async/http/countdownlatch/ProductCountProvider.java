package async.http.countdownlatch;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

import org.apache.hc.client5.http.classic.HttpClient;

import api.AbstractResponseProvider;

public class ProductCountProvider extends AbstractResponseProvider implements Callable<Integer> {

	private final CountDownLatch latch;
	
	public ProductCountProvider(String countURL,CountDownLatch latch,HttpClient httpClient) {
		super(countURL,httpClient);
		this.latch = latch;
	}

	@Override
	public Integer call() throws Exception {		
		try {
			System.out.println("ProductCountProvider processing done by thread "+Thread.currentThread().getName());
			String productsCountStr = getResponse();
			Integer count = Integer.parseInt(productsCountStr);
			return count;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally {
			latch.countDown();
		}
	}
}
