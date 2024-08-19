package async.http.countdownlatch;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

import org.apache.hc.client5.http.classic.HttpClient;

import api.AbstractResponseProvider;

public class ProductDetailsProvider extends AbstractResponseProvider implements Callable<String> {

	private final CountDownLatch latch;
	
	public ProductDetailsProvider(String productURL,CountDownLatch latch, HttpClient httpClient) {
		super(productURL, httpClient);
		this.latch = latch;
	}

	@Override
	public String call() throws Exception  {
		try {
			System.out.println("ProductDetailsProvider processing done by thread "+Thread.currentThread().getName());
			String productsJSON = getResponse();
			return productsJSON;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		finally {
			latch.countDown();
		}

	}
	
}
