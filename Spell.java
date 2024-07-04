package student;

import java.util.*;

public class Spell {
	public String name;
	public SpellQueue prerequisites;
	public Boolean marked;
	public boolean explicit;
	public boolean learned;
	public int req;
	public boolean cycle;
	public Vector<Spell> prereqs;
	
    public Spell(String n) {
        name = n;
        prerequisites = new SpellQueue();
        marked = false;
        explicit = false;
        learned = false;
        req = 0;
        cycle = false;
        prereqs = new Vector<>();
    }

    public String getName() {
        return name;
    }
    
    public void incReq() {
    	req++;
    }
    
    public void decReq() {
    	req--;
    }
    
    public void hasCycle() {
    	cycle = true;
    }
    
    public void setLearned() {
    	learned = true;
    }
    
    public void setUnlearn() {
    	learned = false;
    }
    
    public void forget(Vector<String> results) {
    	if (req > 0) {
    		req--;
    	}
    	else if (req == 0 && explicit == false && learned == true) {
    		learned = false;
    		results.add("   Forgetting " + name);
    	}
    }
    
    public int getReq() {
    	return req;
    }
    
    public boolean isLearnt() {
    	return learned;
    }
    
    public void setExplicit() {
    	explicit = true;
    }
    
    public void setUnExplicit() {
    	explicit = false;
    }
    
    public boolean isExplicit() {
    	return explicit;
    }
    
    public void setMarked() {
    	marked = true;
    }
    
    public void setUnmarked() {
    	marked = false;
    }
    
    public boolean isMarked() {
    	return marked;
    }

    public SpellQueue getPrerequisites() {
        return prerequisites;
    }
    
    public Vector<Spell> getPrereqs() {
    	return prereqs;
    }

    public void addPrerequisite(Spell spell, Integer order) {
        prerequisites.push(spell.getName(), order);
    }
    
    public void print() {
		System.out.print("Spell name: " + name + ", Learned: " + learned + ", Explicit: " + explicit + ", ReqCount: " + req + ", Visited: (" + marked + "), Prereqs: ");
		System.out.println(prerequisites);
	}
    
    public void printQ() {
		System.out.print("Spell name: " + name + ", Prereqs: ");
		System.out.println(prerequisites);
	}

    @Override
    public String toString() {
        return name;
    }
}
