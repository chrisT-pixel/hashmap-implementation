
public class Main {
	
	public static void test(double load) {

		HashMapChaining HM = new HashMapChaining(load);
		
		HM.put("fred", "flinstone", true);
		HM.put("donnie", "whalberg", true);
		HM.put("rick", "james", true);
		
		HM.display();
		System.out.println();
		System.out.println("load threshold " + HM.loadThreshold);
		System.out.println("current size " + HM.size);
		
		HM.display();
		System.out.println();
		System.out.println("current size " + HM.size);
		
		System.out.println(HM.get("donnie"));
		System.out.println(HM.isEmpty());
		System.out.println("current length " + HM.table.length);
		
		System.out.println(HM.table.length);
		System.out.println();
		
		HM.put("clay", "peters", true);
		HM.remove("fred", true);
		HM.display();
		System.out.println();
		System.out.println(HM.table.length);
		System.out.println("current size " + HM.size);
		HM.put("chris", "taylor", true);
		HM.display();
		System.out.println();
		System.out.println(HM.table.length);
		System.out.println("current size " + HM.size);
		HM.put("peter", "harrison", true);
		HM.put(45454, 76, true);
		HM.display();
		System.out.println();
		System.out.println(HM.table.length);
		System.out.println("current size " + HM.size);
		HM.display();
		
	}
	
	public static void main(String[] args) {
		
		long start = System.nanoTime();
		test(0.75);
		long end = System.nanoTime();
		System.out.println();
		System.out.printf("Test took %.3f sec", (end - start) / 1e9);
		System.out.println("--------------------------------------------");
		System.out.println();
		
		long start2 = System.nanoTime();
		test(0.50);
		long end2 = System.nanoTime();
		System.out.println();
		System.out.printf("Test took %.3f sec", (end2 - start2) / 1e9);
		System.out.println("--------------------------------------------");
		System.out.println();
		
		long start3 = System.nanoTime();
		test(0.25);
		long end3 = System.nanoTime();
		System.out.println();
		System.out.printf("Test took %.3f sec", (end2 - start2) / 1e9);
		System.out.println("--------------------------------------------");
		
	
	}

}
