package fork.join.fibonacci;

import java.util.concurrent.RecursiveTask;

public class FibonacciEvaluator extends RecursiveTask<Integer>{
	final int number;
	FibonacciEvaluator(int number) { this.number = number; }
	@Override
	protected Integer compute() {
	     if (number <= 1){
	        return number;
	     }
	     FibonacciEvaluator fibonacciPrev = new FibonacciEvaluator(number - 1);
	     fibonacciPrev.fork();
	     FibonacciEvaluator fibonacciPrevPrev = new FibonacciEvaluator(number - 2);
	     fibonacciPrevPrev.fork();
	     return fibonacciPrev.join() + fibonacciPrevPrev.join();
	}
}
