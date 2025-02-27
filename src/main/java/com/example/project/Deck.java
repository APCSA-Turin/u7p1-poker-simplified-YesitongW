package com.example.project;
import java.util.ArrayList;
import java.util.Collections;

public class Deck{
    private ArrayList<Card> cards;

    public Deck(){
        cards = new ArrayList<>();
        initializeDeck();
        shuffleDeck();
    }

    public ArrayList<Card> getCards(){
        return cards;
    }

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
    

    public  void shuffleDeck(){ //You can use the Collections library or another method. You do not have to create your own shuffle algorithm
        Collections.shuffle(cards);
    }

    public  Card drawCard(){
        if (!isEmpty()) {
            return cards.remove(cards.size() - 1); // Draw the top card
        }
        return null; // Return null if the deck is empty
    }

    public  boolean isEmpty(){
        return cards.isEmpty();
    }

   


}