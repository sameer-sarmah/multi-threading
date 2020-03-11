package promise;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

public class CompletableFutureSnippets {

	public static void main(String[] args)  {
		//thenCompose();
		//thenApply();
		//thenCombine();
		handleExceptions();
	}
	
	private static void allOf() throws InterruptedException, ExecutionException {
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
	
	private static void thenApply() {
		CompletableFuture<String> sameer =  CompletableFuture.completedFuture("sameer");
		//similar to a map operation
		sameer
		.thenApply(name -> name.toUpperCase())
		.thenAccept(System.out::println);
	}

	private static void thenCompose() {
		ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
		CompletableFuture<String> sameer =  CompletableFuture.completedFuture("sameer");
		//similar to a flatmap operation
		sameer
		.thenCompose(name -> {
			CompletableFuture<String> promise = new CompletableFuture<>();
			FullNameProvider nameProvider = new FullNameProvider(promise);
			executorService.submit(nameProvider);
			return promise;
		})
		.thenAccept(System.out::println);
	}
	
	private static void thenCombine() {
		ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
		CompletableFuture<String> addressPromise =  CompletableFuture.completedFuture("A804,Spring beauty");
		
		CompletableFuture<String> promise = new CompletableFuture<>();
		FullNameProvider nameProvider = new FullNameProvider(promise);
		executorService.submit(nameProvider);
		
		addressPromise
		.thenCombine(promise,(address,name)->{
			return "Address is: "+address+" ,name is: "+name;
		})
		.thenAccept(System.out::println);
	}
	
	private static void handleExceptions() {
		ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
		/*
		 * handle() method is always executed regardless of exception occurs or not, 
		 * whereas exceptionally() stage is only executed when there's an exception.
		 * */
		CompletableFuture.supplyAsync(new FullNameProviderWithException(), executorService)
			.handle((name,exception)->{
				if(exception != null) {
					System.out.println("exception encountered in handle operation,in thread: "+Thread.currentThread().getName());
					System.err.println(exception.getMessage());	
				}

				String defaultName = "default";
				return name != null ? name :defaultName;
			})
			.thenAccept((name)->{
				System.out.println(name+" consumed in thread: "+Thread.currentThread().getName());
			});
		
		//if there's no exception then exceptionally() stage is skipped
		CompletableFuture.supplyAsync(new FullNameProviderWithException(), executorService)
		.exceptionally((exception) -> {
			System.out.println("exception encountered in exceptionally operation,in thread: "+Thread.currentThread().getName());
			System.err.println(exception.getMessage());
			String defaultName = "default";
			return defaultName;
		})
		.thenAccept((name)->{
			System.out.println(name+" consumed in thread: "+Thread.currentThread().getName());
		});
	}

	private static class FullNameProvider implements Runnable{
		private final CompletableFuture<String> promise;
		FullNameProvider(CompletableFuture<String> promise){
			this.promise = promise;
		}

		@Override
		public void run() {
			try {
				TimeUnit.SECONDS.sleep(3);
			} catch (InterruptedException e) {
				e.printStackTrace();
				promise.completeExceptionally(e);
			}
			promise.complete( "sameer sarmah");
		}
		
	}
	
	private static class FullNameProviderWithException implements Supplier<String>{


		@Override
		public String get() {
			try {
				TimeUnit.SECONDS.sleep(3);
				if(Math.random() < 0.5) {
					throw new RuntimeException("Value less than 0.5");
				}
				return "Sameer Sarmah";
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return null;
		}
		
	}
}
