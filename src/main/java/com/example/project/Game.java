package com.example.project;

import java.util.ArrayList;
import java.util.Collections;

public class Game {

    // Figure out who wins between two players based on their hands.
    public static String determineWinner(Player p1, Player p2, String p1Hand, String p2Hand, ArrayList<Card> communityCards) {
        // List of hand rankings, best to worst.
        String[] handRanks = {
            "Royal Flush", "Straight Flush", "Four of a Kind", "Full House", "Flush",
            "Straight", "Three of a Kind", "Two Pair", "A Pair", "High Card"
        };

        // Get each player's hand rank index.
        int p1Rank = getHandRankIndex(p1Hand, handRanks);
        int p2Rank = getHandRankIndex(p2Hand, handRanks);

        // Lower index means a better hand.
        if (p1Rank < p2Rank) {
            return "Player 1 wins!";
        } else if (p1Rank > p2Rank) {
            return "Player 2 wins!";
        } else {
            // If hands are equal, break the tie.
            return breakTie(p1, p2);
        }
    }

    // Returns the position of the hand in the ranking list.
    private static int getHandRankIndex(String hand, String[] handRanks) {
        for (int i = 0; i < handRanks.length; i++) {
            // Check if the hand description starts with the ranking.
            if (hand.startsWith(handRanks[i])) {
                return i;
            }
        }
        // Default to worst if nothing matches.
        return handRanks.length;
    }

    // Break the tie by comparing the individual cards.
    private static String breakTie(Player p1, Player p2) {
        // Grab all cards for both players.
        ArrayList<Card> p1Cards = p1.getAllCards();
        ArrayList<Card> p2Cards = p2.getAllCards();

        // Sort the cards in each hand.
        p1.sortAllCards();
        p2.sortAllCards();

        // Compare cards from highest to lowest.
        for (int i = p1Cards.size() - 1; i >= 0; i--) {
            int p1Rank = getRankIndex(p1Cards.get(i).getRank());
            int p2Rank = getRankIndex(p2Cards.get(i).getRank());

            if (p1Rank > p2Rank) {
                return "Player 1 wins!";
            } else if (p1Rank < p2Rank) {
                return "Player 2 wins!";
            }
        }
        // If every card is the same, it's a tie.
        return "Tie!";
    }

    // Get the index of a card rank from the utility's list.
    private static int getRankIndex(String rank) {
        String[] ranks = Utility.getRanks();
        for (int i = 0; i < ranks.length; i++) {
            if (ranks[i].equals(rank)) {
                return i;
            }
        }
        // Shouldn't get here if the rank is legit.
        return -1;
    }

    // Main play function: deals cards, evaluates hands, and prints the result.
    public static void play() {
        // Build and shuffle the deck.
        ArrayList<Card> deck = createDeck();
        Collections.shuffle(deck);

        // Create two players.
        Player p1 = new Player();
        Player p2 = new Player();

        // Give each player two cards.
        for (int i = 0; i < 2; i++) {
            p1.addCard(deck.remove(0));
            p2.addCard(deck.remove(0));
        }

        // Deal out five community cards.
        ArrayList<Card> communityCards = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            communityCards.add(deck.remove(0));
        }

        // Figure out the best hand each player can make.
        String p1Hand = p1.playHand(communityCards);
        String p2Hand = p2.playHand(communityCards);

        // Print out all the cards and best hands.
        System.out.println("Player 1 Hand: " + p1.getHand());
        System.out.println("Player 2 Hand: " + p2.getHand());
        System.out.println("Community Cards: " + communityCards);
        System.out.println("Player 1 Best Hand: " + p1Hand);
        System.out.println("Player 2 Best Hand: " + p2Hand);

        // Decide and print who wins.
        System.out.println(determineWinner(p1, p2, p1Hand, p2Hand, communityCards));
    }

    // Creates a standard 52-card deck.
    private static ArrayList<Card> createDeck() {
        ArrayList<Card> deck = new ArrayList<>();
        String[] suits = Utility.getSuits();
        String[] ranks = Utility.getRanks();

        // Make a card for every suit and rank.
        for (String suit : suits) {
            for (String rank : ranks) {
                deck.add(new Card(rank, suit));
            }
        }
        return deck;
    }
}

