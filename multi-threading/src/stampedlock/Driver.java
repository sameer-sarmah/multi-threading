package stampedlock;

import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class Driver {

	public static void main(String[] args) {
		ArrayWrapper<Integer> array=new ArrayWrapper<>();
		
		Runnable addItemsTask = ()->{
	        IntStream.range(0, 5)
	        .forEach((item)->array.add(item));		
		};
		
    
        Runnable getItemsTask = ()->{
        IntStream.range(0, 5)
        .forEach((index)->array.get(index));
        };
        
        
        Runnable getSize = ()->{
            IntStream.range(0, 50)
            .forEach((index)->System.out.println("Size of array "+array.size()));
            
        };

        
        
		ExecutorService executor=Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
		
        IntStream.range(0, 5)
        .forEach((val)->executor.submit(addItemsTask));
         
        IntStream.range(0, 5)
        .forEach((val)->executor.submit(getItemsTask));
		
        IntStream.range(0, 5)
        .forEach((val)->executor.submit(getSize));
		
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
