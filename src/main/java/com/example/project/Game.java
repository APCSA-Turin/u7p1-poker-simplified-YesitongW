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
            // Tie-breaking logic inlined.
            ArrayList<Card> p1Cards = p1.getAllCards();
            ArrayList<Card> p2Cards = p2.getAllCards();

            // sort algorithm
            sortCards(p1Cards);
            sortCards(p2Cards);

            // Compare cards from highest to lowest.
            for (int i = p1Cards.size() - 1; i >= 0; i--) {
                int p1CardRank = getRankIndex(p1Cards.get(i).getRank());
                int p2CardRank = getRankIndex(p2Cards.get(i).getRank());

                if (p1CardRank > p2CardRank) {
                    return "Player 1 wins!";
                } else if (p1CardRank < p2CardRank) {
                    return "Player 2 wins!";
                }
            }
            // If every card is the same, it's a tie.
            return "Tie!";
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
        Collections.shuffle(deck); // Using shuffle is allowed.

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
        for (int i = 0; i < suits.length; i++) {
            for (int j = 0; j < ranks.length; j++) {
                deck.add(new Card(ranks[j], suits[i]));
            }
        }
        return deck;
    }

    // sort cards by their rank.
    private static void sortCards(ArrayList<Card> cards) {
        int n = cards.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                int rank1 = getRankIndex(cards.get(j).getRank());
                int rank2 = getRankIndex(cards.get(j + 1).getRank());
                if (rank1 > rank2) {
                    // Swap cards at positions j and j+1.
                    Card temp = cards.get(j);
                    cards.set(j, cards.get(j + 1));
                    cards.set(j + 1, temp);
                }
            }
        }
    }
}
