import mayflower.*;

public class PokemonTCG extends Mayflower {
    public PokemonTCG() {
        super("Pokemon TCG", 800, 600);
    }

    @Override
    public void init() {
        setWorld(new PokemonTCGWorld("Player 1", "Player 2"));
    }

    public static void main(String[] args) {
        new PokemonTCG();
    }
} 