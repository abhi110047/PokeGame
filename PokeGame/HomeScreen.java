import mayflower.*;

public class HomeScreen extends World {
    private Deck playerDeck;
    private int coins;
    private boolean canBattle;
    private DeckManager deckManager;
    private CardPackManager cardPackManager;

    public HomeScreen() {
        playerDeck = new Deck();
        coins = 0;
        canBattle = false;
        deckManager = new DeckManager(playerDeck);
        cardPackManager = new CardPackManager(playerDeck);
        setBackground("img/home_background.png");
        showHomeScreen();
    }

    private void showHomeScreen() {
        System.out.println("Home Screen");
        System.out.println("1. Create a Deck");
        System.out.println("2. Open Basic Pack (Free)");
        System.out.println("3. Open Great Pack (Cost: " + cardPackManager.getGreatPackCost() + " coins)");
        System.out.println("4. Open Might Pack (Cost: " + cardPackManager.getMightPackCost() + " coins)");
        System.out.println("5. Battle");
        System.out.println("6. Exit");
        System.out.println("Coins: " + coins);
        
        // Check for user input
        if (Mayflower.isKeyDown(Keyboard.KEY_1)) {
            Mayflower.setWorld(new DeckCreationScreen(this));
        } else if (Mayflower.isKeyDown(Keyboard.KEY_2)) {
            cardPackManager.openCardPack(this, "basic");
        } else if (Mayflower.isKeyDown(Keyboard.KEY_3)) {
            cardPackManager.openCardPack(this, "great");
        } else if (Mayflower.isKeyDown(Keyboard.KEY_4)) {
            cardPackManager.openCardPack(this, "might");
        } else if (Mayflower.isKeyDown(Keyboard.KEY_5)) {
            if (canBattle) {
                Mayflower.setWorld(new BattleScreen(this));
            } else {
                System.out.println("You need to create a deck before battling!");
            }
        } else if (Mayflower.isKeyDown(Keyboard.KEY_6)) {
            Mayflower.exit();
        }
    }

    public void addCoins(int amount) {
        coins += amount;
    }

    public int getCoins() {
        return coins;
    }

    public Deck getPlayerDeck() {
        return playerDeck;
    }
    
    public void setCanBattle(boolean canBattle) {
        this.canBattle = canBattle;
    }
    
    public void act() {
        showHomeScreen();
    }
}