package gna;

import java.util.ArrayList;

public class Position {
  private final int x, y;

  public Position(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  public boolean isAdjacentTo(Position other) {
    return Math.abs(x - other.x) <= 1 && Math.abs(y - other.y) <= 1 && !this.equals(other);
  }
  
  
  /**
   * This method returns the neighbors of the current position.
   * Based on https://github.com/BavoGoosens/roborally-deel2/blob/master/src/roborally/property/Position.java#L139
   * Made for OGP last year by Bavo Goosens & Samuel Debruyn.
   * 
   * @param width Width of the image (neighbors must exist in the image).
   * @param height Height of the image (neighbors must exist in the image).
   * @return An ArrayList with the neigbors of the current position.
   */
  public ArrayList<Position> getNeighbors(int width, int height){
	  
	  ArrayList<Position> positions = new ArrayList<Position>();
	  ArrayList<Position> validPositions = new ArrayList<Position>();
	  
	  positions.add(new Position(getX() - 1, getY()));
	  positions.add(new Position(getX() + 1, getY()));
	  positions.add(new Position(getX(), getY() - 1));
	  positions.add(new Position(getX(), getY() + 1));
	  positions.add(new Position(getX() + 1, getY() + 1));
	  positions.add(new Position(getX() - 1, getY() - 1));
	  positions.add(new Position(getX() - 1, getY() + 1));
	  positions.add(new Position(getX() + 1, getY() - 1));
	  
	  for(Position pos: positions){
		  if(pos.getX() >= 0 && pos.getX() < width && pos.getY() >= 0 && pos.getY() < height)
			  validPositions.add(pos);
	  }
	  
	  return validPositions;
  }

  @Override
public String toString() {
	return "(" + String.valueOf(getX()) + ", " + String.valueOf(getY()) + ")";
}

@Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + x;
    result = prime * result + y;
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Position other = (Position) obj;
    if (x != other.x)
      return false;
    if (y != other.y)
      return false;
    return true;
  }
}
