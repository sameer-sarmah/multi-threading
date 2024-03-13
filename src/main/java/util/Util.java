package util;

import java.util.concurrent.CancellationException;
import java.util.concurrent.TimeUnit;

public class Util {
	public static void busyFor(int time, TimeUnit unit) {
	    try {
	        unit.sleep(time);
	    } catch (InterruptedException e) {
				throw new CancellationException("interrupted");
	    }
	}
}
