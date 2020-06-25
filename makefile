default: src/MastermindPlay.java src/MastermindAuto.java src/MastermindAll.java src/Trie.java src/Node.java
	javac src/MastermindPlay.java src/MastermindAuto.java src/MastermindAll.java src/Trie.java src/Node.java

play: src/MastermindPlay.class src/Trie.class src/Node.class
	java src.MastermindPlay

auto: src/MastermindAuto.class src/Trie.class src/Node.class
	java src.MastermindAuto

all: src/MastermindAll.class src/Trie.class src/Node.class
	java src.MastermindAll

clean:
	rm -rf src/*.class
