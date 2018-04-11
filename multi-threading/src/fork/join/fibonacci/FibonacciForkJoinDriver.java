package fork.join.fibonacci;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

public class FibonacciForkJoinDriver {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		ForkJoinPool pool=ForkJoinPool.commonPool();
		FibonacciEvaluator eval=new FibonacciEvaluator(10);
		ForkJoinTask<Integer> future=pool.submit(eval);
		System.out.println(future.get());
	}

}
