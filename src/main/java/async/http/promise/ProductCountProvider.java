package async.http.promise;

import java.util.concurrent.CompletableFuture;

import org.apache.hc.client5.http.classic.HttpClient;

import api.AbstractResponseProvider;

public class ProductCountProvider extends AbstractResponseProvider implements Runnable {

	private final CompletableFuture<Integer> promise;

	public ProductCountProvider(String countURL,CompletableFuture<Integer> promise,HttpClient httpClient) {
		super(countURL,httpClient);
		this.promise = promise;
	}

	@Override
	public void run() {		
		try {
			System.out.println("ProductCountProvider processing done by thread "+Thread.currentThread().getName());
			String productsCountStr = getResponse();
			Integer count = Integer.parseInt(productsCountStr);
			promise.complete(count);
		} catch (Exception e) {
			e.printStackTrace();
			promise.completeExceptionally(e);
		}
	}

}