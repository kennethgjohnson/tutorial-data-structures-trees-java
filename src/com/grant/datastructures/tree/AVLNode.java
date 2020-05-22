package com.grant.datastructures.tree;

public class AVLNode<KEYTYPE extends Comparable<KEYTYPE>, DATATYPE> extends Node<KEYTYPE, DATATYPE> {

    private int height=0;
    
    public AVLNode(KEYTYPE key, DATATYPE data) {
	super(key, data);
    }

    public AVLNode<KEYTYPE,DATATYPE> getLeftNode() {
	return (AVLNode<KEYTYPE,DATATYPE>) super.getLeftNode();
    }


    public AVLNode<KEYTYPE,DATATYPE> getRightNode() {
	return (AVLNode<KEYTYPE,DATATYPE>) super.getRightNode();
    }
    
    public int getHeight() {
	return height;
    }

    public void setHeight(int height) {
	this.height = height;
    }

    public void recalculateNodeHeight() {
	setHeight(getCalculatedHeight());	
    }
    
    private int getCalculatedHeight() {
	return Math.max(
			getCalculatedNodeHeight(getLeftNode()),
			getCalculatedNodeHeight(getRightNode())
		)+1;
    }

    private int getCalculatedNodeHeight(AVLNode<KEYTYPE,DATATYPE> node) {
	return (isUnsetNode(node)) ? -1 : node.getHeight();
    }

    private boolean isUnsetNode(AVLNode<KEYTYPE, DATATYPE> node) {
	return node == null;
    }
}
