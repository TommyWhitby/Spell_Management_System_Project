package student;

import java.util.*;

public class SpellQueue extends TreeMap<String, Integer> {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private Iterator<Map.Entry<String, Integer>> iterator;

    // This class is used to create a queue of spells to allow easier iteration when trying to learn
    // Prereqs

    public SpellQueue() {
        super();
    }
    
    public int size() {
        return super.size();
    }

    public Iterable<String> spellNames() {
        return this.keySet(); // method to obtain an iterable view of the spell names stored in the queue
    }

    public String topSpell() {
        // returns the first element of the queue
        return this.firstKey();
    }

    public void pop() {
        // deletes first element of the queue
        this.remove(this.firstKey());
    }

    public void push(String name, Integer order) {
        // Add the spell to the map
        this.put(name, order);
        // Reorder the map based on the integer values
        reorder();
    }

    private void reorder() {
        // Create a list of entries from the TreeMap
        List<Map.Entry<String, Integer>> entryList = new ArrayList<>(this.entrySet());
        // Sort the list based on the order values
        entryList.sort(Comparator.comparingInt(Map.Entry::getValue));
        // Clear the current contents of the TreeMap
        this.clear();
        // Insert the sorted entries back into the TreeMap
        for (Map.Entry<String, Integer> entry : entryList) {
            this.put(entry.getKey(), entry.getValue());
        }
    }

    public void print() {
        iterator = this.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Integer> pairs = iterator.next();
            System.out.print(" " + pairs.getKey());
        }
        System.out.println();
    }

    public Iterator<String> iterator() {
        // creates a iterator to use to navigate the spellqueue
        return this.keySet().iterator();
    }
}