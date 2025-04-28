public class TrainerCard extends Card {
    private String effect;
    private String trainerType; // Item, Supporter, Stadium, etc.
    private boolean isUsed;

    public TrainerCard(String name, String description, String imagePath, String trainerType, String effect) {
        super(name, description, imagePath);
        this.trainerType = trainerType;
        this.effect = effect;
        this.isUsed = false;
    }

    public String getEffect() {
        return effect;
    }

    public String getTrainerType() {
        return trainerType;
    }

    public boolean isUsed() {
        return isUsed;
    }

    public void setUsed(boolean used) {
        isUsed = used;
    }

    @Override
    public String toString() {
        return getName() + " (" + trainerType + " Card)\nEffect: " + effect;
    }

    @Override
    public void play() {
        // Trainer card effects will be implemented in the game logic
        // Different effects based on the trainer type and specific card
    }

    @Override
    public void discard() {
        // Handle trainer card discard effects
        isUsed = true;
    }
} 