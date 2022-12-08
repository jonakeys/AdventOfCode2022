import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

class day8
{
	private static ArrayList<ArrayList<Integer>> map =
		new ArrayList<ArrayList<Integer>>();
	private static ArrayList<ArrayList<Integer>> visibilityMap =
		new ArrayList<ArrayList<Integer>>();
	private static int width = 0;
	private static int height = 0;

	public static void main(String[] args) throws FileNotFoundException {
		File file = new File("input.txt");
		Scanner scanner = new Scanner(file);

		while (scanner.hasNext()) {
			String line = scanner.nextLine();
			ArrayList<Integer> mapRow = new ArrayList<Integer>();
			ArrayList<Integer> visiMapRow = new ArrayList<Integer>();
			for (int i = 0; i < line.length(); ++i) {
				mapRow.add(Integer.parseInt(String.valueOf(line.charAt(i))));
				visiMapRow.add(1); // default visible
			}
			map.add(mapRow);
			visibilityMap.add(visiMapRow);
		}
		scanner.close();

		int visibleTrees = 0;
		width = map.get(0).size();
		height = map.size();
		visibleTrees += (width*2 + height*2) - 4;
		System.out.println("Outside visible: " + visibleTrees);

		// Part 1
		for (int y = 1; y < height-1; ++y) {
			for (int x = 1; x < width-1; ++x) {
				visibilityMap.get(y).set(x, setVisibility(x, y));
			}
		}
		for (int y = 1; y < height-1; ++y) {
			for (int x = 1; x < width-1; ++x) {
				visibleTrees += visibilityMap.get(y).get(x);
			}
		}
		System.out.println("Total trees visible: " + visibleTrees);

		// Part 2
	}

	static int setVisibility(int x, int y) {
		int treeNumber = map.get(y).get(x);

		// true = visible, false = invisible
		boolean left = true;
		boolean right = true;
		boolean top = true;
		boolean down = true;
		
		// check visible from left
		for (int i = 0; i < x; ++i) {
			if (!isVisible(i, y, treeNumber)) {
				left = false;
				break;
			}
		}
		// check visible from right
		for (int i = width - 1; i > x; --i) {
			if (!isVisible(i, y, treeNumber)) {
				right = false;
				break;
			}
		}
		// check visible from top
		for (int i = 0; i < y; ++i) {
			if (!isVisible(x, i, treeNumber)) {
				top = false;
				break;
			}
		}
		// check visible from down
		for (int i = height - 1; i > y; --i) {
			if (!isVisible(x, i, treeNumber)) {
				down = false;
				break;
			}
		}

		if (left == false && right == false
			&& top == false && down == false) return 0;
		else return 1;
	}

	static boolean isVisible(int x, int y, int treeNumber) {
		return map.get(y).get(x) < treeNumber;
	}
}
