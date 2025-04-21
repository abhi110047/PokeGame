import mayflower.*;
import mayflower.Label;
import java.util.*;

public class DeckCreationScreen extends World {
    private Deck playerDeck;
    private List<Card> availableCards;
    private int selectedCardIndex;
    private HomeScreen homeScreen;
    private Label deckSizeLabel;
    private List<Label> cardLabels;
    private String color;

    public DeckCreationScreen(HomeScreen homeScreen) {
        this.homeScreen = homeScreen;
        this.playerDeck = homeScreen.getPlayerDeck();
        this.availableCards = playerDeck.getAllCards();
        this.selectedCardIndex = 0;
        this.cardLabels = new ArrayList<>();
        
        setBackground("img/deck_creation_background.png");
        
        // Initialize UI elements
        deckSizeLabel = new Label("Deck Size: " + playerDeck.getSize() + "/60");
        addObject(deckSizeLabel, 10, 10);
        
        displayCards();
    }

    private void displayCards() {
        // Clear previous card displays
        for (Label label : cardLabels) {
            removeObject(label);
        }
        cardLabels.clear();
        
        // Re-add deck size label
        addObject(deckSizeLabel, 10, 10);
        
        // Display available cards
        int y = 50;
        for (int i = 0; i < availableCards.size(); i++) {
            Card card = availableCards.get(i);
            Label cardLabel = new Label(card.getName() + " (" + playerDeck.getCardCount(card) + ")");
            if (i == selectedCardIndex) {
                color = "yellow"; // Yellow
            } else {
                color = "white"; // White
            }
            addObject(cardLabel, 50, y);
            cardLabels.add(cardLabel);
            y += 30;
        }
    }

    private void updateDeckSize() {
        deckSizeLabel.setText("Deck Size: " + playerDeck.getSize() + "/60");
    }

    public void act() {
        // Handle card selection
        if (Mayflower.isKeyDown(Keyboard.KEY_UP) && selectedCardIndex > 0) {
            selectedCardIndex--;
            displayCards();
        } else if (Mayflower.isKeyDown(Keyboard.KEY_DOWN) && selectedCardIndex < availableCards.size() - 1) {
            selectedCardIndex++;
            displayCards();
        }
        
        // Handle card addition
        if (Mayflower.isKeyDown(Keyboard.KEY_SPACE)) {
            Card selectedCard = availableCards.get(selectedCardIndex);
            if (playerDeck.getCardCount(selectedCard) < 4) {
                playerDeck.addCard(selectedCard);
                updateDeckSize();
                displayCards();
            }
        }
        
        // Handle deck completion
        if (Mayflower.isKeyDown(Keyboard.KEY_ENTER)) {
            if (playerDeck.getSize() >= 30) {
                homeScreen.setCanBattle(true);
                Mayflower.setWorld(homeScreen);
            }
        }
        
        // Handle return to home screen
        if (Mayflower.isKeyDown(Keyboard.KEY_ESCAPE)) {
            Mayflower.setWorld(homeScreen);
        }
    }
} 