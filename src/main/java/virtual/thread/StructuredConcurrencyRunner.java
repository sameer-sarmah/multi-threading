package virtual.thread;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.StructuredTaskScope;
import java.util.concurrent.StructuredTaskScope.Subtask;

import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StructuredConcurrencyRunner {
	private static final String serviceBaseURL = "https://services.odata.org/Northwind/Northwind.svc/Products";
	
	private static Logger logger = LoggerFactory.getLogger(StructuredConcurrencyRunner.class);
	
	 public static void main(String[] args) {
		 HttpClient httpClient =  HttpClientBuilder.create().build();
		 String productURL = serviceBaseURL + "?$format=json";
		 String countURL = serviceBaseURL + "/$count";
		 ProductCountProvider countProvider = new ProductCountProvider(countURL,httpClient);
		 ProductDetailsProvider productDetailsProvider = new ProductDetailsProvider(productURL,httpClient);	
		 try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
			 Subtask<Integer> retrieveProductCount = scope.fork(countProvider);
			 Subtask<String> retrieveProducts = scope.fork(productDetailsProvider);
			 try {
				scope.join().throwIfFailed();
				Integer count = retrieveProductCount.get();
				String products = retrieveProducts.get();
				logger.info( String.valueOf(count));
				logger.info( products);
			} catch (InterruptedException | ExecutionException e) {
				logger.error(e.getMessage());
			} 
		 }
	 }
}
