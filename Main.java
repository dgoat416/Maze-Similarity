
// Deron Washington II
import java.io.File;
import java.util.Scanner;
import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Stack;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.awt.Point;
import java.util.*;

/**
 * Class that houses the solution to CECS 328 Project 4 Similarity Mazes
 * 
 * @author DGOAT 
 * Date Started: 11/2/2020
 * Last Edit: 11/2/2020 
 * Date Finished: 11/9/2020
 * RESULT:
 * 
 */
public class Main 
{

	// static reference to the mazes of the room
	public static List<Room[][]> mazes = new ArrayList<Room[][]>();

	/**
	 * Class to define a single cell in the array
	 */
	public static class Room 
	{
		// north
		public int n;

		// south
		public int s;

		// west
		public int w;

		// east
		public int e;

		// holds the position in the maze (vertical, horizontal)
		public myPoint p;

		// Default Constructor
		public Room() 
		{
			// location within the maze
			p = new myPoint(0, 0);

			// directional options
			n = 0;
			s = 0;
			w = 0;
			e = 0;
		}

		/**
		 * Parameterized Constructor
		 * @param p = point object detailing location the room is at in the maze
		 * @param n = details whether the north option is doable or not
		 * @param s = details whether the south option is doable or not
		 * @param w = details whether the west option is doable or not
		 * @param e = details whether the east option is doable or not
		 */
		public Room(Point p, int n, int s, int w, int e) 
		{
			this.p = new myPoint(p);
			this.n = n;
			this.s = s;
			this.w = w;
			this.e = e;
		}

		/**
		 * Method to get the number of paths you can take out of the room (number of options)
		 * Since the maze has been defined as a '0' for open spaces and a '1' for closed spaces
		 * I return the options instead of the true room "score" 
		 * see below 0: We have 4 options to get out 
		 * 1: We have 3 options to get out 
		 * 2: We have 2 options to get out 
		 * 3: We have 1 option to get out 
		 * 4: We have 0 options to get out
		 * 
		 * @return the number of options we have to get out of the current room
		 */
		public int getOptions() 
		{
			int preSum = n + s + w + e;

			switch (preSum) {
				case 0:
					return 4;
				case 1:
					return 3;
				case 2:
					return 2;
				case 3:
					return 1;
				case 4:
					return 0;
			}

			// something went wrong
			return Integer.MAX_VALUE;
		}

		/**
		 * Method to represent this object as a string
		 */
		public String toString() 
		{
			return "Located at:" + p + "With configuration: [" + n + " " + s + " " + w + " " + e + "]";
		}
	}

	/**
	 * New point object to be comparable and cast from myPoint to Point
	 * Also allows for mathematical operations of addition and subtraction
	 * May also use the equals method to compare for equality
	 * @author DGOAT
	 *
	 */
	public static class myPoint extends Point implements Comparable<Point> 
	{
		/**
		 * Serial Version UID because we extended Point and Point is serializable
		 */
		private static final long serialVersionUID = 3504962621239841892L;

		// coordinate x will be vertical and y will be horizontal in board
		public Point iPoint;

		@Override
		public String toString() 
		{
			return iPoint.toString();
		}

		/**
		 * Compares two points
		 * @param p = another point object for comparison
		 */
		@Override
		public int compareTo(Point p) 
		{
			if (iPoint.x == p.x)
			{
				if (iPoint.y == p.y)
					return 0;
				else if (iPoint.y < p.y)
					return -1;
				else if (iPoint.y > p.y)
					return 1;
			} 
			else if (iPoint.x < p.x)
				return -1;

			else if (iPoint.x > p.x)
				return 1;

			// error
			return -999;
		}

		/**
		 * Default Constuctor
		 */
		public myPoint() 
		{
			super(new Point(0, 0));
			iPoint = new Point(0, 0);
		}

		/**
		 * Parameterized Constructor
		 * @param x = x value of the point
		 * @param y = y value of the point
		 */
		public myPoint(int x, int y) 
		{
			super(x, y);
			iPoint = new Point();
			iPoint.x = x;
			iPoint.y = y;
		}

		/**
		 * Parameterized Constructor
		 * @param p = a point object to initialize this object with
		 */
		public myPoint(Point p) 
		{
			super(p);
			iPoint = p;
		}

