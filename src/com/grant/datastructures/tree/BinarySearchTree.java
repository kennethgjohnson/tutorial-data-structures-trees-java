package com.grant.datastructures.tree;

public class BinarySearchTree<KEYTYPE extends Comparable<KEYTYPE>,DATATYPE> implements Tree<KEYTYPE,DATATYPE> {
    public static int opcount = 0;
    private Node<KEYTYPE,DATATYPE> root;
    protected Node<KEYTYPE, DATATYPE> getRoot() {
        return root;
    }

    protected void setRoot(Node<KEYTYPE, DATATYPE> root) {
        this.root = root;
    }

    private long size = 0;

    @Override
    public long getSize() {
	return size;
    }

    @Override
    public void insert(KEYTYPE key, DATATYPE data) {
	setRoot(insertNodeRecursively(getRoot(), key, data));
    }

    private Node<KEYTYPE,DATATYPE> insertNodeRecursively(Node<KEYTYPE,DATATYPE> parentNode, KEYTYPE key, DATATYPE data) {
	// Have we reached an unset edge?
	if (isUnsetNode(parentNode)) {
	    this.size++;
	    return new Node<KEYTYPE,DATATYPE>(key,data);
	}

	int parentNodeDataComparisonValue = parentNode.compareTo(key);
	if (isNodeDataValueOnLeft(parentNodeDataComparisonValue)) {
	    parentNode.setLeftNode(insertNodeRecursively(parentNode.getLeftNode(), key,data));
	} else if (isNodeDataValueOnRight(parentNodeDataComparisonValue)) {
	    parentNode.setRightNode(insertNodeRecursively(parentNode.getRightNode(), key,data));
	}
	return parentNode;
    }

    protected boolean isUnsetNode(Node<KEYTYPE,DATATYPE> node) {
	return node == null;
    }
    protected boolean isNodeDataValueOnLeft(int nodeDataComparisonValue) {
	// if the data of the parent node is a positive value it is a left node.
	return nodeDataComparisonValue > 0;
    }
    protected boolean isNodeDataValueOnRight(int nodeDataComparisonValue) {
	return nodeDataComparisonValue < 0;
    }

    @Override
    public void insert(KEYTYPE key) {
	insert(key,null);
    }
    

    @Override
    public DATATYPE search(KEYTYPE searchKey) {
	return searchNodesRecursive(getRoot(),searchKey);
    }

    private DATATYPE searchNodesRecursive(Node<KEYTYPE, DATATYPE> node, KEYTYPE searchKey) {
	if (node==null) throw new NoNodeWithKeyException();
	
	int nodeCompareToSearchKey=node.compareTo(searchKey);
	if (isNodeDataValueOnLeft(nodeCompareToSearchKey)) {
	    BinarySearchTree.opcount++;
	    return searchNodesRecursive(node.getLeftNode(), searchKey);
	} else if (isNodeDataValueOnRight(nodeCompareToSearchKey)) {
	    BinarySearchTree.opcount++;
	    return searchNodesRecursive(node.getRightNode(), searchKey);
	} else {
	    //we found the node.
	    return node.getData();
	}
    }

    @Override
    public KEYTYPE minimum() {
	if (getRoot()==null) throw new EmptyTreeException();
	return getMinimumNodeValueRecursively(getRoot());
    }

    private KEYTYPE getMinimumNodeValueRecursively(Node<KEYTYPE,DATATYPE> node) {
	if (node.getLeftNode() != null) {
	    return getMinimumNodeValueRecursively(node.getRightNode());
	} else {
	    // we found the min
	    return node.getKey();
	}
    }
    
    @Override
    public KEYTYPE maximum() {
	if (getRoot()==null) throw new EmptyTreeException();
	return getMaximumNodeValueRecursively(getRoot());
    }
    
    private KEYTYPE getMaximumNodeValueRecursively(Node<KEYTYPE,DATATYPE> node) {
	if (node.getRightNode() != null) {
	    return getMaximumNodeValueRecursively(node.getRightNode());
	} else {
	    // we found the max
	    return node.getKey();
	}
    }
    
    @Override
    public void printTraverse() {
	if (isEmptyTree())
	    return;
	printInOrderTraversedNodes(getRoot());
    }

    protected boolean isEmptyTree() {
	return getRoot() == null;
    }

