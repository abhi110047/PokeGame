import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PokemonTCGRunner {
    public static void main(String[] args) {
        // Create sample Pokemon cards with attacks
        PokemonCard pikachu = new PokemonCard("Pikachu", "Mouse Pokemon", "images/pikachu.png", 60, "Electric", "Basic", "", 1, "");
        Map<String, Integer> thunderboltCost = new HashMap<>();
        thunderboltCost.put("Electric", 2);
        pikachu.addAttack(new Attack("Thunderbolt", 50, thunderboltCost, "Flip a coin."));
        
        PokemonCard charizard = new PokemonCard("Charizard", "Flame Pokemon", "images/charizard.png", 120, "Fire", "Stage 2", "Charmeleon", 3, "");
        Map<String, Integer> fireBlastCost = new HashMap<>();
        fireBlastCost.put("Fire", 3);
        charizard.addAttack(new Attack("Fire Blast", 100, fireBlastCost, ""));
        
        PokemonCard blastoise = new PokemonCard("Blastoise", "Shellfish Pokemon", "images/blastoise.png", 100, "Water", "Stage 2", "Wartortle", 2, "");
        Map<String, Integer> hydroPumpCost = new HashMap<>();
        hydroPumpCost.put("Water", 2);
        blastoise.addAttack(new Attack("Hydro Pump", 80, hydroPumpCost, ""));

        // Create sample energy cards
        EnergyCard electricEnergy = new EnergyCard("Electric Energy", "Basic Electric Energy", "images/electric_energy.png", "Electric");
        EnergyCard fireEnergy = new EnergyCard("Fire Energy", "Basic Fire Energy", "images/fire_energy.png", "Fire");
        EnergyCard waterEnergy = new EnergyCard("Water Energy", "Basic Water Energy", "images/water_energy.png", "Water");

        // Create sample trainer cards
        TrainerCard potion = new TrainerCard("Potion", "Heal 30 damage", "Heal 30 damage", "Supporter", "images/potion.png");
        TrainerCard switchCard = new TrainerCard("Switch", "Switch your active Pokemon", "Switch your active Pokemon with one from your bench", "Item", "img/trainer/switch.png");
        TrainerCard professorOak = new TrainerCard("Professor Oak", "Draw 7 cards", "Discard your hand and draw 7 cards", "Supporter", "img/trainer/professoroak.png");

        // Start the game
        System.out.println("Starting Pokemon TCG Game...");
        new PokemonTCG();
    }
} 