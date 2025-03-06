package com.example.project;

import java.util.ArrayList;

public class Player {
    // Player's personal cards
    private ArrayList<Card> hand;
    // Player's hand + community cards
    private ArrayList<Card> allCards;
    // Array of all possible suits
    private String[] suits = Utility.getSuits(); // ["♠","♥","♣", "♦"]
    // Array of all possible ranks
    private String[] ranks = Utility.getRanks(); // ["2", "3", ..., "J", "Q", "K", "A"]

    // Constructor initializes empty hand and allCards
    public Player() {
        hand = new ArrayList<>();
        allCards = new ArrayList<>();
    }

    // Getter methods for hand and allCards
    public ArrayList<Card> getHand() {
        return hand;
    }

    public ArrayList<Card> getAllCards() {
        return allCards;
    }

    // Method to add a card to the player's hand
    public void addCard(Card c) {
        if (c != null) {
            hand.add(c);
        }
    }

    // Method to evaluate the player's hand and determine its ranking
    public String playHand(ArrayList<Card> communityCards) {
        allCards.clear();
        allCards.addAll(hand);
        allCards.addAll(communityCards); // Combine the player's hand with community cards
        sortAllCards();

        // Check for different hand rankings from highest to lowest
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
        // Check for high card in player's hand
        Card highestCard = allCards.get(allCards.size() - 1);
        for (Card card : hand) {
            if (card.getRank().equals(highestCard.getRank())) {
                return "High Card";
            }
        }
        return "Nothing";
    }

    // Method to sort all cards in ascending order of rank using bubble sort
    public void sortAllCards() {
        for (int i = 0; i < allCards.size() - 1; i++) {
            for (int j = 0; j < allCards.size() - i - 1; j++) {
                int rank1 = getRankIndex(allCards.get(j).getRank());
                int rank2 = getRankIndex(allCards.get(j + 1).getRank());
                if (rank1 > rank2) {
                    Card temp = allCards.get(j);
                    allCards.set(j, allCards.get(j + 1));
                    allCards.set(j + 1, temp);
                }
            }
        }
    }

    // Method to calculate the frequency of each rank in allCards
    public ArrayList<Integer> findRankingFrequency() {
        ArrayList<Integer> frequency = new ArrayList<>();
        for (int i = 0; i < ranks.length; i++) {
            frequency.add(0);
        }

        for (Card card : allCards) {
            int index = getRankIndex(card.getRank());
            frequency.set(index, frequency.get(index) + 1);
        }

        return frequency;
    }

    // Method to calculate the frequency of each suit in allCards
    public ArrayList<Integer> findSuitFrequency() {
        ArrayList<Integer> frequency = new ArrayList<>();
        for (int i = 0; i < suits.length; i++) {
            frequency.add(0);
        }

        for (Card card : allCards) {
            int index = getSuitIndex(card.getSuit());
            frequency.set(index, frequency.get(index) + 1);
        }

        return frequency;
    }

    // Helper method to get the index of a rank in the ranks array
    private int getRankIndex(String rank) {
        for (int i = 0; i < ranks.length; i++) {
            if (ranks[i].equals(rank)) {
                return i;
            }
        }
        return -1; // Should never happen if valid ranks are used
    }

    // Helper method to get the index of a suit in the suits array
    private int getSuitIndex(String suit) {
        for (int i = 0; i < suits.length; i++) {
            if (suits[i].equals(suit)) {
                return i;
            }
        }
        return -1;
    }

    // Methods to check for different hand rankings

    private boolean isFlush() {
        for (int count : findSuitFrequency()) {
            if (count >= 5)
                return true;
        }
        return false;
    }

    private boolean isStraight() {
        int consecutive = 0;
        int prevIndex = -1;
        for (Card card : allCards) {
            int currentIndex = getRankIndex(card.getRank());
            if (prevIndex != -1 && currentIndex == prevIndex + 1) {
                consecutive++;
                if (consecutive >= 4)
                    return true;
            } else if (currentIndex != prevIndex) {
                consecutive = 0;
            }
            prevIndex = currentIndex;
        }
        return false;
    }

    private boolean isRoyalFlush() {
        return isStraightFlush() && allCards.get(allCards.size() - 1).getRank().equals("A");
    }

    private boolean isStraightFlush() {
        return isFlush() && isStraight();
    }

    private boolean isFourOfAKind() {
        ArrayList<Integer> freq = findRankingFrequency();
        for (int count : freq) {
            if (count == 4) {
                return true;
            }
        }
        return false;
    }

    private boolean isFullHouse() {
        ArrayList<Integer> freq = findRankingFrequency();
        boolean hasThree = false;
        boolean hasTwo = false;
        for (int count : freq) {
            if (count == 3) {
                hasThree = true;
            } else if (count == 2) {
                hasTwo = true;
            }
        }
        return hasThree && hasTwo;
    }

    private boolean isThreeOfAKind() {
        ArrayList<Integer> freq = findRankingFrequency();
        for (int count : freq) {
            if (count == 3) {
                return true;
            }
        }
        return false;
    }

    private boolean isTwoPair() {
        int pairCount = 0;
        for (int count : findRankingFrequency()) {
            if (count == 2)
                pairCount++;
        }
        return pairCount >= 2;
    }

    private boolean isPair() {
        ArrayList<Integer> freq = findRankingFrequency();
        for (int count : freq) {
            if (count == 2) {
                return true;
            }
        }
        return false;
    }

    // Override toString method to represent the player's hand as a string
    @Override
    public String toString() {
        return hand.toString();
    }
}
