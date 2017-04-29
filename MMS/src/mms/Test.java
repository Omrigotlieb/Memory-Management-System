package mms;

public class Test {
	public static void main (String[] args) {
		// Put your testing code here.
		// For example, the following code creates a linked list and populates it with some test nodes.
		LinkedList testList = new LinkedList();
		MemoryBlock b1 = new MemoryBlock(10,5);
		MemoryBlock b2 = new MemoryBlock(20,10);
		MemoryBlock b3 = new MemoryBlock(30,20);
		MemoryBlock b4 = new MemoryBlock(40,25);
		MemoryBlock b5 = new MemoryBlock(45,35);
		testList.addLastTest(b1);
		testList.addLastTest(b2);
		testList.addLastTest(b3);
		testList.addLastTest(b4);
		testList.addLastTest(b5);
		System.out.println(testList.toString());
		testList.remove(5);
		System.out.println(testList.toString());
	}
}