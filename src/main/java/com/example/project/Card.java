package com.example.project;
import java.util.ArrayList;

public class Card{
    private String rank;// The rank of the card (e.g., "2", "J", "A")

    private String suit;// The suit of the card (e.g., "♠", "♥", "♣", "♦")

    public Card(String rank, String suit){
        this.rank = rank;
        this.suit = suit;
    }

    public String getRank(){return rank;}
    public String getSuit(){return suit;}
    
    @Override
    public String toString(){
        return rank + " of " + suit;
    }

}