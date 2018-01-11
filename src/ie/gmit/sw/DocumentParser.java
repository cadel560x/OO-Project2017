package ie.gmit.sw;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Deque;
import java.util.LinkedList;
import java.util.concurrent.BlockingQueue;




/**
 * 
 * This class parses plaintext documents into shingles. Given the size of the shingle set by the field shingleSize.
 * It uses a LinkedList as a buffer where words from the document are temporarily  stored and then popped out by
 * groups of shingleSize. Then the shingle hashcode is calculated and offered to the BlockingQueue.
 * 
 * @author Javier Mantilla
 *
 */
public class DocumentParser implements Runnable{
//	Fields
	private String file;
	private int shingleSize;
	private BlockingQueue<Shingle> blockingQueue;
	private Deque<String> buffer = new LinkedList<>();
	private int docId;
	
	
	
	
//	Constructors
	public DocumentParser(String file, int shingleSize, BlockingQueue<Shingle> blockingQueue, int docId) {
		this.file = file;
		this.shingleSize = shingleSize;
		this.blockingQueue = blockingQueue;
		this.docId = docId;
	}
	
	
	
	
//	Methods
	public void run() {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			String line = null;
			
			while((line = br.readLine())!= null) {
				if ( line.length() > 0 ) {
					String uLine = line.toUpperCase();
					String [] words = uLine.split("\\s+");
					
					addWordsToBuffer(words);
					Shingle s = getNextShingle();
					blockingQueue.put(s);
				}
			} // while
			
			br.close();
			flushBuffer();
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		} // try - catch
		
	} // run
	
	
	private void addWordsToBuffer(String[] words) {
		for (String s: words) {
			buffer.add(s);
		}
		
	} // addWordsToBuffer
	
	
	private Shingle getNextShingle() {
		StringBuilder sb = new StringBuilder();
		int counter = 0;
		
		while(counter < shingleSize) {
			if(buffer.peek()!=null) {
				sb.append(buffer.poll());
				counter++;
			}
			else {
				counter = shingleSize;
			} // if - else
			
		} // while
		
		if(sb.length() > 0) {
			return (new Shingle(docId,sb.toString().hashCode()));
		}
		else {
			return null;
		} // if - else
		
	} // getNextShingle
	
	
	private void flushBuffer() throws InterruptedException{

		while(buffer.size() > 0) {
			Shingle s = getNextShingle();
			if(s != null) {
				blockingQueue.put(s);
			}
		}
		blockingQueue.put(new Poison(docId, 0));
		
	} // flushBuffer
	
} // class DocumentParser.java
