import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

class day1 {
	public static void main(String[] args) {
		try {
			File file = new File("input.txt");
			Scanner scanner = new Scanner(file);
			ArrayList<Integer> total_calories = new ArrayList<Integer>();
			Integer calories_per_elf = 0;

			while (scanner.hasNextLine()) {
				String data = scanner.nextLine();
				if (data == "") {
					total_calories.add(calories_per_elf);
					calories_per_elf = 0;
				} else {
					calories_per_elf += Integer.valueOf(data);
				}
			}
			scanner.close();
			Collections.sort(total_calories, Collections.reverseOrder());

			// part 1
			Integer most = total_calories.get(0);
			System.out.println("Most calories are: " + most);

			// part 2
			Integer topThree = 0;
			for (int i=0; i<3; ++i) {
				topThree += total_calories.get(i);
			}
			System.out.println("Top three carrying: " + topThree);
		} catch (FileNotFoundException e) {
			System.out.println("Error: file not found.");
			e.printStackTrace();
		}
	}
}
