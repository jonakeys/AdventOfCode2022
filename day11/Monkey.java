package day11;
import java.util.ArrayList;

class Monkey
{
	private ArrayList<Item> items;
	private char operation;
	private int operand;
	private int divisibleBy;
	private int ifTrue;
	private int ifFalse;
	private int inspected;

	public Monkey(char op, String sOp, int div, int ifT, int ifF) {
		this.items = new ArrayList<Item>();
		this.operation = op;
		if (sOp.equals("old")) {
			this.operand = -1;
		}
		else {
			this.operand = Integer.valueOf(sOp);
		}
		this.divisibleBy = div;
		this.ifTrue = ifT;
		this.ifFalse = ifF;
	}

	public int getIfTrue() {
		return ifTrue;
	}

	public int getIfFalse() {
		return ifFalse;
	}

	public ArrayList<Item> getItems() {
		return items;
	}

	public int getInspected() {
		return inspected;
	}

	public void receiveItem(Item i) {
		items.add(i);
	}

	public Item updateItem(Item i, int newWorryLvl) {
		for (int j = 0; j < items.size(); ++j) {
			if (i.getWorryLvl() == items.get(j).getWorryLvl()) {
				return new Item(newWorryLvl / 3);
			}
		}
		return new Item(0);
	}
	
	public void updateItems(ArrayList<Item> newItems) {
		items.clear();
		for (Item i : newItems) {
			items.add(i);
		}
	}
	
	public void throwItem(Item i, Monkey m) {
		for (int j = 0; j < items.size(); ++j) {
				if (i.getWorryLvl() == items.get(j).getWorryLvl()) {
				m.receiveItem(items.get(j));
				items.remove(j);
				break;
			}
		}
	}

	public boolean test(int worryLvl) {
		return ((worryLvl % divisibleBy) == 0);
	}

	public int operation(int worryLvl) {
		inspected++;
		int nOperand = this.operand;
		if (this.operand == -1) {
			nOperand = worryLvl;
		}
		if (operation == '+') {
			return worryLvl + nOperand;
		}
		else {
			return worryLvl * nOperand;
		}
	}

	public void printMonkeyInfo() {
		System.out.println("Monkey");
		for (Item i : items) {
			System.out.print(i.getWorryLvl() + " ");
		}
		System.out.println();
		System.out.println(operation + " " + operand);
		System.out.println("Div by: " + divisibleBy);
		System.out.println("True: " + ifTrue + " False: " + ifFalse);
		System.out.println();
	}

	public void printItems() {
		for (Item i : items) {
			System.out.print(i.getWorryLvl() + " ");
		}
		System.out.println();			
	}
}
