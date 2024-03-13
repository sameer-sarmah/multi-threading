package phaser;

import java.util.Objects;
import java.util.concurrent.Phaser;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import api.IAction;
import api.IPlan;
import domain.Person;
import util.Util;

public class DailyRoutine<T extends IAction> implements Runnable {
	private Person person;
	private Phaser peopleToJoinAPlan;
	private IPlan<T> plan;
	private T action;
	private boolean shouldWait;
	
	private DailyRoutine() {}

	@Override
	public void run() {
		ThreadLocalRandom random = ThreadLocalRandom.current();
		if(shouldWait) {
			int estimatedTime = random.nextInt(500, 3000);
			System.out.println(person.getName() + " will be busy for "+estimatedTime);
			Util.busyFor(estimatedTime, TimeUnit.MILLISECONDS);
		}
		boolean wouldCancelPlan = random.nextBoolean();
		if(wouldCancelPlan && shouldWait) {
			System.out.println(person.getName() + " will not be able to "+ plan.plan(action));
			peopleToJoinAPlan.arriveAndDeregister();
		}else {
			System.out.println(person.getName() + " going to "+ plan.plan(action));
			peopleToJoinAPlan.arriveAndAwaitAdvance();
			System.out.println(person.getName() + " going to have joined");
		}
	}
	
	public void setPerson(Person person) {
		this.person = person;
	}

	public void setPeopleToJoinAPlan(Phaser peopleToJoinAPlan) {
		this.peopleToJoinAPlan = peopleToJoinAPlan;
	}

	public void setPlan(IPlan<T> plan) {
		this.plan = plan;
	}

	public void setAction(T action) {
		this.action = action;
	}

	public void setShouldWait(boolean shouldWait) {
		this.shouldWait = shouldWait;
	}
	
	public static class Builder<T extends IAction>{
		private Person person;
		private Phaser peopleToJoinAPlan;
		private IPlan<T> plan;
		private T action;
		private boolean shouldWait;
		
		public Builder<T> withPhaser(Phaser peopleToJoinAPlan) {
			Objects.requireNonNull(peopleToJoinAPlan, "Phaser can't be null");
			this.peopleToJoinAPlan =peopleToJoinAPlan;
			return this;
		} 
		
		public Builder<T> withPerson(Person person) {
			Objects.requireNonNull(person, "Person can't be null");
			this.person = person;
			return this;
		} 
		
		public Builder<T> withPlan(IPlan<T> plan) {
			Objects.requireNonNull(plan, "Plan can't be null");
			this.plan = plan;
			return this;
		} 
		
		public Builder<T> withAction(T action) {
			Objects.requireNonNull(action, "Action can't be null");
			this.action = action;
			return this;
		} 
		
		public Builder<T> withShouldWait(boolean shouldWait) {
			this.shouldWait = shouldWait;
			return this;
		} 
		


		public DailyRoutine build() throws IllegalStateException {
			Objects.requireNonNull(peopleToJoinAPlan, "Phaser can't be null");
			Objects.requireNonNull(person, "Person can't be null");
			Objects.requireNonNull(person, "Plan can't be null");
			Objects.requireNonNull(person, "Action can't be null");
			DailyRoutine<T> routine = new DailyRoutine<T>();
			routine.setAction(action);
			routine.setPeopleToJoinAPlan(peopleToJoinAPlan);
			routine.setPerson(person);
			routine.setPlan(plan);
			routine.setShouldWait(shouldWait);
			return routine;
		}
	}


}
