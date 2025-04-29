public class Attack {
    private String name;
    private int damage;
    private String description;
    
    public Attack(String name, int damage, String description) {
        this.name = name;
        this.damage = damage;
        this.description = description;
    }
    
    public String getName() {
        return name;
    }
    
    public int getDamage() {
        return damage;
    }
    
    public String getDescription() {
        return description;
    }
    
    @Override
    public String toString() {
        return name + " (" + damage + " damage) - " + description;
    }
}