package gna;

import static org.junit.Assert.*;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

import javax.imageio.ImageIO;

import org.junit.Test;

public class StitcherTest {

	
	@Test
	public void testStitchValidPathSimple() {
		int[][] img1 = 
			{ 
				{ 1, 100, 100 }, 
				{ 100, 1, 100 }, 
				{ 100, 100, 1 }
			};
		int[][] img2 = { 
				{ 2, 3, 3 }, 
				{ 3, 2, 3 }, 
				{ 3, 3, 2 }
			};
		Stitcher stitcher = new Stitcher();
		Position last = null;
		
		Iterable<Position> path = stitcher.seam(img1, img2);
		
		for ( Position p : path )
		{
			if ( last != null )
				assert( last.isAdjacentTo( p ) );
			
			last = p;
		}
		
		assert( last != null );
	}
	
	@Test
	public void testStitchSimple() {
		int[][] img1 = 
			{ 
				{ 1, 100 }, 
				{ 100, 1 }
			};
		int[][] img2 = { 
				{ 2, 3 }, 
				{ 3, 2 }
			};
		Stitcher stitcher = new Stitcher();
		int[][] result = stitcher.stitch(img1, img2);
		
		assert( result != null );
	}
}
