import mayflower.*;

public class BattleScreen extends World {
    private Deck playerDeck;
    private PokemonCard playerPokemon;
    private PokemonCard opponentPokemon;
    private int playerHealth;
    private int opponentHealth;

    public BattleScreen(HomeScreen homeScreen) {
        this.playerDeck = homeScreen.getPlayerDeck();
        this.playerPokemon = selectPlayerPokemon();
        this.opponentPokemon = selectOpponentPokemon();
        this.playerHealth = playerPokemon.getHealth();
        this.opponentHealth = opponentPokemon.getHealth();
        setBackground("img/battle_background.png");
    }

    public void act() {
        showBattleScreen();
        if (Mayflower.isKeyDown(Keyboard.KEY_A)) {
            attackOpponent();
        } else if (Mayflower.isKeyDown(Keyboard.KEY_B)) {
            defend();
        } else if (Mayflower.isKeyDown(Keyboard.KEY_ESCAPE)) {
            Mayflower.setWorld(new HomeScreen());
        }
    }

    private void showBattleScreen() {
        System.out.println("Battle Screen");
        System.out.println("Your Pokémon: " + playerPokemon.getName());
        System.out.println("Opponent's Pokémon: " + opponentPokemon.getName());
        System.out.println("Your Health: " + playerHealth);
        System.out.println("Opponent's Health: " + opponentHealth);
        System.out.println("Press 'A' to Attack, 'B' to Defend, 'ESC' to Exit");
    }

    private PokemonCard selectPlayerPokemon() {
        // Select the first Pokémon from the player's deck for simplicity
        return (PokemonCard) playerDeck.getCards().get(0);
    }

    private PokemonCard selectOpponentPokemon() {
        // Create a simple opponent Pokémon for the battle
        return new PokemonCard("Opponent Pikachu", "Electric type Pokémon", 60, 20);
    }
    
    private void attackOpponent() {
        opponentHealth -= playerPokemon.getAttackPower();
        if (opponentHealth <= 0) {
            System.out.println("You defeated " + opponentPokemon.getName() + "!");
            return;
        }
        // Opponent attacks back
        playerHealth -= opponentPokemon.getAttackPower();
        if (playerHealth <= 0) {
            System.out.println("Your " + playerPokemon.getName() + " has lost!");
            return;
        }
    }
    
    private void defend(){
        System.out.println("You've defended");
    }
}