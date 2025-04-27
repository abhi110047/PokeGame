import mayflower.*;
import java.util.*;

public class CardPacks
{
    private List<Card> basicCards;
    private List<Card> greatCards;
    private List<Card> mightCards;
    
    public CardPacks(){
        // Initialize all card lists
        basicCards = new ArrayList<>();
        greatCards = new ArrayList<>();
        mightCards = new ArrayList<>();
        
        // Basic Pack Cards (Common)
        basicCards.add(new PokemonCard("Pikachu", "Electric type Pokémon", 60, 20, "Electric", 1, 1));
        basicCards.add(new PokemonCard("Charmander", "Fire type Pokémon", 50, 30, "Fire", 1, 1));
        basicCards.add(new PokemonCard("Squirtle", "Water type Pokémon", 50, 20, "Water", 1, 1));
        basicCards.add(new PokemonCard("Bulbasaur", "Grass type Pokémon", 50, 20, "Grass", 1, 1));
        basicCards.add(new TrainerCard("Potion", "Heals 20 HP"));
        basicCards.add(new TrainerCard("Switch", "Switch your active Pokémon"));
        basicCards.add(new EnergyCard("Fire Energy", "Provides fire energy", "Fire"));
        basicCards.add(new EnergyCard("Water Energy", "Provides water energy", "Water"));
        basicCards.add(new EnergyCard("Electric Energy", "Provides electric energy", "Electric"));
        basicCards.add(new EnergyCard("Grass Energy", "Provides grass energy", "Grass"));
        
        // Great Pack Cards (Uncommon + Basic)
        greatCards.addAll(basicCards); // Include basic cards
        greatCards.add(new PokemonCard("Raichu", "Electric type Pokémon", 90, 40, "Electric", 2, 1));
        greatCards.add(new PokemonCard("Charizard", "Fire type Pokémon", 120, 50, "Fire", 3, 2));
        greatCards.add(new PokemonCard("Blastoise", "Water type Pokémon", 120, 50, "Water", 3, 2));
        greatCards.add(new PokemonCard("Venusaur", "Grass type Pokémon", 120, 50, "Grass", 3, 2));
        greatCards.add(new TrainerCard("Super Potion", "Heals 60 HP"));
        greatCards.add(new TrainerCard("Rare Candy", "Evolve a Pokémon immediately"));
        
        // Might Pack Cards (Rare + Great)
        mightCards.addAll(greatCards); // Include great cards
        mightCards.add(new PokemonCard("Mewtwo", "Psychic type Pokémon", 150, 70, "Psychic", 1, 2));
        mightCards.add(new PokemonCard("Mew", "Psychic type Pokémon", 130, 60, "Psychic", 1, 1));
        mightCards.add(new PokemonCard("Lugia", "Flying type Pokémon", 140, 65, "Flying", 1, 2));
        mightCards.add(new PokemonCard("Ho-Oh", "Fire type Pokémon", 140, 65, "Fire", 1, 2));
        mightCards.add(new TrainerCard("Master Ball", "Search your deck for any Pokémon"));
        mightCards.add(new TrainerCard("Full Heal", "Remove all status conditions"));
    }
    
    public List<Card> openPack(String packType) {
        List<Card> selectedCards = new ArrayList<>();
        List<Card> sourceCards;
        int cardsToDraw;
        
        switch(packType.toLowerCase()) {
            case "great":
                sourceCards = greatCards;
                cardsToDraw = 8;
                break;
            case "might":
                sourceCards = mightCards;
                cardsToDraw = 6;
                break;
            default: // Basic pack
                sourceCards = basicCards;
                cardsToDraw = 10;
                break;
        }
        
        Collections.shuffle(sourceCards);
        return sourceCards.subList(0, Math.min(cardsToDraw, sourceCards.size()));
    }
}
