package student;

import java.util.*;

public class Spellbookdfs {
	
	private TreeMap<String, Spell> spellbookMap;
	private Integer numReqs;
	private Iterator<Map.Entry<String, Spell>> spellbookIterator;
	
	public Spellbookdfs() {
		spellbookMap = new TreeMap<String, Spell>();
		numReqs = 0;
	}
	
	public Integer numPrerequisites() {
		return spellbookMap.size();
	}
	
	public Integer numReqs() {
		return numReqs;
	}
	
	public Spell getSpell(String spellName) {
		return spellbookMap.get(spellName);
	}
	
	public NavigableSet<String> getSpellSet() {
		return spellbookMap.navigableKeySet();
	}
	
	public void addSpell(Spell spell) {
	    spellbookMap.put(spell.getName(), spell);
	}
	
	public void print() {
		spellbookIterator = spellbookMap.entrySet().iterator();

		System.out.println("Number of spells is " + spellbookMap.size());
		
		while (spellbookIterator.hasNext()) {
			Map.Entry<String, Spell> reqs = spellbookIterator.next();
			reqs.getValue().print();
			System.out.println();
		}
	}
	
	public void printQ() {
		spellbookIterator = spellbookMap.entrySet().iterator();

		System.out.println("Number of spells is " + spellbookMap.size());
		
		while (spellbookIterator.hasNext()) {
			Map.Entry<String, Spell> reqs = spellbookIterator.next();
			reqs.getValue().printQ();
			System.out.println();
		}
	}
	
	public void setUnmarked() {
		// Sets all spells to be unmarked | Probs don't need this but here just in case
		for (Spell spell : spellbookMap.values()) {
			spell.setUnmarked();
		}
	}
	
	public String getFirstSpellName() {
		// Returns first SpellName in the treemap
		// Use this when you want to start a traversal
		return spellbookMap.firstKey();
	}
	
	public boolean containsSpell(String spellName) {
		// Checks if a spell with the corresponding spellName is present in the map
		return spellbookMap.containsKey(spellName);
	}
	
	public Integer size() {
		return spellbookMap.size();
	}
}