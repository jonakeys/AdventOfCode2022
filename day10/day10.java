import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

class day10
{
	private static int x = 1;
	private static int sumSignal = 0;
	
	public static void main(String[] args) throws FileNotFoundException {
		File file = new File("input.txt");
		Scanner scanner = new Scanner(file);
		int cycle = 1;
			
		while (scanner.hasNext()) {
			String[] instruction = scanner.nextLine().split(" ");
			switch (instruction[0]) {
			case "noop":
				checkSignalStrength(cycle);
				cycle++;
				break;
			case "addx":
				int addition = Integer.valueOf(instruction[1]);
				for (int i = 0; i < 2; ++i) {
					checkSignalStrength(cycle);
					cycle++;
				}
				x += addition;
				break;
			}
			
		}
		scanner.close();

		System.out.println("Sum signal strengths: " + sumSignal);
	}

	static void checkSignalStrength(int cycle) {
		if ((cycle == 20) || ((cycle - 20) % 40) == 0) {
			sumSignal += (x * cycle);
			System.out.println(cycle + " cycles: " + sumSignal + " x: " + x);
		}
	}
}
