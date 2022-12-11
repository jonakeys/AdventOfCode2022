package day11;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

class Day11
{
	public static void main(String[] args) throws FileNotFoundException {
		File file = new File("day11/input.txt");
		Scanner scanner = new Scanner(file);
		ArrayList<Monkey> monkeys = new ArrayList<Monkey>();

		while (scanner.hasNextLine()) {
			// Read all monkey info
			int nMonkey = Integer.valueOf(scanner.nextLine().substring(7, 8));
			ArrayList<Integer> monkeyItems = new ArrayList<Integer>();
			String[] sItems = scanner.nextLine().split(" ");
			for (int i = 4; i < sItems.length; ++i) {
				sItems[i] = sItems[i].replace(',' , ' ');
				sItems[i] = sItems[i].trim();
				monkeyItems.add(Integer.valueOf(sItems[i]));
			}
			String[] sOperation = scanner.nextLine().split(" ");
			char cOperation = sOperation[sOperation.length-2].charAt(0);
			String sOperand = sOperation[sOperation.length-1];
			String[] sTest = scanner.nextLine().split(" ");
			int nTest = Integer.valueOf(sTest[sTest.length-1]);
			String[] sTrue = scanner.nextLine().split(" ");
			String[] sFalse = scanner.nextLine().split(" ");
			int nTrue = Integer.valueOf(sTrue[sTrue.length-1]);
			int nFalse = Integer.valueOf(sFalse[sFalse.length-1]);

			// Create and add monkey
			monkeys.add(new Monkey(cOperation, sOperand, nTest, nTrue, nFalse));
			for (int i: monkeyItems) {
				monkeys.get(nMonkey).receiveItem(new Item(i));
			}
			if (scanner.hasNextLine()) scanner.nextLine();
		}
		scanner.close();

		for (int j = 0; j < 20; ++j) {
			for (Monkey m : monkeys) {
				ArrayList<Item> items = m.getItems();
				ArrayList<Item> throwItems = new ArrayList<Item>();
				for (Item i : items) {
					throwItems.add(m.updateItem(i, m.operation(i.getWorryLvl())));
				}
				m.updateItems(throwItems);
				for (Item i : throwItems) {
					int toMonkey = m.test(i.getWorryLvl()) ? m.getIfTrue() : m.getIfFalse();
					m.throwItem(i, monkeys.get(toMonkey));
				}
			}
		}

		int maxIns1 = 0;
		int maxIns2 = 0;
		for (Monkey m : monkeys) {
			if (m.getInspected() > maxIns1) {
				maxIns2 = maxIns1;
				maxIns1 = m.getInspected();
			}
			else if (m.getInspected() > maxIns2) {
				maxIns2 = m.getInspected();
			}
		}
		System.out.println("Monkey business: " + (maxIns1 * maxIns2));
	}
}
