import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;
import java.util.*;

// G cost - distance to start
// H cost - distance to goal
// F cost - total cost (G + H)

class Day12
{
	static final int XLENGTH = 161; // 8, 161
	static final int YLENGTH = 41; // 5, 41
	static Node startNode = null;
	static Node endNode = null;

	static HashMap<Node, Integer> nodes;

	public static void main(String[] args) throws FileNotFoundException {
		nodes = new HashMap<Node, Integer>();

		File file = new File("input.txt");
		Scanner scanner = new Scanner(file);

		while (scanner.hasNextLine()) {
			for (int y = 0; y < YLENGTH; ++y) {
				String input = scanner.nextLine();
				for (int x = 0; x < XLENGTH; ++x) {
					if (input.charAt(x) == 'S') {
						startNode = new Node(x, y);
						nodes.put(startNode, 'a' - 0);
					}
					else if (input.charAt(x) == 'E') {
						endNode = new Node(x, y);
						nodes.put(endNode, 'z' - 0);
					}
					else {
						nodes.put(new Node(x, y), (int) input.charAt(x));
					}
				}
			}
		}
		scanner.close();

		ArrayList<Integer> shortestPath = new ArrayList<Integer>();
		nodes.forEach(
					  (key, value)
					  -> {
						  if (value == 97) {
							  int pathLength = pathfind(key, endNode);
							  shortestPath.add(pathLength);
						  }
					  });
		Collections.sort(shortestPath);

        System.out.println("Shortest path: " + Integer.toString(pathfind(startNode, endNode)));
		System.out.println("Shortest path from any 'a': " + shortestPath.get(0));
    }

	public static int pathfind(Node startNode, Node endNode) {
        HashMap<Node, Integer> gCost = new HashMap<Node, Integer>();
        HashMap<Node, Node> parent = new HashMap<Node, Node>();
        LinkedList<Node> queue = new LinkedList<>();
		
        gCost.put(startNode, 0);
        queue.add(startNode);
		
        while (queue.size() > 0) {
            Node current = queue.poll();
			
            if (current.equals(endNode)) {
                ArrayList<Node> path = new ArrayList<>();
                while (parent.containsKey(current)) {
                    path.add(current);
                    current = parent.get(current);
                }
                return path.size();
            }
			
            for(Node c : current.nextTo()) {
                if (!nodes.containsKey(c)
					|| (nodes.get(c) > nodes.get(current) + 1)) {
                    continue;
				}

				int tentativeG = gCost.get(current) + 1;
                if (tentativeG < gCost.getOrDefault(c, Integer.MAX_VALUE)) {
                    gCost.put(c, tentativeG);
                    parent.put(c, current);
                    queue.add(c);
                }
            }
        }
        return Integer.MAX_VALUE;
    }

	private static class Node implements Comparable<Node> {
		private int x;
		private int y;
		
		public Node(int x, int y) {
			this.x = x;
			this.y = y;
		}

		public int getX() {
			return x;
		}

		public int getY() {

			return y;
		}
		public void setXY(int x, int y) {
			this.x = x;
			this.y = y;
		}

		public String toString() {
			return "(" + x + ", " + y + ")";
		}

		public ArrayList<Node> nextTo() {
			ArrayList<Node> list = new ArrayList<Node>();
			for (int yOff = -1; yOff < 2; yOff++) {
				for (int xOff = -1; xOff < 2; xOff++) {
					if (xOff == 0 ^ yOff == 0) {
						list.add(new Node(x + xOff, y + yOff));
					}
				}
			}
			return list;
		}

		public int compareTo(Node n) {
		if (this.equals(n))
			return 0;
		else if (n.y > this.y)
			return -1;
		else if (n.y < this.y)
			return 1;
		else
			return (n.x > this.x ? -1 : 1);
		}

		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Node other = (Node) obj;
			if (x != other.x)
				return false;
			if (y != other.y)
				return false;
			return true;
		}
		
		public int hashCode() {
			return Objects.hash(y,x);
		}
	}
}
