import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

class day02
{
	public static void main(String[] args) {
		try {
			int scorePt1 = 0;
			int scorePt2 = 0;
			
			File file = new File("input.txt");
			Scanner scanner = new Scanner(file);
			while(scanner.hasNextLine()) {
				String round = scanner.nextLine();
				scorePt1 += scorePerRoundPt1(round.charAt(0), round.charAt(2));
				scorePt2 += scorePerRoundPt2(round.charAt(0), round.charAt(2));
			}
			scanner.close();
			
			// part 1
			System.out.println("Score part 1: " + scorePt1);
			// part 2
			System.out.println("Score part 2: " + scorePt2);
		} catch (FileNotFoundException e) {
			System.out.println("Error: file not found.");
			e.printStackTrace();
		}
	}

	private static int scorePerRoundPt1(char opp, char pla) {
		int score = 0;
		boolean draw = false;

		// rock = 1, paper = 2, scissors = 3
		// lost = 0, draw = 3, win = 6
		// rock = A, X
		// paper = B, Y
		// scissors = C, Z

		// Add points for used item by player
		switch(pla) {
		case 'X':
			score += 1;
			break;
		case 'Y':
			score += 2;
			break;
		case 'Z':
			score += 3;
			break;
		default:
		}

		// Add points for draw or win
		switch(opp) {
		case 'A':
			switch (pla) {
			case 'Y':
				score += 6;
				break;
			case 'X':
				draw = true;
			}
			break;
		case 'B':
			switch (pla) {
			case 'Z':
				score += 6;
				break;
			case 'Y':
				draw = true;
			}
			break;
		case 'C':
			switch (pla) {
			case 'X':
				score += 6;
				break;
			case 'Z':
				draw = true;
			}
			break;
		default:
		}

		if (draw) score += 3;
		
		return score;
	}

	private static int scorePerRoundPt2(char opp, char end) {
		int score = 0;
		char pla = ' ';

		// rock = 1, paper = 2, scissors = 3
		// lost = 0, draw = 3, win = 6
		// rock = A, paper = B, scissors = C
		// lose = X, draw = Y, win = Z
		
		// Add points for draw or win
		switch (opp) {
		case 'A':
			switch (end) {
			case 'X':
				pla = 'S';
				break;
			case 'Y':
				pla = 'R';
				score += 3;
				break;
			case 'Z':
				pla = 'P';
				score += 6;
			}
			break;
		case 'B':
			switch (end) {
			case 'X':
				pla = 'R';
				break;
			case 'Y':
				pla = 'P';
				score += 3;
				break;
			case 'Z':
				pla = 'S';
				score += 6;
			}
			break;
		case 'C':
			switch (end) {
			case 'X':
				pla = 'P';
			case 'Y':
				pla = 'S';
				score += 3;
				break;
			case 'Z':
				pla = 'R';
				score += 6;
			}
			break;
		default:
		}
		
		// Add points for used item by player
		switch(pla) {
		case 'R':
			score += 1;
			break;
		case 'P':
			score += 2;
			break;
		case 'S':
			score += 3;
			break;
		default:
		}
		
		return score;
	}
}
