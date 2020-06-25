default: src/Mastermind_Algorithm.java src/Trie.java src/Node.java
	javac src/Mastermind_Algorithm.java src/Trie.java src/Node.java

run: src/Mastermind_Algorithm.class src/Trie.class src/Node.class
	java src.Mastermind_Algorithm

clean:
	rm -rf src/*.class
