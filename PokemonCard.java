import java.util.*;

public class PokemonCard extends Card {
    private int hp;
    private int currentHp;
    private String type;
    private String stage; // Basic, Stage 1, Stage 2
    private String evolvesFrom;
    private Map<String, Integer> energyAttached;
    private ArrayList<Attack> attacks;
    private boolean isActive;
    private boolean isEvolved;
    private boolean isAsleep;
    private boolean isParalyzed;
    private boolean isPoisoned;
    private boolean isBurned;
    private boolean isConfused;
    private int damage;
    private String imagePath;
    private int retreatCost;
    private String weakness;
    private String resistance;
    private String evolution; // Name of the Pokemon this evolves from, null if Basic
    private boolean isBenched;

    public PokemonCard(String name, String description, String imagePath, int hp, String type, String stage, String evolvesFrom, int retreatCost, String effect) {
        super(name, description, imagePath);
        this.hp = hp;
        this.currentHp = hp;
        this.type = type;
        this.stage = stage;
        this.evolvesFrom = evolvesFrom;
        this.retreatCost = retreatCost;
        this.attacks = new ArrayList<>();
        this.energyAttached = new HashMap<>();
        this.isActive = false;
        this.isBenched = false;
        this.damage = 0;
    }

    public void addAttack(Attack attack) {
        if (attack != null) {
            attacks.add(attack);
        }
    }

    public void attachEnergy(String energyType) {
        energyAttached.put(energyType, energyAttached.getOrDefault(energyType, 0) + 1);
    }

    public void detachEnergy(String energyType) {
        int current = energyAttached.getOrDefault(energyType, 0);
        if (current > 0) {
            energyAttached.put(energyType, current - 1);
        }
    }

    public boolean canUseAttack(Attack attack) {
        if (attack == null) {
            return false;
        }
        return attack.canUse(energyAttached);
    }

    public void takeDamage(int amount) {
        if (amount > 0) {
            damage += amount;
        }
    }

    public void heal(int amount) {
        if (amount > 0) {
            damage = Math.max(0, damage - amount);
        }
    }

    public void applyStatus(String status) {
        if (status == null || status.isEmpty()) {
            return;
        }
        switch (status.toLowerCase()) {
            case "asleep":
                isAsleep = true;
                break;
            case "paralyzed":
                isParalyzed = true;
                break;
            case "poisoned":
                isPoisoned = true;
                break;
            case "burned":
                isBurned = true;
                break;
            case "confused":
                isConfused = true;
                break;
        }
    }

    public void removeStatus(String status) {
        if (status == null || status.isEmpty()) {
            return;
        }
        switch (status.toLowerCase()) {
            case "asleep":
                isAsleep = false;
                break;
            case "paralyzed":
                isParalyzed = false;
                break;
            case "poisoned":
                isPoisoned = false;
                break;
            case "burned":
                isBurned = false;
                break;
            case "confused":
                isConfused = false;
                break;
        }
    }

    public boolean canEvolve(PokemonCard evolution) {
        if (evolution == null) {
            return false;
        }
        // Check if this Pokemon can evolve into the target
        if (!evolution.getEvolvesFrom().equals(this.name)) {
            return false;
        }
        // Check if the evolution is valid for the current stage
        if (stage.equals("Basic") && !evolution.getStage().equals("Stage 1")) {
            return false;
        }
        if (stage.equals("Stage 1") && !evolution.getStage().equals("Stage 2")) {
            return false;
        }
        return true;
    }

    public void evolve(PokemonCard evolution) {
        if (!canEvolve(evolution)) {
            return;
        }
        
        // Preserve current state
        int previousHp = currentHp;
        Map<String, Integer> previousEnergy = new HashMap<>(energyAttached);
        ArrayList<Attack> previousAttacks = new ArrayList<>(attacks);
        
        // Update properties
        this.name = evolution.getName();
        this.hp = evolution.getHP();
        this.currentHp = Math.min(previousHp, evolution.getHP()); // Don't exceed new max HP
        this.type = evolution.getType();
        this.stage = evolution.getStage();
        this.attacks = new ArrayList<>(evolution.getAttacks());
        this.isEvolved = true;
        this.retreatCost = evolution.getRetreatCost();
        this.weakness = evolution.getWeakness();
        this.resistance = evolution.getResistance();
        
        // Restore energy and attacks if they're compatible
        for (Map.Entry<String, Integer> entry : previousEnergy.entrySet()) {
            String type = entry.getKey();
            int amount = entry.getValue();
            for (int i = 0; i < amount; i++) {
                attachEnergy(type);
            }
        }
        
        // Add back compatible attacks
        for (Attack attack : previousAttacks) {
            if (!attacks.contains(attack)) {
                attacks.add(attack);
            }
        }
    }

