package virtual.thread;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.StructuredTaskScope;
import java.util.concurrent.StructuredTaskScope.Subtask;

import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;

public class StructuredConcurrencyRunner {
	private static final String serviceBaseURL = "https://services.odata.org/Northwind/Northwind.svc/Products";
	
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
				System.out.println(count);
				System.out.println(products);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }
	 }
}
