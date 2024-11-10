package virtual.thread;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class VirtualThreadRunner {
	
	private static Logger logger = LoggerFactory.getLogger(VirtualThreadRunner.class);
	
	private static final String serviceBaseURL = "https://services.odata.org/Northwind/Northwind.svc/Products";
	
	 public static void main(String[] args) {
		 HttpClient httpClient =  HttpClientBuilder.create().build();
		 String productURL = serviceBaseURL + "?$format=json";
		 String countURL = serviceBaseURL + "/$count";
		 ProductCountProvider countProvider = new ProductCountProvider(countURL,httpClient);
		 ProductDetailsProvider productDetailsProvider = new ProductDetailsProvider(productURL,httpClient);
		 ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor();
		 Future<Integer> countFuture = executorService.submit(countProvider);
		 Future<String> productFuture = executorService.submit(productDetailsProvider);
		 try {
			Integer count = countFuture.get();
			String products = productFuture.get();
			logger.info( String.valueOf(count));
			logger.info( products);
		} catch (InterruptedException | ExecutionException e) {
			logger.error(e.getMessage());
		}		 
	 }
}
