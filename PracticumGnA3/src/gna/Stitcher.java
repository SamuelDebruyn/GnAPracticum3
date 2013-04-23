package gna;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
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
	 * 
	 * 
	 * Based on the uniform-cost search algorithm and the Dijkstra algorithm.
	 */
	public Iterable<Position> seam(int[][] image1, int[][] image2) {

		int height = image1.length;
		int width = image1[0].length;
		Position target = new Position(width - 1, height - 1);

		PriorityQueue<State> queue = new PriorityQueue<State>(11, new StateComparator());
		HashSet<Position> closed = new HashSet<Position>();

		queue.add(new State(new Position(0, 0), image1[0][0], image2[0][0]));

		while(!queue.peek().getPosition().equals(target)){
			State currentState = queue.poll();
			if(!closed.contains(currentState.getPosition()))
				closed.add(currentState.getPosition());
			for(Position pos : currentState.getPosition().getNeighbors(width, height, true)){
				State neighbor = new State(pos, image1[pos.getY()][pos.getX()], image2[pos.getY()][pos.getX()], currentState);
				if(!closed.contains(neighbor.getPosition())){
					boolean addN = false;
					if(queue.contains(neighbor)){
						Iterator<State> itr = queue.iterator();
						boolean found = false;
						while(itr.hasNext() && !found){
							State check = itr.next();
							if(neighbor.equals(check)){
								found = true;
								if(neighbor.getTotalCost() < check.getTotalCost()){
									itr.remove();
									addN = true;
								}
							}
						}
					}else{
						addN = true;
					}
					if(addN)
						queue.offer(neighbor);
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

		int height = mask.length;
		int width = mask[0].length;

		// first find the start node for the second image

		HashMap<Integer, Integer> seamIndexes = new HashMap<Integer, Integer>();

		for(int i = 0; i < height; i++){
			for(int j = 0; j < width; j++){
				if(mask[i][j] == SEAM)
					seamIndexes.put(i, j);
			}
			if(seamIndexes.get(i) != width - 1){
				seamIndexes.put(-1, i);
				break;
			}
		}

		int y = seamIndexes.get(-1);
		int x = seamIndexes.get(y) + 1;
		Position secondNode = new Position(x, y);
		
		// now floodfill the second part of the mask

		LinkedList<Position> toFloodWithSecond = new LinkedList<Position>();
		HashSet<Position> done = new HashSet<Position>();
		toFloodWithSecond.offer(secondNode);
		
		while(!toFloodWithSecond.isEmpty()){
			
			Position current = toFloodWithSecond.poll();
			
			if(mask[current.getY()][current.getX()] != SEAM){
				mask[current.getY()][current.getX()] = IMAGE2;
				
				for(Position pos: current.getNeighbors(width, height, false)){
					
					if(!(done.contains(pos) || toFloodWithSecond.contains(pos)))
						toFloodWithSecond.offer(pos);
						
				}	
				
			}
			
			done.add(current);

		}
		
		/*
		 * Code below only necessary if the values for IMAGE1, IMAGE2 and SEAM are changed.
		 * This will not be the case (contacted TA). 
		 * 
		LinkedList<Position> toFloodWithFirst = new LinkedList<Position>();
		Position zero = new Position(0, 0);
		toFloodWithFirst.addAll(zero.getNeighbors(width, height, false));
		
		while(!toFloodWithFirst.isEmpty()){
			
			Position current = toFloodWithFirst.poll();
			
			if(mask[current.getY()][current.getX()] != SEAM && mask[current.getY()][current.getX()] != IMAGE2){
				mask[current.getY()][current.getX()] = IMAGE1;
				
				for(Position pos: current.getNeighbors(width, height, false)){
					
					if(!(done.contains(pos) || toFloodWithFirst.contains(pos)))
						toFloodWithFirst.offer(pos);
					
				}
				
			}
			
			done.add(current);
			
		}*/

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
			mask[pos.getY()][pos.getX()] = SEAM;
		}

		floodfill(mask);
		return mask;	  
	}

}
