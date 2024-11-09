package virtual.thread;

import java.util.concurrent.Callable;

import org.apache.hc.client5.http.classic.HttpClient;

import api.AbstractResponseProvider;

public class ProductDetailsProvider extends AbstractResponseProvider implements Callable<String> {
	
	public ProductDetailsProvider(String productURL,HttpClient httpClient) {
		super(productURL, httpClient);
	}

	@Override
	public String call()  {
		String productsJSON = "{}";
		try {
			System.out.println("ProductDetailsProvider processing done by thread "+Thread.currentThread().getName());
			productsJSON =  getResponse();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return productsJSON;
	}
}