		/**
		 * Method to add two myPoint objects
		 * @param addend = the object to be added
		 * @return
		 * 				= the sum of the two point objects
		 */
		public myPoint add(myPoint addend) 
		{
			return new myPoint(this.x + addend.x, this.y + addend.y);
		}

		/**
		 * Method to add two myPoint objects
		 * @param addend = the object to be added
		 * @return
		 * 				= the sum of the two point objects
		 */
		public myPoint subtract(myPoint addend) 
		{
			return new myPoint(this.x - addend.x, this.y - addend.y);
		}

		/**
		 * Method to get the absolute value of a my point object
		 * @param p = the object to take the absolute value
		 * @return = the object with all positive values
		 */
		public static myPoint abs(myPoint p) 
		{
			return (new myPoint(Math.abs(p.x), Math.abs(p.y)));
		}

		/**
		 * Method to determine if an object 
		 * is equivalent to this
		 @return 
		 			= true if the data members are equal
		 			= false otherwise
		 */
		@Override
		public boolean equals(Object o) 
		{
			// if comparing this object with itself then return true
			if (o == this)
				return true;

			// Check if o is an instance of myPoint or Point 
			// "null instanceof [type]" also returns false */
			if (!(o instanceof myPoint) || !(o instanceof Point)) 
			{
				return false;
			}

			// // typecast o to myPoint so that we can compare data members  
			// myPoint c = (myPoint) o;

			// Normally would just compare the data members and return accordingly  
			// but since we have a super type that will do the same thing we use it
			// could have just did this line but wanted to practice with overriding
			// equals and hashcode
			return super.equals(o);
		}

		/**
		 * Method to calculate the hashcode
		 */
		@Override
		public int hashCode() 
		{
			int hash = 17;
			hash = 31 * hash + x;
			hash = 31 * hash + y;
			return hash;
		}

	}


	public static class LeastSimilarMazes
	{
		public int lcs;

		public int mIndex1;

		public int mIndex2;

		public LeastSimilarMazes()
		{
			this.lcs = Integer.MAX_VALUE;
			this.mIndex1 = 0;
			this.mIndex2 = 0;
		}

		public LeastSimilarMazes(int lcs, int index1, int index2)
		{
			this.lcs = lcs;
			this.mIndex1 = index1;
			this.mIndex2 = index2;
		}

		public static LeastSimilarMazes LEAST(LeastSimilarMazes lsm, LeastSimilarMazes lsm2)
		{
			if (Integer.min(lsm.lcs, lsm2.lcs) == lsm.lcs)
			{
				return lsm;
			}
			
			else
				return lsm2;
		}
	}

	/**
	 * Method to determine the least similar mazes
	 * @return = a LeastSimilarMazes object which defines the lcs and  
	 * 			 where the the lcs of all mazes in the input list are located
	 */
	public static LeastSimilarMazes calcLeastSimilarMazes()
	{
		List<String> mazePaths = new ArrayList<>();

		// get all the maze paths
		for (int i = 0; i < mazes.size(); i++)
		{
			String str = depthFirstSearch(mazes.get(i));
			mazePaths.add(str);
		}

		LeastSimilarMazes lsm = new LeastSimilarMazes();

		// compare every possible combination of the paths created
		for (int j = 0; j < mazePaths.size(); j++)
		{
			for (int k = 1; k < mazePaths.size(); k++)
			{
				int lcs = lcs(mazePaths.get(j), mazePaths.get(k));
				
				if (Integer.min(lcs, lsm.lcs) < lsm.lcs)
				{
					lsm.lcs = lcs;
					lsm.mIndex1 = j;
					lsm.mIndex2 = k;
				}
			}
		}

		return lsm;
	}

	// public static int lcs(String X, String Y)
	// {
	// 	X = X.toUpperCase(Locale.ROOT);
	// 	Y = Y.toUpperCase(Locale.ROOT);
	// 	int m = X.length(), n = Y.length();

	// 	// lookup table stores solution to already computed sub-problems
	// 	// i.e. T[i][j] stores the length of LCS of substring
	// 	// X[0..i-1] and Y[0..j-1]
	// 	int[][] T = new int[m + 1][n + 1];

