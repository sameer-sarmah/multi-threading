package callable.future.async;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MultipleFutureDriver {
	public static void main(String[] args) {
		List<Integer> numbers = IntStream.rangeClosed(1, 2000).boxed().collect(Collectors.toList());
		List<Future<Integer>> futureList=new ArrayList<>();
		ExecutorService executor=Executors.newFixedThreadPool(4);
		ExecutorCompletionService<Integer> asyncExecutor=new ExecutorCompletionService<>(executor );
		Predicate<Integer> predicate = (num) -> num >= 500;
		int key=1500;
		List<List<Integer>> subLists=sublist(numbers,predicate);
		subLists.stream().forEach((List<Integer> subList)->{
			System.out.println("size of sublist "+subList.size() +" starting element "+subList.get(0));
			Future<Integer> indexFutureValue=asyncExecutor.submit(new LinearSearchCallable(subList,key));
			futureList.add(indexFutureValue);
		});
		IntStream.range(0, subLists.size())
		.forEach((int i)->{
			try {
				int index=asyncExecutor.take().get();
				if(index>=0) {
					System.out.println("key found");
				}
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		});
	}

	public static List<List<Integer>> sublist(List<Integer> numbers, Predicate<Integer> predicate) {
		boolean shouldPartition = predicate.test(numbers.size() / 2);
		List<List<Integer>> partitionedList = new ArrayList<>();
		if (shouldPartition) {
			int middleElement=numbers.get(numbers.size()/2);
			Collection<List<Integer>> partitionedCollection = numbers.stream()
			.collect(Collectors.partitioningBy((element)->element>=middleElement)).values();
			partitionedList = new ArrayList<>(partitionedCollection);
			List<Integer> leftSubList=partitionedList.get(0);
			List<List<Integer>> partitionedLeftSubList=sublist(leftSubList,predicate);
			List<Integer> rightSubList=partitionedList.get(1);
			List<List<Integer>> partitionedRightSubList=sublist(rightSubList,predicate);
			partitionedList.clear();
			partitionedList.addAll(partitionedLeftSubList);
			partitionedList.addAll(partitionedRightSubList);
		}else {
			partitionedList.add(numbers);
		}
		return partitionedList;

	}

}
