package gna;

import java.util.Comparator;

public class StateComparator implements Comparator<State> {

	@Override
	public int compare(State first, State second) {
		return Integer.compare(first.getTotalCost(), second.getTotalCost());
	}

}
