package impl;

import api.IAction;

public class WatchMovie implements IAction {

	private String movieName;
	
	public WatchMovie(String movieName) {
		this.movieName = movieName;
	}
	
	@Override
	public String performAction() {
		return "watch movie: "+movieName;
	}

	public String getMovieName() {
		return movieName;
	}

}
