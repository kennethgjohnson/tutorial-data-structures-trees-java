package com.grant.datastructures.tree;

public class AVLTree<KEYTYPE extends Comparable<KEYTYPE>,DATATYPE> 
	extends BinarySearchTree <KEYTYPE,DATATYPE>
	implements Tree<KEYTYPE,DATATYPE>
{
    public AVLNode<KEYTYPE, DATATYPE> getRoot() {
        return (AVLNode<KEYTYPE,DATATYPE>)super.getRoot();
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

    private AVLNode<KEYTYPE,DATATYPE> insertNodeRecursively(AVLNode<KEYTYPE,DATATYPE> parentNode, KEYTYPE key, DATATYPE data) {
	// Have we reached an unset vertex?
	if (isUnsetNode(parentNode)) {
	    this.size++;
	    return new AVLNode<KEYTYPE,DATATYPE>(key,data);
	}

	int parentNodeDataComparisonValue = parentNode.compareTo(key);
	if (isNodeDataValueOnLeft(parentNodeDataComparisonValue)) {
	    parentNode.setLeftNode(insertNodeRecursively(parentNode.getLeftNode(), key,data));
	} else if (isNodeDataValueOnRight(parentNodeDataComparisonValue)) {
	    parentNode.setRightNode(insertNodeRecursively(parentNode.getRightNode(), key,data));
	}
	//AVL Node height property.
	parentNode.recalculateNodeHeight();
	return settleInsertBranchBalanceViolations(parentNode,key);
    }

    private AVLNode<KEYTYPE, DATATYPE> settleInsertBranchBalanceViolations(AVLNode<KEYTYPE, DATATYPE> branchRootNode, KEYTYPE targetedKey) {
	int branchBalance=getNodeBranchBalance(branchRootNode);
	if (isLeftLeftHeavyInsertScenario(branchRootNode, targetedKey, branchBalance)) { //Left-Left
	    branchRootNode=rotateBranchRight(branchRootNode);
	} else if (isLeftRightHeavyInsertScenario(branchRootNode, targetedKey, branchBalance)) { //Left-Right
	    branchRootNode.setLeftNode(rotateBranchLeft(branchRootNode.getLeftNode()));
	    branchRootNode=rotateBranchRight(branchRootNode);
	} else if (isRightRightHeavyInsertScenario(branchRootNode, targetedKey, branchBalance)) { //Right-Right
	    branchRootNode=rotateBranchLeft(branchRootNode);
	} else if (isRightLeftHeavyInsertScenario(branchRootNode, targetedKey, branchBalance)) { //Right-Left
	    branchRootNode.setRightNode(rotateBranchRight(branchRootNode.getRightNode()));
	    branchRootNode=rotateBranchLeft(branchRootNode);
	} 
	return branchRootNode;
    }

    private boolean isLeftLeftHeavyInsertScenario(AVLNode<KEYTYPE, DATATYPE> branchRootNode, KEYTYPE targetedKey,
	    int branchBalance) {
	return isBranchBalanceLeftHeavy(branchBalance) && (isNodeDataValueOnLeft(branchRootNode.getLeftNode().compareTo(targetedKey)));
    }

    private boolean isLeftRightHeavyInsertScenario(AVLNode<KEYTYPE, DATATYPE> branchRootNode, KEYTYPE targetedKey,
	    int branchBalance) {
	return isBranchBalanceLeftHeavy(branchBalance) && (isNodeDataValueOnRight(branchRootNode.getLeftNode().compareTo(targetedKey)));
    }
    
    private boolean isRightRightHeavyInsertScenario(AVLNode<KEYTYPE, DATATYPE> branchRootNode, KEYTYPE targetedKey,
	    int branchBalance) {
	return isBranchBalanceRightHeavy(branchBalance) && (isNodeDataValueOnRight(branchRootNode.getRightNode().compareTo(targetedKey)));
    }

    private boolean isRightLeftHeavyInsertScenario(AVLNode<KEYTYPE, DATATYPE> branchRootNode, KEYTYPE targetedKey,
	    int branchBalance) {
	return isBranchBalanceRightHeavy(branchBalance) && (isNodeDataValueOnLeft(branchRootNode.getRightNode().compareTo(targetedKey)));
    }
    
    private int getNodeBranchBalance(AVLNode<KEYTYPE, DATATYPE> node) {
	if (isUnsetNode(node)) return 0;
	return getNodeHeight(node.getLeftNode()) - getNodeHeight(node.getRightNode());
    }
    private int getNodeHeight(AVLNode<KEYTYPE, DATATYPE> node) {
	if (isUnsetNode(node)) return -1;
	return node.getHeight();
	
    }
    private boolean isBranchBalanceLeftHeavy(int branchBalance) {
	return branchBalance>1;
    }

    private AVLNode<KEYTYPE, DATATYPE> rotateBranchLeft(AVLNode<KEYTYPE, DATATYPE> originalBranchRoot) {
	//System.out.println("Rotating node to left:" +originalBranchRoot);
	AVLNode<KEYTYPE, DATATYPE> newRootNode=originalBranchRoot.getRightNode();
	AVLNode<KEYTYPE, DATATYPE> newRootsOriginalLeftNode=newRootNode.getLeftNode();
	
	originalBranchRoot.setRightNode(newRootsOriginalLeftNode);
	newRootNode.setLeftNode(originalBranchRoot);
	
	originalBranchRoot.recalculateNodeHeight();
	newRootNode.recalculateNodeHeight();
	
	return newRootNode;
    }
    
    private boolean isBranchBalanceRightHeavy(int branchBalance) {
	return branchBalance<-1;
    }
    
    private AVLNode<KEYTYPE, DATATYPE> rotateBranchRight(AVLNode<KEYTYPE, DATATYPE> originalBranchRoot) {
	//System.out.println("Rotating node to right:" +originalBranchRoot);
	AVLNode<KEYTYPE, DATATYPE> newRootNode=originalBranchRoot.getLeftNode();
	AVLNode<KEYTYPE, DATATYPE> newRootsOriginalRightNode=newRootNode.getRightNode();
	
	newRootNode.setRightNode(originalBranchRoot);
	originalBranchRoot.setLeftNode(newRootsOriginalRightNode);
	
	originalBranchRoot.recalculateNodeHeight();
	newRootNode.recalculateNodeHeight();
	
	return newRootNode;
    }

    @Override
    public void remove(KEYTYPE key) {
	if (!isEmptyTree()) {
	    setRoot(removeNodeRecursively(getRoot(), key));
	}
    }

    protected AVLNode<KEYTYPE,DATATYPE> removeNodeRecursively(AVLNode<KEYTYPE,DATATYPE> parentNode, KEYTYPE key) {
	//By Default the existing parent node shall remain in place.
	AVLNode<KEYTYPE,DATATYPE> returnNodeReference = parentNode; 
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
		returnNodeReference = (AVLNode<KEYTYPE,DATATYPE>)removeNode(parentNode);
	    }
	}
	if (!isUnsetNode(returnNodeReference)) {
	    returnNodeReference.recalculateNodeHeight();
	    returnNodeReference=settleRemoveBranchBalanceViolations(returnNodeReference,key);
	}
	return returnNodeReference;
    }
    
    @Override
    protected Node<KEYTYPE,DATATYPE> swapNodeWithPredecessor(Node<KEYTYPE,DATATYPE> node) {
	AVLNode<KEYTYPE,DATATYPE> predecessor = (AVLNode<KEYTYPE,DATATYPE>)getNodePredecessor(node);
	node.setKey(predecessor.getKey());
	node.setData(predecessor.getData());
	((AVLNode<KEYTYPE,DATATYPE>) node).setHeight(0);
	return predecessor;
    }
    
    private AVLNode<KEYTYPE, DATATYPE> settleRemoveBranchBalanceViolations(AVLNode<KEYTYPE, DATATYPE> branchRootNode, KEYTYPE targetedKey) {
	int branchBalance=getNodeBranchBalance(branchRootNode);
	if (isBranchBalanceLeftHeavy(branchBalance)) {
	    if (getNodeBranchBalance(branchRootNode.getLeftNode())<0) { //Left right
		branchRootNode.setLeftNode(rotateBranchLeft(branchRootNode.getLeftNode()));
	    }
	    branchRootNode=rotateBranchRight(branchRootNode);
	} else if (isBranchBalanceRightHeavy(branchBalance)) {
	    if (getNodeBranchBalance(branchRootNode.getRightNode())>0) { //right left
		branchRootNode.setRightNode(rotateBranchRight(branchRootNode.getRightNode()));
	    }
	    branchRootNode=rotateBranchLeft(branchRootNode);
	} 
	return branchRootNode;
    }
    
}
