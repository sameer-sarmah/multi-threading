package phaser;

import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;

import api.IPlan;
import domain.Person;
import impl.MoviePlan;
import impl.WatchMovie;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WorkAssigner {
	private Queue<Person> people;

	@SuppressWarnings("unchecked")
	public void assignWork() {
		ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
		Phaser peopleToJoinAMovie = new Phaser(people.size());
		WatchMovie inception = new WatchMovie("inception");
		IPlan<WatchMovie> moviePlan = new MoviePlan();
		while (!people.isEmpty()) {
			Person person = people.poll();
			DailyRoutine.Builder<WatchMovie> builder = new DailyRoutine.Builder<WatchMovie>();
			DailyRoutine<WatchMovie> routine = builder.withAction(inception)
					.withPerson(person)
					.withPhaser(peopleToJoinAMovie)
					.withPlan(moviePlan)
					.withAction(inception)
					.withShouldWait(true)
					.build();
			
			executorService.submit(routine);
		}
		
		Person mayuri = new Person("mayuri");
		DailyRoutine.Builder<WatchMovie> builder = new DailyRoutine.Builder<WatchMovie>();
		DailyRoutine<WatchMovie> routine = builder.withAction(inception)
				.withPerson(mayuri)
				.withPhaser(peopleToJoinAMovie)
				.withPlan(moviePlan)
				.withAction(inception)
				.withShouldWait(false)
				.build();
		peopleToJoinAMovie.register();
		executorService.submit(routine);
		
		
	}

}
