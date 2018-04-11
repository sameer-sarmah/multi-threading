package consumer.producer.condition.lock;

public class Driver {

	public static void main(String [] args) {
		ConditionLockWrapper condition=new ConditionLockWrapper();
		Consumer c=new Consumer(condition);
		Producer p=new Producer(condition);
		Thread t1=new Thread(c,"consumer");
		Thread t2=new Thread(p,"producer");
		t1.start();
		t2.start();
		
	}
	
}
