package async.http.countdownlatch;

import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;

public class ProductDetailsProvider implements Callable<String> {

	private final String productURL;
	private final CountDownLatch latch;
	private final HttpClient httpClient;
	
	public ProductDetailsProvider(String productURL,CountDownLatch latch, HttpClient httpClient) {
		this.productURL = productURL;
		this.latch = latch;
		this.httpClient = httpClient;
	}

	@Override
	public String call() throws Exception  {
		try {
			System.out.println("ProductDetailsProvider processing done by thread "+Thread.currentThread().getName());
			String productsJSON = "[]";
			HttpGet request = new HttpGet(productURL);
			HttpResponse response = httpClient.execute(request);
			InputStream inputStream = response.getEntity().getContent();
			productsJSON = IOUtils.toString(inputStream, Charset.defaultCharset());
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
