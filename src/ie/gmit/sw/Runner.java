package ie.gmit.sw;

import java.util.Scanner;

public class Runner {
//	Entry point
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		StringBuilder sb = new StringBuilder();
		String file1, file2;
		int shingleSize;
		int k;
		
		sb.append("Object Oriented Programming Project 2017\n");
		sb.append("========================================\n\n");
		
		sb.append("Document similarity program using Jaccard index\n");
		sb.append("-----------------------------------------------\n\n");
		
		sb.append("Notice: Documents must be plain text files!!\n\n");
		
		sb.append("Please enter filepath of the first document: ");		
		System.out.print(sb.toString());
		
		file1 = scanner.nextLine();
		
		sb.setLength(0);
		sb.append("Please enter filepath of the second document: ");
		System.out.print(sb.toString());
		
		file2 = scanner.nextLine();
		
		sb.setLength(0);
		sb.append("Please enter the shingle size [words]: ");
		System.out.print(sb.toString());
		shingleSize = Integer.parseInt(scanner.nextLine());
		
		sb.setLength(0);
		sb.append("Please enter the shingle sample size: ");
		System.out.print(sb.toString());
		k = Integer.parseInt(scanner.nextLine());
		
		scanner.close();
		
		try {
			new Launcher().launch(file1, file2, shingleSize, k, 2);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} // try - catch
		
	} // main

} // class Runner
