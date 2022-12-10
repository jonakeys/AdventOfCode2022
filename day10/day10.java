import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

class day10
{
	private static int x = 1;
	private static int sumSignal = 0;
	private static char[][] crtScreen = new char[6][40]; 
	
	public static void main(String[] args) throws FileNotFoundException {
		File file = new File("input.txt");
		Scanner scanner = new Scanner(file);
		int cycle = 1;

		for (int i = 0; i < 6; ++i) {
			for (int j = 0; j < 40; ++j) {
				crtScreen[i][j] = '.';
			}
		}
			
		while (scanner.hasNext()) {
			String[] instruction = scanner.nextLine().split(" ");
			switch (instruction[0]) {
			case "noop":
				checkSignalStrength(cycle);
				drawScreen(cycle);
				cycle++;
				break;
			case "addx":
				int addition = Integer.valueOf(instruction[1]);
				for (int i = 0; i < 2; ++i) {
					checkSignalStrength(cycle);
					drawScreen(cycle);
					cycle++;
				}
				x += addition;
				break;
			}
		}
		scanner.close();

		// Part 1
		System.out.println("Sum signal strengths: " + sumSignal);

		// Part 2
		for (int i = 0; i < 6; ++i) {
			for (int j = 0; j < 40; ++j) {
				System.out.print(crtScreen[i][j]);
			}
			System.out.println();
		}
	}

	static void checkSignalStrength(int cycle) {
		if ((cycle == 20) || ((cycle - 20) % 40) == 0) {
			sumSignal += (x * cycle);
			//System.out.println(cycle + " cycles: " + sumSignal + " x: " + x);
		}
	}

	static void drawScreen(int cycle) {
		cycle = cycle - 1;
		int[] sprite = new int[3];
		int row = 0;
		int column = cycle;
		
		if (cycle >= 40 && cycle < 80) {
			row = 1;
			column = cycle - 40;
		}
		else if (cycle >= 80 && cycle < 120) {
			row = 2;
			column = cycle - 80;
		}
		else if (cycle >= 120 && cycle < 160) {
			row = 3;
			column = cycle - 120;
		}
		else if (cycle >= 160 && cycle < 200) {
			row = 4;
			column = cycle - 160;
		}
		else if (cycle >= 200 && cycle < 240) {
			row = 5;
			column = cycle - 200;
		}
		
		sprite[0] = x - 1;
		sprite[1] = x;
		sprite[2] = x + 1;
		if (sprite[0] == column || sprite[1] == column || sprite[2] == column) {
			crtScreen[row][column] = '#';
		}
	}
}
