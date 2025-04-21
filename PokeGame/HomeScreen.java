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
        setBackground("img/Menu_Screen.png");
        showHomeScreen();
    }

    private void showHomeScreen() {
        System.out.println("Home Screen");
        System.out.println("1. Create a Deck");
        System.out.println("2. Open Pack");
        System.out.println("3. Battle");
        System.out.println("4. Exit");
        System.out.println("Coins: " + coins);
        
        // Check for user input
        if (Mayflower.isKeyDown(Keyboard.KEY_1)) {
            Mayflower.setWorld(new DeckCreationScreen(this));
            canBattle = true;
        } 
        
        else if (Mayflower.isKeyDown(Keyboard.KEY_2)){
            Mayflower.setWorld(new OpenPackScreen(this));
        }
        else if (Mayflower.isKeyDown(Keyboard.KEY_3)) {
            if (canBattle) {
                Mayflower.setWorld(new BattleScreen(this));
            } else {
                System.out.println("You need to create a deck before battling!");
            }
        } else if (Mayflower.isKeyDown(Keyboard.KEY_9)) {
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