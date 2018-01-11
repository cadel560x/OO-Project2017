package ie.gmit.sw;




/**
 * 
 * Shingle is a n-gram, contiguous subsequence of tokens in a document.
 * 
 * @author Javier Mantilla
 *
 */
public class Shingle {
//  Fields
	private int docId;
	private int hashCode;
	
	
	
	
//	Constructors
	public Shingle() {
		
	}

	/**
	 * 
	 * @param docId Document id
	 * @param hashCode Binding numerical representation of the shingle 
	 */
	public Shingle(int docId, int hashCode) {
		this.docId = docId;
		this.hashCode = hashCode;
	}
	
	
	
	
//	Accessors and mutators
	/**
	 * 
	 * @return Document id 
	 */
	public int getDocId() {
		return docId;
	}

	/**
	 * 
	 * @param docId Document id
	 */
	public void setDocId(int docId) {
		this.docId = docId;
	}

	/**
	 * 
	 * @return Binding numerical representation of the shingle
	 */
	public int getHashCode() {
		return hashCode;
	}
	
	/**
	 * 
	 * @param hashCode Binding numerical representation of the shingle
	 */
	public void setHashCode(int hashCode) {
		this.hashCode = hashCode;
	}
	
} // class Shingle
