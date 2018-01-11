package ie.gmit.sw;




public class Shingle {
//	Fields
	private int docId;
	private int hashCode;
	
	
	
	
//	Constructors
	public Shingle() {
		
	}

	public Shingle(int docId, int hashCode) {
		this.docId = docId;
		this.hashCode = hashCode;
	}

	
	
	
//	Accessors and mutators
	public int getDocId() {
		return docId;
	}

	public void setDocId(int docId) {
		this.docId = docId;
	}

	public int getHashCode() {
		return hashCode;
	}

	public void setHashCode(int hashCode) {
		this.hashCode = hashCode;
	}

	
	
	
//	Methods
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + hashCode;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Shingle other = (Shingle) obj;
		if (hashCode != other.hashCode) {
			return false;
		}
		return true;
	}
	

} // class Shingle