    private void printInOrderTraversedNodes(Node<KEYTYPE,DATATYPE> node) {
	if (node.isLeftNodeSet())
	    printInOrderTraversedNodes(node.getLeftNode());
	System.out.println("=>" + node);
	if (node.isRightNodeSet())
	    printInOrderTraversedNodes(node.getRightNode());
    }

    @Override
    public void remove(KEYTYPE key) {
	if (!isEmptyTree()) {
	    setRoot(removeNodeRecursively(getRoot(), key));
	}
    }

    protected Node<KEYTYPE,DATATYPE> removeNodeRecursively(Node<KEYTYPE,DATATYPE> parentNode, KEYTYPE key) {
	//By Default the existing parent node shall remain in place.
	Node<KEYTYPE,DATATYPE> returnNodeReference = parentNode; 
	
	if (!isUnsetNode(parentNode)) {
	    int parentNodeDataLeftRightComparison = parentNode.compareTo(key);
	    if (isNodeDataValueOnLeft(parentNodeDataLeftRightComparison)) {
		parentNode.setLeftNode(removeNodeRecursively(parentNode.getLeftNode(), key));
		returnNodeReference=parentNode;
	    } else if (isNodeDataValueOnRight(parentNodeDataLeftRightComparison)) {
		parentNode.setRightNode(removeNodeRecursively(parentNode.getRightNode(), key));
		returnNodeReference=parentNode;
	    } else {
		// The node is not on the left or right, i.e. it is the node we are on.
		returnNodeReference = removeNode(parentNode);
	    }
	}
	return returnNodeReference;
    }

    /**
     * Removes a given node from the tree.
     * @param nodeToBeRemoved node targeted for removal
     * @return reference to node replacing the node to be removed - may return null if the node was a leaf node.
     */
    protected Node<KEYTYPE,DATATYPE> removeNode(Node<KEYTYPE,DATATYPE> nodeToBeRemoved) {
	if (nodeToBeRemoved.isLeafNode()) {
	    this.size--;
	    return null;
	} else {
	    if (!nodeToBeRemoved.isLeftNodeSet()) {
		this.size--;
		return nodeToBeRemoved.getRightNode();
	    } else if (!nodeToBeRemoved.isRightNodeSet()) {
		this.size--;
		return nodeToBeRemoved.getLeftNode();
	    } else {
		Node<KEYTYPE,DATATYPE> predecessor = swapNodeWithPredecessor(nodeToBeRemoved);
		nodeToBeRemoved.setLeftNode(removeNodeRecursively(nodeToBeRemoved.getLeftNode(), predecessor.getKey()));
		return nodeToBeRemoved;
	    }
	}
    }

    protected Node<KEYTYPE,DATATYPE> swapNodeWithPredecessor(Node<KEYTYPE,DATATYPE> node) {
	Node<KEYTYPE,DATATYPE> predecessor = getNodePredecessor(node);
	node.setKey(predecessor.getKey());
	node.setData(predecessor.getData());
	
	return predecessor;
    }

    protected Node<KEYTYPE,DATATYPE> getNodePredecessor(Node<KEYTYPE,DATATYPE> node) {
	return getNodePredecessorRecursively(node.getLeftNode());
    }

    private Node<KEYTYPE,DATATYPE> getNodePredecessorRecursively(Node<KEYTYPE,DATATYPE> node) {
	if (node.getRightNode() != null) {
	    return getNodePredecessorRecursively(node.getRightNode());
	} else {
	    // we found the predecessor
	    return node;
	}
    }
    
    @Override
    public Object[] toArray() {
	Object[] elementsArray=new Object[(int) getSize()];
	fillArrayWithInOrderTraversalRecursive(getRoot(),elementsArray,0);
	return elementsArray;
    }

    //Returns last index position
    private int fillArrayWithInOrderTraversalRecursive(Node<KEYTYPE,DATATYPE> node, Object[] elementsArray, int indexPosition) {
	if (node==null) return indexPosition;
	if (node.isLeftNodeSet())
	    indexPosition=fillArrayWithInOrderTraversalRecursive(node.getLeftNode(),elementsArray,indexPosition);
	//System.out.println("Adding Element into index:"+indexPosition);
	elementsArray[indexPosition++]=node.getKey();
	if (node.isRightNodeSet())
	    indexPosition=fillArrayWithInOrderTraversalRecursive(node.getRightNode(),elementsArray,indexPosition);
	return indexPosition;
    }
 
}

