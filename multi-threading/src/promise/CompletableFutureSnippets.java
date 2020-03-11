package promise;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CompletableFutureSnippets {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
		CompletableFuture<String> sameer = new CompletableFuture<>();
		long startTime = System.currentTimeMillis();
		executorService.submit(()->{  
			try {
				TimeUnit.SECONDS.sleep(5);
				sameer.complete("Sameer");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
		
		
		CompletableFuture<String> mayuri = new CompletableFuture<>();
		executorService.submit(()->{  
			try {
				TimeUnit.SECONDS.sleep(3);
				mayuri.complete("mayuri");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
		
		CompletableFuture<Void> allof = CompletableFuture.allOf(sameer,mayuri);
		allof.get();
		if(allof.isDone()) {
			long endTime = System.currentTimeMillis();
			System.out.println(sameer.get() + " " + mayuri.get() +" in time "+(endTime - startTime)/1000 );
		}
		TimeUnit.SECONDS.sleep(50);
	}
	
	

}
