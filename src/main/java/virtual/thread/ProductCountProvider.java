package virtual.thread;

import java.util.concurrent.Callable;

import org.apache.hc.client5.http.classic.HttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import api.AbstractResponseProvider;

public class ProductCountProvider extends AbstractResponseProvider implements Callable<Integer> {
	
	private static Logger logger = LoggerFactory.getLogger(ProductCountProvider.class);

	public ProductCountProvider(String countURL,HttpClient httpClient) {
		super(countURL,httpClient);
	}

	@Override
	public Integer call() {		
		try {
			logger.info("ProductCountProvider processing starting in thread "+Thread.currentThread());
			String productsCountStr = getResponse();
			Integer count = Integer.parseInt(productsCountStr);
			logger.info("ProductCountProvider processing completed in thread="+Thread.currentThread()+",count="+count);
			return count;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Integer.valueOf(0);
	}

}