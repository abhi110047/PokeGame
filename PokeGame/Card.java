import java.util.*;
import mayflower.*;

public abstract class Card extends Actor
{
    protected String name;
    protected String description;
    protected boolean isSelected;
    
    public Card(String name, String description){
        super();
        this.name = name;
        this.description = description;
        this.isSelected = false;
        setImage("img/backgrounds/card_back.png");  // Set default image
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

    @Override
    public void act() {
        MouseInfo mouse = Mayflower.getMouseInfo();
        if (mouse != null) {
            if (mouse.getX() >= getX() && mouse.getX() < getX() + getWidth() &&
                mouse.getY() >= getY() && mouse.getY() < getY() + getHeight()) {
                if (!isSelected) {
                    setLocation(getX(), getY() - 10);
                }
                if (mouse.wasPressed()) {
                    isSelected = true;
                }
            } else if (!isSelected) {
                setLocation(getX(), getY());
            }
        }
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}