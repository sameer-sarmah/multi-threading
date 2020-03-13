package api;

public interface IPlan<T extends IAction> {
	public String plan(T action);
}
