import java.io.*;
import java.util.*;

/* This class contains the nodes of the tree and its associated helper classes */

public class TreeNode {
	String name; // category name for this node,e.g., Computers
	TreeNode parent = null;
	ArrayList<TreeNode> children;
	ArrayList<String> queryList; // This is part of the list of queries for the PARENT
	boolean isLeaf = true; // default is leaf, unless it has >=1 children
	boolean visited = false; // Whether this node was deemed worthy of expansion
	// Following are empty if visited is false after Part 2 is finished
	HashSet<String> samples; // Set of document samples for it and all visited descendants
	ContentSummary summary; // Content summary for this node and all visited descendants
	
	TreeNode(String name) {
		this.name = name;
		// initialization
		children = new ArrayList<TreeNode>();
		queryList = new ArrayList<String>();
		samples = new HashSet<String>();
		summary = new ContentSummary();
	}
	
	/* Queries are stored with children - to naturally associate them with the children
	 * rather than keep many lists for each child in parent. This function simply gets
	 * all the queries and returns them.
	 */
	public ArrayList<String> getQueries() {
		ArrayList<String> result = new ArrayList<String>();
		if (isLeaf)
			return result;
		for (TreeNode n : children)
			result.addAll(n.queryList);
		return result;
	} 

	/* Iterates over children and adds all their samples. Even adds visited=false children
	 * but they are empty, so it's ok.
	 */
	public HashSet<String> getChildSamples() {
		HashSet<String> result = new HashSet<String>();
		for (TreeNode n : children)
			result.addAll(n.samples);
		return result;
	}
	
	/* Also iterates through children summaries and merges. Again for visited = false also
	 * but they are empty.
	 */
	public void mergeChildSummaries() {
		for (TreeNode n : children)
			summary.mergeSummary(n.summary);
	}
	
	public void setParent(TreeNode parent){
		this.parent = parent;
	}
	
	public void addChild(TreeNode child){
		this.children.add(child);
	}

	public void addQuery(String query){
		this.queryList.add(query);
	}
}

/* Class for storing the content summary - word + its document frequency */
class ContentSummary {
	TreeMap<String, Integer> summary;
	
	public ContentSummary() {
		summary = new TreeMap<String, Integer>();
	}
	
	public void addWords(HashSet<String> words) {
		for (Iterator<String> it = words.iterator(); it.hasNext(); ) {
			String word = it.next();
			Integer v = summary.get(word);
			summary.put(word, v == null ? 1 : v+1);
		}
	}
	
	public void mergeSummary(ContentSummary other) {
		for (String s : other.summary.keySet()) {
			Integer freq = other.summary.get(s);
			Integer v = summary.get(s);
			summary.put(s, v == null ? freq : v + freq);
		}
	}
	
	public void writeOut(String fileName) {
		try {
			FileWriter f = new FileWriter(fileName);
			BufferedWriter o = new BufferedWriter(f);
			for (String s : summary.keySet())
				o.write(s + "#" + summary.get(s) + "\n");
			o.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
				
	}
}