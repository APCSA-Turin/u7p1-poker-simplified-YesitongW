package com.example.project;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    private ArrayList<Card> cards;

    // Constructor creates a deck, fills it with 52 cards, and shuffles it.
    public Deck() {
        cards = new ArrayList<>();
        initializeDeck();
        shuffleDeck();
    }

    // Return the list of cards (useful for debugging or if needed elsewhere)
    public ArrayList<Card> getCards() {
        return cards;
    }

    // Build a standard 52-card deck.
    public void initializeDeck() {
        String[] suits = Utility.getSuits();
        String[] ranks = Utility.getRanks();
        for (String suit : suits) {
            for (String rank : ranks) {
                Card card = new Card(rank, suit);
                cards.add(card);
            }
        }
    }

    // Shuffle the deck using Java's Collections library.
    public void shuffleDeck() {
        Collections.shuffle(cards);
    }

    // Draw a single card from the top (end) of the deck.
    public Card drawCard() {
        if (!isEmpty()) {
            return cards.remove(cards.size() - 1);
        }
        return null;
    }

    // Check if the deck has no more cards.
    public boolean isEmpty() {
        return cards.isEmpty();
    }

    // Deal a specified number of cards from the deck.
    public ArrayList<Card> dealCards(int number) {
        ArrayList<Card> dealtCards = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            if (!isEmpty()) {
                dealtCards.add(drawCard());
            }
        }
        return dealtCards;
    }
}