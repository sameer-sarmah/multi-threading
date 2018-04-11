package executor.service;

public class Task implements Runnable {

	private String name;
		
	public Task(String name) {
		super();
		this.name = name;
	}

	@Override
	public void run() {
		System.out.println("Task "+name+" started");
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Task "+name+" completed");
	}

}
