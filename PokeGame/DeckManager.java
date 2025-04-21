import java.util.*;
import mayflower.*;

public class DeckManager {
    private Deck playerDeck;

    public DeckManager(Deck deck) {
        this.playerDeck = deck;
    }

    public void createDeck() {
        // Clear existing deck
        playerDeck.clear();
        
        // Add some initial cards
        playerDeck.addCard(new PokemonCard("Pikachu", "Electric type Pokémon", 60, 20));
        playerDeck.addCard(new PokemonCard("Charmander", "Fire type Pokémon", 50, 30));
        playerDeck.addCard(new PokemonCard("Squirtle", "Water type Pokémon", 50, 30));
        playerDeck.addCard(new PokemonCard("Bulbasaur", "Grass type Pokémon", 50, 30));
        playerDeck.addCard(new EnergyCard("Fire Energy", "Provides fire energy"));
        playerDeck.addCard(new EnergyCard("Water Energy", "Provides water energy"));
        playerDeck.addCard(new EnergyCard("Electric Energy", "Provides electric energy"));
        playerDeck.addCard(new EnergyCard("Grass Energy", "Provides grass energy"));
        playerDeck.addCard(new TrainerCard("Potion", "Heals 20 HP"));
        playerDeck.addCard(new TrainerCard("Switch", "Switch your active Pokémon"));
    }

    public Deck getPlayerDeck() {
        return playerDeck;
    }

    public boolean isValidDeck() {
        return playerDeck.isValid();
    }
}