package consumer.producer;

public class Driver {

	public static void main(String [] args) {
		QueueWrapper queue=new QueueWrapper();
		Consumer c=new Consumer(queue);
		Producer p=new Producer(queue);
		Thread t1=new Thread(c,"consumer");
		Thread t2=new Thread(p,"producer");
		t1.start();
		t2.start();
		
	}
	
}
