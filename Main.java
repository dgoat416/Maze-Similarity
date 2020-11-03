// Deron Washington II
import java.io.File;
import java.util.Scanner;

import javax.lang.model.util.ElementScanner6;

import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.ArrayList;
import java.awt.Point;

/**
 * Class that houses the solution to CECS 328 Project 4 Similarity Mazes
 * 
 * @author DGOAT 
 * Date Started: 11/2/2020
 * Last Edit: 11/2/2020 Date Finished:
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

		// Default Constructor
		public Room() 
		{
			n = 0;
			s = 0;
			w = 0;
			e = 0;
		}

		// Parameterized Constructor
		public Room(int n, int s, int w, int e) 
		{
			this.n = n;
			this.s = s;
			this.w = w;
			this.e = e;
		}

		/**
		 * Method to get the sum of the room The sum tells us how many options we have
		 * to get out of the current room Since the maze has been defined as a '0' for
		 * open spaces and a '1' for closed spaces I return the options instead of the
		 * true "score" see below 0: We have 4 options to get out 1: We have 3 options
		 * to get out 2: We have 2 options to get out 3: We have 1 option to get out 4:
		 * We have no options to get out
		 * 
		 * @return the number of options we have to get out of the current room
		 */
		public int getSum() 
		{
			int preSum = n + s + w + e;

			switch (preSum) 
			{
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
			return "[" + n + " " + s + " " + w + " " + e + "]";
		}
	}


	/**
	 * Method to determine if we can perform DFS on a certain path
	 * @param direction = the direction to test relative to the current position in the maze (static member)
	 * 					  only acceptable values: (North, South, West, East)
	 * @param maze = the current maze within mazes (static member)
	 * @return = true if the proposed operation is allowed and false otherwise (if the proposed operation will lead us to
	 * 			 array out of bounds exception or into a wall)
	 */
	public static boolean canGo(String direction, Room[][] maze, Point p)
	{
		Point testp = p;
		Room room = maze[testp.x][testp.y];

		if (direction.toUpperCase().equals("NORTH")) {
			if (room.n == 1)
				return false;
			else
				testp.x--;
		} else if (direction.toUpperCase().equals("SOUTH")) {
			if (room.s == 1)
				return false;
			else
				testp.x++;
		} else if (direction.toUpperCase().equals("WEST")) {
			if (room.w == 1)
				return false;
			else
				testp.y--;
		} else if (direction.toUpperCase().equals("EAST")) {
			if (room.e == 1)
				return false;
			else
				testp.y++;
		} else {
			System.out.print("\n\nError in canGo(String, Room[][], Point) method. "
					+ "No correct direction attribute specified.\n\n ");
		}

		// might be able to get rid of this because the maze defines it
		// check if going the direction would produce an array out of bounds exception
		try {
			Room testRoom = maze[testp.x][testp.y];
		} catch (ArrayIndexOutOfBoundsException AIOBE) {
			return false;
		}

		return true;
	}

	/**
	 * Method to perform a depth first search on the maze
	 * @param maze = a 2d array holding Room objects 
	 * @param start = starting index of the array (always 0, 0 in this project)
	 * @return = a string representing the path that the dfs traveled by
	 */
	public static String depthFirstSearch(Room[][] maze, Point start)
	{
		String path = "";

		// implement this using a stack for efficiency purposes


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

		try {
			scan = new Scanner(inFile);

			// get the number of mazes
			numMazes = Integer.valueOf(scan.nextLine());

			// get the size of the mazes
			size = Integer.valueOf(scan.nextLine());

			// instantiate maze
			maze = new Room[size][size];

			// get the list of mazes
			for (int total = 0; total < numMazes; total++) {
				// get a maze
				for (int i = 0; i < size; i++) {
					// get a line
					oneLine = scan.nextLine();

					// separate the input into each room
					for (int j = 0; j < oneLine.length(); j += 4) {
						String temp = oneLine.substring(j, j + 4);
						maze[i][j / 4] = 
								new Room(temp.charAt(0) - 48, temp.charAt(1) - 48,
										temp.charAt(2) - 48, temp.charAt(3) - 48);
					}

				}

				// add maze to the list of mazes and reset maze
				mazes.add(maze);
				maze = new Room[size][size];
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			scan.close();
		}

	}

	/**
	 * Method to output the two least similar maze sequences
	 * 
	 * @param outputName = name of the output file
	 * @param mazeNum1   = least similar maze
	 * @param mazeNum2   = corresponding least similar maze
	 */
	public static void writeOutput(String outputName, String mazeNum1, String mazeNum2) 
	{
		File outFile = new File(outputName);
		java.io.PrintWriter writer = null;

		try {
			writer = new PrintWriter(outFile);

			writer.print(mazeNum1 + " " + mazeNum2);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			writer.close();
		}

	}

	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		
		readInput("input.txt");

		// // test canGoMethod
		// System.out.println(canGo("west", mazes.get(0), new Point(0, 0))); // error
		// System.out.println(canGo("south", mazes.get(0), new Point(0, 0))); // true
		// System.out.println(canGo("east", mazes.get(0), new Point(0, 0))); // false
		// System.out.println(canGo("north", mazes.get(0), new Point(0, 0))); // error

		
		System.out.print("Deron");

	}

}
