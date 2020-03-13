package impl;

import api.IPlan;

public class CricketPlan implements IPlan<PlayCricket>{

	@Override
	public String plan(PlayCricket action) {
		System.out.println("");
		return action.performAction();
	}

}
