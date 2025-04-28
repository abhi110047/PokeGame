import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Player {
    private String name;
    private ArrayList<Card> deck;
    private ArrayList<Card> hand;
    private ArrayList<PokemonCard> bench;
    private PokemonCard activePokemon;
    private int prizeCards;
    private boolean hasPlayedSupporter;
    private boolean hasPlayedStadium;
    private Map<String, Integer> energyInPlay;

    public Player(String name) {
        this.name = name;
        this.deck = new ArrayList<>();
        this.hand = new ArrayList<>();
        this.bench = new ArrayList<>();
        this.prizeCards = 6;
        this.hasPlayedSupporter = false;
        this.hasPlayedStadium = false;
        this.energyInPlay = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public ArrayList<PokemonCard> getBench() {
        return bench;
    }

    public PokemonCard getActivePokemon() {
        return activePokemon;
    }

    public int getPrizeCards() {
        return prizeCards;
    }

    public void initializeDeck() {
        // Create sample Pokemon cards with attacks
        PokemonCard pikachu = new PokemonCard("Pikachu", "Mouse Pokemon", "img/pokemon/pikachu.png", 60, "Electric", "Basic", "", 1, "");
        Map<String, Integer> thunderboltCost = new HashMap<>();
        thunderboltCost.put("Electric", 2);
        pikachu.addAttack(new Attack("Thunderbolt", 50, thunderboltCost, "Flip a coin. If tails, this Pokemon does 20 damage to itself."));
        
        PokemonCard charizard = new PokemonCard("Charizard", "Flame Pokemon", "img/pokemon/charizard.png", 120, "Fire", "Stage 2", "Charmeleon", 3, "");
        Map<String, Integer> fireBlastCost = new HashMap<>();
        fireBlastCost.put("Fire", 3);
        charizard.addAttack(new Attack("Fire Blast", 100, fireBlastCost, ""));
        
        PokemonCard blastoise = new PokemonCard("Blastoise", "Shellfish Pokemon", "img/pokemon/blastoise.png", 100, "Water", "Stage 2", "Wartortle", 3, "");
        Map<String, Integer> hydroPumpCost = new HashMap<>();
        hydroPumpCost.put("Water", 2);
        blastoise.addAttack(new Attack("Hydro Pump", 80, hydroPumpCost, ""));

        // Add Pokemon cards to deck
        for (int i = 0; i < 4; i++) {
            deck.add(pikachu);
            deck.add(charizard);
            deck.add(blastoise);
        }

        // Add energy cards
        for (int i = 0; i < 10; i++) {
            deck.add(new EnergyCard("Electric Energy", "Basic Electric Energy", "img/energy/electric.png", "Electric"));
            deck.add(new EnergyCard("Fire Energy", "Basic Fire Energy", "img/energy/fire.png", "Fire"));
            deck.add(new EnergyCard("Water Energy", "Basic Water Energy", "img/energy/water.png", "Water"));
        }

        // Add trainer cards
        for (int i = 0; i < 4; i++) {
            deck.add(new TrainerCard("Potion", "Heal 30 damage from one of your Pokemon", "img/trainer/potion.png", "Item", "Heal 30 damage from one of your Pokemon"));
            deck.add(new TrainerCard("Switch", "Switch your active Pokemon with one from your bench", "img/trainer/switch.png", "Item", "Switch your active Pokemon with one from your bench"));
            deck.add(new TrainerCard("Professor Oak", "Discard your hand and draw 7 cards", "img/trainer/professoroak.png", "Supporter", "Discard your hand and draw 7 cards"));
        }

        shuffleDeck();
    }

    public void shuffleDeck() {
        Collections.shuffle(deck);
    }

    public void drawInitialHand() {
        for (int i = 0; i < 7 && !deck.isEmpty(); i++) {
            drawCard();
        }
    }

    public void drawCard() {
        if (!deck.isEmpty()) {
            Card card = deck.remove(0);
            hand.add(card);
        }
    }

    public boolean attachEnergy(EnergyCard energy, PokemonCard target) {
        if (energy == null || target == null) {
            return false;
        }
        
        if (target == activePokemon || bench.contains(target)) {
            target.attachEnergy(energy.getEnergyType());
            energyInPlay.put(energy.getEnergyType(), 
                energyInPlay.getOrDefault(energy.getEnergyType(), 0) + 1);
            return true;
        }
        return false;
    }

    public boolean detachEnergy(String energyType, PokemonCard target, int amount) {
        if (energyType == null || target == null || amount <= 0) {
            return false;
        }
        
        if (target == activePokemon || bench.contains(target)) {
            Map<String, Integer> attached = target.getEnergyAttached();
            int currentAmount = attached.getOrDefault(energyType, 0);
            if (currentAmount >= amount) {
                for (int i = 0; i < amount; i++) {
                    target.detachEnergy(energyType);
                }
                energyInPlay.put(energyType, 
                    energyInPlay.getOrDefault(energyType, 0) - amount);
                return true;
            }
        }
        return false;
    }

    public boolean playCard(Card card) {
        if (!hand.contains(card)) {
            return false;
        }

        hand.remove(card);
        
        if (card instanceof PokemonCard) {
            PokemonCard pokemon = (PokemonCard)card;
            if (activePokemon == null) {
                activePokemon = pokemon;
                pokemon.setActive(true);
            } else if (!isBenchFull()) {
                bench.add(pokemon);
                pokemon.setBenched(true);
            } else {
                return false;
            }
        } else if (card instanceof EnergyCard) {
            if (hasActivePokemon()) {
                return attachEnergy((EnergyCard)card, activePokemon);
            }
            return false;
        } else if (card instanceof TrainerCard) {
            TrainerCard trainer = (TrainerCard)card;
            if (trainer.getTrainerType().equals("Supporter") && !canPlaySupporter()) {
                return false;
            }
            if (trainer.getTrainerType().equals("Stadium") && !canPlayStadium()) {
                return false;
            }
            applyTrainerEffect(trainer);
            if (trainer.getTrainerType().equals("Supporter")) {
                hasPlayedSupporter = true;
            } else if (trainer.getTrainerType().equals("Stadium")) {
                hasPlayedStadium = true;
            }
        }
        
        return true;
    }

    public void applyTrainerEffect(TrainerCard trainer) {
        if (trainer == null) return;
        
        switch (trainer.getEffect()) {
            case "Heal 30 damage":
                if (activePokemon != null) {
                    activePokemon.heal(30);
                }
                break;
            // Add more trainer card effects here
        }
    }

    public boolean hasWon() {
        return prizeCards <= 0;
    }

    public void takePrizeCard() {
        if (prizeCards > 0 && !deck.isEmpty()) {
            prizeCards--;
            drawCard();
        }
    }

    public boolean canPlaySupporter() {
        return !hasPlayedSupporter;
    }

    public boolean canPlayStadium() {
        return !hasPlayedStadium;
    }

    public void resetTurn() {
        hasPlayedSupporter = false;
        hasPlayedStadium = false;
    }

    public boolean isBenchFull() {
        return bench.size() >= 5;
    }

    public boolean hasActivePokemon() {
        return activePokemon != null;
    }

    public boolean hasPlayedSupporter() {
        return hasPlayedSupporter;
    }

    public boolean hasPlayedStadium() {
        return hasPlayedStadium;
    }

    public Map<String, Integer> getEnergyInPlay() {
        return energyInPlay;
    }

    public void setActivePokemon(PokemonCard pokemon) {
        activePokemon = pokemon;
    }

    public void setHasPlayedSupporter(boolean played) {
        hasPlayedSupporter = played;
    }

    public void setHasPlayedStadium(boolean played) {
        hasPlayedStadium = played;
    }

    public ArrayList<Card> getDeck() {
        return new ArrayList<>(deck);
    }

    public void setPrizeCards(int count) {
        if (count >= 0) {
            prizeCards = count;
        }
    }

    public boolean switchPokemon(PokemonCard newActive) {
        if (newActive == null || !bench.contains(newActive)) {
            return false;
        }
        
        PokemonCard oldActive = activePokemon;
        if (oldActive != null) {
            oldActive.setActive(false);
            bench.add(oldActive);
        }
        
        activePokemon = newActive;
        bench.remove(newActive);
        newActive.setActive(true);
        newActive.setBenched(false);
        
        return true;
    }

    public boolean retreatActivePokemon() {
        if (activePokemon == null || bench.isEmpty()) {
            return false;
        }
        
        // Check if we have enough energy to retreat
        int retreatCost = activePokemon.getRetreatCost();
        if (retreatCost > 0) {
            Map<String, Integer> attachedEnergy = activePokemon.getEnergyAttached();
            int totalEnergy = 0;
            for (int amount : attachedEnergy.values()) {
                totalEnergy += amount;
            }
            if (totalEnergy < retreatCost) {
                return false;
            }
            
            // Detach required energy
            int remainingCost = retreatCost;
            for (Map.Entry<String, Integer> entry : attachedEnergy.entrySet()) {
                String type = entry.getKey();
                int amount = entry.getValue();
                int toDetach = Math.min(amount, remainingCost);
                detachEnergy(type, activePokemon, toDetach);
                remainingCost -= toDetach;
                if (remainingCost == 0) {
                    break;
                }
            }
        }
        
        // Switch with first benched Pokemon
        PokemonCard newActive = bench.get(0);
        return switchPokemon(newActive);
    }

    public boolean canRetreat() {
        if (activePokemon == null || bench.isEmpty()) {
            return false;
        }
        
        int retreatCost = activePokemon.getRetreatCost();
        if (retreatCost == 0) {
            return true;
        }
        
        Map<String, Integer> attachedEnergy = activePokemon.getEnergyAttached();
        int totalEnergy = 0;
        for (int amount : attachedEnergy.values()) {
            totalEnergy += amount;
        }
        
        return totalEnergy >= retreatCost;
    }
} 