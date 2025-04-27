import mayflower.*;
import java.io.File;

public class CardActor extends Actor {
    private Card card;
    private boolean isSelected;
    private boolean isFaceUp;
    private static final String DEFAULT_IMAGE = "img/backgrounds/card_back.png";
    
    public CardActor(Card card) {
        this.card = card;
        this.isSelected = false;
        this.isFaceUp = true;
        setImage(DEFAULT_IMAGE); // Default back image
        updateImage();
    }
    
    public void updateImage() {
        try {
            if (isFaceUp) {
                String imagePath = null;
                if (card instanceof PokemonCard) {
                    PokemonCard pokemon = (PokemonCard) card;
                    imagePath = "img/pokemon/Lardias_Lardios.png";
                } else if (card instanceof EnergyCard) {
                    EnergyCard energy = (EnergyCard) card;
                    // Get the first word of the energy type (e.g., "Fire" from "Fire Energy")
                    String energyType = energy.getEnergyType().split(" ")[0].toLowerCase();
                    imagePath = "img/energy/" + energyType + ".png";
                }
                
                if (imagePath != null && new File(imagePath).exists()) {
                    setImage(imagePath);
                } else {
                    System.out.println("Image not found: " + imagePath);
                    setImage(DEFAULT_IMAGE);
                }
            } else {
                setImage(DEFAULT_IMAGE);
            }
        } catch (Exception e) {
            System.out.println("Error loading image for card: " + card.getName());
            e.printStackTrace();
            setImage(DEFAULT_IMAGE);
        }
    }
    
    public void setSelected(boolean selected) {
        this.isSelected = selected;
        if (selected) {
            setLocation(getX(), getY() - 20); // Move up when selected
        } else {
            setLocation(getX(), getY() + 20); // Move back down
        }
    }
    
    public void setFaceUp(boolean faceUp) {
        this.isFaceUp = faceUp;
        updateImage();
    }
    
    public Card getCard() {
        return card;
    }
    
    public boolean isSelected() {
        return isSelected;
    }
    
    public void act() {
        try {
            // Handle mouse hover using Mayflower's mouse methods
            int mouseX = Mayflower.mouseX();
            int mouseY = Mayflower.mouseY();
            
            // Check if mouse is over this card
            if (mouseX >= getX() && mouseX < getX() + getWidth() &&
                mouseY >= getY() && mouseY < getY() + getHeight()) {
                setLocation(getX(), getY() - 10);
            } else if (!isSelected) {
                setLocation(getX(), getY() + 10);
            }
        } catch (Exception e) {
            System.out.println("Error in CardActor act method: " + e.getMessage());
        }
    }
} 