	// 	// fill the lookup table in bottom-up manner
	// 	for (int i = 1; i <= m; i++)
	// 	{
	// 		for (int j = 1; j <= n; j++)
	// 		{
	// 			// if current character of X and Y matches
	// 			if (X.charAt(i - 1) == Y.charAt(j - 1)) {
	// 				T[i][j] = T[i - 1][j - 1] + 1;
	// 			}
	// 			// else if current character of X and Y don't match,
	// 			else {
	// 				T[i][j] = Integer.max(T[i - 1][j], T[i][j - 1]);
	// 			}
	// 		}
	// 	}

	// 	// LCS will be last entry in the lookup table
	// 	return T[m][n];
	// }
	

	/**
	 * Method to determine the longest common subsequence 
	 * between two strings
	 * @param s = string 1
	 * @param t = string 2
	 * @return = the substring and the length of the substring
	 */
	public static int lcs(String s, String t) 
	{ 
		// make sure everything is of the same case
		s = s.toUpperCase(Locale.ROOT);
		t = t.toUpperCase(Locale.ROOT);

		// get the size of current string size
		int size = s.length();
		int smSize = t.length();

		// lookup table to prevent us from having to recalculate problems
		// increase size to account for empty string/character case
		int[][] lookup = new int[size + 1][smSize + 1];
			
		// this is where the dynamic programming actually happens
		for (int i = 1; i < size + 1; i++)
		{
			for (int j = 1; j < smSize + 1; j++)
			{
				// characters at this location match?
				if (s.charAt(i - 1) == t.charAt(j - 1))
				 {
					lookup[i][j] = 1 + lookup[i - 1][j - 1];
				 }

				 // no match? find the max between the higher ups on the table
				 else 
				 {
					lookup[i][j] = Integer.max(lookup[i][j - 1], lookup[i - 1][j]);
				 }
			}
		}

		// dynamic programming answer always at the 
		// southeast corner of the lookup table 
		return lookup[size][smSize];
	}

	/**
	 * Method to determine if we can perform DFS on a certain path
	 * @param direction = the direction to test relative to the current position in the maze (static member)
	 * 					  only acceptable values: (North, South, West, East)
	 * @param room = current room we are in
	 * @return = true if the proposed operation is allowed and false otherwise (if the proposed operation will lead us to
	 * 			 array out of bounds exception or into a wall)
	 */
	public static boolean canGo(String direction, Room room) 
	{

		if (direction.toUpperCase().equals("NORTH")) 
		{
			if (room.n == 1)
				return false;
		} 
		else if (direction.toUpperCase().equals("SOUTH")) 
		{
			if (room.s == 1)
				return false;
		} 
		else if (direction.toUpperCase().equals("WEST")) 
		{
			if (room.w == 1)
				return false;
		} 
		else if (direction.toUpperCase().equals("EAST")) 
		{
			if (room.e == 1)
				return false;
		} 
		else
		{
			System.out.print("\n\nError in canGo(String, Room[][], Point) method. "
					+ "No correct direction attribute specified.\n\n ");
		}

		return true;
	}

	/**
	 * Method to "close the door" after we go pursue 
	 * that pathway
	 * @param maze = current maze we are in
	 * @param curr = current point in maze
	 * @param c = direction that we are going relative to curr
	 * @return = maze reflecting closed door changes
	 */
	public static Room[][] closeDoor(Room[][] maze, Point curr, char c) 
	{
		// make sure it is of the same case
		c = Character.toUpperCase(c);

		// going north
		if (c == 'N') 
		{
			// "close the door"
			maze[curr.x][curr.y].n = 1;
			maze[curr.x - 1][curr.y].s = 1;
		}

		// going south
		else if (c == 'S') 
		{
			// "close the door"
			maze[curr.x][curr.y].s = 1;
			maze[curr.x + 1][curr.y].n = 1;
		}

		// going west
		else if (c == 'W') 
		{
			// "close the door"
			maze[curr.x][curr.y].w = 1;
			maze[curr.x][curr.y - 1].e = 1;
		}

		// going east
		else if (c == 'E') 
		{
			// "close the door"
			maze[curr.x][curr.y].e = 1;
			maze[curr.x][curr.y + 1].w = 1;
		}

		// error
		else 
		{
			System.out.print("Error error!!!!");
			System.exit(-1);
		}

		return maze;
	}

