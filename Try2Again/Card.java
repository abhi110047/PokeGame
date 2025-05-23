 

/**
 * Base class for all cards in the game
 */
public abstract class Card {
    private String name;
    private String type;
    
    public Card(String name, String type) {
        this.name = name;
        this.type = type;
    }
    
    public String getName() {
        return name;
    }
    
    public String getType() {
        return type;
    }
    
    @Override
    public String toString() {
        return name + " (" + type + ")";
    }
}
