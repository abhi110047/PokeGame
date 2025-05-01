import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class representing a deck of cards
 */
public class Deck {
    private List<Card> cards;
    private String name;
    
    public Deck(String name) {
        this.name = name;
        this.cards = new ArrayList<>();
    }
    
    public String getName() {
        return name;
    }
    
    public void addCard(Card card) {
        cards.add(card);
    }
    
    public int getSize() {
        return cards.size();
    }
    
    public void shuffle() {
        Collections.shuffle(cards);
    }
    
    public Card drawCard() {
        if (cards.isEmpty()) {
            return null;
        }
        return cards.remove(0);
    }
    
    public List<Card> getCards() {
        return new ArrayList<>(cards);
    }
    
    @Override
    public String toString() {
        return name + " (" + cards.size() + " cards)";
    }
}