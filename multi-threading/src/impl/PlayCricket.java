package impl;

import api.IAction;

public class PlayCricket implements IAction{

	private String venue;
	
	@Override
	public String performAction() {
		return "play cricket in "+venue;
	}

}
