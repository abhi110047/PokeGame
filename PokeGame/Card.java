import java.util.*;

public abstract class Card
{
    
    protected String name;
    protected String description;
    
    public Card(String name, String description){
        this.name = name;
        this.description = description;
    }
    
    public String getName(){
        return name;
    }
    
    public String getDescription(){
        return description;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Card card = (Card) obj;
        return name.equals(card.name) && description.equals(card.description);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(name, description);
    }
}