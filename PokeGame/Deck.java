import mayflower.*;
import java.util.*;

public class Deck
{
    private List<Card> cards;
    private List<Card> allCards; // All cards owned by the player
    
    public Deck(){
        cards = new ArrayList<>();
        allCards = new ArrayList<>();
    }
    
    public void addCard(Card card){
        if (cards.size() < 60) {
            cards.add(card);
            if (!allCards.contains(card)) {
                allCards.add(card);
            }
        }
    }
    
    public List<Card> getCards(){
        return cards;
    }
    
    public List<Card> getAllCards() {
        return allCards;
    }
    
    public int getSize() {
        return cards.size();
    }
    
    public int getCardCount(Card card) {
        int count = 0;
        for (Card c : cards) {
            if (c.equals(card)) {
                count++;
            }
        }
        return count;
    }
    
    public boolean deckIsFull(){
        return cards.size() >= 60;
    }
    
    public boolean isValid() {
        return cards.size() >= 30 && cards.size() <= 60;
    }
    
    public void clear() {
        cards.clear();
    }
}
