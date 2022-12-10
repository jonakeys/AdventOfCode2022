import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.Math;
import java.util.ArrayList;
import java.util.Scanner;

class day09
{
	private static int PART = 2;
	private static ArrayList<Point> headMap = new ArrayList<Point>();
	private static ArrayList<Point> tailMap = new ArrayList<Point>();
	private static ArrayList<Point> tailVisitedOnceMap = new ArrayList<Point>();
	private static ArrayList<ArrayList<Point>> knotsMap = new ArrayList<ArrayList<Point>>();
	
	public static void main(String[] args) throws FileNotFoundException {
		File file = new File("input.txt");
		Scanner scanner = new Scanner(file);

		// Initialize starting points
		headMap.add(new Point(0, 0));
		tailMap.add(new Point(0, 0));
		for (int i = 0; i < 8; ++i) {
			knotsMap.add(new ArrayList<Point>());
			knotsMap.get(i).add(new Point(0,0));
		}

		while (scanner.hasNext()) {
			String[] input = scanner.nextLine().split(" ");
			char direction = input[0].charAt(0);
			int distance = Integer.valueOf(input[1]);
			for (int i = 0; i < distance; ++i) {
				move(direction);
				if (PART == 1) {
					moveTail(getLocHead());
				}
				else {
					knotsMap.get(0).add(moveKnot(getLocHead(), 0));
					for (int j = 1; j < knotsMap.size(); ++j) {
						knotsMap.get(j).add(moveKnot(getLocKnot(j-1), j));
					}
					moveTail(getLocKnot(7));
				}
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
			headX--;
			break;
		case 'R':
			headX++;
			break;
		case 'U':
			headY++;
			break;
		case 'D':
			headY--;
			break;
		}
		
		headMap.add(new Point(headX, headY));
	}

	private static Point moveKnot(Point prevKnotPosition, int n) {
		int headX = (int)prevKnotPosition.getX();
		int headY = (int)prevKnotPosition.getY();
		int tailX = (int)getLocKnot(n).getX();
		int tailY = (int)getLocKnot(n).getY();
		
		if (tailY == headY) { // same row
			if (Math.abs(headX - tailX) > 1) {
				if ((tailX < headX)) tailX++;
				else tailX--;
			}
		}
		else if (tailX == headX) { // same col
			if (Math.abs(headY - tailY) > 1) {
				if (tailY < headY) tailY++;
				else tailY--;
			}
		}
		else { // diagonally
			if (Math.abs(headX - tailX) > 1) {
				if (headY > tailY) {
					if (headX > tailX) {
						tailX++;
						tailY++;
					}
					else {
						tailX--;
						tailY++;
					}
				}
				else {
					if (headX < tailX) {
						tailX--;
						tailY--;
					}
					else {
						tailX++;
						tailY--;
					}
				}
			}
			else if (Math.abs(headY - tailY) > 1) {
				if (headX > tailX) {
					if (headY > tailY) {
						tailX++;
						tailY++;
					}
					else {
						tailX++;
						tailY--;
					}
				}
				else {
					if (headY < tailY) {
						tailX--;
						tailY--;
					}
					else {
						tailX--;
						tailY++;
					}
				}
			}
		}
		return new Point(tailX, tailY);			
	}

	private static void moveTail(Point headPosition) {
		int headX = (int)headPosition.getX();
		int headY = (int)headPosition.getY();
		int tailX = (int)getLocTail().getX();
		int tailY = (int)getLocTail().getY();
		
		if (tailY == headY) { // same row
			if (Math.abs(headX - tailX) > 1) {
				if (tailX < headX) tailX++;
				else tailX--;
			}
		}
		else if (tailX == headX) { // same col
			if (Math.abs(headY - tailY) > 1) {
				if (tailY < headY) tailY++;
				else tailY--;
			}
		}
		else { // diagonally
			if (Math.abs(headX - tailX) > 1) {
				if (headY > tailY) {
					if (headX > tailX) {
						tailX++;
						tailY++;
					}
					else {
						tailX--;
						tailY++;
					}
				}
				else {
					if (headX < tailX) {
						tailX--;
						tailY--;
					}
					else {
						tailX++;
						tailY--;
					}
				}
			}
			else if (Math.abs(headY - tailY) > 1) {
				if (headX > tailX) {
					if (headY > tailY) {
						tailX++;
						tailY++;
					}
					else {
						tailX++;
						tailY--;
					}
				}
				else {
					if (headY < tailY) {
						tailX--;
						tailY--;
					}
					else {
						tailX--;
						tailY++;
					}
				}
			}
		}
		tailMap.add(new Point(tailX, tailY));
	}

	private static Point getLocHead() {
		return headMap.get(headMap.size()-1);
	}

	private static Point getLocTail() {
		return tailMap.get(tailMap.size()-1);
	}

	private static Point getLocKnot(int n) {
		return knotsMap.get(n).get(knotsMap.get(n).size()-1);
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
}
