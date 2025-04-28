public class EnergyCard extends Card {
    private String energyType;
    private boolean isSpecial;

    public EnergyCard(String name, String description, String imagePath, String energyType) {
        super(name, description, imagePath);
        this.energyType = energyType;
        this.isSpecial = false;  // Basic energy cards are not special
    }

    public String getEnergyType() {
        return energyType;
    }

    public boolean isSpecial() {
        return isSpecial;
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

    @Override
    public String toString() {
        return getName() + " (" + energyType + " Energy)";
    }
} 