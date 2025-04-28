public abstract class Card {
    protected String name;
    protected String description;
    protected String imagePath;
    protected boolean isPlayed;
    protected boolean isFaceUp;

    public Card(String name, String description) {
        this(name, description, "");
    }

    public Card(String name, String description, String imagePath) {
        this.name = name;
        this.description = description;
        this.imagePath = imagePath;
        this.isPlayed = false;
        this.isFaceUp = false;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getImagePath() {
        return imagePath;
    }

    public boolean isPlayed() {
        return isPlayed;
    }

    public void setPlayed(boolean played) {
        isPlayed = played;
    }

    public boolean isFaceUp() {
        return isFaceUp;
    }

    public void setFaceUp(boolean faceUp) {
        isFaceUp = faceUp;
    }

    public abstract void play();
    public abstract void discard();
} 