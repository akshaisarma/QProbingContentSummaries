
public class Driver {
	
	public static void main(String[] args) {
		String accountKey = "MWQrrA8YW+6ciAUTJh56VHz1vi/Mdqu0lSbzms3N7NY=";
		String site = "yahoo.com";
		double t_es = 0.6;
		int t_ec = 100;
		
		if (args.length >= 4) {
			accountKey = args[0];
			t_es = Double.parseDouble(args[1]);
			t_ec = Integer.parseInt(args[2]);
			site = args[3];
		}

		System.out.println("\n\nClassifying...");
		Part1 p1 = new Part1(accountKey);
		// results stands for nodes (often only one node) of the category
		// e.g., Diseases(standing for Root/Health/Diseases) for cancer.org
		// For how to recursively get the path, refer to printClassification
		p1.classifyDB(site, t_es, t_ec);
		
		 
		/* Output content summaries of the part of the tree that was visited during 
		 * classification. Uses partial tree and does a post order traversal pushing up
		 * results. Knows which nodes were "visited" due to visited flag in each node.
		 */
		System.out.println("\n\nExtracting topic content summaries...");
		Part2 p2 = new Part2(accountKey, p1.tree, site);
		p2.outputContentSummaries();
	}

}
