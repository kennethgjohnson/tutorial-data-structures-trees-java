package com.grant.datastructures.tree;

public class Node<KEYTYPE extends Comparable<KEYTYPE>,DATATYPE> implements Comparable<KEYTYPE> {
    private KEYTYPE key;
    private DATATYPE data;
    
    private Node<KEYTYPE,DATATYPE> leftNode;

    private Node<KEYTYPE,DATATYPE> rightNode;

    public Node(KEYTYPE key, DATATYPE data) {
	setKey(key);
	setData(data);
    }

    public KEYTYPE getKey() {
	return key;
    }

    public void setKey(KEYTYPE data) {
	this.key = data;
    }

    public Node<KEYTYPE,DATATYPE> getLeftNode() {
	return leftNode;
    }

    public void setLeftNode(Node<KEYTYPE,DATATYPE> leftNode) {
	this.leftNode = leftNode;
    }

    public Node<KEYTYPE,DATATYPE> getRightNode() {
	return rightNode;
    }

    public void setRightNode(Node<KEYTYPE,DATATYPE> rightNode) {
	this.rightNode = rightNode;
    }

    public boolean isLeftNodeSet() {
   	return leftNode!=null;
    }
    
    public boolean isRightNodeSet() {
   	return rightNode!=null;
    }
    
    public boolean isLeafNode() {
	return (!isLeftNodeSet()) && (!isRightNodeSet());
    }
    
    @Override
    public String toString() {
	return key.toString();
    }

    @Override
    public int compareTo(KEYTYPE o) {
	return getKey().compareTo(o);
    }

    public DATATYPE getData() {
	return data;
    }

    public void setData(DATATYPE data) {
	this.data = data;
    }
}
