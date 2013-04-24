package gna;

public class State {

	private final Position position;
	private final int currentCost;
	private final int totalCost;
	private final State previous;

	public State(Position position, int firstColor, int secondColor, State previous){
		this.position = position;
		this.previous = previous;
		this.currentCost = ImageCompositor.pixelSqDistance(firstColor, secondColor);
		if(this.getPrevious() != null)
			this.totalCost = this.getCurrentCost() + this.getPrevious().getTotalCost();
		else
			this.totalCost = this.getCurrentCost();
	}

	public State(Position position, int firstColor, int secondColor){
		this(position, firstColor, secondColor, null);
	}

	public Position getPosition() {
		return position;
	}

	public int getCurrentCost() {
		return currentCost;
	}

	public int getTotalCost() {
		return totalCost;
	}

	public State getPrevious() {
		return previous;
	}

	@Override
	public String toString() {
		return "Position: " + this.getPosition().toString() + " Total cost: " + String.valueOf(this.getTotalCost()) + " (current: " + String.valueOf(this.getCurrentCost()) + ")";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		State other = (State) obj;
		if(!this.getPosition().equals(other.getPosition()))
			return false;
		// needed for queue.contains(), only the position is important
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + this.getPosition().hashCode();
		return result;
	}

}
