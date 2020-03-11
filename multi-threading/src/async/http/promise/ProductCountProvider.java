package async.http.promise;

import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.concurrent.CompletableFuture;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;

public class ProductCountProvider implements Runnable {

	private final String countURL;
	private final CompletableFuture<Integer> promise;
	private final HttpClient httpClient;

	public ProductCountProvider(String countURL,CompletableFuture<Integer> promise,HttpClient httpClient) {
		this.countURL = countURL;
		this.promise = promise;
		this.httpClient = httpClient;
	}

	@Override
	public void run() {		
		try {
			System.out.println("ProductCountProvider processing done by thread "+Thread.currentThread().getName());
			HttpGet request = new HttpGet(countURL);
			HttpResponse response = httpClient.execute(request);
			InputStream inputStream = response.getEntity().getContent();
			String productsCountStr = IOUtils.toString(inputStream, Charset.defaultCharset());
			Integer count = Integer.parseInt(productsCountStr);
			promise.complete(count);
		} catch (Exception e) {
			e.printStackTrace();
			promise.completeExceptionally(e);
		}
	}

}