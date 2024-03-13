package phaser;

import java.util.Arrays;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Phaser;
import java.util.function.Consumer;

import api.IAction;
import api.IPlan;
import domain.Person;
import impl.CricketPlan;
import impl.MoviePlan;
import impl.PlayCricket;
import impl.WatchMovie;

/*
 * 
1.Cyclic barriers throws InterruptedException and BrokenBarrierException which need not be handled in Phaser

2.The parties are fixed in a Cyclic barrier instance (which is provided in the constructor) but the number of parties 
in dynamic in a Phaser.
 * */
public class PhaserDriver {
	 public static void main(String[] args) {
		Person sameer = new Person("sameer");
		Person panda = new Person("panda");
		Person pari = new Person("pari");
		Person negi = new Person("negi");
		Queue<Person> people = new ConcurrentLinkedQueue<>(Arrays.asList(sameer,panda,pari,negi));

		WorkAssigner assigner = new WorkAssigner(people);
		assigner.assignWork();
	}
	 
}
