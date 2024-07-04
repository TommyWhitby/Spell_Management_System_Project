# Spell_Management_System_Project
Project Overview
This project implements a simplified Magic Spell Management System (SMS) inspired by fantasy-themed computer games. The system keeps track of spells learned, manages prerequisites for learning spells, 
and supports the unlearning of spells under certain conditions. The system reads and processes a list of specifications and outputs responses based on the commands provided.

Files
BuildSpellBook.java
Spell.java
Spellbookdfs.java
SpellPrerequisites.java
Files Description
BuildSpellBook.java

This is the main class responsible for reading specifications from a file, executing the specifications, and outputting the results. 
It supports four primary operations:

- LEARN <spellname>: Explicitly learns a spell and its prerequisites.
- FORGET <spellname>: Forgets a spell if it's not a prerequisite for other spells.
- ENUM: Enumerates all the learned spells.
- PREREQ <spellname1> <spellname2> ... <spellnamen>: Specifies prerequisites for learning a spell.

Spell.java

This class represents a spell with attributes like its name, prerequisites, and whether it is learned explicitly or implicitly. 
It includes methods to add prerequisites, mark a spell as learned, and check if a spell is part of a cycle.

Spellbookdfs.java

This class manages a collection of spells and their relationships. It provides methods to add and retrieve spells, 
check for cycles, and traverse the spell graph.

SpellPrerequisites.java

This class represents the prerequisites for a spell. It includes methods to add and retrieve prerequisite spells.

Execution

To execute the Spell Management System, follow these steps:

Compile the Java files:
javac BuildSpellBook.java Spell.java Spellbookdfs.java SpellPrerequisites.java

Run the main class:
java BuildSpellBook <input_file>
Replace <input_file> with the path to the file containing the specifications.

Example
Given an input file specifications.txt with the following content:

PREREQ fireball basic_fire
LEARN fireball
ENUM
FORGET basic_fire
END

The system will output:

PREREQ fireball basic_fire
LEARN fireball
   Learning basic_fire
   Learning fireball
ENUM
   fireball
   basic_fire
FORGET basic_fire
   basic_fire is still needed
END

Methods in BuildSpellBook.java

- Constructor: Initializes the vectors for input specifications, solution, and executed specifications.
- execNSpecs(Vector<String> specs, Integer N): Executes up to N specifications and returns the results.
- execNSpecswCheck(Vector<String> specs, Integer N): Executes specifications with cycle checking.
- execNSpecswCheckRecLarge(Vector<String> specs, Integer N): Executes specifications, checks for cycles, and recommends fixes by removing the largest cycle.
- execNSpecswCheckRecSmall(Vector<String> specs, Integer N): Executes specifications, checks for cycles, and recommends fixes by removing the smallest cycle.
- readSpecsFromFile(String fInName): Reads specifications from a file and returns them as a vector of strings.
- readSolnFromFile(String fInName, Integer N): Reads up to N lines from a solution file.
- compareExecWSoln(Vector<String> execd, Vector<String> soln): Compares the executed specifications with the solution.

Key Points

- The system uses depth-first search (DFS) to manage and traverse the spell graph.
- It handles cycles in the spell prerequisites and provides recommendations to resolve them.
- The results are formatted to show responses to each specification command, maintaining the order of operations.

This project demonstrates the management of complex dependencies and provides a foundation for more 
advanced spell management systems in games or similar applications.
