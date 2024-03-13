package callable.future.async;

import java.util.List;
import java.util.concurrent.Callable;

public class LinearSearchCallable implements Callable<Integer>{

	private List<Integer> list;
	private int key;

	public LinearSearchCallable(List<Integer> list, int key) {
		super();
		this.list = list;
		this.key = key;
	}


	@Override
	public Integer call() throws Exception {
		for(int index = 0;index<list.size();index++) {
			if(list.get(index)==key) {
				return index;
			}
		}
		return -1;
	}

}
