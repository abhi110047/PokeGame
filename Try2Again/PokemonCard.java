public class PokemonCard extends Card {
    private int hp;
    private Attack attack1;
    private Attack attack2;
    private int energyRequired;
    private String weakness;
    
    public PokemonCard(String name, String type, int hp, Attack attack1, Attack attack2, int energyRequired, String weakness) {
        super(name, type);
        this.hp = hp;
        this.attack1 = attack1;
        this.attack2 = attack2;
        this.energyRequired = energyRequired;
        this.weakness = weakness;
    }
    
    public int getHp() {
        return hp;
    }
    
    public Attack getAttack1() {
        return attack1;
    }
    
    public Attack getAttack2() {
        return attack2;
    }
    
    public int getEnergyRequired() {
        return energyRequired;
    }
    
    public String getWeakness() {
        return weakness;
    }
    
    public void reduceHp(int damage) {
        this.hp -= damage;
        if (this.hp < 0) {
            this.hp = 0;
        }
    }
    
    public boolean isFainted() {
        return hp <= 0;
    }
    
    @Override
    public String toString() {
        return getName() + " (HP: " + hp + ")";
    }
    
    public String getDetails() {
        StringBuilder sb = new StringBuilder();
        sb.append("Name: ").append(getName()).append("\n");
        sb.append("Type: ").append(getType()).append("\n");
        sb.append("HP: ").append(hp).append("\n");
        sb.append("Energy Required: ").append(energyRequired).append("\n");
        sb.append("Weakness: ").append(weakness).append("\n");
        sb.append("Attack 1: ").append(attack1).append("\n");
        
        if (attack2 != null) {
            sb.append("Attack 2: ").append(attack2).append("\n");
        }
        
        return sb.toString();
    }
}