import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

class day03 {
	public static void main(String[] args) {
		try {
			File file = new File("input.txt");
			Scanner scanner = new Scanner(file);
			ArrayList<Character> itemTypes = new ArrayList<Character>();
			ArrayList<Character> itemBadges = new ArrayList<Character>();
			ArrayList<Character> line0 = new ArrayList<Character>();
			ArrayList<Character> line1 = new ArrayList<Character>();
			ArrayList<Character> line2 = new ArrayList<Character>();
			int lineCount = 0;
			long sumItems = 0;
			long sumBadges = 0;
			
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				String firstHalf = line.substring(0, line.length() / 2);
				String secondHalf = line.substring(line.length() / 2);
				ArrayList<Character> items = new ArrayList<Character>();

				// part 1
				for (int i = 0; i < firstHalf.length(); ++i) {
					int charIndex = secondHalf.indexOf(firstHalf.charAt(i));
					if (charIndex >= 0) {
						Character c = firstHalf.charAt(i);
						if (!items.contains(c)) {
							items.add(c);
							itemTypes.add(c);
						}
					}
				}

				// part 2
				if (lineCount == 0) {
					for (int i = 0; i < line.length(); ++i) {
						if (!line0.contains(line.charAt(i)))
							line0.add(line.charAt(i));
					}
				}
				else if (lineCount == 1) {
					for (int i = 0; i < line.length(); ++i) {
						if (!line1.contains(line.charAt(i)))
							line1.add(line.charAt(i));
					}
				}
				else {
					for (int i = 0; i < line.length(); ++i) {
						if (!line2.contains(line.charAt(i)))
							line2.add(line.charAt(i));
					}
				}

				if (lineCount < 2) {
					++lineCount;
				}
				else {
					lineCount = 0;
					for (Character c : line0) {
						for (Character d : line1) {
							for (Character e : line2) {
								if (c == d) {
									if (d == e) {
										itemBadges.add(e);
									}
								}
							}
						}
					}
					line0.clear();
					line1.clear();
					line2.clear();
				}
			}
			scanner.close();

			for (Character c : itemTypes) {
				if (Character.isLowerCase(c))
					sumItems += (c - 96);
				else
					sumItems += (c - 38);
			}

			for (Character c : itemBadges) {
				if (Character.isLowerCase(c))
					sumBadges += (c - 96);
				else
					sumBadges += (c - 38);
			}

			// part 1
			System.out.println("Sum priorities items: " + sumItems);

			// part 2
			System.out.println("Sum priorities badges: " + sumBadges);
			
		} catch (FileNotFoundException e) {
			System.out.println("Error: file not found.");
			e.printStackTrace();
		}
	}
}
