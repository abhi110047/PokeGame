import mayflower.*;
import java.util.*;

public class CardPacks
{
    private List<Card> Cards;

    
    public CardPacks(){
        // Initialize all card lists
        Cards = new ArrayList<>();

        // Basic Pack Cards (Common)
        Cards.add(new PokemonCard("Pikachu", "Electric type Pokémon", 60, 20));
        Cards.add(new PokemonCard("Charmander", "Fire type Pokémon", 50, 30));
        Cards.add(new PokemonCard("Squirtle", "Water type Pokémon", 50, 30));
        Cards.add(new PokemonCard("Bulbasaur", "Grass type Pokémon", 50, 30));
        Cards.add(new TrainerCard("Potion", "Heals 20 HP"));
        Cards.add(new TrainerCard("Switch", "Switch your active Pokémon"));
        Cards.add(new EnergyCard("Fire Energy", "Provides fire energy"));
        Cards.add(new EnergyCard("Water Energy", "Provides water energy"));
        Cards.add(new EnergyCard("Electric Energy", "Provides electric energy"));
        Cards.add(new EnergyCard("Grass Energy", "Provides grass energy"));
        
        // Great Pack Cards (Uncommon + Basic)
        Cards.addAll(Cards); // Include basic cards
        Cards.add(new PokemonCard("Raichu", "Electric type Pokémon", 90, 40));
        Cards.add(new PokemonCard("Charizard", "Fire type Pokémon", 120, 50));
        Cards.add(new PokemonCard("Blastoise", "Water type Pokémon", 120, 50));
        Cards.add(new PokemonCard("Venusaur", "Grass type Pokémon", 120, 50));
        Cards.add(new TrainerCard("Super Potion", "Heals 60 HP"));
        Cards.add(new TrainerCard("Rare Candy", "Evolve a Pokémon immediately"));
        
        // Might Pack Cards (Rare + Great)
        //Cards.addAll(Cards); // Include great cards
        Cards.add(new PokemonCard("Mewtwo", "Psychic type Pokémon", 150, 70));
        Cards.add(new PokemonCard("Mew", "Psychic type Pokémon", 130, 60));
        Cards.add(new PokemonCard("Lugia", "Flying type Pokémon", 140, 65));
        Cards.add(new PokemonCard("Ho-Oh", "Fire type Pokémon", 140, 65));
        Cards.add(new TrainerCard("Master Ball", "Search your deck for any Pokémon"));
        Cards.add(new TrainerCard("Full Heal", "Remove all status conditions"));
    }
    
    public List<Card> openPack(String packType) {
        List<Card> selectedCards = new ArrayList<>();
        List<Card> sourceCards;
        int cardsToDraw;
        
        switch(packType.toLowerCase()) {
            case "great":
                sourceCards = Cards;
                cardsToDraw = 8;
                break;
            case "might":
                sourceCards = Cards;
                cardsToDraw = 6;
                break;
            default: // Basic pack
                sourceCards = Cards;
                cardsToDraw = 10;
                break;
        }
        
        Collections.shuffle(sourceCards);
        return sourceCards.subList(0, Math.min(cardsToDraw, sourceCards.size()));
    }
}
