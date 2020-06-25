default: src/MastermindPlay.java src/MastermindAuto.java src/Trie.java src/Node.java
	javac src/MastermindPlay.java src/MastermindAuto.java src/Trie.java src/Node.java

play: src/MastermindPlay.class src/Trie.class src/Node.class
	java src.MastermindPlay

auto: src/MastermindAuto.class src/Trie.class src/Node.class
	java src.MastermindAuto

clean:
	rm -rf src/*.class
