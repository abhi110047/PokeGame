import java.util.*;

public class Attack {
    private String name;
    private int damage;
    private Map<String, Integer> energyCost;
    private String effect;

    public Attack(String name, int damage, Map<String, Integer> energyCost, String effect) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Attack name cannot be null or empty");
        }
        if (damage < 0) {
            throw new IllegalArgumentException("Attack damage cannot be negative");
        }
        if (energyCost == null) {
            throw new IllegalArgumentException("Energy cost map cannot be null");
        }
        
        this.name = name;
        this.damage = damage;
        this.energyCost = energyCost;
        this.effect = effect != null ? effect : "";
    }

    public String getName() {
        return name;
    }

    public int getDamage() {
        return damage;
    }

    public Map<String, Integer> getEnergyCost() {
        return new HashMap<>(energyCost);
    }

    public String getEffect() {
        return effect;
    }

    public boolean canUse(Map<String, Integer> attachedEnergy) {
        if (attachedEnergy == null || attachedEnergy.isEmpty()) {
            return energyCost.isEmpty();
        }

        // Check if there's enough of each required energy type
        for (Map.Entry<String, Integer> cost : energyCost.entrySet()) {
            String requiredType = cost.getKey();
            int requiredAmount = cost.getValue();
            
            if (requiredType == null || requiredAmount <= 0) {
                continue;
            }
            
            // If it's colorless energy, any type can be used
            if (requiredType.equals("Colorless")) {
                int totalAvailable = 0;
                for (int amount : attachedEnergy.values()) {
                    totalAvailable += amount;
                }
                if (totalAvailable < requiredAmount) {
                    return false;
                }
            } else {
                // For specific energy types, check if enough of that type is available
                int available = attachedEnergy.getOrDefault(requiredType, 0);
                if (available < requiredAmount) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name).append(" - ").append(damage).append(" damage\n");
        sb.append("Energy Cost: ");
        
        if (energyCost.isEmpty()) {
            sb.append("None");
        } else {
            for (Map.Entry<String, Integer> cost : energyCost.entrySet()) {
                sb.append(cost.getValue()).append(" ").append(cost.getKey()).append(", ");
            }
            // Remove the trailing comma and space
            sb.setLength(sb.length() - 2);
        }
        
        if (effect != null && !effect.isEmpty()) {
            sb.append("\nEffect: ").append(effect);
        }
        
        return sb.toString();
    }
} 