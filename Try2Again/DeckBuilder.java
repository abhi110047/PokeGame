
import java.util.Stack;
public class DeckBuilder {
    
    /**
     * Creates a sample starter deck with the 5 sample Pokémon cards
     */
    public static Deck createStarterDeck() {
        Stack<Card> cardStack = new Stack<>();
    
        // Push Pokémon cards (5 sample cards)
        cardStack.push(createLardiasLardios());
        cardStack.push(createNigamath());
        cardStack.push(createSuhasStroll());
        cardStack.push(createCrusader());
        cardStack.push(createVedanth());
    
        // Push Energy cards (15 cards)
        for (int i = 0; i < 3; i++) {
            cardStack.push(new EnergyCard("Fire", "Fire", 1));
            cardStack.push(new EnergyCard("Water", "Water", 1));
            cardStack.push(new EnergyCard("Grass", "Grass", 1));
            cardStack.push(new EnergyCard("Lightning", "Lightning", 1));
            cardStack.push(new EnergyCard("Psychic", "Psychic", 1));
        }
    
        // Create Deck and add all cards
        Deck deck = new Deck("Sample Starter Deck");
        while (!cardStack.isEmpty()) {
            deck.addCard(cardStack.pop());  // Add in LIFO order
        }
    
        return deck;
    }
    
    /**
     * Creates a LardiosLardias card
     */
    public static PokemonCard createLardiasLardios() {
        Attack thunderShock = new Attack("Thunder Shock", 20, "Flip a coin. If heads, the defending Pokémon is now Paralyzed.");
        Attack quickAttack = new Attack("Quick Attack", 10, "This attack does 10 damage plus 20 more damage if you flip heads.");
        
        return new PokemonCard("LardiosLardias", "Lightning", 60, thunderShock, quickAttack, 2, "Fighting");
    }
    
    /**
     * Creates a Nigamath card
     */
    public static PokemonCard createNigamath() {
        Attack flamethrower = new Attack("Flamethrower", 90, "Discard 1 Energy card attached to this Pokémon.");
        Attack fireBlast = new Attack("Fire Blast", 120, "Discard 2 Energy cards attached to this Pokémon.");
        
        return new PokemonCard("Nigamath", "Fire", 120, flamethrower, fireBlast, 4, "Water");
    }
    
    /**
     * Creates a SuhasStroll card
     */
    public static PokemonCard createSuhasStroll() {
        Attack hydroPump = new Attack("Hydro Pump", 40, "Does 40 damage plus 20 more damage for each Water Energy attached to this Pokémon.");
        Attack hydroCannon = new Attack("Hydro Cannon", 120, "This Pokémon can't attack during your next turn.");
        
        return new PokemonCard("SuhasStroll", "Water", 100, hydroPump, hydroCannon, 3, "Grass");
    }
    
    /**
     * Creates a Crusader card
     */
    public static PokemonCard createCrusader() {
        Attack razorLeaf = new Attack("Razor Leaf", 30, "No additional effect.");
        Attack solarBeam = new Attack("Solar Beam", 60, "No additional effect.");
        
        return new PokemonCard("Crusader", "Grass", 100, razorLeaf, solarBeam, 3, "Fire");
    }
    
    /**
     * Creates a Vedanth card
     */
    public static PokemonCard createVedanth() {
        Attack psychic = new Attack("Psychic", 50, "This attack does 10 more damage for each Energy card attached to the Defending Pokémon.");
        Attack psystrike = new Attack("Psystrike", 120, "This attack's damage isn't affected by any effects on the Defending Pokémon.");
        
        return new PokemonCard("Mewtwo", "Psychic", 90, psychic, psystrike, 4, "Psychic");
    }
}