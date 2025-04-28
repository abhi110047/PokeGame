import mayflower.*;

public class CardActor extends Actor {
    private Card card;
    private boolean isSelected;
    private boolean isFaceUp;
    private int x;
    private int y;

    public CardActor(Card card, int x, int y) {
        super();
        this.card = card;
        this.x = x;
        this.y = y;
        this.isSelected = false;
        this.isFaceUp = true;
        
        setLocation(x, y);
        updateImage();
    }

    private void updateImage() {
        if (isFaceUp && card != null) {
            // Try to load the card's image first
            try {
                MayflowerImage img = new MayflowerImage(card.getImagePath());
                img.scale(100, 140);
                setImage(img);
            } catch (Exception e) {
                // If image loading fails, create a basic card front
                setImage("images/card_front.png");
                MayflowerImage img = getImage();
                img.scale(100, 140);
                
                // Draw card info as text
                World w = getWorld();
                if (w != null) {
                    w.showText(card.getName(), x + 5, y + 20, new Color(0, 0, 0));
                    
                    if (card instanceof PokemonCard) {
                        PokemonCard pokemon = (PokemonCard)card;
                        w.showText("HP: " + pokemon.getHP(), x + 5, y + 35, new Color(0, 0, 0));
                        w.showText("Type: " + pokemon.getType(), x + 5, y + 50, new Color(0, 0, 0));
                    } else if (card instanceof EnergyCard) {
                        EnergyCard energy = (EnergyCard)card;
                        w.showText("Energy: " + energy.getEnergyType(), x + 5, y + 35, new Color(0, 0, 0));
                    } else if (card instanceof TrainerCard) {
                        TrainerCard trainer = (TrainerCard)card;
                        w.showText("Trainer: " + trainer.getTrainerType(), x + 5, y + 35, new Color(0, 0, 0));
                    }
                }
            }
        } else {
            // Show card back
            setImage("images/card_back.png");
            MayflowerImage img = getImage();
            img.scale(100, 140);
        }
    }

    public Card getCard() {
        return card;
    }

    public void setFaceUp(boolean faceUp) {
        if (this.isFaceUp != faceUp) {
            this.isFaceUp = faceUp;
            updateImage();
        }
    }

    public boolean isFaceUp() {
        return isFaceUp;
    }

    @Override
    public void act() {
        // Handle hover effect
        if (Mayflower.mouseHovered(this)) {
            if (!isSelected) {
                MayflowerImage currentImg = getImage();
                currentImg.scale(120, 168);
                isSelected = true;
            }
        } else if (isSelected) {
            updateImage();
            isSelected = false;
        }

        // Handle click
        if (Mayflower.mouseClicked(this)) {
            // Toggle face up/down when clicked
            setFaceUp(!isFaceUp);
            
            // Print card info when clicked
            if (card != null) {
                System.out.println("Clicked card: " + card.getName());
                if (card instanceof PokemonCard) {
                    PokemonCard pokemon = (PokemonCard)card;
                    System.out.println("HP: " + pokemon.getHP());
                    System.out.println("Type: " + pokemon.getType());
                } else if (card instanceof EnergyCard) {
                    EnergyCard energy = (EnergyCard)card;
                    System.out.println("Energy Type: " + energy.getEnergyType());
                } else if (card instanceof TrainerCard) {
                    TrainerCard trainer = (TrainerCard)card;
                    System.out.println("Trainer Type: " + trainer.getTrainerType());
                }
            }
        }
    }
} 