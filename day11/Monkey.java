package day11;
import java.math.BigInteger;
import java.util.ArrayList;

class Monkey
{
	private final long SUPERMOD = 9699690; // input
	//private final long SUPERMOD = 96577; // test
	private ArrayList<Item> items;
	private char operation;
	private long operand;
	private long divisibleBy;
	private int ifTrue;
	private int ifFalse;
	private long inspected;

	public Monkey(char op, String sOp, long div, int ifT, int ifF) {
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

	public long getInspected() {
		return inspected;
	}

	public void receiveItem(Item i) {
		items.add(i);
	}

	public Item updateItem(Item i, long newWorryLvl) {
		for (int j = 0; j < items.size(); ++j) {
			if (i.getWorryLvl() == items.get(j).getWorryLvl()) {
				return new Item(newWorryLvl / 3);
			}
		}
		return new Item(0);
	}

	public Item updateItem2(Item i, long newWorryLvl) {
		for (int j = 0; j < items.size(); ++j) {
			if (i.getWorryLvl() == items.get(j).getWorryLvl()) {
				long ans = newWorryLvl % SUPERMOD;
				return new Item(ans);
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

	public boolean test(long worryLvl) {
		return ((worryLvl % divisibleBy) == 0);
	}

 	public long operation(long worryLvl) {
		inspected++;
		long nOperand = this.operand;
		
		if (this.operand == -1) {
			nOperand = worryLvl;
		}

		BigInteger supermod = BigInteger.valueOf(SUPERMOD);
		BigInteger a = BigInteger.valueOf(worryLvl).remainder(supermod);
		BigInteger b = BigInteger.valueOf(nOperand).remainder(supermod);
		
		if (operation == '+') {
			return a.add(b).longValue();
		}
		else {
			return a.multiply(b).longValue();
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
}
