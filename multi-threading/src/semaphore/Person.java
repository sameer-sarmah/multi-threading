package semaphore;

import java.util.concurrent.Semaphore;

public class Person implements Runnable{

	private String name;
	private Semaphore toilets;
	
	public Person(String name, Semaphore toilets) {
		super();
		this.name = name;
		this.toilets = toilets;
	}

	@Override
	public void run() {
     System.out.println("Available toilets "+toilets.availablePermits());
     System.out.println(name + " occupying one toilet");
     
     try {
    	toilets.acquire();
		Thread.sleep(3000);
	} catch (InterruptedException e) {
		e.printStackTrace();
	}finally {
		System.out.println(name + " releasing occupied toilet");
		toilets.release();
		System.out.println("Available toilets "+toilets.availablePermits());
	}
     
     
	}

}
