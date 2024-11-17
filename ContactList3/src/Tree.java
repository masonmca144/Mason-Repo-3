/*
File: Tree.java
Author: Ram Longman
Date: 8/3/2023
Description: A class for a binary tree
*/
public class Tree<T extends Comparable<T>> {
	
	T data;
	Tree<T> left;
	Tree<T> right;
	
	public Tree(T d) {
		this.data = d;
		left = null;
		right = null;
	}
	
}
