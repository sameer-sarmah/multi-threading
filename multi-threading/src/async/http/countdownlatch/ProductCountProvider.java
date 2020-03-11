package async.http.countdownlatch;

import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;

public class ProductCountProvider implements Callable<Integer> {
	private final String countURL;
	private final CountDownLatch latch;
	private final HttpClient httpClient;

	public ProductCountProvider(String countURL,CountDownLatch latch,HttpClient httpClient) {
		this.countURL = countURL;
		this.latch = latch;
		this.httpClient = httpClient;
	}

	@Override
	public Integer call() throws Exception {		
		try {
			System.out.println("ProductCountProvider processing done by thread "+Thread.currentThread().getName());
			HttpGet request = new HttpGet(countURL);
			HttpResponse response = httpClient.execute(request);
			InputStream inputStream = response.getEntity().getContent();
			String productsCountStr = IOUtils.toString(inputStream, Charset.defaultCharset());
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
