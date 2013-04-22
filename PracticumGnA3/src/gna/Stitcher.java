package gna;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.PriorityQueue;

/**
 * Implement the methods stitch, seam and floodfill.
 * 
 * @author Samuel Debruyn (r0305472)
 */
public class Stitcher {

  public static final int IMAGE1 = 0;
  public static final int IMAGE2 = 1;
  public static final int SEAM = 2;

  /**
   * Return the sequence of positions on the seam. The first position in the
   * sequence is (0, 0) and the last is (width - 1, height - 1). Each position
   * on the seam must be adjacent to its predecessor and successor (if any).
   * Positions that are diagonally adjacent are considered adjacent.
   * 
   * <code>image1</code> and <code>image2</code> are both non-null and have
   * equal dimensions.
   */
  public Iterable<Position> seam(int[][] image1, int[][] image2) {
	  
	  int width = image1.length;
	  int height = image1[0].length;
	  Position target = new Position(width - 1, height - 1);
	  
	  PriorityQueue<State> queue = new PriorityQueue<State>(11, new StateComparator());
	  HashSet<State> closed = new HashSet<State>();
	  
	  queue.add(new State(new Position(0, 0), image1[0][0], image2[0][0]));
	  
	  while(!queue.peek().getPosition().equals(target)){
		  State currentState = queue.poll();
		  boolean addCurrent = false;
		  if(closed.contains(currentState)){
			  Iterator<State> itr = closed.iterator();
			  while(itr.hasNext()){
				  State st = itr.next();
				  if(st.getPosition().equals(currentState.getPosition())){
					  if(st.getTotalCost() > currentState.getTotalCost()){
						  itr.remove();
						  addCurrent = true;
					  }
				  }
			  }  
		  }else{
			  addCurrent = true;
		  }
		  if(addCurrent)
			  closed.add(currentState);
		  for(Position pos : currentState.getPosition().getNeighbors(width, height)){
			  State neighbor = new State(pos, image1[pos.getX()][pos.getY()], image2[pos.getX()][pos.getY()], currentState);
			  if(!closed.contains(neighbor)){
				  queue.add(neighbor);
			  }
		  }
	  }
	  
	  ArrayList<Position> result = new ArrayList<Position>();
	  
	  State current = queue.poll();
	  while(current != null){
		  result.add(current.getPosition());
		  current = current.getPrevious();
	  }
	  
	  Collections.reverse(result);
	  
	  return result;
  }

  /**
   * Apply the floodfill algorithm described in the assignment to mask. You can assume
   * the mask contains a seam from the upper left corner to the bottom right corner.
   */
  public void floodfill(int[][] mask) {
	  // TODO
  }

  /**
   * Return the mask to stitch two images together. The seam runs from the upper
   * left to the lower right corner, with the rightmost part coming from the
   * first image. A pixel in the mask is 0 on the places where <code>img1</code>
   * should be used, and 1 where <code>img2</code> should be used. On the seam
   * record a value of 2.
   * 
   * ImageCompositor will only call this method (not seam and floodfill) to
   * stitch two images.
   * 
   * <code>image1</code> and <code>image2</code> are both non-null and have
   * equal dimensions.
   */
  public int[][] stitch(int[][] image1, int[][] image2) {
	  
	  Iterable<Position> seam = seam(image1, image2);
	  int[][] mask = new int[image1.length][image1[0].length];
	  
	  for(Position pos: seam){
		  mask[pos.getX()][pos.getY()] = SEAM;
	  }
	  
	  floodfill(mask);
	  return mask;	  
  }
  
}
