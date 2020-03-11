package async.http.promise;

import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.concurrent.CompletableFuture;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;

public class ProductDetailsProvider implements Runnable {

	private final String productURL;
	private final CompletableFuture<String> promise;
	private final HttpClient httpClient;
	
	public ProductDetailsProvider(String productURL,CompletableFuture<String> promise, HttpClient httpClient) {
		this.productURL = productURL;
		this.promise = promise;
		this.httpClient = httpClient;
	}

	@Override
	public void run()  {
		try {
			System.out.println("ProductDetailsProvider processing done by thread "+Thread.currentThread().getName());
			String productsJSON = "[]";
			HttpGet request = new HttpGet(productURL);
			HttpResponse response = httpClient.execute(request);
			InputStream inputStream = response.getEntity().getContent();
			productsJSON = IOUtils.toString(inputStream, Charset.defaultCharset());
			promise.complete(productsJSON);
		} catch (Exception e) {
			e.printStackTrace();
			promise.completeExceptionally(e);
		}

	}
}