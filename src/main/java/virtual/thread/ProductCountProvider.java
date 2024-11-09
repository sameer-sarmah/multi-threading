package virtual.thread;

import java.util.concurrent.Callable;

import org.apache.hc.client5.http.classic.HttpClient;

import api.AbstractResponseProvider;

public class ProductCountProvider extends AbstractResponseProvider implements Callable<Integer> {

	public ProductCountProvider(String countURL,HttpClient httpClient) {
		super(countURL,httpClient);
	}

	@Override
	public Integer call() {		
		try {
			System.out.println("ProductCountProvider processing done by thread "+Thread.currentThread().getName());
			String productsCountStr = getResponse();
			Integer count = Integer.parseInt(productsCountStr);
			return count;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Integer.valueOf(0);
	}

}