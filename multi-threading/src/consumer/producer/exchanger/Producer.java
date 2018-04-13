package consumer.producer.exchanger;

import java.util.concurrent.Exchanger;

import org.fluttercode.datafactory.impl.DataFactory;

public class Producer implements Runnable{
	private Exchanger<String> exchanger;
	
	public Producer(Exchanger<String> exchanger) {
		this.exchanger=exchanger;
	}
	
	@Override
	public void run() {
		while(true) {
		DataFactory df=new DataFactory();
		String name=df.getName();
		try {
			String receivedMessage=this.exchanger.exchange(name);
			System.out.println("In producer,"+name+" is sending regards from "+Thread.currentThread().getName());
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	 }	
	}
}
