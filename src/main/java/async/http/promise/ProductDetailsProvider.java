package async.http.promise;

import java.util.concurrent.CompletableFuture;

import org.apache.hc.client5.http.classic.HttpClient;

import api.AbstractResponseProvider;

public class ProductDetailsProvider extends AbstractResponseProvider implements Runnable {

	private final CompletableFuture<String> promise;
	
	public ProductDetailsProvider(String productURL,CompletableFuture<String> promise, HttpClient httpClient) {
		super(productURL, httpClient);
		this.promise = promise;
	}

	@Override
	public void run()  {
		try {
			System.out.println("ProductDetailsProvider processing done by thread "+Thread.currentThread().getName());
			String productsJSON =  getResponse();
			promise.complete(productsJSON);
		} catch (Exception e) {
			e.printStackTrace();
			promise.completeExceptionally(e);
		}

	}
}