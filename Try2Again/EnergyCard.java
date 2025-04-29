/**
 * Class representing an Energy card
 */
public class EnergyCard extends Card {
    private int energyValue;
    
    public EnergyCard(String name, String type, int energyValue) {
        super(name, type);
        this.energyValue = energyValue;
    }
    
    public int getEnergyValue() {
        return energyValue;
    }
    
    @Override
    public String toString() {
        return getName() + " Energy (Value: " + energyValue + ")";
    }
}