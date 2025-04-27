import java.util.*;
import mayflower.*;
import java.util.*;

public class DeckManager {
    private Deck playerDeck;

    public DeckManager(Deck deck) {
        this.playerDeck = deck;
    }

    public void createDeck() {
        // Clear existing deck
        playerDeck.clear();
        
        // Add some initial cards
        playerDeck.addCard(new PokemonCard("Pikachu", "Electric type Pokémon", 60, 20, "Electric", 1, 1));
        playerDeck.addCard(new PokemonCard("Charmander", "Fire type Pokémon", 50, 30, "Fire", 1, 1));
        playerDeck.addCard(new PokemonCard("Squirtle", "Water type Pokémon", 50, 20, "Water", 1, 1));
        playerDeck.addCard(new PokemonCard("Bulbasaur", "Grass type Pokémon", 50, 20, "Grass", 1, 1));
        playerDeck.addCard(new EnergyCard("Fire Energy", "Provides fire energy", "Fire"));
        playerDeck.addCard(new EnergyCard("Water Energy", "Provides water energy", "Water"));
        playerDeck.addCard(new EnergyCard("Electric Energy", "Provides electric energy", "Electric"));
        playerDeck.addCard(new EnergyCard("Grass Energy", "Provides grass energy", "Grass"));
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