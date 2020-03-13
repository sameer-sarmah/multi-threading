package impl;

import api.IAction;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class WatchMovie implements IAction {

	private String movieName;
	
	@Override
	public String performAction() {
		return "watch movie: "+movieName;
	}


}
