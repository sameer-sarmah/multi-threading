package virtual.thread;

import java.util.concurrent.Callable;

import org.apache.hc.client5.http.classic.HttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import api.AbstractResponseProvider;

public class ProductDetailsProvider extends AbstractResponseProvider implements Callable<String> {
	
	private static Logger logger = LoggerFactory.getLogger(ProductDetailsProvider.class);
	
	public ProductDetailsProvider(String productURL,HttpClient httpClient) {
		super(productURL, httpClient);
	}

	@Override
	public String call()  {
		String productsJSON = "{}";
		try {
			logger.info("ProductDetailsProvider processing starting in thread "+Thread.currentThread());
			productsJSON =  getResponse();
			logger.info("ProductDetailsProvider processing complted in thread "+Thread.currentThread());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return productsJSON;
	}
}