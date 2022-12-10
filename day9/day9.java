import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.Math;
import java.util.ArrayList;
import java.util.Scanner;

class day9
{
	private static boolean DEBUG = false;
	private static ArrayList<Point> headMap = new ArrayList<Point>();
	private static ArrayList<Point> tailMap = new ArrayList<Point>();
	private static ArrayList<Point> tailVisitedOnceMap = new ArrayList<Point>();
	
	public static void main(String[] args) throws FileNotFoundException {
		File file = new File("input.txt");
		Scanner scanner = new Scanner(file);

		headMap.add(new Point(0, 0));
		tailMap.add(new Point(0, 0));
		while (scanner.hasNext()) {
			String[] input = scanner.nextLine().split(" ");
			char direction = input[0].charAt(0);
			int distance = Integer.valueOf(input[1]);
			if (DEBUG) System.out.println(direction + "\t" + distance);
			for (int i = 0; i < distance; ++i) {
				if (DEBUG) System.out.println(direction);
				move(direction);
				//printProgressMap();
			}
		}
		scanner.close();

		for (Point p : tailMap) {
			if (!tailVisitedOnceMap.contains(p)) {
				tailVisitedOnceMap.add(p);
			}
		}
		//printMap();
		System.out.println("Positions visited by tail: " + tailVisitedOnceMap.size());
	}

	private static void move(char direction) {
		int headX = (int)getLocHead().getX();
		int headY = (int)getLocHead().getY();

		switch (direction) {
		case 'L':
			headMap.add(new Point(headX - 1, headY));
			break;
		case 'R':
			headMap.add(new Point(headX + 1, headY));
			break;
		case 'U':
			headMap.add(new Point(headX, headY + 1));
			break;
		case 'D':
			headMap.add(new Point(headX, headY - 1));
			break;
		}

		moveTail(getLocHead());
	}

	private static void moveTail(Point headPosition) {
		int headX = (int)getLocHead().getX();
		int headY = (int)getLocHead().getY();
		int tailX = (int)getLocTail().getX();
		int tailY = (int)getLocTail().getY();
		
		if (DEBUG) {
			System.out.println("Loc head: " + getLocHead().toString());
			System.out.println("Loc tail: " +  getLocTail().toString());
		}
		
		if (tailY == headY) { // same row
			if (Math.abs(headX - tailX) > 1) {
				if ((tailX < headX)) {
					tailMap.add(new Point(tailX + 1, tailY));
				}
				else if (tailX > headX) {
					tailMap.add(new Point(tailX - 1, tailY));
				}
			}
		}
		else if (tailX == headX) { // same col
			if (Math.abs(headY - tailY) > 1) {
				if (tailY < headY) {
					tailMap.add(new Point(tailX, tailY + 1));
				}
				else if (tailY > headY) {
					tailMap.add(new Point(tailX, tailY - 1));
				}
			}
		}
		else { // diagonally
			if (Math.abs(headX - tailX) > 1) {
				if (headY > tailY) {
					if (headX > tailX) {
						tailMap.add(new Point(tailX + 1, tailY + 1));
					}
					else {
						tailMap.add(new Point(tailX - 1, tailY + 1));
					}
				}
				else {
					if (headX < tailX) {
						tailMap.add(new Point(tailX - 1, tailY - 1));
					}
					else {
						tailMap.add(new Point(tailX + 1, tailY - 1));
					}
				}
			}
			else if (Math.abs(headY - tailY) > 1) {
				if (headX > tailX) {
					if (headY > tailY) {
						tailMap.add(new Point(tailX + 1, tailY + 1));
					}
					else {
						tailMap.add(new Point(tailX + 1, tailY - 1));
					}
				}
				else {
					if (headY < tailY) {
						tailMap.add(new Point(tailX - 1, tailY - 1));
					}
					else {
						tailMap.add(new Point(tailX - 1, tailY + 1));
					}
				}
			}
		}
	}

	private static Point getLocHead() {
		return headMap.get(headMap.size()-1);
	}

	private static Point getLocTail() {
		return tailMap.get(tailMap.size()-1);
	}

	private static void printMap() {
		int minX = 0;
		int minY = 0;
		int maxX = 0;
		int maxY = 0;
		
		for (Point p : headMap) {
			int x = (int)p.getX();
			int y = (int)p.getY();
			minX = (x < minX) ? x : minX;
			maxX = (x > maxX) ? x : maxX;
			minY = (y < minY) ? y : minY;
			maxY = (y > maxY) ? y : maxY;
		}
		
		int width = (maxX - minX) + 1;
		int height = (maxY - minY) + 1;

		char[][] charMap = new char[width][height];
		int offsetX = (minX < 0) ? Math.abs(minX) : 0;
		int offsetY = (minY < 0) ? Math.abs(minY) : 0;

		if (DEBUG) {
			System.out.println("\nX (" + minX + " - " + maxX + ")  Y ("
							   + minY + " - " + maxY + ")");

			System.out.println(width + " " + height);
		}

		for (int y = 0; y < height; ++y) {
			for (int x = 0; x < width; ++x) {
				charMap[x][y] = '.';
			}
		}

		for (Point p : tailMap) {
			int x = (int)p.getX();
			int y = (int)p.getY();
			charMap[x+offsetX][y+offsetY] = '#';
		}

		for (int y = height-1; y >= 0; --y) {
			for (int x = 0; x < width; ++x) {
				System.out.print(charMap[x][y]);
			}
			System.out.println();
		}
	}

	private static void printProgressMap() {
		int minX = 0;
		int minY = 0;
		int maxX = 0;
		int maxY = 0;
		
		for (Point p : headMap) {
			int x = (int)p.getX();
			int y = (int)p.getY();
			minX = (x < minX) ? x : minX;
			maxX = (x > maxX) ? x : maxX;
			minY = (y < minY) ? y : minY;
			maxY = (y > maxY) ? y : maxY;
		}
		
		int width = (maxX - minX) + 1;
		int height = (maxY - minY) + 1;

		char[][] charMap = new char[width][height];
		int offsetX = (minX < 0) ? Math.abs(minX) : 0;
		int offsetY = (minY < 0) ? Math.abs(minY) : 0;

		for (int y = 0; y < height; ++y) {
			for (int x = 0; x < width; ++x) {
				charMap[x][y] = '.';
			}
		}

		int headX = (int)getLocHead().getX();
		int headY = (int)getLocHead().getY();
		int tailX = (int)getLocTail().getX();
		int tailY = (int)getLocTail().getY();
		charMap[tailX+offsetX][tailY+offsetY] = 'T';
		charMap[headX+offsetX][headY+offsetY] = 'H';		

		for (int y = height-1; y >= 0; --y) {
			for (int x = 0; x < width; ++x) {
				System.out.print(charMap[x][y]);
			}
			System.out.println();
		}
		System.out.println();
	}
}
