
package mms;

/**
 * Represents a managed memory space
 */
public class MemorySpace {

	// A list representing all the memory blocks that are presently allocated
	LinkedList allocatedList;

	// A list representing all the memory blocks that are presently free
	LinkedList freeList;

	/**
	 * Constructs a new managed memory space of a given maximal size.
	 * Specifically, constructs an empty list of allocated blocks, and a free
	 * list containing a single block which represents the entire memory space.
	 * The base address of this single block is zero, and its length is the
	 * given memory size.
	 * 
	 * @param maxSize
	 *            the size of the memory space to be managed
	 */
	public MemorySpace(int maxSize) {
		this.allocatedList = new LinkedList();
		this.freeList = new LinkedList();
		MemoryBlock freeBlock = new MemoryBlock(0, maxSize);
		this.freeList.add(0, freeBlock);
	}

	/**
	 * Allocates a memory block of a requested length (in words). Returns the
	 * base address of the allocated block, or -1 if unable to allocate.
	 * 
	 * This implementation scans the freeList, looking for the first free memory
	 * block whose length equals at least the given length. If such a block is
	 * found, the method performs the following operations:
	 * 
	 * (1) A new memory block is constructed. The base address of the new block
	 * is set to the base address of the found free block. The length of the new
	 * block is set to the value of the method's length parameter.
	 * 
	 * (2) The new memory block is appended to the end of the allocatedList.
	 * 
	 * (3) The base address and the length of the found free block are updated,
	 * to reflect the allocation. For example, suppose that the requested block
	 * length is 17, and suppose that the base address and length of the the
	 * found free block are 250 and 20, respectively. In such a case, the base
	 * address and length of of the allocated block are set to 250 and 17,
	 * respectively, and the base address and length of the found free block are
	 * set to 267 and 3, respectively.
	 * 
	 * (4) The new memory block is returned (to the caller, which is typically a
	 * constructor).
	 * 
	 * If we are lucky to find a block whose length is EXCATLY that of the
	 * requested length, then the found block is removed from the freeList and
	 * appended to the allocatedList.
	 * 
	 * @param length
	 *            the length (in words) of the memory block that has to be
	 *            allocated
	 * @return the base address of the allocated block, or -1 if unable to
	 *         allocate
	 */
	public int malloc(int length) {
		int newBaseAddress = -1;
		MemoryBlock newBlock = new MemoryBlock(0, 0);

		// Scan for available memory in freeList with enough length to allocate.
		for (int i = 0; newBaseAddress == -1 && i < freeList.size; i++) {

			// When found the same exact size - transfer it as is to
			// allocatedList.
			if (freeList.getBlock(i).length == length) {
				allocatedList.addLast(freeList.getBlock(i));
				freeList.remove(freeList.getBlock(i));
				newBaseAddress = newBlock.baseAddress;

				// When found a block with a bigger size, create a new Block
				// with the requested length and add it to allocatedList
				// and removes the found block from freeList when it's done.
			} else if (freeList.getBlock(i).length > length) {
				newBlock.baseAddress = freeList.getBlock(i).baseAddress;
				newBlock.length = length;
				allocatedList.addLast(newBlock);
				freeList.getBlock(i).baseAddress += length;
				freeList.getBlock(i).length -= length;
				newBaseAddress = newBlock.baseAddress;
			}
		}
		return newBaseAddress;
	}

	/**
	 * Frees the memory block whose base address equals the given address:
	 * deletes the block whose base address equals the given address from the
	 * allocatedList, and adds it at the end of the free list.
	 * 
	 * @param address
	 *            the starting address of the block to freeList
	 */
	public void free(int address) {

		// Using a loop to find the block with the given address.
		// Create a new block in the freeList with the same address and length
		// and remove the block with the same base address from allocated list.
		for (int i = 0; i < allocatedList.size; i++) {
			if (allocatedList.getBlock(i).baseAddress == address) {
				MemoryBlock freeBlock = new MemoryBlock(
						allocatedList.getBlock(i).baseAddress,
						allocatedList.getBlock(i).length);
				freeList.addLast(freeBlock);
				allocatedList.remove(allocatedList.getBlock(i));
			}
		}
	}

	/**
	 * A textual representation of the current states of the free list and the
	 * allocated list, using some sensible and easy to read format.
	 */
	public String toString() {
		return "Free List: " + freeList.toString() + System.lineSeparator()
				+ "Allocated List: " + allocatedList.toString();
	}
}