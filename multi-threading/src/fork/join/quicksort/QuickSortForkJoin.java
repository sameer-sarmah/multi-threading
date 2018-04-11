package fork.join.quicksort;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class QuickSortForkJoin {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		

		int[] arr=IntStream.range(0, 1000)
		.map((int element)->{
			int random=(int) (Math.random()*100);
			return element*random;
		})
		.toArray();
		System.out.println("unsorted array");
        System.out.println(Arrays.toString(arr));
		ForkJoinPool pool=ForkJoinPool.commonPool();
		QuickSort eval=new QuickSort(arr);
//		System.out.println(Arrays.toString(eval.partition(arr)));
		ForkJoinTask<int []> future=pool.submit(eval);
		System.out.println("sorted array");
		System.out.println(Arrays.toString(future.get()));
	
		
	}

}
