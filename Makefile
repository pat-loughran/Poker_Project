run: compile
	java PlayPoker

test: FindHandTester.class
	java FindHandTester

compile: PlayPoker.class Enums

PlayPoker.class: PlayPoker.java PokerTable.class
	javac PlayPoker.java

PokerTable.class: PokerTable.java FindHand.class Card.class
	javac PokerTable.java

FindHand.class: FindHand.java Player.class
	javac FindHand.java

FindHandTester.class: FindHandTester.java FindHand.class
	javac FindHandTester.java

Player.class: Player.java Card.class
	javac Player.java

Deck.class: Deck.java Card.class
	javac Deck.java

Card.class: Card.java
	javac Card.java

Enums: Action.class Blinds.class HandType.class Position.class Street.class Suit.class Value.class


Action.class: Action.java
	javac Action.java

Blinds.class: Blinds.java
	javac Blinds.java

HandType.class: HandType.java
	javac HandType.java

Position.class: Position.java
	javac Position.java

Street.class: Street.java
	javac Street.java

Suit.class: Suit.java
	javac Suit.java

Value.class: Value.java
	javac Value.java

clean:
	rm *.class

superClean:
	rm *.class
	rm *.java
