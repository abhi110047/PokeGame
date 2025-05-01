import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Class representing a computer-controlled player
 */
public class ComputerPlayer extends Player {
    private Random random;

    public ComputerPlayer(String name) {
        super(name);
        this.random = new Random();
    }

    public void takeTurn(Player opponent) {
        System.out.println("\n=== " + getName() + "'s Turn ===");

        // Add a card to hand (simulating draw)
        addCardToHand(new EnergyCard("Basic", "Colorless", 1));

        // Play a Pokémon if no active Pokémon
        if (!hasActivePokemon()) {
            playPokemonFromHand();
        }

        // Play a Pokémon to bench (50% chance)
        if (random.nextBoolean() && getBenchPokemon().size() < 5) {
            playPokemonFromHand();
        }

        // Attach energy if possible
        attachEnergyFromHand();

        // Attack if possible
        if (canAttack()) {
            int attackChoice = 1;
            if (getActivePokemon().get(0).getAttack2() != null && random.nextBoolean()) {
                attackChoice = 2;
            }

            System.out.println(getName() + " chooses to attack!");
            performAttack(opponent, attackChoice);
        } else {
            System.out.println(getName() + " ends turn without attacking.");
        }
    }

    private void playPokemonFromHand() {
        List<PokemonCard> pokemonInHand = new ArrayList<>();

        for (Card card : getHand()) {
            if (card instanceof PokemonCard) {
                pokemonInHand.add((PokemonCard) card);
            }
        }

        if (pokemonInHand.isEmpty()) {
            return;
        }

        // Choose a random Pokémon to play
        PokemonCard selected = pokemonInHand.get(random.nextInt(pokemonInHand.size()));

        if (!hasActivePokemon()) {
            setActivePokemon(selected);
            System.out.println(getName() + " plays " + selected.getName() + " as active Pokémon!");
        } else if (getBenchPokemon().size() < 5) {
            addPokemonToBench(selected);
            System.out.println(getName() + " plays " + selected.getName() + " to the bench!");
        }
    }

    private void attachEnergyFromHand() {
        if (!hasActivePokemon()) {
            return;
        }

        List<EnergyCard> energyInHand = new ArrayList<>();
        Map<String, Integer> energyCountMap = new HashMap<>();

        for (Card card : getHand()) {
            if (card instanceof EnergyCard) {
                EnergyCard energyCard = (EnergyCard) card;
                energyInHand.add(energyCard);

                // Update energy count in the map
                String type = energyCard.getType();
                energyCountMap.put(type, energyCountMap.getOrDefault(type, 0) + 1);
            }
        }

        // Debug: print energy counts
        if (!energyCountMap.isEmpty()) {
            System.out.println(getName() + "'s energy types in hand: " + energyCountMap);
        }

        if (energyInHand.isEmpty()) {
            return;
        }

        // Choose a random energy to attach
        EnergyCard selected = energyInHand.get(random.nextInt(energyInHand.size()));
        attachEnergy(selected);
        System.out.println(getName() + " attaches " + selected.getName() + " energy to " + getActivePokemon().get(0).getName() + "!");
    }
}