	/**
	 * Method to get the direction that was enacted on current to create next
	 * @param current = current position
	 * @param next = next position
	 * @return = character representing the direction we took
	 */
	public static char getDirection(myPoint current, myPoint next) 
	{
		// find the difference between the two points
		myPoint diff = next.subtract(current);

		// same point send back a blank
		if (diff.equals(new myPoint(0, 0))) 
		{
			return '\0';
		}

		// south
		if (diff.x == 1) 
		{
			return 'S';
		}

		// north
		else if (diff.x == -1) 
		{
			return 'N';
		}

		// west
		else if (diff.y == -1) 
		{
			return 'W';
		}

		// east
		else if (diff.y == 1) 
		{
			return 'E';
		}

		// failed somehow
		System.out.print("WE FAILED");
		System.exit(-1);
		return 'f';
	}

	/**
	 * Method to perform a depth first search on the maze
	 * @param maze = a 2d array holding Room objects 
	 * @return = a string representing the path that the dfs traveled by
	 */
	public static String depthFirstSearch(Room[][] maze) 
	{
		/**
		 * ALGORITHM: 
		 * Essentially use a stack to keep track of the "recursive calls" to the dfs. Due to the LIFO nature of the stack I will check in the opposite precedence when evaluating what the next path should be. So instead of checking in the original (NSWE) format I will check in EWSN precedence to ensure that the precedence is maintained through the stack (could have changed it to a Queue in theory but didn't want to.
		 *
		 * More notable points of the algo:
		 *		- add to string path a capital letter for non-backtracking paths and a lowercase letter for backtracking 
		 *	
		 *		- keep a hashmap to track the direction that the room was entered on for example if I enter a room using a north operation then I would store the value of the hashmap as the point say (2,0) and would store the value as 'N' for north
		 * 	
		 *		- add to the path only when you pop off the stack or when doing backtracking step using hashmap
		 *	
		 *		- to backtrack use the hashmap to get the recorded value and perform the opposite operation to the current point within the maze 
		 *
		 */

		// this will track the path keeping all elements which we will return 
		String path = "";

		// this will keep track of each point in the maze and the operation was used to enter into that room (point in the maze)
		Map<myPoint, Character> tracker = new HashMap<myPoint, Character>();

		// keeps track of the point we are at in the maze
		myPoint curr = new myPoint(0, 0);

		// room we start at in the maze
		Room start = maze[0][0];

		// keeps track of the current room we are at in the maze
		Room room = start;

		// implement this using a stack for efficiency purposes
		Stack<Room> callStack = new Stack<Room>();

		// character to add to the path
		char c = '\0';

		// push starting point on the stack
		callStack.push(start);
		tracker.put(curr, 'D');

		// while not empty
		while (!callStack.isEmpty()) 
		{
			// pop the node off the stack and process it as in a recursive function call
			room = callStack.pop();

			// get the direction to add to the path (dest - curr)
			c = getDirection(curr, room.p);

			// only add to the path & close the doors if we are not backtracking
			if (c != '\0') 
			{
				path += c;
				maze = closeDoor(maze, curr, c);
			}

			// get new current
			curr = room.p;

			// add current spot to tracker if it is not already in tracker
			if (tracker.containsKey(curr) == false) 
			{
				tracker.put(curr, c);
			}

		
			/* 
				base case (no path out of the current room)
				so we go back to the previous node			
			*/
			if (room.getOptions() == 0) 
			{
				// at starting position?
				if (room.p.equals(start.p)) 
				{
					// we are done
					break;
				}

				// go back to the location that the room was first entered from
				Character goBack = tracker.get(curr);

				// perform the operation to get us back
				switch (Character.toUpperCase(goBack)) 
				{
					case 'N': 
					{
						// go south 
						curr.x++;
						path += 's';
						break;
					}
					case 'S': 
					{
						// go north
						curr.x--;
						path += 'n';
						break;
					}
					case 'W': 
					{
						// go east
						curr.y++;
						path += 'e';
						break;
					}
					case 'E': 
					{
						// go west
						curr.y--;
						path += 'w';
						break;
					}
					default: 
					{
						System.out.print("There is a mistake!");
						System.exit(-1);
					}

				}

				// push maze at current onto the stack
				callStack.push(maze[curr.x][curr.y]);
				continue;
			}

			// go east
			if (canGo("east", room)) 
			{
				// push eastern node onto the stack
				callStack.push(maze[curr.x][curr.y + 1]);
			}

			// go west
			if (canGo("west", room)) 
			{
				// push western node onto the stack
				callStack.push(maze[curr.x][curr.y - 1]);
			}

			// go south
			if (canGo("south", room)) 
			{
				// push southern node onto the stack
				callStack.push(maze[curr.x + 1][curr.y]);
			}

			// go north
			if (canGo("north", room))
			{
				// push northern node onto the stack 
				callStack.push(maze[curr.x - 1][curr.y]);
			}

		}

		return path;
	}

