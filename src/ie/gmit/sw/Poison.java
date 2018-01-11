package ie.gmit.sw;




/**
 * 
 * This class poisons the BlockingQueue, it indicates that there are no more shingles in the BlockingQueue
 * 
 * @author Javier Mantilla
 *
 */
public class Poison extends Shingle{
//  Constructors
	/**
	 * 
	 * @param docId Document id
	 * @param hashCode Hashcode for the poison, usually is 0
	 */
	public Poison(int docId, int hashCode) {
		super(docId, hashCode);
	}
	
} // class Poison
