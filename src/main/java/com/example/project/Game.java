package com.example.project;

import java.util.ArrayList;
import java.util.Collections;

public class Game {

    // Determines the winner between two players
    public static String determineWinner(Player p1, Player p2, String p1Hand, String p2Hand, ArrayList<Card> communityCards) {
        // List of hand rankings in descending order
        String[] handRanks = {
            "Royal Flush", "Straight Flush", "Four of a Kind", "Full House", "Flush",
            "Straight", "Three of a Kind", "Two Pair", "A Pair", "High Card"
        };

        int p1Rank = getHandRankIndex(p1Hand, handRanks);
        int p2Rank = getHandRankIndex(p2Hand, handRanks);

        if (p1Rank < p2Rank) {
            return "Player 1 wins!";
        } else if (p1Rank > p2Rank) {
            return "Player 2 wins!";
        } else {
            // Tiebreaker logic if both players have the same hand ranking
            return breakTie(p1, p2);
        }
    }

    private static int getHandRankIndex(String hand, String[] handRanks) {
        for (int i = 0; i < handRanks.length; i++) {
            if (hand.startsWith(handRanks[i])) {
                return i;
            }
        }
        return handRanks.length; // Default to lowest rank if unrecognized
    }

    private static String breakTie(Player p1, Player p2) {
        ArrayList<Card> p1Cards = p1.getAllCards();
        ArrayList<Card> p2Cards = p2.getAllCards();

        p1.sortAllCards();
        p2.sortAllCards();

        // Compare highest cards in sorted order
        for (int i = p1Cards.size() - 1; i >= 0; i--) {
            int p1Rank = getRankIndex(p1Cards.get(i).getRank());
            int p2Rank = getRankIndex(p2Cards.get(i).getRank());

            if (p1Rank > p2Rank) {
                return "Player 1 wins!";
            } else if (p1Rank < p2Rank) {
                return "Player 2 wins!";
            }
        }

        return "Tie!";
    }

    private static int getRankIndex(String rank) {
        String[] ranks = Utility.getRanks();
        for (int i = 0; i < ranks.length; i++) {
            if (ranks[i].equals(rank)) {
                return i;
            }
        }
        return -1; // Should never happen with valid ranks
    }

    public static void play() {
        // Initialize deck and shuffle
        ArrayList<Card> deck = createDeck();
        Collections.shuffle(deck);

        // Create players
        Player p1 = new Player();
        Player p2 = new Player();

        // Deal two cards to each player
        for (int i = 0; i < 2; i++) {
            p1.addCard(deck.remove(0));
            p2.addCard(deck.remove(0));
        }

        // Deal community cards
        ArrayList<Card> communityCards = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            communityCards.add(deck.remove(0));
        }

        // Determine each player's best hand
        String p1Hand = p1.playHand(communityCards);
        String p2Hand = p2.playHand(communityCards);

        // Print game details
        System.out.println("Player 1 Hand: " + p1.getHand());
        System.out.println("Player 2 Hand: " + p2.getHand());
        System.out.println("Community Cards: " + communityCards);
        System.out.println("Player 1 Best Hand: " + p1Hand);
        System.out.println("Player 2 Best Hand: " + p2Hand);

        // Determine the winner
        System.out.println(determineWinner(p1, p2, p1Hand, p2Hand, communityCards));
    }

    private static ArrayList<Card> createDeck() {
        // Creates a standard 52-card deck
        ArrayList<Card> deck = new ArrayList<>();
        String[] suits = Utility.getSuits();
        String[] ranks = Utility.getRanks();

        for (String suit : suits) {
            for (String rank : ranks) {
                deck.add(new Card(rank, suit));
            }
        }
        return deck;
    }
}
