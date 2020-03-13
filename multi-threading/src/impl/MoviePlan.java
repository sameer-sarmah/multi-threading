package impl;

import api.IPlan;

public class MoviePlan implements IPlan<WatchMovie> {

	@Override
	public String plan(WatchMovie action) {
		System.out.println();
		return action.performAction();
	}

}
