# Poker_Project
Welcome to Patrick's Poker Player! A java based Texas Hold'em style poker simulator.

I started this project after I learned about Object Oriented Programming and my friends and I started moving our monthly poker games online from the pandemic.
The core of this project is not the UI (which is text based) or the poker bots (which are psuedo-random) but rather to show competence in larger multi-class projects that runs a complex driver program correctly.
If you're new to poker, having someone who knows how to play around can help to undertsand the program or if you'd like to check it out here's a link

https://www.pokernews.com/poker-rules/texas-holdem.htm

If your using a linux system, you can download these source files into a new folder and run the makefile to play
If your using a windows system and you have access to an IDE, you can download these source files into it and run it from there

The class that runs the game is PlayPoker.java

Other classes I'd like to highlight
1) FindHand.java - takes between two and seven cards and determines the type of hand present (Pair, flush, ect.)
2) Player.java - a poker player that has a very, very, rudimentary poker bot to play against
3) Deck.java - represents a deck of cards, check out the unbiased shuffling algorithm!
4) PokerTable.java - represents a poker table of players with updating positions
5) Card.java - comprises a deck, can be constructed to be any size

all other classes are small enum classes like Suit.java (Spades, clubs...) or Value.java (two, three, ... king ace)

Good Luck!
