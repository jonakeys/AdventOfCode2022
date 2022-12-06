import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

class day6
{
	public static void main(String[] args) {
		File file = new File("input.txt");
		try {
			Scanner scanner = new Scanner(file);
			int afterChar = 0;
			String serie = "";
			while (scanner.hasNextLine()) {
				String input = scanner.nextLine();
				for (int i = 0; i < input.length()-4; ++i) {
					String res = input.substring(i, i+4);
					afterChar = i+4;
					if (res.chars().distinct().count() == 4) {
						serie = res;
						break;
					}
				}
				System.out.println("Found: " + serie + " after "
								   + afterChar + " characters.");
			}
			scanner.close();
		}
		catch (FileNotFoundException e) {
			System.out.println("File not found!");
			e.printStackTrace();
		}
	}
}
