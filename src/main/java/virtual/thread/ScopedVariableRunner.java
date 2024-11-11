package virtual.thread;

import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.StructuredTaskScope;
import java.util.concurrent.StructuredTaskScope.Subtask;
import java.util.stream.IntStream;

import org.fluttercode.datafactory.impl.DataFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ScopedVariableRunner {

	private static Logger logger = LoggerFactory.getLogger(ScopedVariableRunner.class);
	
	public static void main(String[] args) {
		//ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
		//ExecutorService executorService = Executors.newWorkStealingPool();
		ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor();
		//threadLocal(executorService);
		scopeValue();
		executorService.shutdown();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			logger.error(e.getMessage());
		}
	}

	private static void scopeValue() {
		ScopedValue<String> scopedValue = ScopedValue.newInstance();
		Runnable accessScopedValue = () -> {
			if(scopedValue.isBound()) {
				String name = scopedValue.get();
				logger.info("Get Name="+name+" as ScopedValue in thread "+Thread.currentThread());
			} 
		};
		
		Callable<Optional<String>> retrieveScopedValue = () -> {
			if(scopedValue.isBound()) {
				String name = scopedValue.get();
				logger.info("Get Name="+name+" as ScopedValue in thread "+Thread.currentThread());
				return Optional.of(name);
			} else {
				return Optional.empty();
			}
				
		};
		
		IntStream.rangeClosed(0, 9).forEach(number -> {
			DataFactory df = new DataFactory();
			String firstName=df.getFirstName();
			ScopedValue.where(scopedValue, firstName).run(accessScopedValue);		
		});
		
		IntStream.rangeClosed(0, 9).forEach(number -> {
			DataFactory df = new DataFactory();
			String firstName=df.getFirstName();
			ScopedValue.where(scopedValue, firstName).run(() -> {
				try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
					Subtask<Optional<String>> nameSubtask= scope.fork(retrieveScopedValue);
					scope.join().throwIfFailed();
					if(nameSubtask.state().equals(Subtask.State.SUCCESS)) {
						Optional<String> nameOptional = nameSubtask.get();
						nameOptional.ifPresent(name -> logger.info("Get Name="+name+" as ScopedValue in thread "+Thread.currentThread()));	
					}
					
				} catch (InterruptedException | ExecutionException e) {
					logger.error(e.getMessage());
				}
			});		
		});

	} 
	
	private static void threadLocal(ExecutorService executorService) {
		ThreadLocal<String> threadLocal = new ThreadLocal<String>();
		Runnable accessThreadLocal = () -> {
			DataFactory df = new DataFactory();
			String firstName=df.getFirstName();
			logger.info("Set Name="+firstName+" as ThreadLocal in thread "+Thread.currentThread());
			threadLocal.set(firstName);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				logger.error(e.getMessage());
			}
			String name = threadLocal.get();
			logger.info("Get Name="+name+" as ThreadLocal in thread "+Thread.currentThread());
		};
		
		IntStream.rangeClosed(0, 9).forEach(number -> executorService.submit(accessThreadLocal));

	} 
}
