package com.grant.datastructures.tree;

import java.util.Random;

public class App {
    public static void main(String[] args) throws InterruptedException {
	Tree<Long,Long> bst = new AVLTree<>();
	Tree<Long,Long> bst2 = new BinarySearchTree<>();
	//bst.insert(10L);
	//bst.insert(15L);
	//bst.insert(5L);
	//bst.insert(14L);
	//bst.remove(5L);
	//bst.printTraverse();
	//Tree<Long,Long> bst = new BinarySearchTree<>();
	//Tree<String,Long> bst = new BinarySearchTree<>();
	//test
	/*

	bst.insert("G");
	bst.insert("A");
	bst.insert("H");
	bst.insert("F");
	bst.insert("Z");
	bst.insert("C");
	bst.insert("B");
	bst.insert("1");
	bst.printTraverse();
	System.out.println(bst.getSize());
	System.out.println();
	bst.remove("G");
	bst.printTraverse();
	System.out.println(bst.getSize());
	System.out.println();
	bst.remove("1");
	bst.remove("C");
	bst.remove("A");
	bst.printTraverse();
	System.out.println(bst.getSize());
	System.out.println();
	System.out.println();
	Object[] values=bst.toArray();
	for(int i=0;i<values.length;i++) {
	    System.out.println(values[i]);
	}*/
	
	//Add Items to BST
	
	Random rand = new Random();
	long startTime = System.currentTimeMillis();
		
	System.out.println("begin");
	startTime = System.currentTimeMillis();
	
	long x=0;
	for(long i=1;i<=10000000;i++) {
	    x=rand.nextLong();
	    bst.insert(x,i);
	    bst2.insert(x,i);
	}
	long endTime = System.currentTimeMillis();
	System.out.println("done");
	System.out.println("Last key inserted:"+x);
	System.out.println("Total execution time: " + (endTime - startTime) );
	Thread.sleep(1000);
	
	//BST Search
	System.out.println("1)Starting find of last key inserted");
	startTime = System.currentTimeMillis();
	System.out.println("1)Last key inserted data:"+bst.search(x));
	System.out.println("1)Op Count:" + BinarySearchTree.opcount);
	endTime = System.currentTimeMillis();
	System.out.println("done");
	System.out.println("Total execution time: " + (endTime - startTime) );
	Thread.sleep(1000);
	
	BinarySearchTree.opcount=0;
	System.out.println("2)Starting find of last key inserted");
	startTime = System.currentTimeMillis();
	System.out.println("2)Last key inserted data:"+bst2.search(x));
	System.out.println("2)Op Count:" + BinarySearchTree.opcount);
	endTime = System.currentTimeMillis();
	System.out.println("done");
	System.out.println("Total execution time: " + (endTime - startTime) );
	Thread.sleep(1000);
	
	//Convert to array
	System.out.println("Array convert start");
	startTime = System.currentTimeMillis();
	Object[] arr=bst.toArray();
	endTime = System.currentTimeMillis();
	System.out.println("done");
	System.out.println("Total execution time: " + (endTime - startTime) );
	
	//Brute force array find
	System.out.println("bruteforce find start");
	startTime = System.currentTimeMillis();
	long dataValue=0; 
	
	for(long i=0;i<arr.length;i++) {
	    dataValue=(long) arr[(int)i];
	    if (dataValue==x) {
		System.out.println("found dataValue:"+dataValue+ " at index: "+i);
		break;
	    }
	}
	endTime = System.currentTimeMillis();
	System.out.println("done");
	System.out.println("Total execution time: " + (endTime - startTime) );
	
    }
}
