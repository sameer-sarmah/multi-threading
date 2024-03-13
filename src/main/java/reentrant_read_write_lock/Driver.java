package reentrant_read_write_lock;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import cyclicbarrier.CementSupplier;
import cyclicbarrier.GravelSupplier;
import cyclicbarrier.RailingSupplier;

public class Driver {

	public static void main(String[] args) {
		ArrayWrapper<Integer> array=new ArrayWrapper<>();
		
		Runnable addItemsTask = ()->{
	        IntStream.range(0, 5)
	        .forEach((item)->array.add(item));		
		};
		

		Runnable addItemsListTask = ()->{
        List<Integer> itemList=IntStream.range(10, 15)
        .boxed()
        .collect(Collectors.toList());
        array.addAllItems(itemList);
		};
        
       
        
        Runnable getItemsTask = ()->{
        IntStream.range(0, 5)
        .forEach((index)->System.out.println(array.get(index)));
        };
        
        Runnable getItemsListTask = ()->{
        List<Integer> indexList=IntStream.range(0, 5)
                .boxed()
                .collect(Collectors.toList());
        
        array.getAllItems(indexList);
        };
        
        Runnable printListTask = ()->{printItems(array);};
        
        printItems(array);
        
		ExecutorService executor=Executors.newFixedThreadPool(4);
		
        IntStream.range(0, 5)
        .forEach((val)->executor.submit(addItemsTask));
        
        IntStream.range(0, 5)
        .forEach((val)->executor.submit(addItemsListTask));
        
        IntStream.range(0, 5)
        .forEach((val)->executor.submit(getItemsTask));
        
        IntStream.range(0, 5)
        .forEach((val)->executor.submit(getItemsListTask));
        
        IntStream.range(0, 5)
        .forEach((val)->executor.submit(printListTask));
		

		
		executor.shutdown();
		
		System.out.println("all tasks are submitted");
		try {
			executor.awaitTermination(1, TimeUnit.DAYS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static <T> void printItems(ArrayWrapper<T> array) {
        Iterator<T> it=array.iterator();
        while(it.hasNext()) {
        	System.out.println(it.next());
        }
	}

}
