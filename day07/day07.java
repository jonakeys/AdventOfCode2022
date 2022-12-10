import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

class day07
{
	public static void main(String[] args) throws FileNotFoundException {
		Scanner scanner = new Scanner(new java.io.File("input.txt"));

		Directory topDir = new Directory("/", null, new ArrayList<Node>());
		Directory currDir = topDir;
		
		while (scanner.hasNext()) {
			String line = scanner.nextLine();

			if (line.startsWith("$")) {
				String[] command = line.split(" ");

				switch (command[1]) {
				case "cd":
					switch (command[2]) {
					case "/":
						currDir = topDir;
						break;
					case "..":
						currDir = (Directory) currDir.getParent();
						break;
					default:
						currDir.addChild(new Directory(command[2], currDir,
													   new ArrayList<Node>()));
						currDir = (Directory) currDir.getChild(command[2]);
						break;
					}
					break;
				case "ls":

					break;
				}
			}
			else {
				String[] command = line.split(" ");
				switch (command[0]) {
				case "dir":
					currDir.addChild(new Directory(command[1], currDir,
												   new ArrayList<Node>()));
					break;
				default:
					currDir.addChild(new File(command[1],
											  Integer.valueOf(command[0]),
											  currDir));
				}
			}
		}
		scanner.close();

		// Part 1
		ArrayList<Node> dirs = new ArrayList<Node>();
		recurse(topDir, dirs);
		int sumFileSizesMax = 0;
		for (Node d : dirs) {
			if (d.getSize() < 100000) {
				sumFileSizesMax += d.getSize();
				//System.out.println(d.getName() + ": " + d.getSize());
			}
		}
		System.out.println("Filesize of dirs with max size '100000': "
						   + sumFileSizesMax);

		// Part 2
		int totalDiskspace = 70000000;
		int neededDiskspace = 30000000;
		Collections.sort(dirs);
		int usedSpace = dirs.get(0).getSize();
		int freeSpace = totalDiskspace - usedSpace;
		int remaining = neededDiskspace - freeSpace;
		for (int i = 0; i < dirs.size(); ++i) {
			if (dirs.get(i).getSize() > 0) {
				if (dirs.get(i).getSize() < remaining) {
					System.out.println("Needed dir to free enough space: "
									   + dirs.get(i-1).getName() + " ("
									   + dirs.get(i-1).getSize() + ")");
					break;
				}
			}
		}
 	}

	public static void recurse(Node n, ArrayList<Node> list) {
		if (n instanceof Directory) {
			list.add(n);
		}
		for (Node c : n.children) {
			recurse(c, list);
		}
	}

}

class Node implements Comparable<Node> {
	String name;
	Node parent;
	ArrayList<Node> children;

	public int compareTo(Node n) {
		if (this.getSize() < n.getSize()) {
			return 1;
		}
		else if (this.getSize() > n.getSize()) {
			return -1;
		}
		return 0;
	}
	
	public Node(String name, Node parent, ArrayList<Node> children) {
		this.name = name;
		this.parent = parent;
		this.children = children;
	}

	String getName() {
		return name;
	}

	String getParentName() {
		return parent.getName();
	}

	ArrayList<Node> getChildren() {
		return children;
	}

	int getSize() {
		if (this instanceof File) {
			return ((File) this).size;
		}
		else {
			int sumSize = 0;
			for (Node n : this.children) {
				sumSize += n.getSize();
			}
			return sumSize;
		}
	}
		
	void addChild(Node child) {
		children.add(child);
	}

	Node getParent() {
		return parent;
	}

	Node getChild(String name) {
		for (int i = 0; i < children.size(); ++i) {
			if (children.get(i).getName() == name) {
				return children.get(i);
			}
		}
		return null;
	}
}

class Directory extends Node {
	public Directory(String name, Directory parent, ArrayList<Node> children) {
		super(name, parent, children);
	}
}

class File extends Node {
	int size;

	public File(String name, int size, Directory parent) {
		super(name, parent, new ArrayList<Node>());
		this.size = size;
	}
}
