package com.grant.datastructures.tree;

public interface Tree<KEYTYPE extends Comparable<KEYTYPE>,DATATYPE> {
	long getSize();
	void insert(KEYTYPE key, DATATYPE data);
	void insert(KEYTYPE key);
	DATATYPE search(KEYTYPE searchKey);
	KEYTYPE minimum();
	KEYTYPE maximum();
	void printTraverse();
	void remove(KEYTYPE key);
	Object[] toArray();
}
