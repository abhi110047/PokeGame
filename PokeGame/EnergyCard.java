import mayflower.*;
import java.util.*;

public class EnergyCard extends Card {
    private String energyType;
    private boolean isSpecial;
    private String specialEffect;
    
    public EnergyCard(String name, String description, String energyType) {
        super(name, description);
        this.energyType = energyType;
        this.isSpecial = false;
        this.specialEffect = null;
        setImage("img/cards/energy/" + energyType.toLowerCase() + ".png");
    }
    
    public EnergyCard(String name, String description, String energyType, String specialEffect) {
        super(name, description);
        this.energyType = energyType;
        this.isSpecial = true;
        this.specialEffect = specialEffect;
        setImage("img/cards/energy/special_" + energyType.toLowerCase() + ".png");
    }
    
    public String getEnergyType() {
        return energyType;
    }
    
    public boolean isSpecial() {
        return isSpecial;
    }
    
    public String getSpecialEffect() {
        return specialEffect;
    }
}