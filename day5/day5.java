import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

class day5
{
	public static void main(String[] args) {
		File file = new File("test.txt");
		try {
			Scanner scanner = new Scanner(file);
			ArrayList<ArrayList<Character>> stacks = new ArrayList<ArrayList<Character>>();
			ArrayList<ArrayList<Integer>> instructions = new ArrayList<ArrayList<Integer>>();
			int nRows = 3;
			int nStacks = 3;
			boolean TEST = false;

			// Create number of stacks
			for (int i = 0; i < nStacks; ++i) {
				ArrayList<Character> newArr = new ArrayList<Character>();
				stacks.add(newArr);
			}

			// Read characters (ex '[C]') from stacks and put in stack
			for (int i = 0; i < nRows; ++i) {
				String row = scanner.nextLine();
				ArrayList<Character> arrlist = new ArrayList<Character>();
				int stackNr = 0;
				for (int j = 1; j < (nStacks * 3) + 1; j += 4) {
					Character c = row.charAt(j);
					arrlist.add(c);
					if (c != ' ') {
						stacks.get(stackNr).add(c);
					}
					stackNr++;
					if (stackNr > nStacks - 1)
						stackNr = 0;
				}
			}

			scanner.nextLine();

			while (scanner.hasNext()) {
				String instruction = scanner.nextLine();
				String sInstruction = instruction.replaceAll("[[a-zA-Z]+.*[a-zA-Z]+.*[a-zA-Z]+]", "").strip();
				String[] strArr = sInstruction.split("\\s+");
				ArrayList<Integer> intArr = new ArrayList<Integer>();
				for (String s : strArr) {
					if (s.length() > 0) {
						intArr.add(Integer.valueOf(s));
					}
				}
				instructions.add(intArr);
			}

			

			if (TEST) {
				// Test stacks
				for (ArrayList<Character> charArr : stacks) {
					for (Character c : charArr) {
						System.out.print(c + " ");
					}
					System.out.println();
				}
				// Test instructions
				for (ArrayList<Integer> intArr : instructions) {
					for (Integer i : intArr) {
						System.out.print(i + " ");
					}
					System.out.println();
				}
			}

			scanner.close();
		} catch (FileNotFoundException e) {
			System.out.println("Oops, file not found!");
			e.printStackTrace();
		}
	}
}
