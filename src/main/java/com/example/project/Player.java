package com.example.project;

import java.util.ArrayList;

public class Player {
    // Player's own cards
    private ArrayList<Card> hand;
    // Player's own cards combined with the community cards
    private ArrayList<Card> allCards;
    // All possible suits (like ♠, ♥, ♣, ♦)
    private String[] suits = Utility.getSuits(); // ["♠","♥","♣", "♦"]
    // All possible ranks (from "2" up to "A")
    private String[] ranks = Utility.getRanks(); // ["2", "3", ..., "J", "Q", "K", "A"]

    // Constructor: create empty lists for hand and allCards
    public Player() {
        hand = new ArrayList<>();
        allCards = new ArrayList<>();
    }

    // Get the player's own cards
    public ArrayList<Card> getHand() {
        return hand;
    }

    // Get the combined list of all cards (personal + community)
    public ArrayList<Card> getAllCards() {
        return allCards;
    }

    // Add a card to the player's hand (if the card isn't null)
    public void addCard(Card c) {
        if (c != null) {
            hand.add(c);
        }
    }

    // Evaluate the player's hand using the community cards and return the ranking
    public String playHand(ArrayList<Card> communityCards) {
        allCards.clear();                     // Clear any previous cards
        allCards.addAll(hand);                // Add player's own cards
        allCards.addAll(communityCards);       // Add community cards
        sortAllCards();                       // Sort them in order

        // Check for hand rankings from best to worst
        if (isRoyalFlush()) {
            return "Royal Flush";
        } else if (isStraightFlush()) {
            return "Straight Flush";
        } else if (isFourOfAKind()) {
            return "Four of a Kind";
        } else if (isFullHouse()) {
            return "Full House";
        } else if (isFlush()) {
            return "Flush";
        } else if (isStraight()) {
            return "Straight";
        } else if (isThreeOfAKind()) {
            return "Three of a Kind";
        } else if (isTwoPair()) {
            return "Two Pair";
        } else if (isPair()) {
            return "A Pair";
        }
        // If none of the above, then check for high card in the player's hand
        Card highestCard = allCards.get(allCards.size() - 1);
        for (Card card : hand) {
            if (card.getRank().equals(highestCard.getRank())) {
                return "High Card";
            }
        }
        // If no ranking applies, return "Nothing"
        return "Nothing";
    }

    // Sort all cards by rank using bubble sort (not the fastest, but simple!)
    public void sortAllCards() {
        for (int i = 0; i < allCards.size() - 1; i++) {
            for (int j = 0; j < allCards.size() - i - 1; j++) {
                int rank1 = getRankIndex(allCards.get(j).getRank());
                int rank2 = getRankIndex(allCards.get(j + 1).getRank());
                if (rank1 > rank2) {
                    // Swap the cards if they're out of order
                    Card temp = allCards.get(j);
                    allCards.set(j, allCards.get(j + 1));
                    allCards.set(j + 1, temp);
                }
            }
        }
    }

    // Count how many times each rank appears in allCards
    public ArrayList<Integer> findRankingFrequency() {
        ArrayList<Integer> frequency = new ArrayList<>();
        // Start with zero count for each rank
        for (int i = 0; i < ranks.length; i++) {
            frequency.add(0);
        }

        // Go through every card and update the count for its rank
        for (Card card : allCards) {
            int index = getRankIndex(card.getRank());
            frequency.set(index, frequency.get(index) + 1);
        }

        return frequency;
    }

    // Count how many times each suit appears in allCards
    public ArrayList<Integer> findSuitFrequency() {
        ArrayList<Integer> frequency = new ArrayList<>();
        // Initialize count to zero for each suit
        for (int i = 0; i < suits.length; i++) {
            frequency.add(0);
        }

        // Tally up each suit from all cards
        for (Card card : allCards) {
            int index = getSuitIndex(card.getSuit());
            frequency.set(index, frequency.get(index) + 1);
        }

        return frequency;
    }

    // Helper: get the index of a given rank from the ranks array
    private int getRankIndex(String rank) {
        for (int i = 0; i < ranks.length; i++) {
            if (ranks[i].equals(rank)) {
                return i;
            }
        }
        // This shouldn't happen if the rank is valid
        return -1;
    }

    // Helper: get the index of a given suit from the suits array
    private int getSuitIndex(String suit) {
        for (int i = 0; i < suits.length; i++) {
            if (suits[i].equals(suit)) {
                return i;
            }
        }
        // Should never happen with correct suit values
        return -1;
    }

    // Check if the hand is a flush (5+ cards of the same suit)
    private boolean isFlush() {
        for (int count : findSuitFrequency()) {
            if (count >= 5) {
                return true;
            }
        }
        return false;
    }

    // Check if the hand is a straight (5 consecutive ranks)
    private boolean isStraight() {
        int consecutive = 0;
        int prevIndex = -1;
        // Look through sorted cards for consecutive ranks
        for (Card card : allCards) {
            int currentIndex = getRankIndex(card.getRank());
            if (prevIndex != -1 && currentIndex == prevIndex + 1) {
                consecutive++;
                // When we see 4 gaps (meaning 5 cards in a row), it's a straight
                if (consecutive >= 4) {
                    return true;
                }
            } else if (currentIndex != prevIndex) {
                // Reset if the cards aren't consecutive
                consecutive = 0;
            }
            prevIndex = currentIndex;
        }
        return false;
    }

    // Check for a royal flush (straight flush that ends with an Ace)
    private boolean isRoyalFlush() {
        return isStraightFlush() && allCards.get(allCards.size() - 1).getRank().equals("A");
    }

    // Check for a straight flush (both straight and flush)
    private boolean isStraightFlush() {
        return isFlush() && isStraight();
    }

    // Check for four cards of the same rank
    private boolean isFourOfAKind() {
        ArrayList<Integer> freq = findRankingFrequency();
        for (int count : freq) {
            if (count == 4) {
                return true;
            }
        }
        return false;
    }

    // Check for a full house (a three-of-a-kind plus a pair)
    private boolean isFullHouse() {
        ArrayList<Integer> freq = findRankingFrequency();
        boolean hasThree = false;
        boolean hasTwo = false;
        // See if there's a three-of-a-kind and a pair
        for (int count : freq) {
            if (count == 3) {
                hasThree = true;
            } else if (count == 2) {
                hasTwo = true;
            }
        }
        return hasThree && hasTwo;
    }

    // Check for three cards of the same rank
    private boolean isThreeOfAKind() {
        ArrayList<Integer> freq = findRankingFrequency();
        for (int count : freq) {
            if (count == 3) {
                return true;
            }
        }
        return false;
    }

    // Check for two different pairs
    private boolean isTwoPair() {
        int pairCount = 0;
        // Count each pair you find
        for (int count : findRankingFrequency()) {
            if (count == 2) {
                pairCount++;
            }
        }
        return pairCount >= 2;
    }

    // Check for a single pair
    private boolean isPair() {
        ArrayList<Integer> freq = findRankingFrequency();
        for (int count : freq) {
            if (count == 2) {
                return true;
            }
        }
        return false;
    }

    // Simple override to print the player's hand nicely
    @Override
    public String toString() {
        return hand.toString();
    }
}
