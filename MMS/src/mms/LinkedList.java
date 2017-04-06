
package mms;

/**
 * Represents a list of Nodes. The list has a "first" pointer, which points to the first node in the list,
 * a "last" pointer, which points to the last node in the list, and a size, which is the number of nodes in the list.
 */
public class LinkedList {
	
    Node first;
    Node last;
    int size;

	/**
	 * Constructs a new doubly-connected linked list.
	 */ 
	public LinkedList () {	
		
		// Set the first and last to null.
		// Set default size of lists.
		this.first = null;
		this.last = null;
		this.size = 0;
	}
	
	/**
	 * Gets the node located at the given index in this list. 
	 * 
	 * @param index
	 *        the index of the node to retrieve, between 0 and size
	 * @throws IllegalArgumentException
	 *         if index is negative or greater than the list's size
	 * @return the node at the given index
	 */		
	public Node getNode(int index) {
		Node newNode = first;
		
		// Scan the nodes untill there's a match for index.
		// Otherwise, throws exception.
		if (index >= 0 && index < size) {
			for (int i = 0; i < index; i++) {
				newNode = newNode.next;
			}
		} else { 
			throw new IllegalArgumentException();
		}
		return newNode;
	}

	/**
	 * Creates a new Node object that points to the given memory block, and
	 * inserts the node to this list immediately prior to the given index
	 * (position in this list).
	 * <p>
	 * If the given index is 0, the new node becomes the first node in this
	 * list.
	 * <p>
	 * If the given index equals the size of this list, the new node becomes the
	 * last node in this list.
	 * 
	 * @param block
	 *            the memory block to be inserted into the list
	 * @param index
	 *            the index before which the memory block should be inserted
	 * @throws IllegalArgumentException
	 *             if index is negative or greater than the list's size
	 */
	public void add(int index, MemoryBlock block) {
		// Matching cases for Index (first block, last block or general block).
		if (index == 0) {
			addFirst(block);
		} else if (index == size) {
			addLast(block);
		} else {
			Node newNode = new Node(block);
			Node prevNode = this.getNode(index - 1);
			Node currentNode = this.getNode(index);
			prevNode.next = newNode;
			currentNode.prev = newNode;
			newNode.next = currentNode;
			newNode.prev = prevNode;
			size++;
		}
	}
	/**
	 * Creates a new node with a reference to the given memory block, and appends it to the end of this list
	 * (the node will become the list's last node).
	 * 
	 * @param block
	 *        the given memory block
	 */
	public void addLast(MemoryBlock block) {
		
		// relinking the block into 'last' location.
		if (last != null) {
			last.next = new Node(block);
			last.next.prev = last;
			last = last.next;
		} else {
			first = new Node(block);
			last = first;
		}
		size++;
	}
	
	/**
	 * Appends a node with the given block to the end of this list (the given
	 * block will become the list's last block). <b>This method is not an
	 * official part of the API and serves only testing purposes.</b>
	 * 
	 * @param block
	 *            the given memory block
	 */
	/*
	 * Given test method, for test purposes only !! 
	 * public void addLastTest(MemoryBlock block) { addLast(block); }
	 */

	/**
	 * Creates a new node with a reference to the given memory block, and inserts it at the beginning of this list
	 * (the node will become the list's first node).
	 * 
	 * @param block
	 *        the given memory block
	 */
	
	public void addFirst(MemoryBlock block) {
		
		// relinking blocks into 'first' location.
		if (first != null) {
		first.prev = new Node(block);
		first.prev.next = first;
		first = first.prev;
		} else {
			first = new Node(block);
			last = first;
		}
		size++;
	}

	/**
	 * Gets the memory block located at the given index in this list.
	 * 
	 * @param index
	 *        the index of the retrieved memory block
	 * @return the memory block at the given index
	 * @throws IllegalArgumentException
	 *         if index is negative or greater than or equal to size
	 */
	public MemoryBlock getBlock(int index) {
		return this.getNode(index).block;
	}	

	/**
	 * Gets the index of the node pointing to the given memory block.
	 * 
	 * @param block
	 *        the given memory block
	 * @return the index of the block, or -1 if the block is not in this list
	 */
	public int indexOf(MemoryBlock block) {
		
		// Unifying return value for loop iteration.
		int retValue = -1;
		for (int i = 0; i < size && retValue == -1; i++) {
			if (getBlock(i) == block) {
				retValue = i;
			}
		}
		return retValue;
	}

	/**
	 * Removes the given node from this list.	
	 * 
	 * @param node
	 *        the node that will be removed from this list
	 */
	public void remove(Node node) {
		
		// Cases for node:
		// 1. node doesn't exist throw exception.
		if (node == null) {
			throw new IllegalArgumentException();
		
		// 2. Remove node from a list with only a single node.
		} else if (size == 1) {
			first = null;
			last = null;
			size = 0;
			return;
		
		// 3. Removes the last node.
		} else if  (node == last) {
			last.prev.next = null;
			last = node.prev;

		// 4. Removes the first node.
		} else if (node == first) {
			first.next.prev = null;
			first = node.next;
			
		// 5. Default node removal.
		} else {
			node.next.prev = node.prev;
			node.prev.next = node.next;
		}
		
		// Adjusting size of list accordingly.
		size = (size > 0) ? size - 1 : size;
	}

	/**
	 * Removes from this list the node which is located at the given index.
	 * 
	 * @param index the location of the node that has to be removed.
	 * @throws IllegalArgumentException
	 *         if index is negative or greater than or equal to size
	 */
	public void remove(int index) {
		remove(this.getNode(index));
	}

	/**
	 * Removes from this list the node pointing to the given memory block.
	 * 
	 * @param block the memory block that should be removed from the list
	 * @throws IllegalArgumentException
	 *         if the given memory block is not in this list
	 */
	public void remove(MemoryBlock block) {
		remove(this.indexOf(block));
	}	

	/**
	 * A textual representation of this list, useful for debugging.
	 */
	public String toString() {
		StringBuilder newString = new StringBuilder();
		newString.append("[ ");

		// Using loop to add every node string presentation to a LinkedList one.
		for (Node iter = first; iter != null; iter = iter.next) {
			newString.append(iter.toString() + " ");
		}
		newString.append("]" + System.lineSeparator());
		return newString.toString();
	}
}