    public boolean isFullyEvolved() {
        return stage.equals("Stage 2");
    }

    public String getNextEvolutionStage() {
        switch (stage) {
            case "Basic":
                return "Stage 1";
            case "Stage 1":
                return "Stage 2";
            default:
                return null;
        }
    }

    @Override
    public void play() {
        isPlayed = true;
        isFaceUp = true;
    }

    @Override
    public void discard() {
        // Handle discard effects
    }

    // Getters
    public int getHP() { return hp; }
    public int getCurrentHP() { return currentHp; }
    public String getType() { return type; }
    public String getStage() { return stage; }
    public String getEvolvesFrom() { return evolvesFrom; }
    public Map<String, Integer> getEnergyAttached() { return new HashMap<>(energyAttached); }
    public ArrayList<Attack> getAttacks() { return attacks; }
    public boolean isActive() { return isActive; }
    public boolean isEvolved() { return isEvolved; }
    public boolean isAsleep() { return isAsleep; }
    public boolean isParalyzed() { return isParalyzed; }
    public boolean isPoisoned() { return isPoisoned; }
    public boolean isBurned() { return isBurned; }
    public boolean isConfused() { return isConfused; }
    public int getDamage() { return damage; }
    public String getImagePath() { return imagePath; }
    public int getRetreatCost() { return retreatCost; }
    public String getWeakness() { return weakness; }
    public String getResistance() { return resistance; }
    public String getEvolution() { return evolution; }
    public boolean isBenched() { return isBenched; }

    // Setters
    public void setActive(boolean active) {
        isActive = active;
        if (active) {
            isBenched = false;
        }
    }
    public void setRetreatCost(int cost) { this.retreatCost = cost; }
    public void setWeakness(String weakness) { this.weakness = weakness; }
    public void setResistance(String resistance) { this.resistance = resistance; }
    public void setBenched(boolean benched) {
        isBenched = benched;
        if (benched) {
            isActive = false;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getName()).append(" (").append(type).append(")\n");
        sb.append("HP: ").append(currentHp).append("/").append(hp).append("\n");
        sb.append("Stage: ").append(stage);
        if (!evolvesFrom.isEmpty()) {
            sb.append(" (Evolves from ").append(evolvesFrom).append(")");
        }
        sb.append("\n");
        
        // Show attached energy
        if (!energyAttached.isEmpty()) {
            sb.append("Attached Energy: ");
            for (Map.Entry<String, Integer> entry : energyAttached.entrySet()) {
                sb.append(entry.getValue()).append(" ").append(entry.getKey()).append(", ");
            }
            sb.setLength(sb.length() - 2); // Remove trailing comma and space
            sb.append("\n");
        }
        
        // Show status conditions
        ArrayList<String> statuses = new ArrayList<>();
        if (isAsleep) statuses.add("Asleep");
        if (isParalyzed) statuses.add("Paralyzed");
        if (isPoisoned) statuses.add("Poisoned");
        if (isBurned) statuses.add("Burned");
        if (isConfused) statuses.add("Confused");
        
        if (!statuses.isEmpty()) {
            sb.append("Status: ");
            sb.append(String.join(", ", statuses));
            sb.append("\n");
        }
        
        if (!attacks.isEmpty()) {
            sb.append("Attacks:\n");
            for (Attack attack : attacks) {
                sb.append("- ").append(attack.toString()).append("\n");
            }
        }
        
        if (retreatCost > 0) {
            sb.append("Retreat Cost: ").append(retreatCost).append("\n");
        }
        if (!weakness.isEmpty()) {
            sb.append("Weakness: ").append(weakness).append("\n");
        }
        if (!resistance.isEmpty()) {
            sb.append("Resistance: ").append(resistance).append("\n");
        }
        
        if (evolution != null) {
            sb.append("Evolves from: ").append(evolution).append("\n");
        }
        
        return sb.toString();
    }

    public boolean isFainted() {
        return damage >= hp;
    }
} 