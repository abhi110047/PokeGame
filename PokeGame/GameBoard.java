import java.util.*;

public class GameBoard {
    private PokemonCard activePokemon;
    private List<PokemonCard> bench;
    private List<Card> prizeCards;
    private List<Card> discardPile;
    private Map<PokemonCard, List<EnergyCard>> attachedEnergy;
    private Deck playerDeck;
    
    public GameBoard() {
        this.bench = new ArrayList<>();
        this.prizeCards = new ArrayList<>();
        this.discardPile = new ArrayList<>();
        this.attachedEnergy = new HashMap<>();
    }
    
    public GameBoard(Deck deck) {
        this();
        this.playerDeck = deck;
    }
    
    public PokemonCard getActivePokemon() {
        return activePokemon;
    }
    
    public void setActivePokemon(PokemonCard pokemon) {
        this.activePokemon = pokemon;
    }
    
    public List<PokemonCard> getBench() {
        return new ArrayList<>(bench);
    }
    
    public void addToBench(PokemonCard pokemon) {
        if (bench.size() < 5 && pokemon != null) {
            bench.add(pokemon);
        }
    }
    
    public void removeFromBench(PokemonCard pokemon) {
        bench.remove(pokemon);
    }
    
    public List<Card> getPrizeCards() {
        return new ArrayList<>(prizeCards);
    }
    
    public void setPrizeCards(List<Card> prizeCards) {
        if (prizeCards != null) {
            this.prizeCards = new ArrayList<>(prizeCards);
        }
    }
    
    public int getRemainingPrizeCards() {
        return prizeCards.size();
    }
    
    public Card takePrizeCard() {
        if (!prizeCards.isEmpty()) {
            return prizeCards.remove(0);
        }
        return null;
    }
    
    public List<EnergyCard> getAttachedEnergy(PokemonCard pokemon) {
        return attachedEnergy.getOrDefault(pokemon, new ArrayList<>());
    }
    
    public void attachEnergy(PokemonCard pokemon, EnergyCard energy) {
        if (pokemon != null && energy != null) {
            List<EnergyCard> energyList = attachedEnergy.getOrDefault(pokemon, new ArrayList<>());
            energyList.add(energy);
            attachedEnergy.put(pokemon, energyList);
        }
    }
    
    public void discardCard(Card card) {
        if (card != null) {
            discardPile.add(card);
            if (card instanceof PokemonCard) {
                PokemonCard pokemon = (PokemonCard) card;
                attachedEnergy.remove(pokemon);
                if (pokemon == activePokemon) {
                    activePokemon = null;
                } else {
                    bench.remove(pokemon);
                }
            }
        }
    }
    
    public boolean canRetreat(PokemonCard pokemon) {
        if (pokemon == null) return false;
        List<EnergyCard> energy = getAttachedEnergy(pokemon);
        return energy.size() >= pokemon.getRetreatCost();
    }
    
    public void retreatPokemon(PokemonCard pokemon) {
        if (canRetreat(pokemon)) {
            List<EnergyCard> energy = getAttachedEnergy(pokemon);
            for (int i = 0; i < pokemon.getRetreatCost(); i++) {
                if (!energy.isEmpty()) {
                    discardPile.add(energy.remove(0));
                }
            }
            attachedEnergy.put(pokemon, energy);
            if (pokemon == activePokemon) {
                activePokemon = null;
            }
        }
    }
    
    public List<Card> getDiscardPile() {
        return new ArrayList<>(discardPile);
    }
} 