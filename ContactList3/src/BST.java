/*
File: ContactsBST.java
Author: Ram Longman
Date: 8/3/2023
Description: A binary search tree implementation of contacts
*/
public class BST<T extends Comparable<T>> {

	private Tree<T> root; //the root of the tree
	
	public BST() {
		root = null; //empty tree
	}
	
	public T Search(T t) {
		if (root == null) {	//empty tree
			return null;
		}
		else {
			//Compare the name the current contact's name.
			//If they are equal, contact found.
			//If the name is greater, move to the right subtree
			//If it's smaller move to the left subtree
			return recursiveSearch(root, t);
		}
	}
	
	public void Insert(T d) {
		
		if (root == null) {	//empty tree, insert the first element
			root = new Tree<>(d);
		}
		else {
			Tree<T> current = root;
			Tree<T> parent = null;
			Boolean traversing = true;

			//Search for correct location to insert the new contact
			//to be able to add the element as a child to the parent

			//insert new Tree in correct location
			while(traversing)
			{
				parent = current;

				if(d.compareTo(current.data) < 0)
				{
					current = current.left;
					if(current == null)
					{
						parent.left = new Tree<>(d);
						traversing = false;
					}
				}
				else
				{
					current = current.right;
					if(current == null)
					{
						parent.right = new Tree<>(d);
						traversing = false;
					}
				}
			}
		}
	}
	
	public void Print() {
		//Hint: use the PrintInOrder function
		PrintInOrder(root);

		System.out.println();
	}
	
	private void PrintInOrder(Tree<T> n) {
		//Hint: Recursion

		if(n == null)
		{
			return;
		}

		PrintInOrder(n.left);

		System.out.println(n.data);

		PrintInOrder(n.right);
	}
	
	public T Remove(T name) {
		
		if (root == null) {	//can't remove from an empty tree
			System.out.println("\nContact not found.\n");
			return null;
		}
		else { //tree is not empty
			//Search for the contact
			Tree<T> t = root, parent = null;
			boolean left = true;
			while (t != null) {
				int compare = name.compareTo(t.data);
				if (t.data.toString().equals(name)) { //contact found
					//remove the contact
					if (t.left == null && t.right == null) { //if it's a leaf
						if (left) parent.left = null;
						else parent.right = null;
						return t.data;
					}
					else if (t.left == null) { //there's only a right child
						if (left) parent.left = t.right;
						else parent.right = t.right;
						return t.data;
					}
					else if (t.right == null) { //there's only a left child
						if (left) parent.left = t.left;
						else parent.right = t.left;
						return t.data;
					}
					else { //there are two children
						Tree<T> p = t.left, parent1 = t;
						while (p.right != null) { //find immediate predecessor
							parent1 = p;
							p = p.right; 
						}
						//swap contact to be removed with immediate predecessor
						T temp = t.data;
						t.data = p.data; 
						p.data = temp;
						
						//remove from tree
						if (parent1.equals(t)) parent1.left = null;
						else if (p.left == null) parent1.right = null;
						else parent1.right = p.left;
						
						return p.data; //return contact
					}
				}
				else if (compare < 0) {
					//contact is alphabetically smaller than current node-> move left
					parent = t;
					t = t.left;
					left = true;
				}
				else {
					//contact is alphabetically greater than current node-> move right
					parent = t;
					t = t.right;
					left = false;
				}
			}
			System.out.println("\nContact not found.\n");
			return null;
		}
	}

	private T recursiveSearch(Tree<T> node, T t)
	{
		if(node == null)
		{
			return null;
		}

		int compare = t.compareTo(node.data);

		if(compare == 0)
		{
			return node.data;
		}

		else if(compare	 < 0)
		{
			return recursiveSearch(node.left, t);
		}
		else
		{
			return recursiveSearch(node.right, t);
		}
	}
	
}