	/**
	 * Method to read the input from the file and populate the static reference of
	 * maze accordingly
	 */
	public static void readInput(String fileName) 
	{
		// Read input from file
		File inFile = new File(fileName);
		Scanner scan = null;

		// necessary variables to store input
		int numMazes = 0;
		int size = 0;
		String oneLine = ""; // one line of input
		Room[][] maze = null;

		try 
		{
			scan = new Scanner(inFile);

			// get the number of mazes
			numMazes = Integer.valueOf(scan.nextLine());

			// get the size of the mazes
			size = Integer.valueOf(scan.nextLine());

			// instantiate maze
			maze = new Room[size][size];

			// get the list of mazes
			for (int total = 0; total < numMazes; total++) 
			{
				// get a maze
				for (int i = 0; i < size; i++) 
				{
					// get a line
					oneLine = scan.nextLine();

					// separate the input into each room
					for (int j = 0; j < oneLine.length(); j += 4) 
					{
						String temp = oneLine.substring(j, j + 4);
						maze[i][j / 4] = new Room(new Point(i, j / 4), temp.charAt(0) - 48, temp.charAt(1) - 48,
								temp.charAt(2) - 48, temp.charAt(3) - 48);
					}

				}

				// add maze to the list of mazes and reset maze
				mazes.add(maze);
				maze = new Room[size][size];

				// get rid of the blank line
				if (numMazes > 1)
					scan.nextLine();
			}

		} 
		catch (FileNotFoundException e) 
		{

			e.printStackTrace();
		} 
		finally
		 {
			scan.close();
		}

	}

	/**
	 * Method to output the two least similar maze sequences
	 * 
	 * @param outputName = name of the output file
	 * @param lsm = the object that defines the lcs and where the two mazes
	 * 				that cause the lcs are in the maze list
	 */
	public static void writeOutput(String outputName, LeastSimilarMazes lsm) 
	{
		File outFile = new File(outputName);
		PrintWriter writer = null;

		try 
		{
			writer = new PrintWriter(outFile);

			writer.print(lsm.mIndex1 + " " + lsm.mIndex2);

		} catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		} finally 
		{
			writer.close();
		}

	}


	public static void main(String[] args) 
	{
		String[] files = new String[] {"input.txt", "inputTest.txt"};
		for (String s : files) {
		readInput(s);
		// String test = depthFirstSearch(mazes.get(1));

		// for (int i = 0; i < mazes.size(); i++)
		// {
		// 	printMaze(mazes.get(i));
		// }

		int x = lcs("ab", "ab");
		int y = lcs("abc", "ab");
		int z = lcs("abcbdab", "bdcaba");

		LeastSimilarMazes lsm = calcLeastSimilarMazes();
		writeOutput("testO.txt", lsm);


		System.out.print( "\n\n\n" + "LSM: :" + lsm.lcs + "\nDeron");

		// I THINK THE ISSUE LIES IN THE DFS METHOD 
		// I THINK I NEED TO FIX THE DUPLICATES ISSUE
		// IT MIGHT BE GIVING EXTRA LENGTH TO THE PATH
		// NEED TO CHECK THAT THEY ARE RETURNING THE SAME THING
		}

	}

}
