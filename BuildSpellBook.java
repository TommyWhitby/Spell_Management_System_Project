package student;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class BuildSpellbook {
	
	Integer MAXCOMS = 1000;  // maximum number of specs
	Vector<String> inSpecs;
	Vector<String> soln;
	Vector<String> execd;
	
	
	
	// TODO: add appropriate attributes / variables
	

	public BuildSpellbook() {
		// Constructor
		inSpecs = new Vector<>();
		soln = new Vector<>();
		execd = new Vector<>();
		// TODO
	
	}
	
	public Vector<String> execNSpecs (Vector<String> specs, Integer N) {
		// PRE: specs contains set of specifications read in by readSpecsFromFile()
		// POST: executed min(N, all) specifications,
        //       returning required output, one line per string in vector
		
		// TODO
		
		Vector<Spell> learnedSpells = new Vector<>();
		
		Spellbookdfs allSpellsMap = new Spellbookdfs();
		
		Vector<String> results = new Vector<>();
		
		Vector<String> cycles = new Vector<>();
		
		for (int i = 0; i < specs.size(); i++) {
			inSpecs.add(specs.get(i));
		}
		
		for (String element : specs) {
			if (N < 1) {
				return results;
			}
			N--;
			results.add(element);
			String[] spellParts = element.split(" ");
			if (element.contains("PREREQ")) {
				processPREREQ(element, allSpellsMap, results, cycles);
			}
			else if (element.contains("LEARN")) {
			    String spellName = spellParts[1];
			    Spell spellToLearn = createOrRetrieveSpell(spellName, allSpellsMap);
			    learnSpellAndPrerequisites(spellToLearn, learnedSpells, allSpellsMap, results);
			}
			else if (element.contains("FORGET")) {
				String spellName = spellParts[1];
			    Spell spellToForget = createOrRetrieveSpell(spellName, allSpellsMap);
				forgetSpell(spellToForget, learnedSpells, allSpellsMap, results, false);
			}
			else if (element.contains("ENUM")) {
				for (int i = 0; i < learnedSpells.size(); i++) {
					results.add("   " + learnedSpells.get(i));
				}
			}
		}
		return results;
	}
	
	public Vector<String> execNSpecswCheck (Vector<String> specs, Integer N) {
		// PRE: specs contains set of specifications read in by readSpecsFromFile()
		// POST: executed min(N, all) specifications, checking for cycles,
        //       returning required output, one line per string in vector

		// TODO
		Vector<Spell> learnedSpells = new Vector<>();
		
		Spellbookdfs allSpellsMap = new Spellbookdfs();
		
		Vector<String> results = new Vector<>();
		
		Vector<String> cycles = new Vector<String>();
		
		boolean cycleFound = false;
		
		for (int i = 0; i < specs.size(); i++) {
			inSpecs.add(specs.get(i));
		}
		
		for (String element : specs) {
			if (N < 1) {
				return results;
			}
			N--;
			results.add(element);
			String[] spellParts = element.split(" ");
			
			if (element.contains("PREREQ")) {
				if (processPREREQ(element, allSpellsMap, results, cycles) > 0) {
					cycleFound = true;
				}
				if (cycleFound) {
					checkForCycle(results);
				}
			}
			else if (element.contains("LEARN")) {
				if (!cycleFound) {
					String spellName = spellParts[1];
				    Spell spellToLearn = createOrRetrieveSpell(spellName, allSpellsMap);
				    learnSpellAndPrerequisites(spellToLearn, learnedSpells, allSpellsMap, results);
				}
			}
			else if (element.contains("FORGET")) {
				if (!cycleFound) {
					String spellName = spellParts[1];
				    Spell spellToForget = createOrRetrieveSpell(spellName, allSpellsMap);
					forgetSpell(spellToForget, learnedSpells, allSpellsMap, results, false);
				}
			}
			else if (element.contains("ENUM")) {
				if (!cycleFound) {
					for (int i = 0; i < learnedSpells.size(); i++) {
						results.add("   " + learnedSpells.get(i));
					}
				}
			}
		}
		return results;
	}
	
	public Vector<String> execNSpecswCheckRecLarge (Vector<String> specs, Integer N) {
		// PRE: specs contains set of specifications read in by readSpecsFromFile()
		// POST: executed min(N, all) specifications, checking for cycles and 
		//       recommending fix by removing largest cycle,
		//       returning required output, one line per string in vector

		
		// TODO

		Vector<Spell> learnedSpells = new Vector<>();
	    
	    Spellbookdfs allSpellsMap = new Spellbookdfs();
	    
	    Vector<String> results = new Vector<>();
	    
	    boolean cycleFound = false;
	    
	    Vector<String> cycles = new Vector<String>();
	    // format of cycles is length and then the PREREQ part
	    // example: 3 PREREQ shining_sphere_of_protection3 shining_sphere_of_protection2
	    
	    int longest = 0;

	    for (int i = 0; i < specs.size(); i++) {
			inSpecs.add(specs.get(i));
		}
	    
		for (String element : specs) {
			if (N < 1) {
				return results;
			}
			N--;
			results.add(element);
			String[] spellParts = element.split(" ");
			if (element.contains("PREREQ")) {
				if (processPREREQ(element, allSpellsMap, results, cycles) > 0) { // If a cycle is found, run it through second cycle function
					for (String str : cycles) {
			            String[] words = str.split(" ");
			            int c = Integer.parseInt(words[0]);
						if (c > 0) {
							cycleFound = true;
						}
			        }
				}
				if (cycleFound) {
					longest = updateLongest(cycles, longest, results);
				}
			}
			else if (element.contains("LEARN")) {
				if (!cycleFound) {
					String spellName = spellParts[1];
				    Spell spellToLearn = createOrRetrieveSpell(spellName, allSpellsMap);
				    learnSpellAndPrerequisites(spellToLearn, learnedSpells, allSpellsMap, results);
				}
			}
			else if (element.contains("FORGET")) {
				if (!cycleFound) {
					String spellName = spellParts[1];
				    Spell spellToForget = createOrRetrieveSpell(spellName, allSpellsMap);
					forgetSpell(spellToForget, learnedSpells, allSpellsMap, results, false);
				}
			}
			else if (element.contains("ENUM")) {
				if (!cycleFound) {
					for (int i = 0; i < learnedSpells.size(); i++) {
						results.add("   " + learnedSpells.get(i));
					}
				}
			}
		}
		return results;
	}
	
	public Vector<String> execNSpecswCheckRecSmall (Vector<String> specs, Integer N) {
		// PRE: specs contains set of specifications read in by readSpecsFromFile()
		// POST: executed min(N, all) specifications, checking for cycles and 
		//       recommending fix by removing smallest cycle,
        //       returning required output, one line per string in vector

		
		// TODO
		
		Vector<Spell> learnedSpells = new Vector<>();
	    
	    Spellbookdfs allSpellsMap = new Spellbookdfs();
	    
	    Vector<String> results = new Vector<>();
	    
	    boolean cycleFound = false;
	    
	    Vector<String> cycles = new Vector<String>();
	    // format of cycles is length and then the PREREQ part
	    // example: 3 PREREQ shining_sphere_of_protection3 shining_sphere_of_protection2
	    
	    int shortest = Integer.MAX_VALUE;

	    for (int i = 0; i < specs.size(); i++) {
			inSpecs.add(specs.get(i));
		}
		for (String element : specs) {
			if (N < 1) {
				return results;
			}
			N--;
			results.add(element);
			String[] spellParts = element.split(" ");
			if (element.contains("PREREQ")) {
				int test = processPREREQ(element, allSpellsMap, results, cycles);
				if (test > 0) { // If a cycle is found, run it through second cycle function
					cycleFound = true;
				}
				if (cycleFound) {
					shortest = updateShortest(cycles, shortest, results);
				}
			}
			else if (element.contains("LEARN")) {
				if (!cycleFound) {
					String spellName = spellParts[1];
				    Spell spellToLearn = createOrRetrieveSpell(spellName, allSpellsMap);
				    learnSpellAndPrerequisites(spellToLearn, learnedSpells, allSpellsMap, results);
				}
			}
			else if (element.contains("FORGET")) {
				if (!cycleFound) {
					String spellName = spellParts[1];
				    Spell spellToForget = createOrRetrieveSpell(spellName, allSpellsMap);
					forgetSpell(spellToForget, learnedSpells, allSpellsMap, results, false);
				}
			}
			else if (element.contains("ENUM")) {
				if (!cycleFound) {
					for (int i = 0; i < learnedSpells.size(); i++) {
						results.add("   " + learnedSpells.get(i));
					}
				}
			}
		}
		return results;
	}
	
	public void checkForCycle(Vector<String> results) {
		results.add("   Found cycle in prereqs");
	}
	
	public Integer updateLongest(Vector<String> cycles, int longest, Vector<String> results) {
		String recommended = "";
		for (String foundCycles : cycles) { // Go through all entries in cycles
			String[] foundCyclesSplit = foundCycles.split(" "); // Split the current entry
			if (Integer.parseInt(foundCyclesSplit[0]) >= longest) { // Read the int at the start of the entry
				longest = Integer.parseInt(foundCyclesSplit[0]); // Cast that in to longest
				recommended = "";
				for (int i = 1; i < foundCyclesSplit.length; i++) { // Add the rest of the split string onto recommended
					recommended += foundCyclesSplit[i];
					if (i < foundCyclesSplit.length - 1) { // Remove the space at the end
						recommended += " ";
				    }
				}
			}
		}
		results.add("   Found cycle in prereqs");
		results.add("   Suggest forgetting " + recommended);
		return longest; // Return the updated recommended PREREQ to forget
	}
	
	public int updateShortest(Vector<String> cycles, int shortest, Vector<String> results) {
		String recommended = "";
		for (String foundCycles : cycles) { // Go through all entries in cycles
			String[] foundCyclesSplit = foundCycles.split(" "); // Split the current entry
			if (Integer.parseInt(foundCyclesSplit[0]) <= shortest) { // Read the int at the start of the entry
				shortest = Integer.parseInt(foundCyclesSplit[0]); // Cast that in to longest
				recommended = "";
				for (int i = 1; i < foundCyclesSplit.length; i++) { // Add the rest of the split string onto recommended
					recommended += foundCyclesSplit[i];
					if (i < foundCyclesSplit.length - 1) { // Remove the space at the end
						recommended += " ";
				    }
				}
			}
		}
		results.add("   Found cycle in prereqs");
		results.add("   Suggest forgetting " + recommended);
		return shortest; // Return the updated recommended PREREQ to forget
	}
	
	private Integer hasCycle(Spell focalSpell, Spell currentSpell, Spellbookdfs allSpellsMap, int count) {
		if (count > allSpellsMap.size()) {
			return 0;
		}
		
		if (count < allSpellsMap.size()) {
			count++;
			for (Spell prerequisite : currentSpell.prereqs) {
				if (prerequisite.equals(focalSpell)) {
		            // Cycle detected
		            return count;
		        }
		        return hasCycle(focalSpell, prerequisite, allSpellsMap, count);
			}
		}
		return 0;
	}
	
	private Integer processPREREQ(String element, Spellbookdfs allSpellsMap, Vector<String> results, Vector<String> cycles) {
	    String[] spellParts = element.split(" ");
	    String focalSpellName = spellParts[1];
	    
	    Spell focalSpell = createOrRetrieveSpell(focalSpellName, allSpellsMap);
	    
	    for (int i = 2; i < spellParts.length; i++) {
	        String prerequisiteName = spellParts[i];
	        Spell prerequisiteSpell = createOrRetrieveSpell(prerequisiteName, allSpellsMap);
	        focalSpell.addPrerequisite(prerequisiteSpell, i - 1);
	        focalSpell.prereqs.add(prerequisiteSpell);
	    }
	    Integer test = hasCycle(focalSpell, focalSpell, allSpellsMap, 0);
        if (test > 0) {
        	focalSpell.hasCycle();
            cycles.add(test + " " + element);
            return test;
        }
	    return 0;
	}

	private Spell createOrRetrieveSpell(String spellName, Spellbookdfs allSpellsMap) {
	    if (!allSpellsMap.containsSpell(spellName)) {
	        // Create a new spell if it doesn't exist
	        Spell newSpell = new Spell(spellName);
	        allSpellsMap.addSpell(newSpell);
	        return newSpell;
	    } else {
	        // Retrieve existing spell
	        return allSpellsMap.getSpell(spellName);
	    }
	}
	
	public void learnSpellAndPrerequisites(Spell spell, Vector<Spell> learnedSpells, Spellbookdfs allSpellsMap, Vector<String> results) {
		if (spell.isLearnt()) {
			results.add("   " + spell.getName() + " is already learned");
			return;
		}
		allSpellsMap.setUnmarked();
		spell.setExplicit();
		dfsSpellLearn(spell, learnedSpells,allSpellsMap, results);
	}
	
	public void dfsSpellLearn(Spell spell, Vector<Spell> learnedSpells, Spellbookdfs allSpellsMap, Vector<String> results) {
		spell.setMarked();
		if (spell.isLearnt()) {
			spell.incReq();
			return;
		}
		else if (!spell.isLearnt()) {
			for (String prerequisiteName : spell.getPrerequisites().spellNames()) {
		        Spell prerequisiteSpell = allSpellsMap.getSpell(prerequisiteName);
		        prerequisiteSpell.incReq();
		        if (!prerequisiteSpell.isMarked() && !prerequisiteSpell.isLearnt()) {
		        	dfsSpellLearn(prerequisiteSpell, learnedSpells, allSpellsMap, results);
		        }
		    }
			spell.setLearned();
			results.add("   Learning " + spell.getName());
			if (!learnedSpells.contains(spell)) {
				learnedSpells.add(spell);
			}
		}
	}

	public void forgetSpell(Spell spell, Vector<Spell> learnedSpells, Spellbookdfs allSpellsMap, Vector<String> results, boolean first) {		
		if (spell.isExplicit() && spell.req > 0 && spell.isLearnt() && !first) {
			results.add("   " + spell.getName() + " is still needed");
			return;
		}
		if (spell.isExplicit() && spell.req > 0 && spell.isLearnt() && first) {
			return;
		}
		else if (spell.req == 0 && spell.isLearnt() && !first) {
			forgetSpellTraversal(spell, learnedSpells, allSpellsMap, results);
		}
		else if (!spell.isExplicit() && spell.req > 0 && !first) {
			results.add("   " + spell.getName() + " is still needed");
		}
		else if (!spell.isExplicit() && spell.req == 0 && first) {
			forgetSpellTraversal(spell, learnedSpells, allSpellsMap, results);
		}
		else if (!spell.isLearnt()) {
			results.add("   " + spell.getName() + " is not learned");
		}
	}
	
	public void forgetSpellTraversal(Spell spell, Vector<Spell> learnedSpells, Spellbookdfs allSpellsMap, Vector<String> results) {
		if (spell.req == 0) {
			results.add("   Forgetting " + spell.getName());
			spell.setUnlearn();
			spell.setUnExplicit();
		}
		for (int i = learnedSpells.size() - 1; i > 0; i--) {
			for (String s : spell.getPrerequisites().spellNames()) {
				Spell temp = createOrRetrieveSpell(s, allSpellsMap);
				if (temp == learnedSpells.get(i)) {
					if (temp.req > 0) {
						temp.req--;
						if (temp.getPrerequisites().size() == 0) {
							forgetSpell(temp, learnedSpells, allSpellsMap, results, true);
						} else {
							if (!temp.isExplicit()) {
								forgetSpell(temp, learnedSpells, allSpellsMap, results, true);
							}
							else {
								forgetSpell(temp, learnedSpells, allSpellsMap, results, false);
							}
						}
					}
				}
			}
		}
    }

	// Provided files below

	public Vector<String> readSpecsFromFile(String fInName) throws IOException {
		// PRE: -
		// POST: returns lines from input file as vector of string

		BufferedReader fIn = new BufferedReader(
							 new FileReader(fInName));
		String s;
		Vector<String> comList = new Vector<String>();
		
		while ((s = fIn.readLine()) != null) {
			comList.add(s);
		}
		fIn.close();
		
		return comList;
	}

	public Vector<String> readSolnFromFile(String fInName, Integer N) throws IOException {
		// PRE: -
		// POST: returns (up to) N lines from input file as a vector of N strings;
		//       only the specification lines are counted in this N, not responses

		BufferedReader fIn = new BufferedReader(
							 new FileReader(fInName));
		String s;
		Vector<String> out = new Vector<String>();
		Integer i = 0;

		while (((s = fIn.readLine()) != null) && (i <= N)) {
			if ((i != N) || s.startsWith("   ")) // responses to commands start with three spaces
				out.add(s);
			if (!s.startsWith("   "))  
				i += 1;
		}
		fIn.close();
		
		return out;
	}
	
	public Boolean compareExecWSoln (Vector<String> execd, Vector<String> soln) {
		// PRE: -
		// POST: Returns True if execd and soln string-match exactly, False otherwise

		if (execd.size() != soln.size()) {
			return Boolean.FALSE;
		}
		for (int i = 0; i < execd.size(); i++) {
			if (!execd.get(i).equals(soln.get(i))) {
				return Boolean.FALSE;
			}
		}

		return Boolean.TRUE;

	}

	

	public static void main(String[] args) {
		

	}
}
