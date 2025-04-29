import java.util.ArrayList;
import java.util.List;

/**
 * Class representing a player in the game
 */
public class Player {
    private String name;
    private List<Card> hand;
    private List<PokemonCard> activePokemon;
    private List<PokemonCard> benchPokemon;
    private List<EnergyCard> attachedEnergy;
    
    public Player(String name) {
        this.name = name;
        this.hand = new ArrayList<>();
        this.activePokemon = new ArrayList<>();
        this.benchPokemon = new ArrayList<>();
        this.attachedEnergy = new ArrayList<>();
    }
    
    public String getName() {
        return name;
    }
    
    public List<Card> getHand() {
        return hand;
    }
    
    public void addCardToHand(Card card) {
        hand.add(card);
    }
    
    public List<PokemonCard> getActivePokemon() {
        return activePokemon;
    }
    
    public void setActivePokemon(PokemonCard pokemon) {
        if (activePokemon.isEmpty()) {
            activePokemon.add(pokemon);
        } else {
            activePokemon.set(0, pokemon);
        }
        hand.remove(pokemon);
    }
    
    public List<PokemonCard> getBenchPokemon() {
        return benchPokemon;
    }
    
    public void addPokemonToBench(PokemonCard pokemon) {
        if (benchPokemon.size() < 5) {
            benchPokemon.add(pokemon);
            hand.remove(pokemon);
        }
    }
    
    public List<EnergyCard> getAttachedEnergy() {
        return attachedEnergy;
    }
    
    public void attachEnergy(EnergyCard energy) {
        attachedEnergy.add(energy);
        hand.remove(energy);
    }
    
    public int getTotalEnergy() {
        int total = 0;
        for (EnergyCard energy : attachedEnergy) {
            total += energy.getEnergyValue();
        }
        return total;
    }
    
    public boolean hasActivePokemon() {
        return !activePokemon.isEmpty() && !activePokemon.get(0).isFainted();
    }
    
    public boolean canAttack() {
        return hasActivePokemon() && getTotalEnergy() >= activePokemon.get(0).getEnergyRequired();
    }
    
    public void performAttack(Player opponent, int attackNumber) {
        if (!canAttack()) {
            return;
        }
        
        PokemonCard attacker = activePokemon.get(0);
        if (!opponent.hasActivePokemon()) {
            return;
        }
        
        PokemonCard defender = opponent.getActivePokemon().get(0);
        Attack attack = attackNumber == 1 ? attacker.getAttack1() : attacker.getAttack2();
        
        if (attack == null) {
            return;
        }
        
        int damage = attack.getDamage();
        // Apply weakness multiplier if applicable
        if (defender.getWeakness().equals(attacker.getType())) {
            damage *= 2;
        }
        
        defender.reduceHp(damage);
        System.out.println(attacker.getName() + " used " + attack.getName() + " for " + damage + " damage!");
        
        if (defender.isFainted()) {
            System.out.println(defender.getName() + " has fainted!");
            opponent.getActivePokemon().clear();
            
            // Promote a Pokémon from the bench if available
            if (!opponent.getBenchPokemon().isEmpty()) {
                PokemonCard promoted = opponent.getBenchPokemon().remove(0);
                opponent.getActivePokemon().add(promoted);
                System.out.println(opponent.getName() + " promoted " + promoted.getName() + " to active!");
            }
        }
    }
}