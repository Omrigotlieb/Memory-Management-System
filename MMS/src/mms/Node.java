package mms;

/**
 * Represents a node in a doubly linked list. Each node points to a MemoryBlock object.
 */
public class Node {
	
	Node prev;
	Node next; 
	MemoryBlock block;
	/**
	 * Constructs a new node pointing to the given memory block
	 * 
	 * @param block
	 *        the given memory block
	 */
	public Node(MemoryBlock block) {
		
		// Setting next and prev for each Node link to null.
		this.block = block;
		this.prev = null; 
		this.next = null;
	}
	
	/**
	 * A textual representation of this node, useful for debugging.
	 */
	public String toString() {
		return "{" + block.toString() + "}";
	}
}