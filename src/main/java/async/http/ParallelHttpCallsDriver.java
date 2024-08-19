package async.http;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;

import async.http.promise.ProductCountProvider;
import async.http.promise.ProductDetailsProvider;

public class ParallelHttpCallsDriver {
	private static final String serviceBaseURL = "https://services.odata.org/Northwind/Northwind.svc/Products";

	public static void main(String[] args) {
		usingCountDownLatch();
		//usingCompletableFuture();
	}

	
	private static void usingCompletableFuture() throws InterruptedException, ExecutionException {
		ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
		
		CompletableFuture<Integer> productCountPromise = new CompletableFuture<>();
		getProductCount(productCountPromise,executorService);
		
		CompletableFuture<String> productDetailsPromise = new CompletableFuture<>();
		getProducts(productDetailsPromise,executorService);
		
		CompletableFuture<Void> allof = CompletableFuture.allOf(productDetailsPromise,productCountPromise);
		allof.get();
		if(allof.isDone()) {
			System.out.println("product count is "+productCountPromise.get() );
			System.out.println("product details is "+productDetailsPromise.get() );
		}

		TimeUnit.SECONDS.sleep(50);
	}
	
	private static HttpClient getInstance() {
		return HttpClientBuilder.create().build();
	}

	public static void getProducts(CompletableFuture<String> promise,ExecutorService executorService) {
		String productURL = serviceBaseURL + "?$format=json";
		ProductDetailsProvider supplier = new ProductDetailsProvider(productURL,promise,getInstance());
		executorService.submit(supplier);
	}

	public static void getProductCount(CompletableFuture<Integer> promise,ExecutorService executorService) {
		String countURL = serviceBaseURL + "/$count";
		ProductCountProvider supplier = new ProductCountProvider(countURL,promise,getInstance());
		executorService.submit(supplier);
	}
	
	private static void usingCountDownLatch() {
		CountDownLatch latch=new CountDownLatch(2);
		ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
		
		String productURL = serviceBaseURL + "?$format=json";
		async.http.countdownlatch.ProductDetailsProvider productDetailsProvider = new async.http.countdownlatch.ProductDetailsProvider(productURL,latch,getInstance());
		Future<String> productDetailsFuture = executorService.submit(productDetailsProvider);
		
		String countURL = serviceBaseURL + "/$count";
		async.http.countdownlatch.ProductCountProvider supplier = new async.http.countdownlatch.ProductCountProvider(countURL,latch,getInstance());
		Future<Integer> productCountFuture = executorService.submit(supplier);

		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("all tasks are completed");
		try {
			System.out.println("product count is "+productCountFuture.get() );
			System.out.println("product details is "+productDetailsFuture.get() );
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		
	}
}
