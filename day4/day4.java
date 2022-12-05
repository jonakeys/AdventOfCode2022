import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.Scanner;
import java.util.HashSet;
import java.util.Set;

class day4 {
	public static void main(String[] args) {
		try {
			File file = new File("input.txt");
			Scanner scanner = new Scanner(file);
			int numberFullyContains = 0;
			int numberOverlap = 0;

			while (scanner.hasNext()) {
				Set<Integer> group1 = new HashSet<Integer>();
				Set<Integer> group2 = new HashSet<Integer>();
				String line = scanner.nextLine();
				String[] strList = line.split(",");
				String[] gr1 = strList[0].split("-");
				String[] gr2 = strList[1].split("-");
				for (int i = Integer.parseInt(gr1[0]);
					 i <= Integer.parseInt(gr1[1]); ++i) {
					group1.add(i);
				}
				for (int i = Integer.parseInt(gr2[0]);
					 i <= Integer.parseInt(gr2[1]); ++i) {
					group2.add(i);
				}
				if (group1.containsAll(group2) || group2.containsAll(group1)) {
					numberFullyContains++;
				}
				if (!Collections.disjoint(group1, group2)) {
					numberOverlap++;
				}
			}
			scanner.close();
			
			// part 1
			System.out.println("Number of fully contains: " + numberFullyContains);
			//part 2
			System.out.println("Number of overlaps: " + numberOverlap);
		} catch (FileNotFoundException e) {
			System.out.println("File not found!");
			e.printStackTrace();
		}
	}
}
