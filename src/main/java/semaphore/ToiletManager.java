package semaphore;

import java.util.Arrays;
import java.util.concurrent.Semaphore;

public class ToiletManager {

	public static void main(String[] args) {
		Semaphore toilets=new Semaphore(2);
		String[] names= {"Bruce","Bane","Maverick","Goose","Alexandra"};
		Arrays.stream(names).forEach((name)->{
			Person p=new Person(name,toilets);
			new Thread(p).start();
		});

	}

}
