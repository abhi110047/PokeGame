import mayflower.*;
import java.util.*;

public class Deck
{
    private List<Card> cards;
    private List<Card> allCards; // All cards owned by the player
    private Random random;
    
    public Deck(){
        cards = new ArrayList<>();
        allCards = new ArrayList<>();
        random = new Random();
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
    
    public void shuffle() {
        for (int i = cards.size() - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            Card temp = cards.get(i);
            cards.set(i, cards.get(j));
            cards.set(j, temp);
        }
    }
    
    public Card drawCard() {
        if (!cards.isEmpty()) {
            return cards.remove(0);
        }
        return null;
    }
    
    public boolean isEmpty() {
        return cards.isEmpty();
    }
    
    public int size() {
        return cards.size();
    }
    
    public void addAll(List<Card> cards) {
        if (cards != null) {
            this.cards.addAll(cards);
        }
    }
}
