import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

class day08
{
	private static ArrayList<ArrayList<Integer>> map =
		new ArrayList<ArrayList<Integer>>();
	private static ArrayList<ArrayList<Integer>> visibilityMap =
		new ArrayList<ArrayList<Integer>>();
	private static ArrayList<ArrayList<Integer>> scenicScoreMap =
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
			ArrayList<Integer> scenicRow = new ArrayList<Integer>();
			
			for (int i = 0; i < line.length(); ++i) {
				mapRow.add(Integer.parseInt(String.valueOf(line.charAt(i))));
				visiMapRow.add(1); // default visible
				scenicRow.add(0);
			}
			map.add(mapRow);
			visibilityMap.add(visiMapRow);
			scenicScoreMap.add(scenicRow);
		}
		scanner.close();

		int visibleTrees = 0;
		int highestScenicScore = 0;
		width = map.get(0).size();
		height = map.size();
		visibleTrees += (width*2 + height*2) - 4;
		System.out.println("From outside visible: " + visibleTrees);

		// Part 1
		for (int y = 1; y < height-1; ++y) {
			for (int x = 1; x < width-1; ++x) {
				visibilityMap.get(y).set(x, setVisibility(x, y));
				visibleTrees += visibilityMap.get(y).get(x);
			}
		}
		System.out.println("Total trees visible: " + visibleTrees);

		// Part 2
		for (int y = 0; y < height; ++y) {
			for (int x = 0; x < width; ++x) {
				int treeScenicScore = scenicScore(x, y);
				scenicScoreMap.get(y).set(x, treeScenicScore);
				if (treeScenicScore > highestScenicScore)
					highestScenicScore = treeScenicScore;
			}
		}

		System.out.println("Highest scenic score: " + highestScenicScore);
		
	}

	static int setVisibility(int x, int y) {
		int treeHeight = map.get(y).get(x);

		// true = visible, false = invisible
		boolean left = true;
		boolean right = true;
		boolean top = true;
		boolean down = true;
		
		// check visible from left
		for (int i = 0; i < x; ++i) {
			if (!isVisible(i, y, treeHeight)) {
				left = false;
				break;
			}
		}
		// check visible from right
		for (int i = width - 1; i > x; --i) {
			if (!isVisible(i, y, treeHeight)) {
				right = false;
				break;
			}
		}
		// check visible from top
		for (int i = 0; i < y; ++i) {
			if (!isVisible(x, i, treeHeight)) {
				top = false;
				break;
			}
		}
		// check visible from down
		for (int i = height - 1; i > y; --i) {
			if (!isVisible(x, i, treeHeight)) {
				down = false;
				break;
			}
		}

		if (left == false && right == false
			&& top == false && down == false) return 0;
		else return 1;
	}

	static boolean isVisible(int x, int y, int treeHeight) {
		return map.get(y).get(x) < treeHeight;
	}

	static int scenicScore(int x, int y) {
		int left = 0;
		int right = 0;
		int top = 0;
		int down = 0;
		int treeHeight = map.get(y).get(x);

		// scenic left
		if (x > 0) {
			for (int i = x-1; i >= 0; --i) {
				left++;
				if (map.get(y).get(i) >= treeHeight)
					break;
			}
		}
			
		// scenic right
		if (x < width - 1) {
			for (int i = x+1; i < width; ++i) {
				right++;
				if (map.get(y).get(i) >= treeHeight)
					break;
			}
		}
		
		// scenic top
		if (y > 0) {
			for (int i = y-1; i >= 0; --i) {
				top++;
				if (map.get(i).get(x) >= treeHeight)
					break;
			}
		}
		
		// scenic down
		if (y < height - 1) {
			for (int i = y+1; i < height; ++i) {
				down++;
				if (map.get(i).get(x) >= treeHeight)
					break;
			}
		}

		return left * right * top * down;
	}
}
